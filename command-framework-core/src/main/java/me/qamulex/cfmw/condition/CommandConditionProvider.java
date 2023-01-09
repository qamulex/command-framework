package me.qamulex.cfmw.condition;

import lombok.Getter;
import me.qamulex.cfmw.exception.CommandConditionInitializationException;

public abstract class CommandConditionProvider {

    @Getter private final String name;

    private final Class<?>[] requiredArgumentTypes;
    private final CommandCondition staticallyInitializedCondition;

    public CommandConditionProvider(String name, Class<?>... types) {
        this.name = name;
        requiredArgumentTypes = types;
        staticallyInitializedCondition = types.length == 0 ? get() : null;
    }

    public final boolean isStatic() {
        return requiredArgumentTypes.length == 0;
    }

    protected abstract CommandCondition get(Object... args);

    public final CommandCondition tryGet(Object... args) throws CommandConditionInitializationException {
        if (staticallyInitializedCondition != null)
            return staticallyInitializedCondition;

        if (args.length < requiredArgumentTypes.length)
            throw new CommandConditionInitializationException(String.format(
                    "condition requires at least %d arguments to initialize",

                    requiredArgumentTypes.length
            ));

        for (int i = 0; i < requiredArgumentTypes.length; i++)
            if (!requiredArgumentTypes[i].isInstance(args[i]))
                throw new CommandConditionInitializationException(String.format(
                        "argument #%d must be of type \"%s\", \"%s\" was given",

                        i + 1,
                        requiredArgumentTypes[i].getSimpleName(),
                        args[i].getClass().getSimpleName()
                ));

        return get(args);
    }

}
