package com.ems.datalogging.data.jmf;


import com.ems.datalogging.data.db.SnapshotDAO;
import com.ems.datalogging.data.utils.DAOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.MediaLocator;

public class CameraThread implements Runnable {

    // VARS --------------------------------------------------------------------
    
    protected Thread runner;
    protected boolean running = false;
    protected int MILLISECOND_OFFSET = 1000;
    protected CameraDataManager cameraThreadDataManager;


    // CONSTRUCTORS ------------------------------------------------------------

    /**
     * Default Constructor
     */
    public CameraThread () {
    }
    /**
     * Constructor when you have snapshots to access
     * @param aId
     * @param aTitle
     * @param aDesc
     * @param aUpdateSpeed
     * @param aNetworkAddress
     * @param someSnapshots
     */
    //public CameraThread (long aId, String aTitle, String aDesc, double aUpdateSpeed, String aNetworkAddress, ArrayList<SnapshotTO> someSnapshots) {
    public CameraThread(MediaLocator mediaLocation) throws DAOException, Exception
    {
       // Initialize the camera components
        cameraThreadDataManager = new CameraDataManager(mediaLocation.toString());
        //snapshotter.initSnapshotterDAO(this, false, false);
    }

    
    // ACTIONS  ----------------------------------------------------------------

    public void run () {
        while(running){
            
            try {
                this.captureData();  
                runner.sleep(StrictMath.round(cameraThreadDataManager.getUpdateSpeed() * MILLISECOND_OFFSET));
            } catch (InterruptedException ex) {
                Logger.getLogger(CameraThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex2) {
                Logger.getLogger(CameraThread.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
    }
 
    /**
     * This will call the {@link SnapshotDAO} class in order to use its takePhysicalSnapshot() method.
     * That method will try and take a snapshot for the camera device located at 
     * the network address in this Object (Camera) and place it in the file path
     * location noted in the FILE_PATH of this object.
     * @return
     * @throws java.lang.Exception
     */
    public boolean captureData () throws Exception{
       
        cameraThreadDataManager.takePictureAsJpg();
        
        return true;         
    }
    
    public boolean startRunner()
    {
        runner = new Thread(this);
        runner.start();
        running = true;
        return true;
    }
    public boolean stopRunner()
    {
        running = false;
        return true;
    }
    public void destroyDevice()
    {
        if(runner !=null) // MAKE SURE A THREAD IS EVEN RUNNING
        {
            if(runner.isAlive()) // ONLY TIDY IF THE DEVICE IS RUNNING
            {
                cameraThreadDataManager.cleanup();
            }
        }
        
        
    }
     // Override ---------------------------------------------------------------

    /**
     * The  ID is unique for each Transfer Object. So this should compare ContactDataType by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
       CameraThread otherType = (CameraThread)other;
        
        //SEE IF ITS ID IS THE SAME AS MY ID
        if(cameraThreadDataManager.cameraTO.getTheId() == otherType.cameraThreadDataManager.cameraTO.getTheId());
        {
            return true;
        }
        //return false;
    }

    
    /**
     * Returns the String representation of this Transfer Object. Not really required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {  
       
        //CREATE THE FIRST PART
        String returnStr =  "Camera[theId="         + cameraThreadDataManager.cameraTO.getTheId()
                            + "', theTitle='"       + cameraThreadDataManager.cameraTO.getTheTitle() 
                            + "', theDesc='"        + cameraThreadDataManager.cameraTO.getTheDesc() 
                            + "', updateSpeed='"    + cameraThreadDataManager.cameraTO.getUpdateSpeed() 
                            + "', networkAddress='" + cameraThreadDataManager.cameraTO.getNetworkAddress() 
                            + "', runner='"         + runner
                            + ")]";
        
        // RETURN VALUE
        return returnStr;
   
    }
}

