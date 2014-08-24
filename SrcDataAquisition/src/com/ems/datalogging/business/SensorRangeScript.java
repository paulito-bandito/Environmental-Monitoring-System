package com.ems.datalogging.business;

/**
 * A script that will be called to use reflection in order to take the necessary actions
 * @author Paul Walter
 */
public class SensorRangeScript implements IBusinessObject {

    // VARS --------------------------------------------------------------------
    
    private long scriptId;
    private String theXML;
    private String title;
    private String description;
    private SensorRange parent;

    // CONSTRUCTORS ------------------------------------------------------------
        
    public SensorRangeScript () {
    }
    
    public SensorRangeScript ( long scriptId, String theXML, String title, String description) {
        this.scriptId    = scriptId;
        this.theXML      = theXML;
        this.title       = title;
        this.description = description;
    }

    // GETTERS AND SETTERS -----------------------------------------------------
    
    public SensorRange getParent() {
        return parent;
    }

    public void setParent(SensorRange parent) {
        this.parent = parent;
    }
    
    public String getDescription () {
        return description;
    }

    public void setDescription (String val) {
        this.description = val;
    }

    public long getScriptId () {
        return scriptId;
    }

    public void setScriptId (long val) {
        this.scriptId = val;
    }

    public String getTheXML () {
        return theXML;
    }

    public void setTheXML (String val) {
        this.theXML = val;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String val) {
        this.title = val;
    }

    // ACTIONS -----------------------------------------------------------------
    
    /**
     * If a SensorRangeScript needs to notify the stewards registered to a domain
     * this is what the SensorRangeScript will use to call it. <br/>
     * This method will in turn call its parent (Sensor Range) which will call
     * its parent (Sensor) which will call its parent (the Environment) which 
     * will call its parent (the Domain) which will notify all Stewards 
     * registered to that Domain using the method NotifyDomainStewards(String alertMessage)
     * in each class mentioned.<br/>
     * This method can be called by the executeXMLScript() method, which may alternately
     * call a controller device sometime in the future scope.
     */
    public void NotifyDomainStewards(String alertMessage)
    {
        parent.NotifyDomainStewards(alertMessage);
    }
    
    /**
     * This method will attempt to execute the XML reflection script inside of it
     * using DOM. 
     * @throws java.lang.Exception
     */
    public void executeXMLScript() throws Exception
    {
        // TRY AND USE REFLECTION TO PARSE AND EXECUTE THIS SCRIPT
        
        NotifyDomainStewards("Test the cascade");
        
        
        
        
    }
    
    // Override ----------------------------------------------------------------

    /**
     * The  ID is unique for each SensorRangeScript. So this should compare SensorRangeScript by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
        SensorRangeScript otherType = (SensorRangeScript)other;
        
        //SEE IF ITS ID IS THE SAME AS MY ID
        if(otherType.getScriptId() == this.getScriptId())
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
       return "SensorRangeScript[contactDataTypeId=" +scriptId
                + ", contactDataTypeDesc='"  + title
                + "', contactDataTypeDesc='"  + description
                + "', contactDataTypeTitle='" + theXML                
                + "']";
    }
    
    

}

