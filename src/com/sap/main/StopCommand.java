package com.sap.main;

 
import com.sap.ui.Gui;
import com.sap.util.Utils;

public class StopCommand implements Commands{

	@Override
	public boolean executeCommand(Gui gui) {
		// TODO Auto-generated method stub
		return Utils.execCommand(gui,"Stop");
	}

	@Override
	public String getLog() {
		// TODO Auto-generated method stub
		return null;
	}

 
 

}
