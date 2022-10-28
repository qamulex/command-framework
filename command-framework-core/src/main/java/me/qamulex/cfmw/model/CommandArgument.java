package me.qamulex.cfmw.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.qamulex.cfmw.context.SuggestionContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter
public abstract class CommandArgument {

    private final String name;
    private CommandArgument parent = null;
    private final List<CommandArgument> successors = new ArrayList<>();

    public CommandArgument(@NonNull String name) {
        this.name = name;
    }

    public List<String> suggest(SuggestionContext ctx) {
        return Collections.emptyList();
    }

    public final CommandArgument getLastLocalSuccessor() {
        return successors.stream()
                .filter(CommandArgument::isCustom)
                .findAny()
                .map(CommandArgument::getLastLocalSuccessor)
                .orElse(this);
    }

    public final boolean isCustom() {
        return this instanceof CustomArgument;
    }

    public final boolean isLiteral() {
        return this instanceof LiteralArgument;
    }

    public final boolean isExecutable() {
        return this instanceof Executable;
    }

    public final CustomArgument asCustom() {
        return (CustomArgument) this;
    }

    public final LiteralArgument asLiteral() {
        return (LiteralArgument) this;
    }

    public final Executable asExecutable() {
        return (Executable) this;
    }



}
