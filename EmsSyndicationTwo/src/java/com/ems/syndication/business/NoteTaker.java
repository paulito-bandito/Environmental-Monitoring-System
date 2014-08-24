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
public class NoteTaker extends ACaptureData {
    
    public NoteTaker()
    {
        super();
    }
    
    public NoteTaker(long a_id, String aTitle, String aDesc )
    {
        super(a_id, aTitle, aDesc, EmsSyndicationConstants.DEVICETYPE_DAO_NOTE_TAKER);
    }
}
