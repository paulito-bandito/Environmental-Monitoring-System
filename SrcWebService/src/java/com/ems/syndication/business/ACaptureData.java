/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

/**
 *
 * @author Paul W Walter
 */
public abstract class ACaptureData extends ADescription {
    public String captureDataType; // is this a camera, or sensor, or what.
    
    public ACaptureData()
    {
        super();
    }
    
    public ACaptureData(long a_id, String a_title, String a_description, String a_captureDataType)
    {
        super(a_id,  a_title, a_description);
        this.captureDataType = a_captureDataType;
    }
}
