package me.qamulex.cfmw.condition;

import lombok.NonNull;
import me.qamulex.cfmw.context.CommandContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public final class CommandConditionSet implements Iterable<CommandCondition> {

    private final List<CommandCondition> conditions = new ArrayList<>();

    public CommandConditionSet add(CommandCondition condition) {
        conditions.add(condition);
        return this;
    }

    public Optional<CommandCondition> test(@NonNull CommandContext ctx) {
        for (CommandCondition condition : conditions) {
            if (condition.test(ctx)) {
                condition.passed(ctx);
            } else {
                condition.failed(ctx);
                return Optional.of(condition);
            }
        }
        return Optional.empty();
    }

    @Override
    public Iterator<CommandCondition> iterator() {
        return conditions.iterator();
    }

}
