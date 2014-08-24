/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

import com.ems.syndication.constants.EmsSyndicationConstants;

/**
 *
 * @author Paul W Walter
 */
public class Camera extends ACaptureData{
    
    public Camera()
    {
        super();
    }
    
    public Camera(long a_id, String aTitle, String aDesc)
    {
        super(a_id, aTitle, aDesc, EmsSyndicationConstants.DEVICETYPE_DAO_CAMERA_TYPE);
    }

}
