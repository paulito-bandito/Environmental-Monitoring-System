/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.datalogging.data.db;

import com.ems.datalogging.business.SensorTO;
import com.ems.datalogging.business.SensorChannel;
import com.ems.datalogging.business.SensorMetricType;
import com.ems.datalogging.business.SensorTypeTO;
import com.ems.datalogging.business.UserTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paul W Walter
 */
public class DataDiagnostic {
    
    // Get the DAO factory
    
    public static void main (String args[])
    {
        try {
            EmsDataLoggingDbDAOFactory emsLoggingDaoFactory = EmsDataLoggingDbDAOFactory.getInstance(EmsDataLoggingConstants.EMS_INSTANCE_NAME);

            // get sensor dao
            SensorDAO sensorDAO = new SensorDAO(emsLoggingDaoFactory);

            SensorTO thisSensor = sensorDAO.findByNetworkAddress("4300000008628410");
            
            // Log new solar, temp, humidity sensor.
            // Obtain Sensor Metric types from the database for the Channels
//            SensorMetricTypeDAO sensorMetricTypeDAO = emsLoggingDaoFactory.getSensorMetricTypeDAO();
//            SensorMetricType channelZeroMetricType = sensorMetricTypeDAO.find(emsLoggingDaoFactory.getSensorMetricTypeIdForCelcius());
//            SensorMetricType channelOneMetricType = sensorMetricTypeDAO.find(emsLoggingDaoFactory.getSensorMetricTypeIdForHumidity());
//            SensorMetricType channelTwoMetricType = sensorMetricTypeDAO.find(emsLoggingDaoFactory.getSensorMetricTypeIdForSolar());
//
//
//            // Create Channels, without sensor ranges which will be null here
//            SensorChannel channelZero = new SensorChannel(2, 0, channelZeroMetricType, null);
//            SensorChannel channelOne = new SensorChannel(2, 1, channelOneMetricType, null);
//            SensorChannel channelTwo = new SensorChannel(2, 2, channelTwoMetricType, null);
//
//            ArrayList <SensorChannel> theseChannels = new ArrayList<SensorChannel>();
//            theseChannels.add(channelTwo);
//            theseChannels.add(channelOne);
//            theseChannels.add(channelZero);
//
//            // Obtain Sensor type from the DB for Sensor
//            SensorTypeDAO sensorTypeDAO = emsLoggingDaoFactory.getSensorTypeDAO();
//            SensorType sensorType = sensorTypeDAO.find(emsLoggingDaoFactory.getSensorTypeIdForTemperatureSolarHumiditySensor());
//
//            // Get list of users for a given sensor id
//            //UserDAO userDAO = emsLoggingDaoFactory.getUserDAO();
//            //ArrayList<User> userList = userDAO.listForGivenSensorID(aId);
//            
//            // Create Sensor object
//            Sensor aSensor = new Sensor(-1, "Test", "Description of Test.", "123abc", 1.5, sensorType, theseChannels, null);
//            //long aId, String aTitle, String aDesc, String theNetworkAddress, double updateSpeed, SensorType sensorType, ArrayList<SensorChannel> aSensorChannels
//            
            // Insert Sensor Object into the DB
            //sensorDAO.save(aSensor);
                int i = 1;
            
        } catch (DAOException ex) {
            Logger.getLogger(DataDiagnostic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    
   
}
