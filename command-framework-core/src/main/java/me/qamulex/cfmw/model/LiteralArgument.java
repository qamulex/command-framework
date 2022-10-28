package me.qamulex.cfmw.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import me.qamulex.cfmw.context.SuggestionContext;
import me.qamulex.cfmw.exception.LiteralArgumentDefinitionException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter @Setter
public class LiteralArgument extends CommandArgument {

    private final List<String> definitions;
    private boolean offerShortDefinitions = false;

    @SneakyThrows
    public LiteralArgument(@NonNull String name, String... definitions) {
        super(name);
        if (definitions.length == 0)
            throw new LiteralArgumentDefinitionException("at least one definition must be present");
        this.definitions = Collections.unmodifiableList(Arrays.asList(definitions));
    }

    public String getPrimaryDefinition() {
        return definitions.get(0);
    }

    public List<String> getDefinitions(boolean includingShortDefinitions) {
        return includingShortDefinitions ? definitions : Collections.singletonList(getPrimaryDefinition());
    }

    public boolean canBeDefinedAs(String definition) {
        return definitions.contains(definition);
    }

    @Override
    public List<String> suggest(SuggestionContext ctx) {
        return getDefinitions(offerShortDefinitions || !ctx.getTypedString().isEmpty());
    }

}
