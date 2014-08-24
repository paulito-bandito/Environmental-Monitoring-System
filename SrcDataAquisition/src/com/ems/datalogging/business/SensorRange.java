package com.ems.datalogging.business;

import java.util.ArrayList; 

public class SensorRange implements IBusinessObject {

    // VARS --------------------------------------------------------------------
    
    private long theId;
    private long sensorID;
    private int channelNum;
    private String theTitle;
    private double minimumThreshold;
    private double maximumThreshold;
    private ArrayList<SensorRangeScript> scriptCollection;
    //private Sensor parent;


    // CONSTRUCTORS ------------------------------------------------------------
    
    public SensorRange () {
        theId = -1;
    }
    
    public SensorRange (long aId, long aSensorID, int aChannelNum, String aTitle, double aMinimumThreshold, double aMaximumThreshold, ArrayList<SensorRangeScript> aScriptCollection) 
    {
        this.theId = aId;
        this.sensorID = aSensorID;
        this.channelNum = aChannelNum;
        this.minimumThreshold = aMinimumThreshold;
        this.maximumThreshold = aMaximumThreshold;
        this.theTitle = aTitle;
        this.scriptCollection = aScriptCollection;
                
        // SET PARENT FOR SCRIPTS
//        for(int a=0; a<this.scriptCollection.size(); a++){
//            this.scriptCollection.get(a).setParent(this);
//        }
    }

    

    // GETTERS AND SETTERS -----------------------------------------------------
    
    public int getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }

    public long getSensorID() {
        return sensorID;
    }

    public void setSensorID(long sensorID) {
        this.sensorID = sensorID;
    }
    
    public long getTheId() {
        return theId;
    }

    public void setTheId(long theId) {
        this.theId = theId;
    }
    
    public double getMax() {
        return maximumThreshold;
    }

    public void setMax(double max) {
        this.maximumThreshold = max;
    }

    public double getMin() {
        return minimumThreshold;
    }

    public void setMin(double min) {
        this.minimumThreshold = min;
    }

    public String getTitle() {
        return theTitle;
    }

    public void setTitle(String title) {
        this.theTitle = title;
    }   

//    public Sensor getParent() {
//        return parent;
//    }
//
//    public void setParent(Sensor parent) {
//        this.parent = parent;
//    }
    public ArrayList<SensorRangeScript> getScriptCollection () {
        return scriptCollection;
    }

    public void setScriptCollection (ArrayList<SensorRangeScript> val) {
        this.scriptCollection = val;
    }

    public boolean addScript (SensorRangeScript thisScript) {
        return true;
    }

    public boolean removeScript (SensorRangeScript thisScript) {
        return true;
    }

    // Actions -----------------------------------------------------------------
    
    /**
     * If a SensorRangeScript needs to notify the stewards registered to a domain
     * this is what the SensorRangeScript will use to call it. <br/>
     * This method will in turn call its parent (Sensor) which will call its parent
     * (the Environment) which will call its parent (the Domain) which will notify
     * all Stewards registered to that Domain using the method 
     * NotifyDomainStewards(String alertMessage)
     * in each class mentioned.
     */
    public void NotifyDomainStewards(String alertMessage)
    {
        //parent.NotifyDomainStewards(alertMessage);
    }
    
    // Override -----------------------------------------------------------------------------------

    /**
     * The  ID is unique for each SensorRange. So this should compare SensorRangeScript by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
        SensorRange otherType = (SensorRange)other;
        
        //SEE IF ITS ID IS THE SAME AS MY ID
        if(otherType.getTheId() == this.getTheId())
        {
            return true;
        }
        return false;
    }
   
    /**
     * Returns the String representation of this Sensor Range. Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
       
        return "SensorRange[theId=" +theId
                + ", theTitle='" + theTitle      
                + "', minimumThreshold='"  + minimumThreshold
                + "', maximumThreshold='"  + maximumThreshold
                + "', scriptCollection=("  + scriptCollection.size()         
                + ")]";
    }
    
}

