package me.qamulex.cfmw.context;

import lombok.Getter;
import me.qamulex.cfmw.CommandInvoker;
import me.qamulex.cfmw.CommandManager;
import me.qamulex.cfmw.PlainCommand;

@Getter
public class SuggestionContext extends CommandContext {

    private final String typedString;

    public SuggestionContext(CommandManager commandManager, CommandInvoker invoker, PlainCommand plainCommand) {
        super(commandManager, invoker, plainCommand);
        typedString = plainCommand.getPiece(plainCommand.countPieces() - 1).toLowerCase();
    }

}
