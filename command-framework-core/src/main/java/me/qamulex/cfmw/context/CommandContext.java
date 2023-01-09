package me.qamulex.cfmw.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.qamulex.cfmw.CapturedArguments;
import me.qamulex.cfmw.CommandInvoker;
import me.qamulex.cfmw.CommandManager;
import me.qamulex.cfmw.PassedCommand;
import me.qamulex.cfmw.model.CommandNode;

@RequiredArgsConstructor @Getter
public class CommandContext {

    private final CommandManager commandManager;
    private final CommandInvoker invoker;
    private final PassedCommand passedCommand;
    private final CapturedArguments capturedArguments = new CapturedArguments();

    private CommandNode currentNode;

}
