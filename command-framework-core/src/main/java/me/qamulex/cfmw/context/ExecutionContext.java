package me.qamulex.cfmw.context;

import me.qamulex.cfmw.CommandInvoker;
import me.qamulex.cfmw.CommandManager;
import me.qamulex.cfmw.PlainCommand;

public class ExecutionContext extends CommandContext {

    public ExecutionContext(CommandManager commandManager, CommandInvoker invoker, PlainCommand plainCommand) {
        super(commandManager, invoker, plainCommand);
    }

}
