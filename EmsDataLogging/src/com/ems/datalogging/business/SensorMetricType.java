package com.ems.datalogging.business;


public class SensorMetricType implements IBusinessObject{

    // VARS --------------------------------------------------------------------
    
    private String theName;
    private String theDescription;
    private long theId;
    //private Sensor parent;

    // CONSTRUCTORS ------------------------------------------------------------

    public SensorMetricType () {
    }
    
    public SensorMetricType (long theId, String theName, String theDescription ) {
        this.theId = theId;
        this.theName = theName;
        this.theDescription = theDescription;
    }

    // GETTERS AND SETTERS -----------------------------------------------------
    
//    public Sensor getParent() {
//        return parent;
//    }
//
//    public void setParent(Sensor parent) {
//        this.parent = parent;
//    }
    
    public String getTheDescription () {
        return theDescription;
    }

    public void setTheDescription (String val) {
        this.theDescription = val;
    }

    public long getTheId () {
        return theId;
    }

    public void setTheId (long val) {
        this.theId = val;
    }

    public String getTheName () {
        return theName;
    }

    public void setTheName (String val) {
        this.theName = val;
    }

    // Override -----------------------------------------------------------------------------------

    /**
     * The  ID is unique for each ContactDataType. So this should compare ContactDataType by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
        SensorMetricType otherType = (SensorMetricType)other;
        
        //SEE IF ITS ID IS THE SAME AS MY ID
        if(otherType.getTheId()== this.getTheId())
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
            
        return "SensorMetricType[theId=" +theId
                + ", theTitle='" + theName
                + "', theDescription='"  + theDescription                
                + "']";
    }
}

