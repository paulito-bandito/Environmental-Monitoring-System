package com.ems.datalogging;


import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.db.EmsDataLoggingDbDAOFactory;
import com.ems.datalogging.data.onewire.OWDeviceManager;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Paul W Walter
 */
public final class EmsDataLogger {
    
    private String INSTANCE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    public static String appTitle = 
            "---------------------------------------\n" +
            "-      EMS Data Logging Manager       -\n"+
            "---------------------------------------\n" ;
    public static String menuText =
            "   Menu:\n" +
            "       1.) Start devices\n" +
            "       2.) Stop devices\n" +
            "       3.) Exit\n";
                
    public static void main (String [] args)
    {
        EmsDataLogger thisLogger = new EmsDataLogger();
    }
            
    public EmsDataLogger()        
    {
        // Variables
        Scanner scanner = new Scanner(System.in);
	boolean isRunning = true;
        boolean isDevicesRunning = false;
        
        // Initialize the DAOFactory
        EmsDataLoggingDbDAOFactory emsDaoFactory =  EmsDataLoggingDbDAOFactory.getInstance(INSTANCE_NAME);
        
        // Print out title and Menu for the user.
        System.out.println(appTitle);
        System.out.println(menuText);
        
        OWDeviceManager owdm = null;
        
        // Begin looping.
        while(isRunning)
        {
            
            System.out.print("Enter choice:\t");
            int userInput = scanner.nextInt();
            
            
            // Parse user input.
            switch(userInput)
            {
                case 1: 
                    if(!isDevicesRunning)
                    {
                        System.err.println("\tStarting Devices...");
                        isDevicesRunning = true;
                        
                        System.out.println("\tLooking for one-wire network...");
                        owdm = new OWDeviceManager(); 
                    }else{
                        System.err.println("\tThe devices are running already.");
                    }
                    break;
                case 2: 
                    if(isDevicesRunning)
                    {
                        System.err.println("\tStopping Devices.");
                        isDevicesRunning = false;
                    }else{
                        System.err.println("\tThe devices are stopped already.");
                    }
                    break;
                case 3: 
                    System.err.println("\tExiting.");
                    isRunning = false;
                    break;
                    
            }           
        }
    }
    
   
    
    
}
