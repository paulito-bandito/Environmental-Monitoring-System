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
public class SensorChannel extends ACaptureData {
    
    public int channelNum;
    public String sensorMetricTypeName;
    public String sensorTypeName;
    
    
    public SensorChannel()
    {
        super();
    }
    
    public SensorChannel(long a_id, String a_title, String a_desc, int a_channelNum, String a_sensorType, String a_sensorMetricType)
    {
        super(a_id, a_title, a_desc, EmsSyndicationConstants.DEVICETYPE_DAO_SENSOR_TYPE);
        this.channelNum = a_channelNum;
        this.sensorMetricTypeName = a_sensorMetricType;
        this.sensorTypeName = a_sensorType;
    }
}
