package me.qamulex.cfmw.condition;

import me.qamulex.cfmw.context.CommandContext;
import me.qamulex.cfmw.model.CommandNode;

public abstract class CommandCondition {

    private final String name;

    public CommandCondition(String name) {
        this.name = name;
    }

    public abstract boolean test(CommandContext ctx, CommandNode arg);

}
