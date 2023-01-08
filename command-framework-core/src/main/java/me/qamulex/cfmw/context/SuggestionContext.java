package me.qamulex.cfmw.context;

import lombok.Getter;
import me.qamulex.cfmw.CommandInvoker;
import me.qamulex.cfmw.CommandManager;
import me.qamulex.cfmw.PassedCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class SuggestionContext extends CommandContext {

    private final String typedString;
    private final List<String> suggestedVariants = new ArrayList<>();

    public SuggestionContext(CommandManager commandManager, CommandInvoker invoker, PassedCommand passedCommand) {
        super(commandManager, invoker, passedCommand);
        typedString = passedCommand.getPiece(passedCommand.countPieces() - 1).toLowerCase();
    }

    public void addVariants(Stream<String> stream) {
        stream.forEach(suggestedVariants::add);
    }

    public void addVariants(Collection<String> collection) {
        suggestedVariants.addAll(collection);
    }

    public void addVariants(String... variants) {
        suggestedVariants.addAll(Arrays.asList(variants));
    }

}
