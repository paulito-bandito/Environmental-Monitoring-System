/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.datalogging.data.onewire;

import com.ems.datalogging.business.SensorTO;
import com.ems.datalogging.business.SensorChannel;
import com.ems.datalogging.business.SensorMetricType;
import com.ems.datalogging.business.SensorTypeTO;
import com.ems.datalogging.business.UserTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.db.UserDAO;
import com.ems.datalogging.data.utils.DAOException;
import java.util.ArrayList;

/**
 *
 * @author Paul W Walter
 */
public class SensorMonitorSolarTempHumDataManager extends SensorMonitorBaseDataManager {

    
    // -------------
    //   Getters 
    // -------------
    
    @Override
    public long getSensorDefaultUpdateSpeed() {
        return this.emsDaoFactory.getSensorDefaultUpdateSpeed();
    }

    @Override
    public long getSensorMetricTypeIdForCelcius() {
        return this.emsDaoFactory.getSensorMetricTypeIdForCelcius();
    }

    @Override
    public long getSensorMetricTypeIdForHumidity() {
        return this.emsDaoFactory.getSensorMetricTypeIdForHumidity();
    }

    @Override
    public long getSensorMetricTypeIdForSolar() {
        return this.emsDaoFactory.getSensorMetricTypeIdForSolar();
    }

    @Override
    public long getSensorTypeIdForTemperatureSensor() {
        return this.emsDaoFactory.getSensorTypeIdForTemperatureSensor();
    }

    @Override
    public long getSensorTypeIdForTemperatureSolarHumiditySensor() {
        return this.emsDaoFactory.getSensorTypeIdForTemperatureSolarHumiditySensor();
    }
    
    // -------------
    //   Constructor 
    // -------------
    
    public SensorMonitorSolarTempHumDataManager(String aNetworkAddress, String aTitle, String aDesc, double aUpdateSpeed) throws DAOException
    {
        super();
        init(aNetworkAddress, aTitle, aDesc, aUpdateSpeed);
    }
    
    // -------------
    //   Actions 
    // -------------
    
    @Override
    void init(String aNetworkAddress, String aTitle, String aDesc, double aUpdateSpeed) throws DAOException {
        
        // Try to locate this device in the database
        SensorTO thisSensor = this.findSensor(aNetworkAddress);
        //System.out.println("\t\t\t\tTEST:: " + thisSensor.getTheDesc());
            
        //If can't locate it then log it to the database.
        if(thisSensor == null){           
           thisSensor = this.logSensor(aNetworkAddress, aTitle, aDesc, aUpdateSpeed);
        }
        
        // give the ok to start logging data.
        if(thisSensor != null) this.sensor = thisSensor;
    }

    @Override
    public SensorTO logSensor(String aNetworkAddress, String aTitle, String aDesc, double aUpdateSpeed) throws DAOException {
        
        synchronized(this)
        {
            // Obtain Sensor Metric types from the database for the Channels
            //SensorMetricType channelZeroMetricType = this.sensorMetricTypeDAO.find(1l);
            SensorMetricType channelZeroMetricType = this.sensorMetricTypeDAO.find(this.emsDaoFactory.getSensorMetricTypeIdForCelcius());
            SensorMetricType channelOneMetricType = this.sensorMetricTypeDAO.find(this.emsDaoFactory.getSensorMetricTypeIdForHumidity());
            SensorMetricType channelTwoMetricType = this.sensorMetricTypeDAO.find(this.emsDaoFactory.getSensorMetricTypeIdForSolar());
            
            SensorTypeTO channelZeroType = this.sensorTypeDAO.find(this.emsDaoFactory.getSensorTypeIdForTemperatureSensor());
            SensorTypeTO channelOneType = this.sensorTypeDAO.find(this.emsDaoFactory.getSensorTypeIdForHumiditySensor());
            SensorTypeTO channelTwoType = this.sensorTypeDAO.find(this.emsDaoFactory.getSensorTypeIdForSolarSensor());


            // Create Channels
            SensorChannel channelZero = new SensorChannel(2, EmsDataLoggingConstants.SENSOR_CHANNEL_DAO_CELCIUS_CHANNEL, channelZeroMetricType, null, channelZeroType);
            SensorChannel channelOne = new SensorChannel(2, EmsDataLoggingConstants.SENSOR_CHANNEL_DAO_HUMIDITY_CHANNEL, channelOneMetricType, null, channelOneType);
            SensorChannel channelTwo = new SensorChannel(2, EmsDataLoggingConstants.SENSOR_CHANNEL_DAO_SOLAR_CHANNEL, channelTwoMetricType, null, channelTwoType);

            ArrayList <SensorChannel> theseChannels = new ArrayList<SensorChannel>();
            
            theseChannels.add(channelZero);
            theseChannels.add(channelOne);
            theseChannels.add(channelTwo);
            
            // Obtain Sensor type from the DB for Sensor
            SensorTypeTO sensorType = sensorTypeDAO.find(this.emsDaoFactory.getSensorTypeIdForTemperatureSolarHumiditySensor());

            // Create Sensor object
            SensorTO aSensor = new SensorTO(-1, aTitle, aDesc, aNetworkAddress, aUpdateSpeed, sensorType, theseChannels, null, -1, null);

            // Insert Sensor Object into the DB
            this.sensorDAO.save(aSensor);

            // Get list of users for a given sensor id, it is important to do this after the save because it will then have an ID assigned to it.
            ArrayList<UserTO> userList = userDAO.listForGivenSensorID(aSensor.getTheId());
            
            aSensor.setCallList(userList);
            
            return aSensor;
        }
        
    }

    

}

   
