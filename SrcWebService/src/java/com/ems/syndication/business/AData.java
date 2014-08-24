/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

/**
 *
 * @author Paul W Walter
 */
public abstract class AData extends AIdentifer{
    
    public long mesurementDate;
    
    public AData()
    {
        super();
    }
    
    public AData(long a_id, long a_measurementDate)
    {
        super(a_id);
        this.mesurementDate = a_measurementDate;
    }
}
