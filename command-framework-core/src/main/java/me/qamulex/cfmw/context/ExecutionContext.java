package me.qamulex.cfmw.context;

import me.qamulex.cfmw.CommandInvoker;
import me.qamulex.cfmw.CommandManager;
import me.qamulex.cfmw.PassedCommand;

public class ExecutionContext extends CommandContext {

    public ExecutionContext(CommandManager commandManager, CommandInvoker invoker, PassedCommand passedCommand) {
        super(commandManager, invoker, passedCommand);
    }

}
