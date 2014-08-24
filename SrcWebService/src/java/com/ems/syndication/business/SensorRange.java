/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

/**
 *
 * @author Paul W Walter
 */
public class SensorRange extends ADescription {
    
    public Double minThreshold;
    public Double maxThreshold;
    public long sensorId;
    public int sensorChannelNum;
    public int priority;
    
    public SensorRange()
    {
        super();
    }
    
    public SensorRange(long a_id, String a_title, String a_desc, double a_minThreshold, double a_maxThreshold, int a_priority, long a_sensor_id, int a_sensor_chanel_num)
    {
        super(a_id, a_title, a_desc);
        
        this.minThreshold = a_minThreshold;
        this.maxThreshold = a_maxThreshold;
        this.priority = a_priority;
        sensorChannelNum = a_sensor_chanel_num;
        this.sensorId = a_sensor_id;
    }
}
