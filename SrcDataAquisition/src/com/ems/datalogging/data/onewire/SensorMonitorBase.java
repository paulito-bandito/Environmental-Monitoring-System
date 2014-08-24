package com.ems.datalogging.data.onewire;
        
/*---------------------------------------------------------------------------
 * Copyright (C) 1999,2000 Dallas Semiconductor Corporation, All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY,  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL DALLAS SEMICONDUCTOR BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * Except as contained in this notice, the name of Dallas Semiconductor
 * shall not be used except as stated in the Dallas Semiconductor
 * Branding Policy.
 *---------------------------------------------------------------------------
 */

import com.dalsemi.onewire.utils.OWPath;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.OneWireIOException;
import com.dalsemi.onewire.container.OneWireContainer;
import com.ems.datalogging.data.utils.DAOException;


/**
 * Thread to monitor a device on a path in a
 * a complex network.
 * 
 * Modified by Paul Walter (September 2008).
 *
 * @version    0.00, 18 September 2000, modified 2008
 * @author     DS
 */
public abstract class SensorMonitorBase
   extends Thread
{

   //--------
   //-------- Variables 
   //--------
   protected boolean              keepRunning = true;
   protected OWPath               path;
   protected String               address;
   protected DSPortAdapter        adapter;
   protected OneWireContainer     owc;
   protected long                 samplingRate; // in milliseconds
   protected SensorMonitorBaseDataManager dataManager;
   protected boolean              hasDataManagerBeenInitialized = false;
   
   
   
   //--------
   //-------- Constructor
   //--------

   /**
    * Create TemperatureContainer Watcher
    *
    * @param tc TemperatureContainer to read
    * @param path path to get to the device in a complex
    *        network
    * @param a_samplingRate Number of milliseconds that 
    *        should elapse between each reading.
    */
   public SensorMonitorBase (OneWireContainer a_owc, OWPath a_path, long a_samplingRate) throws DAOException
   {
      this.path = a_path;

      // extract out the address and adapter
      owc     = a_owc;
      address = owc.getAddressAsString();
      adapter = owc.getAdapter();
      samplingRate = a_samplingRate;
   }
   
   /**
    * Sets the update speed of this device in milliseconds.
    * @param aUpdateSpeedInMillis
    */
   public void setSamplingRate(long aUpdateSpeedInMillis)
   {
       this.samplingRate = aUpdateSpeedInMillis;
   }
   
    /**
    * Stop this watcher thread
    */
   public void killWatch ()
   {

      // clear the flag to stop the thread
      synchronized (this)
      {
         keepRunning = false;
      }

      // wait for thread death
      while (isAlive())
      {
         try
         {
            sleep(20);
         }
         catch (InterruptedException e)
         {

            // DRAIN
         }
      }
   }

   /**
    * Work done by thread, you don't need to use this in any extended class.
    */
    @Override
   public void run ()
   {
      while (keepRunning)
      {
         try
         {
             if(!hasDataManagerBeenInitialized)
             {
                hasDataManagerBeenInitialized = true;
                this.initDataManager();
                
                // set the sampling rate before we continue.
                this.samplingRate = (long)this.dataManager.sensor.getUpdateSpeed();
             }

            // get exclusive use of port
            adapter.beginExclusive(true);

            // open a path to the temp device
            path.open();

            // check if present
            if (owc.isPresent())
            {
              
                // Call takeAMeasurementFromDevice method so the 
                // classes that extend this object know when it 
                // can take a measurement.
                
                takeAMeasurementFromDevice(); 

            }
            else
            {
               System.out.println("Device " + address
                                  + " not present so stopping thread");

               synchronized (this)
               {
                  keepRunning = false;
               }
            }

            // close the path to the device
            path.close();

            // release exclusive use of port
            adapter.endExclusive();

            // sleep for a while
            if (keepRunning)
            {
               sleep(samplingRate);
            }
         }
         catch (OneWireIOException e)
         {

            // DRAIN
         }
         catch (OneWireException e)
         {

            // DRAIN
         }
         catch (InterruptedException e)
         {

            // DRAIN
         }
         catch (DAOException de)
         {
             System.err.println(de.getMessage());
         }
         catch (Exception e)
         {

            System.err.println(e.getMessage());
         }
      }
   }
   
   /**
    * This method gets called when all the dependencies for this device
    * have been properly configured, all network branches are active.
    * 
    * Here you can read the device's state or do what ever it is you need
    * to do for this particular device.
    * 
    */
   protected synchronized void  takeAMeasurementFromDevice() throws InterruptedException, OneWireException, OneWireIOException, Exception
   {
       
   }
   
   protected abstract void initDataManager() throws DAOException;
   
}
