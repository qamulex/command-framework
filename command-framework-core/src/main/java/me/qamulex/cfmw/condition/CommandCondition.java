package me.qamulex.cfmw.condition;

import me.qamulex.cfmw.context.CommandContext;

public interface CommandCondition {

    boolean test(CommandContext ctx);

    default void passed(CommandContext ctx) { }

    default void failed(CommandContext ctx) { }

}
