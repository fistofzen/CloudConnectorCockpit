package com.sap.main;

 
import com.sap.ui.Gui;
import com.sap.util.Utils;

public class StartCommand implements Commands{

	@Override
	public boolean executeCommand(Gui gui) {
		// TODO Auto-generated method stub
		return Utils.execCommand(gui,"Start");
	}

	@Override
	public String getLog() {
		// TODO Auto-generated method stub
		return null;
	}
 

}
