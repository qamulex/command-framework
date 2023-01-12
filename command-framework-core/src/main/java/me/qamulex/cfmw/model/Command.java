package me.qamulex.cfmw.model;

import lombok.Getter;
import lombok.SneakyThrows;
import me.qamulex.cfmw.condition.CommandCondition;
import me.qamulex.cfmw.condition.CommandConditionSet;
import me.qamulex.cfmw.context.CommandContext;
import me.qamulex.cfmw.context.SuggestionContext;
import me.qamulex.cfmw.exception.CommandRegistrationException;
import me.qamulex.cfmw.model.argument.Argument;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public abstract class Command extends CommandNode implements Executable {

    private final List<String> alternatives;
    private final CommandConditionSet conditions = new CommandConditionSet();

    public Command(String name, String... alternatives) {
        super(name);
        this.alternatives = Collections.unmodifiableList(new ArrayList<String>() {{
            add(name);
            addAll(Arrays.asList(alternatives));
        }});
    }

    public boolean hasName(String name) {
        return alternatives.contains(name);
    }

    @Override
    public void suggest(SuggestionContext ctx) {
        if (!ctx.getTypedString().isEmpty())
            ctx.addVariants(alternatives);
        else
            ctx.addVariants(getName());
    }

    // sub commands

    public List<Command> getRegisteredCommands() {
        return getLocal().getSuccessors().stream()
                .filter(CommandNode::isCommand)
                .map(CommandNode::asCommand)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public Command registerCommand(Class<? extends Command> commandClass) {
        CommandNode local = getLocal();
        if (local.getSuccessors().stream().anyMatch(commandClass::isInstance))
            throw new CommandRegistrationException("command with this type is already registered");
        Command command = commandClass.getConstructor().newInstance();
        if (local.getSuccessors().stream()
                .filter(CommandNode::isCommand)
                .map(CommandNode::asCommand)
                .map(Command::getAlternatives)
                .flatMap(Collection::stream)
                .anyMatch(command::hasName))
            throw new CommandRegistrationException("command with same name is already registered");
        command.setParent(this);
        local.getSuccessors().add(command);
        return this;
    }

    public Optional<Command> getCommand(Class<? extends Command> commandClass) {
        return getLocal().getSuccessors().stream()
                .filter(commandClass::isInstance)
                .map(CommandNode::asCommand)
                .findFirst();
    }

    public Optional<Command> getCommand(String name) {
        return getLocal().getSuccessors().stream()
                .filter(CommandNode::isCommand)
                .map(CommandNode::asCommand)
                .filter(command -> command.hasName(name))
                .findFirst();
    }

    public Command unregisterCommand(Command command) {
        CommandNode local = getLocal();
        if (local.getSuccessors().contains(command)) {
            command.setParent(null);
            local.getSuccessors().remove(command);
        }
        return this;
    }

    public Command unregisterCommand(Class<? extends Command> commandClass) {
        getCommand(commandClass).ifPresent(Command::unregister);
        return this;
    }

    public Command unregisterCommand(String name) {
        getCommand(name).ifPresent(Command::unregister);
        return this;
    }

    public void unregister() {
        if (getParent() != null && getParent().isCommand())
            getParent().asCommand().unregisterCommand(this);
    }

    // arguments

    private static void findArgumentsRecursively(CommandNode node, List<Argument> arguments) {
        if (node.isArgument())
            arguments.add(node.asArgument());
        node.getSuccessors().stream()
                .filter(CommandNode::isArgument)
                .findFirst()
                .ifPresent(argument -> findArgumentsRecursively(argument, arguments));
    }

    public List<Argument> getRegisteredArguments() {
        List<Argument> arguments = new ArrayList<>();
        findArgumentsRecursively(this, arguments);
        return arguments;
    }

    public Command registerArgument(Argument argument) {

        return this;
    }

    // conditions

    public void addCondition(CommandCondition condition) {
        conditions.add(condition);
    }

    public void addCondition(String conditionName, Object... args) {

    }

    public Optional<CommandCondition> testConditions(CommandContext ctx) {
        return conditions.test(ctx);
    }

}
