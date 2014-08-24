package com.ems.datalogging.business;


public class ContactDataTO implements IBusinessObject{

    private long contactDataId;
    private String contactDataTitle;
    private String contactDataAddress;
    private String contactDataDesc;
    private ContactDataTypeTO contactDataType;
    private long userID;
    private UserTO parent;


    public ContactDataTO () {
    }
    
    public ContactDataTO (long contactDataId, String contactDataTitle, String contactDataAddress, String contactDataDesc, ContactDataTypeTO contactDataType, long userId)
    {
        this.contactDataId      = contactDataId;
        this.contactDataAddress = contactDataAddress;
        this.contactDataDesc    = contactDataDesc;
        this.contactDataType    = contactDataType;
        this.userID             = userId;
        
        // SET THEIR PARENTS' TO THIS OBJECT.
        this.contactDataType.setParent(this);
    }
    
    
    public UserTO getParent() {
        return parent;
    }

    public void setParent(UserTO parent) {
        this.parent = parent;
    }
    
    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getContactDataTitle() {
        return contactDataTitle;
    }

    public void setContactDataTitle(String contactDataTitle) {
        this.contactDataTitle = contactDataTitle;
    }
    public ContactDataTypeTO getContactDataType() {
        return contactDataType;
    }

    public void setContactDataType(ContactDataTypeTO contactDataType) {
        this.contactDataType = contactDataType;
    }
    
    public String getContactDataAddress () {
        return contactDataAddress;
    }

    public void setContactDataAddress (String val) {
        this.contactDataAddress = val;
    }

    public String getContactDataDesc () {
        return contactDataDesc;
    }

    public void setContactDataDesc (String val) {
        this.contactDataDesc = val;
    }

    public long getContactDataId () {
        return contactDataId;
    }

    public void setContactDataId (long val) {
        this.contactDataId = val;
    }
  // Override -----------------------------------------------------------------------------------

    /**
     * The  ID is unique for each ContactDataType. So this should compare ContactDataType by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
        ContactDataTO otherType = (ContactDataTO)other;
        
        //SEE IF ITS ID IS THE SAME AS MY ID
        if(otherType.getContactDataId() == this.getContactDataId())
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
            
        return "ContactData[contactDataId=" +contactDataId
                + ", contactDataTitle='" + contactDataTitle
                + "', contactDataAddress='"  + contactDataAddress 
                + "', contactDataDesc='"  + contactDataDesc 
                + "', contactDataType='"  + (contactDataType.toString())
                + "', userID='"  +userID
                + "']";
    }
}

