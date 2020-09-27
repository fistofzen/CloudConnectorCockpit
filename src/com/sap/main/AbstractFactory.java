package com.sap.main;
public abstract class AbstractFactory {
    public abstract Commands getCommand(CommandType name);
}