package com.ems.datalogging.business;

import java.util.ArrayList; 
/**
 * User Transfer Object, in accordance with the J2SE factory pattern
 * 
 *  @author Paul Walter 
 */
public class UserTO implements IBusinessObject{

    // VARS --------------------------------------------------------------------
    
    private String userName;
    private long userId;
    private String password;
    private String status;
    private String role;
    private ArrayList<ContactDataTO> contactData;
    
    // CONSTRUCTORS ------------------------------------------------------------
    
    public UserTO () {
    }
    
    public UserTO (long userId, String userName, String password, String status, String role, ArrayList<ContactDataTO> contactData) 
    {
        this.userName    = userName;
        this.userId      = userId;
        this.password    = password;
        this.status      = status;
        this.role        = role;
        this.contactData = contactData;
        
        // LOOP THROUGH THE COLLECTIONS OF THIS OBJECT AND SET THEIR PARENTS' TO THIS OBJECT.
               
        // CONTACT DATA
        for(int a=0; a<this.contactData.size(); a++){
            this.contactData.get(a).setParent(this);
        }
    }
 

    // GETTERS AND SETTERS -----------------------------------------------------

   
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public ArrayList<ContactDataTO> getContactData () {
        return contactData;
    }

    public void setContactData (ArrayList<ContactDataTO> val) {
        this.contactData = val;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String val) {
        this.password = val;
    }

    public String getRole () {
        return role;
    }

    public void setRole (String val) {
        this.role = val;
    }

    public long getUserId () {
        return userId;
    }

    public void setUserId (long val) {
        this.userId = val;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String val) {
        this.userName = val;
    }

    public boolean addContactData(ContactDataTO thisData)
    {
        this.contactData.add(thisData);
        return true;
    }
    public boolean removeContactData(ContactDataTO thisData)
    {
        this.contactData.remove(thisData);
        return true;
    }
    // Actions -----------------------------------------------------------------
    
    public void sendMessage(String message)
    {
        // TODO
        System.err.println("Message sent for user: " + this.userName + "/n/t'" + message +  "'" );
    }
    
     // Override -----------------------------------------------------------------------------------

    /**
     * The  ID is unique for each Transfer Object. So this should compare ContactDataType by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        // CAST INCOMING OBJECT
       UserTO otherType = (UserTO)other;
        
        //SEE IF ITS ID IS THE SAME AS MY ID
        if(otherType.getUserId() == this.getUserId())
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
        String returnStr =  "User[userId=" +userId
                            + ", userName='" + userName
                            + "', password='"  + password 
                            + "', status='"  + status 
                            + "', role='"  + role
                            + "', contactData=("; 
       
        // INSERT THE CONTACT DATA
        for(int i=0; i<contactData.size(); i++)
        {
            returnStr +=  contactData.get(i).toString()+ ", ";
        }
        
        returnStr+= ")]";
        
        // RETURN VALUE
        return returnStr;
   
    }
}

