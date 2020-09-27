package com.sap.main;

 
import com.sap.ui.Gui;

public interface Commands {
	boolean executeCommand(Gui gui);
	String getLog();
}
