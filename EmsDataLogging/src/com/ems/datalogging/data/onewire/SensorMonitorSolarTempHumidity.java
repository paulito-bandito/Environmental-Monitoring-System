/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.datalogging.data.onewire;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.OneWireIOException;
import com.dalsemi.onewire.container.OneWireContainer;
import com.dalsemi.onewire.container.OneWireContainer26;
import com.dalsemi.onewire.utils.OWPath;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * This class extends the DeviceMonitorBase and is used for logging
 * temperature
 * 
 * @author Paul W Walter
 */
public class SensorMonitorSolarTempHumidity extends SensorMonitorBase {

    private OneWireContainer26 owd26;
    
    
    public SensorMonitorSolarTempHumidity(OneWireContainer26 a_owd26, OWPath a_path, long a_samplingRate) throws DAOException
    {
       
        super((OneWireContainer) a_owd26, a_path, a_samplingRate);
        this.owd26 = a_owd26;
        
        
        System.out.println("Creating Device Monitor for logging Temperature, Solar and Humidity.");
        System.out.println("\taddress: " + a_owd26.getAddressAsString());
        System.out.println("\tname: " + a_owd26.getName());
        System.out.println("\talternate name: " + a_owd26.getAlternateNames());
        System.out.println("\tdescription: " + a_owd26.getDescription());
        
        // Get data manager
        //dataManager = new SensorMonitorSolarTempHumDataManager(this.owd26.getAddressAsString(), this.owd26.getName(), this.owc.getDescription(), a_samplingRate);
        //this.samplingRate = dataManager.getSensorDefaultUpdateSpeed();
        this.samplingRate = a_samplingRate;
    }
        
    /**
     * 
     * @throws java.lang.InterruptedException
     * @throws com.dalsemi.onewire.OneWireException
     * @throws com.dalsemi.onewire.adapter.OneWireIOException
     */
    @Override
    protected synchronized void takeAMeasurementFromDevice() throws InterruptedException, OneWireException, OneWireIOException, Exception{
        
        System.out.println("Device " + address + ", speed: " + owd26.getMaxSpeed() );
                
        byte[] tempState = owd26.readDevice();  // get  temp device state      
        owd26.doTemperatureConvert(tempState);
        System.out.println("\tTemperature: " + owd26.getTemperature(tempState) + " C");
        Calendar cal = Calendar.getInstance();
        this.dataManager.loadAndCheckMeasurements(EmsDataLoggingConstants.SENSOR_CHANNEL_DAO_CELCIUS_CHANNEL, new Timestamp(cal.getTimeInMillis()), owd26.getTemperature(tempState));
        
        
        byte[] humState = owd26.readDevice(); // get humidity state.
        owd26.doHumidityConvert(humState);
        System.out.println("\tHumidity of " + owd26.getHumidity(humState) + " % ");
        this.dataManager.loadAndCheckMeasurements(EmsDataLoggingConstants.SENSOR_CHANNEL_DAO_HUMIDITY_CHANNEL, new Timestamp(cal.getTimeInMillis()), owd26.getHumidity(humState));
        
        
        byte[] solState = owd26.readDevice(); // get solar state.
        owd26.doADConvert(2, solState);
        System.out.println("\tSolar reading of " + owd26.getADVoltage(2, solState) + " volts ");
        this.dataManager.loadAndCheckMeasurements(EmsDataLoggingConstants.SENSOR_CHANNEL_DAO_SOLAR_CHANNEL, new Timestamp(cal.getTimeInMillis()), owd26.getADVoltage(2, solState));
        
    }

    @Override
    protected void initDataManager() throws DAOException {
       
        dataManager = new SensorMonitorSolarTempHumDataManager(this.owd26.getAddressAsString(), this.owd26.getName(), this.owc.getDescription(), this.samplingRate);
        
    }
}
