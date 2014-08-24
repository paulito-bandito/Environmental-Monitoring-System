/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

/**
 *
 * @author Paul W Walter
 */
public abstract class AIdentifer {
    public long id;
    
    public AIdentifer()
    {
        super();
    }
    
    public AIdentifer(long a_id)
    {
        this.id = a_id;
    }
}
