package com.ems.datalogging.business;

import java.sql.Timestamp;


public class SensorReading implements IBusinessObject {

    //
    // VARS --------------------------------------------------------------------
    //
    private long theId;
    private long sensorId;
    private int channelNum;
    private java.sql.Timestamp timestamp;
    private double measurement;
    //private Sensor parent;
    
    //
    // CONSTRUCTORS ------------------------------------------------------------
    //
    
    public SensorReading () {
    }

    public SensorReading (long aId, long aSensorId, int aChannelNum, java.sql.Timestamp aTimestamp, double aMeasurement) {
        this.theId = aId;
        this.sensorId = aSensorId;
        this.channelNum = aChannelNum;
        this.timestamp = aTimestamp;
        this.measurement = aMeasurement;
    }
    
    //
    // GETTERS AND SETTERS -----------------------------------------------------
    //

    public int getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public long getTheId() {
        return theId;
    }

    public void setTheId(long theId) {
        this.theId = theId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    
  
    
   
    //
    // Override ----------------------------------------------------------------
    //
    
    /**
     * The  ID is unique for each ContactDataType. So this should compare ContactDataType by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
        SensorReading otherType = (SensorReading)other;
        
        //SEE IF ITS ID IS THE SAME AS MY ID
        if(otherType.getTheId() == this.getTheId())
        {
            return true;
        }
        return false;
    }
   
    /**
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
            
        return "SensorReading[theId=" +theId
                + ", theDate='" + timestamp
                + "', measurement='"  + measurement                
                + "']";
    }
}

