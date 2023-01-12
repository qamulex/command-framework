package me.qamulex.cfmw.condition;

import lombok.Getter;
import lombok.NonNull;
import me.qamulex.cfmw.exception.CommandConditionInitializationException;

public abstract class CommandConditionProvider {

    @Getter private final String name;

    private final Class<?>[] argumentTypes;
    private final CommandCondition condition;

    public CommandConditionProvider(@NonNull String name, Class<?>... types) {
        this.name = name;
        argumentTypes = types;
        condition = types.length == 0 ? createCondition() : null;
    }

    public final boolean isStatic() {
        return condition != null;
    }

    protected abstract CommandCondition createCondition(Object... args);

    public final CommandCondition get(Object... args) {
        if (isStatic())
            return condition;

        if (args.length < argumentTypes.length)
            throw new CommandConditionInitializationException(String.format(
                    "condition requires at least %d arguments to initialize",

                    argumentTypes.length
            ));

        for (int i = 0; i < argumentTypes.length; i++)
            if (!argumentTypes[i].isInstance(args[i]))
                throw new CommandConditionInitializationException(String.format(
                        "argument #%d must be of type \"%s\", \"%s\" was given",

                        i + 1,
                        argumentTypes[i].getSimpleName(),
                        args[i].getClass().getSimpleName()
                ));

        return createCondition(args);
    }

}
