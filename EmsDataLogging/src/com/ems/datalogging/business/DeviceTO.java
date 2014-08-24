/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.datalogging.business;

/**
 *
 * @author Paul W Walter
 */
public class DeviceTO implements IBusinessObject{
    
    private long theId;
    private String title;
    private String description;
    private long environmentID;

    // Getter Setter
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEnvironmentID() {
        return environmentID;
    }

    public void setEnvironmentID(long environmentID) {
        this.environmentID = environmentID;
    }

    public long getTheId() {
        return theId;
    }

    public void setTheId(long theId) {
        this.theId = theId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    // Constructor
    public DeviceTO(long theId, String title, String description, long environmentID) {
        this.theId = theId;
        this.title = title;
        this.description = description;
        this.environmentID = environmentID;
    }
    
    
    
//    public Device(long a_id, String a_title, String a_desc, long a_evironmentID)
//    {
//        
//    }
    
    
}
