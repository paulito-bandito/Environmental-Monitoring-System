/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.datalogging.data.onewire;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.OneWireIOException;
import com.dalsemi.onewire.container.OneWireContainer;
import com.dalsemi.onewire.container.TemperatureContainer;
import com.dalsemi.onewire.utils.OWPath;
import com.ems.datalogging.business.SensorTO;
import com.ems.datalogging.data.utils.DAOException;

/**
 * This class extends the DeviceMonitorBase and is used for logging
 * temperature
 * 
 * @author Paul W Walter
 */
public class SensorMonitorTemperature extends SensorMonitorBase {

    private TemperatureContainer tc;
    
    public SensorMonitorTemperature(TemperatureContainer a_tc, OWPath a_path, long a_samplingRate) throws DAOException
    {
       
        super((OneWireContainer) a_tc, a_path, a_samplingRate);
        this.tc = a_tc;
        
         System.out.println("Creating Device Monitor for logging Temperature.");
         System.out.println("\taddress: " + owc.getAddressAsString());
         System.out.println("\tname: " + owc.getName());
         System.out.println("\talternate name: " + owc.getAlternateNames());
         System.out.println("\tdescription: " + owc.getDescription());
         
         // Get data manager
        //dataManager = new SensorMonitorTemperatureDataManager(address);
        
    }
    
    /**
     * 
     * @throws java.lang.InterruptedException
     * @throws com.dalsemi.onewire.OneWireException
     * @throws com.dalsemi.onewire.adapter.OneWireIOException
     */
    @Override
    protected synchronized void takeAMeasurementFromDevice() throws InterruptedException, OneWireException, OneWireIOException{
        
        
        byte[] state = tc.readDevice();

        tc.doTemperatureConvert(state);

        state = tc.readDevice();
        System.out.println("Device " + address);
        System.out.println("\tTemperature: " + tc.getTemperature(state) + " C");
        
        
    }

    @Override
    protected void initDataManager() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
