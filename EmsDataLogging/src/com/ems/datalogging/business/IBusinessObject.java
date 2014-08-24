package com.ems.datalogging.business;

/**
 * This will ensure that the developer must override the toString method, and the equals
 * @author Admin
 */
public interface IBusinessObject {
  /**
     * Returns the String representation of this Transfer Object. Not really required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    
    @Override
    public String toString();
   /**
     * The  ID is unique for each Transfer Object. So this should compare ContactDataType by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other);
   
}
