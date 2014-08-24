/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.datalogging.data.jmf;

import com.ems.datalogging.business.CameraTO;
import com.ems.datalogging.business.SnapshotTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.db.CameraDAO;
import com.ems.datalogging.data.db.EmsDataLoggingDbDAOFactory;
import com.ems.datalogging.data.db.SnapshotDAO;
import com.ems.datalogging.data.utils.DAOException;
import com.ems.datalogging.data.utils.DAOUtil;
import java.sql.Timestamp;

/**
 *
 * @author Paul W Walter
 */
public class CameraDataManager {
    
    protected EmsDataLoggingDbDAOFactory emsDaoFactory;
    protected SnapshotDAO snapshotDAO;
    protected CameraDAO cameraDAO;
    protected NetworkCamera networkCamera;
    protected CameraTO cameraTO;
    private boolean shouldWeCleanUp = false;
    
     // -------------
    //   Constructor 
    // -------------
    
    public CameraDataManager(String aNetworkAddress) throws DAOException
    {
        emsDaoFactory = EmsDataLoggingDbDAOFactory.getInstance(EmsDataLoggingConstants.EMS_INSTANCE_NAME); 
        snapshotDAO = emsDaoFactory.getSnapshotDAO();
        cameraDAO = emsDaoFactory.getCameraDAO();
        networkCamera = new NetworkCamera(emsDaoFactory.getCameraSnapshotDirectory());
        
        // Initialize the cameraTO
        cameraTO = cameraDAO.findByNetworkAddress(aNetworkAddress);
        
        if(cameraTO != null)
        {
            // Pass this into the network camera
            networkCamera.initSnapshotterDAO(cameraTO, false, false);
        }else{
            System.err.println("Camera with a network address of " + aNetworkAddress + " was not found in the database. Please enter one before pictures can be logged.");
        }
        
    }

    void cleanup() {
        if(networkCamera != null)
        {
            shouldWeCleanUp = true;        
        
            this.networkCamera.tidyClose();
        }
    }

    long getCameraID() {
        return cameraTO.getTheId();
    }

    int getUpdateSpeed() {
        return (int) cameraTO.getUpdateSpeed();
    }

    boolean takePictureAsJpg() throws DAOException, Exception {
        // Create the timestamp and filename
        
        //System.out.println(thisShot);
        if(!shouldWeCleanUp)
        {
            Timestamp thisTimestamp = DAOUtil.getCurTimestamp();
            long timeInMillis = DAOUtil.getCurTimeInMillis();
            String fileName = createJpgFileName(this.cameraTO.getTheId(), timeInMillis); //this.cameraTO.getTheId() + "_" + timeInMillis;

            // Get Business Object Snapshot
            SnapshotTO thisShot = new SnapshotTO(-1, this.cameraTO.getTheId(),fileName, thisTimestamp );

            snapshotDAO.save(thisShot);        

            // Get a physical snapshot.
            networkCamera.takeAPicture(thisShot);
        }
        
        return true;         
    }
    
    private String createJpgFileName(long aCameraID, long aTimeInMilliseconds )
    {
        // CONVERT TIMESTAMP TO "Time in Millis" BECAUSE IT IS FILE NAME SAFE (It doesn't have any specirfal characters like ':')
        return aCameraID + "_" + aTimeInMilliseconds + ".jpg";
    }
}
