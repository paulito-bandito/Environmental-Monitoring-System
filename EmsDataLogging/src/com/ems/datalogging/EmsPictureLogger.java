package com.ems.datalogging;


import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.db.EmsDataLoggingDbDAOFactory;
import com.ems.datalogging.data.jmf.JMFVideoCameraManager;
import com.ems.datalogging.data.onewire.OWDeviceManager;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paul W Walter
 */
public final class EmsPictureLogger {
    
    private String INSTANCE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    public static String appTitle = 
            "---------------------------------------\n" +
            "-      EMS Picture Logging Manager       -\n"+
            "---------------------------------------\n" ;
    public static String menuText =
            "   Menu:\n" +
            "       1.) Start cameras\n" +
            "       2.) Stop cameras\n" +
            "       3.) Exit\n";
                
    public static void main (String [] args)
    {
        EmsPictureLogger thisLogger = new EmsPictureLogger();
    }
            
    public EmsPictureLogger()        
    {
        try {
            // Variables
            Scanner scanner = new Scanner(System.in);
            boolean isRunning = true;
            boolean isDevicesRunning = false;

            // Print out title and Menu for the user.
            System.out.println(appTitle);
            System.out.println(menuText);

            // Let the Video manager look for cameras on the network.
            JMFVideoCameraManager jmfVidMan = new JMFVideoCameraManager();

            // Begin looping.
            while (isRunning) {

                System.out.print("Enter choice:\t");
                int userInput = scanner.nextInt();


                // Parse user input.
                switch (userInput) {
                    case 1:
                        if (!isDevicesRunning) {
                            System.err.println("\tStarting Cameras...");
                            isDevicesRunning = true;
                            jmfVidMan.startTakingPictures();
                        } else {
                            System.err.println("\tThe devices are running already.");
                        }
                        break;
                    case 2:
                        if (isDevicesRunning) {
                            System.err.println("\tStopping Devices.");
                            jmfVidMan.stopTakingPictures();
                            isDevicesRunning = false;
                        } else {
                            System.err.println("\tThe devices aren't running.");
                        }
                        break;
                    case 3:
                        if(!isDevicesRunning)
                        {
                            System.err.println("\tExiting.");
                            isRunning = false;
                            System.exit(0);
                        }else{
                            System.err.println("\tShutdown the cameras so their threads get cleaned up properly.");
                        }
                        
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmsPictureLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    
    
}
