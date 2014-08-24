/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

/**
 *
 * @author Paul W Walter
 */
public class Environment extends ADescription {
    
    public long domainID;
    public long x;
    public long y;
    
    public long timestampCreated;
    
    public Environment()
    {
        super();
    }
    
    public Environment(long a_id, long domain_id, String a_title, String a_description, long a_timestampCreated, long a_x, long a_y)
    {
        super( a_id,  a_title,  a_description);
        this.timestampCreated = a_timestampCreated;
        this.domainID = domain_id;
        this.x = a_x;
        this.y = a_y;
    }
}
