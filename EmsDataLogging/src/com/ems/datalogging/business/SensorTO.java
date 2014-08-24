package com.ems.datalogging.business;

import com.ems.datalogging.data.db.SensorReadingDAO;
import java.util.ArrayList; 
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Sensor Transfer Object, in accordance with the J2SE factory pattern
 * 
 *  @author Paul Walter 
 */
public class SensorTO implements IBusinessObject {

    
    // VARS --------------------------------------------------------------------

    private long theId;
    private String theTitle;
    private String theDesc;
    private double updateSpeed;
    private String networkAddress;
    private Thread runner;
    private SensorTypeTO sensorType;
    private ArrayList<SensorChannel> sensorChannels;
    private ArrayList<UserTO> callList;
    private long environmentID;
    private String environmentName;
    //private Environment parent;


    
    // CONSTRUCTORS ------------------------------------------------------------

    
    /**
     * Create a new Sensor Transfer Object.
     * 
     * @param aId
     * @param aTitle
     * @param aDesc
     * @param theNetworkAddress
     * @param updateSpeed
     * @param sensorType
     * @param aSensorChannels
     */
    public SensorTO (long aId, String aTitle, String aDesc, String theNetworkAddress, double updateSpeed, 
                    SensorTypeTO sensorType, ArrayList<SensorChannel> aSensorChannels, ArrayList<UserTO> a_callList, long aEnvId, String aEnvName) {
        
        this.theId = aId;
        this.theTitle = aTitle;
        this.theDesc = aDesc;
        this.networkAddress = theNetworkAddress;
        this.updateSpeed = updateSpeed;        
        this.sensorType = sensorType;
        this.sensorChannels = aSensorChannels;
        this.callList = a_callList;
        this.environmentID = aEnvId;
        this.environmentName = aEnvName;
    }
  
    // GETTERS AND SETTERS -----------------------------------------------------
    
    public ArrayList<UserTO> getCallList() {
        return callList;
    }

    public void setCallList(ArrayList<UserTO> callList) {
        this.callList = callList;
    }

    public String getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(String networkAddress) {
        this.networkAddress = networkAddress;
    }

    public ArrayList<SensorChannel> getSensorChannels() {
        return sensorChannels;
    }

    public void setSensorChannels(ArrayList<SensorChannel> sensorChannels) {
        this.sensorChannels = sensorChannels;
    }

    public SensorTypeTO getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorTypeTO sensorType) {
        this.sensorType = sensorType;
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
        
        // set the ids of the sensor channels contained in this object
        if(sensorChannels != null)
        {
            for(int i=0; i<sensorChannels.size(); i++)
            {
                SensorChannel thisChannel = sensorChannels.get(i);
                thisChannel.setSensorId(this.theId);
            }  
        }
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

    public long getEnvironmentID() {
        return environmentID;
    }

    public void setEnvironmentID(long environmentID) {
        this.environmentID = environmentID;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    
    
    

    // Override -----------------------------------------------------------------------------------

    /**
     * The  ID is unique for each Transfer Object. So this should compare ContactDataType by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
       SensorTO otherType = (SensorTO)other;
        
        //SEE IF ITS ID IS THE SAME AS MY ID
        if(otherType.getTheId() == this.getTheId())
        {
            return true;
        }
        return false;
    }
   
    /**
     * Returns the String representation of this Transfer Object. Not really required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {        
      //CREATE THE FIRST PART
        String returnStr =  "Sensor[theId=" +theId
                            + ", theTitle='" + theTitle
                            + "', theDesc='"  + theDesc 
                            + "', runner='"  + runner 
                            + "', sensorType='"  + sensorType;        
         
        returnStr+= ")]";
        
        // RETURN VALUE
        return returnStr;
   
    }
}

