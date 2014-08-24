package com.ems.datalogging.business;

/**
 * Whether this digital sensor is Analog or Digital
 * @author Paul Walter
 */
public class SensorTypeTO implements IBusinessObject {

    // VARS---------------------------------------------------------------------
    
    private long theId;
    private String theName;
    //private Sensor parent;

    // CONSTRUCTORS ------------------------------------------------------------
    
    public SensorTypeTO () {
    }
    
    public SensorTypeTO (long theId, String theName) {
        this.theId = theId;
        this.theName = theName;
    }

    // GETTERS AND SETTERS -----------------------------------------------------
    
    public long getTheId () {
        return theId;
    }

    public void setTheId (long val) {
        this.theId = val;
    }

//    public Sensor getParent() {
//        return parent;
//    }
//
//    public void setParent(Sensor parent) {
//        this.parent = parent;
//    }

    
    
    public String getTheName () {
        return theName;
    }

    public void setTheName (String val) {
        this.theName = val;
    }

     // Override -----------------------------------------------------------------------------------

    /**
     * The  ID is unique for each Transfer Object. So this should compare ContactDataType by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
       SensorTypeTO otherType = (SensorTypeTO)other;
        
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
        String returnStr =  "SensorType[theId=" +theId
                            + ", theName='" + theName
                            + "']";
        
        // RETURN VALUE
        return returnStr;
   
    }
}

