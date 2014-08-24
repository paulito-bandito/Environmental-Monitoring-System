/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.datalogging.business;

import java.util.ArrayList;

/**
 *
 * @author Paul W Walter
 */
public class CameraTO {
    
    private long theId;
    private String theTitle;
    private String theDesc;
    private double updateSpeed;
    private String networkAddress;
    private long environmentID;

    public CameraTO(long cameraId, String aTitle, String aDesc, double aUpdateSpeed, String aNetworkAddress, long aEnvId) {
        this.theId = cameraId;
        this.theTitle = aTitle;
        this.theDesc = aDesc;
        this.updateSpeed = aUpdateSpeed;
        this.networkAddress = aNetworkAddress;
        this.environmentID = aEnvId;
    }

    // Getters and Setters
    
    public String getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(String networkAddress) {
        this.networkAddress = networkAddress;
    }
    
    public String getTheDesc() {
        return theDesc;
    }

    public void setTheDesc(String theDesc) {
        this.theDesc = theDesc;
    }

    public long getTheId() {
        return theId;
    }

    public void setTheId(long theId) {
        this.theId = theId;
    }

    public String getTheTitle() {
        return theTitle;
    }

    public void setTheTitle(String theTitle) {
        this.theTitle = theTitle;
    }

    public double getUpdateSpeed() {
        return updateSpeed;
    }

    public void setUpdateSpeed(double updateSpeed) {
        this.updateSpeed = updateSpeed;
    }
    
    

}
