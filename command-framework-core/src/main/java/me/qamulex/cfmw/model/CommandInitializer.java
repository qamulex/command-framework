package me.qamulex.cfmw.model;

import me.qamulex.cfmw.condition.CommandCondition;

import java.util.Collections;

public class CommandInitializer {

    private final Command command;

    private CommandInitializer(Command command) {
        this.command = command;
    }



    public CommandInitializer requires(CommandCondition condition) {
        command.conditions.add(condition);
        return this;
    }

    private void finalizeInitialization() {
        command.conditions = Collections.unmodifiableList(command.conditions);
    }

    static void init(Command command) {
        CommandInitializer initializer = new CommandInitializer(command);
        command.init(initializer);
        initializer.finalizeInitialization();
    }

}
