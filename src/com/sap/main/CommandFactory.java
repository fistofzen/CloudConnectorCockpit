package com.sap.main;

public class CommandFactory extends AbstractFactory {

	@Override
	public
	Commands getCommand(CommandType name) {
		if (CommandType.START_FACTORY == name) {
			return new StartCommand();
		} else if (CommandType.STOP_FACTORY == name) {
			return new StopCommand();
		}
		return null;
	}
}