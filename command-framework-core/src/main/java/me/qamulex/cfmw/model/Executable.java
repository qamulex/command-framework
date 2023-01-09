package me.qamulex.cfmw.model;

import me.qamulex.cfmw.CapturedArguments;
import me.qamulex.cfmw.CommandInvoker;
import me.qamulex.cfmw.context.ExecutionContext;

@FunctionalInterface
public interface Executable {

    void execute(ExecutionContext ctx, CommandInvoker invoker, CapturedArguments args);

}
