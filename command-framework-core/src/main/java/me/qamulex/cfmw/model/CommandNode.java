package me.qamulex.cfmw.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.qamulex.cfmw.context.SuggestionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public abstract class CommandNode {

    private final String name;
    private CommandNode parent = null;
    private final List<CommandNode> successors = new ArrayList<>();

    public CommandNode(@NonNull String name) {
        this.name = name;
    }

    public void suggest(SuggestionContext ctx) { }

    public final Optional<CommandNode> getSuccessor(String name) {
        return getSuccessors().stream()
                .filter(node -> node.getName().equals(name))
                .findFirst();
    }

    public final CommandNode getLocal() {
        return getSuccessors().stream()
                .filter(CommandNode::isArgument)
                .findAny()
                .map(CommandNode::getLocal)
                .orElse(this);
    }

    public final boolean isArgument() {
        return this instanceof Argument;
    }

    public final boolean isCommand() {
        return this instanceof Command;
    }

    public final Argument asArgument() {
        return (Argument) this;
    }

    public final Command asCommand() {
        return (Command) this;
    }

}
