package me.qamulex.cfmw.condition;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CommandConditionProviderRegistry {

    private final Map<String, CommandConditionProvider> conditionProviderMap = new HashMap<>();

    public void add(@NonNull CommandConditionProvider provider) {
        if (!conditionProviderMap.containsKey(provider.getName()))
            conditionProviderMap.put(provider.getName(), provider);
    }

    @SneakyThrows
    public void add(Class<? extends CommandConditionProvider> providerClass) {
        if (conditionProviderMap.values().stream().noneMatch(providerClass::isInstance))
            add(providerClass.getConstructor().newInstance());
    }

    @SneakyThrows
    public Optional<CommandConditionProvider> get(String conditionName) {
        return Optional.ofNullable(conditionProviderMap.get(conditionName));
    }

}
