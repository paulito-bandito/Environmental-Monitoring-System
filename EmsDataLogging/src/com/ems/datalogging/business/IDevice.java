package com.ems.datalogging.business;


public interface IDevice {

    public long getId ();
    public String getTitle ();
    public boolean setTitle (String theTitle);
    public String getDescription ();
    public boolean setDescription (String desc);
    public boolean captureData () throws Exception;
    public boolean startRunner();
    public boolean stopRunner();
    public void destroyDevice();
    
    //public boolean startThread();
   // public boolean stopThread();
   // public boolean sleepThread(long millis);

}

