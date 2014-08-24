/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

/**
 *
 * @author Paul W Walter
 */
public abstract class ADescription extends AIdentifer {
    public String title;
    public String description;
    
    public ADescription()
    {
        super();
    }
    
    public ADescription(long a_id, String a_title, String a_description)
    {
        super(a_id);
        this.description = a_description;
        this.title = a_title;
    }
}
