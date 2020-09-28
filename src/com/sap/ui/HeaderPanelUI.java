package com.sap.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sap.util.Utils;

public class HeaderPanelUI extends JPanel{

	/**
	 * Header panel for status message
	 */
	
	private static final long serialVersionUID = 1L;
	private JLabel labeStatus;
	JPanel paneHeader;
	JPanel paneInsideHeader;
	
	public HeaderPanelUI(boolean isRunning) {
		paneHeader = new JPanel();
		paneInsideHeader = new JPanel();
		
		//add the logos
		Image imageLogo = Utils.createImage("images/sap.png", "Logo");
		JLabel labeLogo = new JLabel(new ImageIcon(imageLogo));		
		paneInsideHeader.add(labeLogo);

		//add the status
		labeStatus = new JLabel("Cloud Connnector is " + ( isRunning == true ? "RUNNING" : "NOT RUNNING" ));
		labeStatus.setFont(new Font("Calibri", Font.BOLD, 26));
		labeStatus.setForeground(new Color(0x003049));
		paneInsideHeader.add(labeStatus);

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		paneHeader.setBackground(new Color(0xfcbf49));
		paneInsideHeader.setBackground(new Color(0xfcbf49));
		
		
		paneHeader.add(paneInsideHeader);
		
		this.setLayouts();
		this.add(paneHeader);
		 
	}
	public void setStatusText(boolean isRunning) {
		labeStatus.setText(("Cloud Connnector is " + ( isRunning == true ? "RUNNING" : "NOT RUNNING" )));
	}
	public void setLayouts() {
		this.setLayout(new GridLayout(1, 1));
		paneInsideHeader.setLayout(new FlowLayout(FlowLayout.CENTER));
		paneHeader.setLayout(new GridBagLayout());
		
	}
	
}
