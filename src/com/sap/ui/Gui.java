package com.sap.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import com.sap.main.AbstractFactory;
import com.sap.main.CommandType;
import com.sap.main.FactoryProvider;
import com.sap.util.Utils;

public class Gui implements ActionListener {
	
    private static String labelPrefix = "";
    private JButton startButton;
    private JButton stopButton;
    private JButton logButton;
    private AbstractFactory factory;
    private HeaderPanelUI paneHeader;
    private MenuItem startMenuItem;
    private MenuItem stopMenuItem;
    private MenuItem browserMenuItem;


	final JLabel label = new JLabel(labelPrefix + "0    ");
    // Specify the look and feel to use by defining the LOOKANDFEEL constant
    // Valid values are: null (use the default), "Metal", "System", "Motif",
    // and "GTK"
    final static String LOOKANDFEEL = "Metal";
    
    // If you choose the Metal L&F, you can also choose a theme.
    // Specify the theme to use by defining the THEME constant
    // Valid values are: "DefaultMetal", "Ocean",  and "Test"
    final static String THEME = "Test";
    
    
    public Gui() {
    	factory = FactoryProvider.getFactory(CommandType.ALL);
    }

    public Component createComponents() {
    	logButton = new JButton();
    	JPanel pane = new JPanel(new GridLayout(0, 1));
    	paneHeader = new HeaderPanelUI(Utils.checkIfServiceRunning());
    	
    	
    	GridLayout layoGrid = new GridLayout(0, 3);
    	layoGrid.setHgap(20);
    	layoGrid.setVgap(20);
    	JPanel paneActions = new JPanel(layoGrid);
    	
    	
        
        logButton.setMnemonic(KeyEvent.VK_I);
        logButton.addActionListener(this);
        logButton.setIcon( new ImageIcon(Utils.createImage("images/log-format.png", "log_icon")));
        label.setLabelFor(logButton);

        
        startButton = new JButton();
        startButton.setFont(new Font("Calibri", Font.PLAIN, 14));
        startButton.setBackground(new Color(0xeae2b7));
        startButton.setIcon( new ImageIcon(Utils.createImage("images/play.png", "start_icon")));
        startButton.setForeground(Color.white);
        startButton.addActionListener(this);
        // customize the button new SAP_button look&feel
        startButton.setUI(new StartButtonUI());

        stopButton = new JButton();
        stopButton.setFont(new Font("Calibri", Font.PLAIN, 14));
        stopButton.setIcon( new ImageIcon(Utils.createImage("images/stop.png", "stop_icon")));
        stopButton.setBackground(new Color(0x8ecae6));
        stopButton.setForeground(Color.white);
        stopButton.addActionListener(this);
        // customize the button new SAP_button look&feel
        stopButton.setUI(new StartButtonUI());
       
        
        
        paneActions.add(startButton);
        paneActions.add(stopButton);
        paneActions.add(logButton);
        
        
        pane.add(paneHeader);
        pane.add(paneActions);
        //pane.add(paneMessage);
        
        paneActions.setBorder(BorderFactory.createEmptyBorder(
						                30, //top
						                30, //left
						                30, //bottom
						                30) //right
						                );
        
        //pane.add(label);
        pane.setBorder(BorderFactory.createEmptyBorder(
                                        0, //top
                                        0, //left
                                        0, //bottom
                                        0) //right
                                        );

        return pane;
    }

    
    private  void createAndShowGUI() {
    	
        //Set the look and feel.
        initLookAndFeel();

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("SAP Cloud Connector UI");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    frame.setDefaultCloseOperation(
                            JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(false);
                    frame.dispose();
            }
        });
        
        
        Gui app = new Gui();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);

        //Display the window.
        frame.setSize(500, 300);
        frame.setVisible(true);
        
        
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(Utils.createImage("images/network.png", "tray icon"));
        
        frame.setIconImage(Utils.createImage("images/network.png", "icon"));
        final SystemTray tray = SystemTray.getSystemTray();
        
        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        startMenuItem = new MenuItem("Start Connector");
        stopMenuItem = new MenuItem("Stop Connector");
        browserMenuItem = new MenuItem("Open Admin Browser");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");
         
        //Add components to popup menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(startMenuItem);
        popup.add(stopMenuItem);
        popup.addSeparator();
        popup.add(browserMenuItem);
        popup.addSeparator();
        popup.add(displayMenu);
        displayMenu.add(errorItem);
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        
        
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);

            }
        });
        
        
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "SAP Cloud Connector is a tool that uses standart scc exe file for start and stop operations. Dev fistofzen . ");
            }
            
        });
        
        startMenuItem.addActionListener(this);
        stopMenuItem.addActionListener(this);
        
        
        
//        ActionListener listener = new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                MenuItem item = (MenuItem)e.getSource();
//                //TrayIcon.MessageType type = null;
//                System.out.println(item.getLabel());
//                if ("Error".equals(item.getLabel())) {
//                    //type = TrayIcon.MessageType.ERROR;
//                    trayIcon.displayMessage("Sun TrayIcon Demo",
//                            "This is an error message", TrayIcon.MessageType.ERROR);
//                    
//                } else if ("Warning".equals(item.getLabel())) {
//                    //type = TrayIcon.MessageType.WARNING;
//                    trayIcon.displayMessage("Sun TrayIcon Demo",
//                            "This is a warning message", TrayIcon.MessageType.WARNING);
//                    
//                } else if ("Info".equals(item.getLabel())) {
//                    //type = TrayIcon.MessageType.INFO;
//                    trayIcon.displayMessage("Sun TrayIcon Demo",
//                            "This is an info message", TrayIcon.MessageType.INFO);
//                    
//                } else if ("None".equals(item.getLabel())) {
//                    //type = TrayIcon.MessageType.NONE;
//                    trayIcon.displayMessage("Sun TrayIcon Demo",
//                            "This is an ordinary message", TrayIcon.MessageType.NONE);
//                }
//            }
//        };
        
//        errorItem.addActionListener(listener);
//        warningItem.addActionListener(listener);
//        infoItem.addActionListener(listener);
//        noneItem.addActionListener(listener);
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
    

    
    
    public void actionPerformed(ActionEvent e) {
    	boolean status = false;
        if (e.getSource() == this.startButton || e.getSource() == this.startMenuItem ) {
        	status = factory.getCommand(CommandType.START_FACTORY).executeCommand(this);
		}else if(e.getSource() == this.stopButton || e.getSource() == this.stopMenuItem) {
        	status = factory.getCommand(CommandType.STOP_FACTORY).executeCommand(this);
		}else if(e.getSource() == this.logButton) {
			
		}
        System.out.println(status);

    }

    private static void initLookAndFeel() {
        String lookAndFeel = null;
       
        if (LOOKANDFEEL != null) {
            if (LOOKANDFEEL.equals("Metal")) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
              //  an alternative way to set the Metal L&F is to replace the 
              // previous line with:
              // lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
                
            }
            
            else if (LOOKANDFEEL.equals("System")) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            } 
            
            else if (LOOKANDFEEL.equals("Motif")) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } 
            
            else if (LOOKANDFEEL.equals("GTK")) { 
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            } 
            
            else {
                System.err.println("Unexpected value of LOOKANDFEEL specified: "
                                   + LOOKANDFEEL);
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }

            try {
            	
            	
                UIManager.setLookAndFeel(lookAndFeel);
                
                // If L&F = "Metal", set the theme
                
                if (LOOKANDFEEL.equals("Metal")) {
                  if (THEME.equals("DefaultMetal"))
                     MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
                  else if (THEME.equals("Ocean"))
                     MetalLookAndFeel.setCurrentTheme(new OceanTheme());
            
                     
                  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

                }	
                	
                	
                  
                
            } 
            
            catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                                   + lookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            } 
            
            catch (UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("
                                   + lookAndFeel
                                   + ") on this platform.");
                System.err.println("Using the default look and feel.");
            } 
            
            catch (Exception e) {
                System.err.println("Couldn't get specified look and feel ("
                                   + lookAndFeel
                                   + "), for some reason.");
                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }
        }
    }
    public HeaderPanelUI getPaneHeader() {
		return paneHeader;
	}

	public void setPaneHeader(HeaderPanelUI paneHeader) {
		this.paneHeader = paneHeader;
	}


    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Gui gui = new Gui();
                gui.createAndShowGUI();
            }
        });
    }
}