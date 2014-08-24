package com.ems.datalogging.data.onewire;

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.OneWireContainer;

/**
 *
 * @author Paul W Walter
 */
public class OWDeviceManager {

    private  OneWireContainer owd;
    private int delay;
    
    public OWDeviceManager()
    {
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

//         delay = 2000;
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
    }
}
