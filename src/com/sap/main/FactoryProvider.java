package com.sap.main;

/**
 * Factory provider
 */
public class FactoryProvider {
    public static AbstractFactory getFactory(CommandType commandType) {
        if (CommandType.ALL == commandType) {
            return new CommandFactory();
        }
        return null;
    }
}