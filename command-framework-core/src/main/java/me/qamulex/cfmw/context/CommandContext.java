package me.qamulex.cfmw.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.qamulex.cfmw.CapturedArguments;
import me.qamulex.cfmw.CommandInvoker;
import me.qamulex.cfmw.CommandManager;
import me.qamulex.cfmw.PlainCommand;

@RequiredArgsConstructor @Getter
public class CommandContext {

    private final CommandManager commandManager;
    private final CommandInvoker invoker;
    private final PlainCommand plainCommand;
    private final CapturedArguments capturedArguments = new CapturedArguments();

}