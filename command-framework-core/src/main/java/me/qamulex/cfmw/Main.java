package me.qamulex.cfmw;

import me.qamulex.cfmw.condition.CommandCondition;
import me.qamulex.cfmw.condition.CommandConditionProvider;

public class Main {

    static class Test extends CommandConditionProvider {

        public Test(String name, Class<?>... types) {
            super(name, types);
        }

        @Override
        protected CommandCondition createCondition(Object... args) {
            return null;
        }

    }

    public static void main(String[] args) {

    }

}
