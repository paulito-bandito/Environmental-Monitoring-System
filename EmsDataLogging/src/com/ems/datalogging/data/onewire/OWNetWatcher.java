package com.ems.datalogging.data.onewire;

/*---------------------------------------------------------------------------
 * Copyright (C) 2001 Dallas Semiconductor Corporation, All Rights Reserved.
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

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.adapter.OneWireIOException;
import com.dalsemi.onewire.container.TemperatureContainer;
import com.dalsemi.onewire.container.OneWireContainer;
import com.dalsemi.onewire.application.monitor.*;
import com.dalsemi.onewire.container.OneWireContainer26;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Minimal demo to monitor a complex network and read temperatures
 *
 * @version    0.00, 28 August 2000
 * @author     DS
 */
public class OWNetWatcher
   implements DeviceMonitorEventListener
{

   /**
    * Main for the OWNetWatch Demo
    *
    * @param args command line arguments
    */
   public static void main (String args [])
   {
      OneWireContainer owd;
      int              delay;

      try
      {

         // get the default adapter  
         DSPortAdapter adapter = OneWireAccessProvider.getDefaultAdapter();

         System.out.println();
         System.out.println("Adapter: " + adapter.getAdapterName()
                            + " Port: " + adapter.getPortName());
         System.out.println();

         // clear any previous search restrictions
         adapter.setSearchAllDevices();
         adapter.targetAllFamilies();
         adapter.setSpeed(adapter.SPEED_FLEX);

         // create the watcher with this adapter
         OWNetWatcher nw = new OWNetWatcher(adapter);

         // sleep for the specified time
//         if (args.length >= 1)
//            delay = Integer.decode(args [0]).intValue();
//         else
//            delay = 20000;
//
//         System.out.println("Monitor run for: " + delay + "ms");
//         Thread.sleep(delay);
//
//         // clean up
//         System.out.println("Done with monitor run, now cleanup threads");
//         nw.killNetWatch();
//
//         // free port used by adapter
//         adapter.freePort();
      }
      catch (Exception e)
      {
         System.out.println(e);
      }

      return;
   }

   //--------
   //-------- Variables 
   //--------

   /** Network Monitor intance */
   private NetworkDeviceMonitor nm;

   /** Vector of temperature watches, used in cleanup */
   private Vector watchers;
   
   //private Vector <Class> validDeviceClasses;

   //--------
   //-------- Constructor
   //--------

   /**
    * Create a 1-Wire Network Watcher
    *
    * @param  adapter for 1-Wire Network to monitor
    */
   public OWNetWatcher (DSPortAdapter adapter)
   {
      // initialize the device class array
      //validDeviceClasses  =  new Vector();
      //validDeviceClasses.add( OneWireContainer26.class);
      //validDeviceClasses.add( TemperatureContainer.class);

      // create vector to keep track of device watches
      watchers = new Vector(1, 1);

      // create a network monitor
      nm = new NetworkDeviceMonitor(adapter);

      // add this to the event listers
      nm.addDeviceMonitorEventListener(this);

      // start the monitor
      Thread t = new Thread(nm);
      t.start();
   }

   /**
    * Clean up the threads
    */
   public void killNetWatch ()
   {
      SensorMonitorBase dmb;

      // kill the network monitor
      nm.killMonitor();

      // kill the temp watchers
      for (int i = 0; i < watchers.size(); i++)
      {
         dmb = ( SensorMonitorBase ) watchers.elementAt(i);

         dmb.killWatch();
      }
   }

   /**
    * Arrival event as a NetworkMonitorEventListener
    *
    * @param nme NetworkMonitorEvent add
    */
   public void deviceArrival (DeviceMonitorEvent dme)
   {
      for(int i=0; i<dme.getDeviceCount(); i++)
      {
         System.out.println("ADD: " + dme.getPathForContainerAt(i)
                            + dme.getAddressAsStringAt(i));
   
         // if new devices is a TemperatureContainter the start a thread to read it
         OneWireContainer owc = dme.getContainerAt(i);
   
         if(owc instanceof OneWireContainer26){
            System.out.println("Found a Container 26");            
            SensorMonitorSolarTempHumidity dmsth;
            
            try {
                dmsth = new SensorMonitorSolarTempHumidity((OneWireContainer26) owc, dme.getPathForContainerAt(i), EmsDataLoggingConstants.SENSOR_DEFAULT_UPDATE_SPEED_IN_MILLISECONDS);
                //dmsth.i.init();
                dmsth.start();
                watchers.addElement(dmsth);
            } catch (DAOException ex) {
                Logger.getLogger(OWNetWatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
           
            
         }else if (owc instanceof TemperatureContainer){
            System.out.println("Found a Temperature Device");
            //DeviceMonitorTemperature dmt  = new DeviceMonitorTemperature(( TemperatureContainer ) owc, dme.getPathForContainerAt(i), 500);
            //dmt.start();
            //watchers.addElement(dmt);
         }
      }
   }

   /**
    * Depart event as a NetworkMonitorEventListener
    *
    * @param nme NetworkMonitorEvent depart
    */
   public void deviceDeparture (DeviceMonitorEvent dme)
   {
      for(int i=0; i<dme.getDeviceCount(); i++)
      {
         System.out.println("REMOVE: " + dme.getPathForContainerAt(i)
                            + dme.getAddressAsStringAt(i));
   
         // it would be nice if I killed the temp watcher
         //watchers.remove(dme.getContainerAt(i));
      }
   }

   /**
    * Exception event as a NetworkMonitorEventListener
    *
    * @param ex Exception
    */
   public void networkException (DeviceMonitorException ex)
   {
      if (ex.getException() instanceof OneWireIOException)
         System.out.print(".IO.");
      else
         System.out.print(ex);
      ex.getException().printStackTrace();
   }
}
