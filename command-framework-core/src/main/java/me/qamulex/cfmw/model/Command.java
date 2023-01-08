package me.qamulex.cfmw.model;

import lombok.Getter;
import lombok.SneakyThrows;
import me.qamulex.cfmw.CapturedArguments;
import me.qamulex.cfmw.CommandInvoker;
import me.qamulex.cfmw.context.ExecutionContext;
import me.qamulex.cfmw.context.SuggestionContext;
import me.qamulex.cfmw.exception.CommandRegistrationException;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public abstract class Command extends CommandNode {

    private final List<String> alternatives;
    private final boolean suggestAllAlternatives;

    public Command(String name, boolean suggestAllAlternatives, String... alternatives) {
        super(name);
        this.alternatives = Collections.unmodifiableList(new ArrayList<String>() {{
            add(name);
            addAll(Arrays.asList(alternatives));
        }});
        this.suggestAllAlternatives = suggestAllAlternatives;
    }

    public Command(String name, String... alternatives) {
        this(name, false, alternatives);
    }

    protected abstract void init(CommandInitializer init);

    public abstract void execute(ExecutionContext ctx, CommandInvoker invoker, CapturedArguments arguments);

    public boolean hasName(String name) {
        return alternatives.contains(name);
    }

    @Override
    public void suggest(SuggestionContext ctx) {
        if (suggestAllAlternatives || !ctx.getTypedString().isEmpty())
            ctx.addVariants(alternatives);
        else
            ctx.addVariants(getName());
    }

    public List<Command> getRegisteredCommands() {
        return getLocal().getSuccessors().stream()
                .map(CommandNode::asCommand)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public Command registerCommand(Class<? extends Command> commandClass) {
        CommandNode local = getLocal();
        if (local.getSuccessors().stream().anyMatch(commandClass::isInstance))
            throw new CommandRegistrationException("command of this type is already registered");
        Command command = commandClass.getConstructor().newInstance();
        if (local.getSuccessors().stream()
                .map(CommandNode::asCommand)
                .map(Command::getAlternatives)
                .flatMap(Collection::stream)
                .anyMatch(command::hasName))
            throw new CommandRegistrationException("command with same name is already registered");
        command.setParent(this);
        command.init(new CommandInitializer(command));
        local.getSuccessors().add(command);
        return this;
    }

    public Optional<Command> getCommand(Class<? extends Command> commandClass) {
        return getLocal().getSuccessors().stream()
                .map(CommandNode::asCommand)
                .filter(commandClass::isInstance)
                .findFirst();
    }

    public Optional<Command> getCommand(String name) {
        return getLocal().getSuccessors().stream()
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

}
