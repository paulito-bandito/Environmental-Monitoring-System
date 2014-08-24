package com.ems.datalogging.business;


public class ContactDataTypeTO implements IBusinessObject{

    private long contactDataTypeId;
    private String contactDataTypeTitle;
    private String contactDataTypeDesc;
    private ContactDataTO parent;

    public ContactDataTypeTO () {
    }
    
    public ContactDataTypeTO (long contactDataTypeId, String contactDataTypeTitle, String contactDataTypeDesc) 
    {
        this.contactDataTypeId = contactDataTypeId;
        this.contactDataTypeTitle = contactDataTypeTitle;
        this.contactDataTypeDesc = contactDataTypeDesc;
    }

    
   public ContactDataTO getParent() {
        return parent;
   }

   public void setParent(ContactDataTO parent) {
        this.parent = parent;
   }
   
    
   public String getContactDataTypeDesc () {
        return contactDataTypeDesc;
    }

    public void setContactDataTypeDesc (String val) {
        this.contactDataTypeDesc = val;
    }

    public long getContactDataTypeId () {
        return contactDataTypeId;
    }

    public void setContactDataTypeId (long val) {
        this.contactDataTypeId = val;
    }

    public String getContactDataTypeTitle () {
        return contactDataTypeTitle;
    }

    public void setContactDataTypeTitle (String val) {
        this.contactDataTypeTitle = val;
    }

     // Override -----------------------------------------------------------------------------------

    /**
     * The  ID is unique for each ContactDataType. So this should compare ContactDataType by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
        ContactDataTypeTO otherType = (ContactDataTypeTO)other;
        
        //SEE IF ITS ID IS THE SAME AS MY ID
        if(otherType.getContactDataTypeId() == this.getContactDataTypeId())
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
        return "ContactDataType[contactDataTypeId=" +contactDataTypeId
                + ", contactDataTypeTitle='" + contactDataTypeTitle
                + "', contactDataTypeDesc='"  + contactDataTypeDesc + "']";
    }
}

