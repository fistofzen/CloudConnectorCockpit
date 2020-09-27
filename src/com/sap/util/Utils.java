package com.sap.util;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.sap.ui.Gui;

public class Utils {
    //Obtain the image URL
    public static Image createImage(String path, String description) {
        URL imageURL = Gui.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
	public static boolean stopService(Gui gui,final String serviceName) {
		return execCommand(gui,"C:\\SAP\\scc20\\SCCHost.exe", "start");
	}

	public static boolean checkIfServiceRunning() {
		Process process;
		try {
			process = Runtime.getRuntime().exec("sc query " + "\"SAP Cloud Connector\"");
			Scanner reader = new Scanner(process.getInputStream(), "UTF-8");
			while (reader.hasNextLine()) {
				if (reader.nextLine().contains("RUNNING")) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean startService(Gui gui,final String serviceName) {
		return execCommand(gui,"cmd.exe", "/c", "net", "start", "\"" + serviceName + "\"");
	 
	}

	public static boolean execCommand(Gui gui,final String... args) {

		String startCommandScript[] = { "cmd.exe", "/c", "sc", args[0],  "SAP Cloud Connector" };
		Process process;
		try {
			process = new ProcessBuilder(startCommandScript).start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			// System.out.printf("Output of running cmd /c dir is:");

			while ((line = br.readLine()) != null)
			    System.out.println("tasklist: " + line);
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//dirty solution
			while (true) {
				if(checkIfServiceRunning() && args[0].equals("Start")) {
					break;
				}if(!checkIfServiceRunning() && args[0].equals("Stop")) {
					break;
				}
			}
			if(gui.getPaneHeader() != null)
				gui.getPaneHeader().setStatusText(checkIfServiceRunning());
			
			br.close();
			isr.close();
			is.close();
			process.destroy();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}
}
