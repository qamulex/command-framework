package me.qamulex.cfmw.model;

import lombok.Getter;
import lombok.NonNull;
import me.qamulex.cfmw.context.SuggestionContext;

import java.util.List;
import java.util.function.Function;

@Getter
public abstract class CustomArgument extends CommandArgument {

    private final Function<String, Object> parser;
    private final boolean capturesRemaining;
    private final boolean optional;

    public CustomArgument(@NonNull String name, Function<String, Object> parser, boolean capturesRemaining, boolean optional) {
        super(name);
        this.parser = parser;
        this.capturesRemaining = capturesRemaining;
        this.optional = optional;
    }

    @Override
    public abstract List<String> suggest(SuggestionContext ctx);

}
