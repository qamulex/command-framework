package me.qamulex.cfmw.condition;

import lombok.SneakyThrows;
import me.qamulex.cfmw.exception.CommandConditionInitializationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CommandConditionRegistry {

    private final Map<String, CommandConditionProvider> conditionProviderMap = new HashMap<>();

    public void register(CommandConditionProvider provider) {
        if (!conditionProviderMap.containsKey(provider.getName()))
            conditionProviderMap.put(provider.getName(), provider);
    }

    public List<CommandConditionProvider> getRegistry() {
        return new ArrayList<>(conditionProviderMap.values());
    }

    @SneakyThrows
    public CommandCondition get(String conditionName, Object... args) {
        if (conditionProviderMap.containsKey(conditionName))
            return conditionProviderMap.get(conditionName).tryGet(args);
        throw new CommandConditionInitializationException(String.format(
                "condition not found: %s",

                conditionName
        ));
    }

}
