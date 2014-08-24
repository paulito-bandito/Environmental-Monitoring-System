/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.data;

import com.ems.syndication.business.ACaptureData;
import com.ems.syndication.business.AData;
import com.ems.syndication.business.Environment;
import com.ems.syndication.business.Measurement;
import com.ems.syndication.business.Note;
import com.ems.syndication.business.SensorChannel;
import com.ems.syndication.business.SensorRange;
import com.ems.syndication.business.Snapshot;
import com.ems.syndication.constants.EmsSyndicationConstants;
import java.util.ArrayList;

/**
 *
 * @author Paul W Walter
 */
public class DataLayerDiagnostic {
    
    public DataLayerDiagnostic()
    {
        SyndicationDAOFactory sdf = SyndicationDAOFactory.getInstance(EmsSyndicationConstants.EMS_INSTANCE_NAME);
    
        // get environmentDAO
        EnvironmentDAO envDAO = sdf.getEnvironmentDAO();
        
      
        
        // get environments
        try
        {
            //
            // Get environments
            //
            ArrayList<Environment> listOfEnvs = envDAO.getEnvironments(1L);            
            for(int i=0; i<listOfEnvs.size(); i++)
            {
                Environment thisEnv = (Environment)listOfEnvs.get(i);
                System.out.println("Environment: " + thisEnv.title);
                
               
                //
                // Get capture devices.
                //
                CaptureDeviceDAO capDeviceDAO = sdf.getCaptureDeviceDAO();
                ArrayList<ACaptureData> listOfCaptureDevices = capDeviceDAO.getCaptureDevices(listOfEnvs.get(i));
                
                for(int g=0; g<listOfCaptureDevices.size(); g++)
                {
                    ACaptureData thisDevice = (ACaptureData)listOfCaptureDevices.get(g);
                    System.out.println("\tDevice: " + thisDevice.title + ", " + thisDevice.id  + ", "  + thisDevice.captureDataType);
                    
                    if(thisDevice instanceof SensorChannel)
                    {
                        System.out.println("\t\t, channel num: " + ((SensorChannel)thisDevice).channelNum + ", sensor type: " + ((SensorChannel)thisDevice).sensorTypeName + ", " + ((SensorChannel)thisDevice).sensorMetricTypeName );
                        
                         //
                        // Get current sensor ranges.
                        //
                        SensorRangeDAO senRanDAO = sdf.getSensorRangeDAO();
                        
                        ArrayList<SensorRange> listOfCurrentRangesForThisChannel = senRanDAO.getCurrentProblemSensorRangesForSensorChannel(((SensorChannel)thisDevice).id, ((SensorChannel)thisDevice).channelNum);
                        for(int z=0; z<listOfCurrentRangesForThisChannel.size(); z++)
                        {
                            SensorRange thisRange = listOfCurrentRangesForThisChannel.get(z);
                            System.out.println("\t\t\t, sensor range: " + thisRange.title + ", min: " + thisRange.minThreshold +  ", max: " + thisRange.maxThreshold + ", sensorID: " + thisRange.sensorId + ", sen chan num: " + thisRange.sensorChannelNum);
                            
                        }
                    }
                    
                    //
                    // Get current device readings
                    //
                    DataDAO dataDAO = sdf.getDataDAO();
                    AData thisData = dataDAO.getCurrentData(thisDevice);
                    
                    if(thisData instanceof Note)
                    {
                        Note thisNote = (Note)thisData;
                        System.out.println("\t\tNote: " + thisNote.message + ", " + thisNote.id + ", " + thisNote.author.username);
                    }
                    
                    else if(thisData instanceof Snapshot)
                    {
                        Snapshot thisSnapshot = (Snapshot)thisData;
                        System.out.println("\t\tSnapshot: " + thisSnapshot.id + ", " + thisSnapshot.uri );
                    }
                    
                    else if(thisData instanceof Measurement)
                    {
                        Measurement thisMeasurement = (Measurement)thisData;
                        System.out.println("\t\tMeasurement: " + thisMeasurement.id + ", " + thisMeasurement.measurement );
                    }
                    
                }
                
                
            }
            
            // for each environment in the list, get the capture devices:
           
            
            

        }catch(Exception exp)
        {
            System.err.println("Error in data diagnostic: " + exp.getMessage());    

        }
        
       
       
            
           
        
    }
    
    public static void main (String args[])
    {
        new DataLayerDiagnostic();
    }
    
}
