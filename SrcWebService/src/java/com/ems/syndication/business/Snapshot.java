/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

/**
 *
 * @author Paul W Walter
 */
public class Snapshot extends AData {
    public String uri;
    
    public Snapshot()
    {
        super();
    }
     
    public Snapshot(long a_id, long a_measurementDate, String a_uri )
    {
        super(a_id, a_measurementDate);
        this.uri = a_uri;
    }
}
