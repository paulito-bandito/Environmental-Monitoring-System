/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.datalogging.data.jmf;

import com.ems.datalogging.data.utils.DAOException;
import java.util.Vector;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.format.VideoFormat;

/**
 *
 * @author Paul W Walter
 */
public class JMFVideoCameraManager {

    private Vector <CameraThread> watchers;
    
    public JMFVideoCameraManager() throws DAOException, Exception
    {
        // Create new vector
        watchers = new Vector<CameraThread>();
        
        // Look through the registry and locate suitable video cameras        
        Vector info = CaptureDeviceManager.getDeviceList(new Format(VideoFormat.YUV));
        
        if (info == null)
        {
          throw new Exception("No YUV video Capture devices known to JMF suitable for taking pictures.");
        } else {
         for (int i = 0; i < info.size(); i++)
          {
            CaptureDeviceInfo captureInfo =  (CaptureDeviceInfo) info.elementAt(i);
            CameraThread newCam = new CameraThread(captureInfo.getLocator());           
            watchers.add(newCam);
            System.out.println("\t\tCamera found at media location: " + newCam.cameraThreadDataManager.cameraTO.getNetworkAddress());
          }
        }
    }
    
    public void startTakingPictures()
    {
        for(int i=0; i<watchers.size(); i++)
        {
             watchers.get(i).startRunner();
        }
    }
    
    public void stopTakingPictures()
    {
        for(int i=0; i<watchers.size(); i++)
        {
             watchers.get(i).stopRunner();
             watchers.get(i).destroyDevice();
        }
    }
}
