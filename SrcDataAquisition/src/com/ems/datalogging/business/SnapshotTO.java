package com.ems.datalogging.business;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class SnapshotTO implements IBusinessObject{

    // VARS --------------------------------------------------------------------
    
    private long snapshotId;
    private long cameraId;
    private java.sql.Timestamp theTimestamp;
    private String fileName;
    private CameraTO parent;

    // CONSTRUCTORS ------------------------------------------------------------

    public SnapshotTO () {
    }

    public SnapshotTO (long snapshotId, long cameraId, String fileName, Timestamp theTimestamp ) {
           this.snapshotId = snapshotId;
           this.cameraId = cameraId;
           this.fileName = fileName;
           this.theTimestamp = theTimestamp;
    }
     
    // GETTERS AND SETTERS -----------------------------------------------------

    

    public long getCameraId() {
        return cameraId;
    }

    public void setCameraId(long cameraId) {
        this.cameraId = cameraId;
    }

    public CameraTO getParent() {
        return parent;
    }

    public void setParent(CameraTO parent) {
        this.parent = parent;
    }
    
    public String getFileName () {
        return fileName;
    }

    public void setFileName (String val) {
        this.fileName = val;
    }

    public long getId () {
        return snapshotId;
    }

    public void setId (long val) {
        this.snapshotId = val;
    }

    public java.sql.Timestamp getTimestamp () {
        return theTimestamp;
    }

    public void setTimestamp (java.sql.Timestamp val) {
        this.theTimestamp = val;
    }
    
    public String getFormattedDateString () {
        long theMillis = theTimestamp.getTime();
        Date thisDate = new Date(theMillis);
        // FORMAT THE DATE TO LOOK LIKE "09/12/08 EST"
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yy z");
        String stringDate = s.format(thisDate);
        return stringDate;
    }

    public String getFormattedHourString () {
        long theMillis = theTimestamp.getTime();
        Date thisDate = new Date(theMillis);
        // FORMAT THE DATE TO LOOK LIKE "10:31 AM"
        SimpleDateFormat s = new SimpleDateFormat("hh:mm a");
        String stringDate = s.format(thisDate);
        return stringDate;
    }
    
     // Override ---------------------------------------------------------------

    /**
     * The  ID is unique for each Transfer Object. So this should compare ContactDataType by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
       SnapshotTO otherType = (SnapshotTO)other;
        
        //SEE IF ITS ID IS THE SAME AS MY ID
        if(otherType.getId() == this.getId())
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
        String returnStr =  "Snapshot[snapshotId="  + snapshotId
                            + "', cameraId='"       + cameraId 
                            + "', fileName='"       + fileName 
                            + "', theTimestamp='"   + theTimestamp 
                            + ")]";
        
        // RETURN VALUE
        return returnStr;
   
    }

}

