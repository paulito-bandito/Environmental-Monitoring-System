/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

/**
 *
 * @author Paul W Walter
 */
public class Measurement extends AData {
    public double measurement;
    public long sensor_id;
    
    public Measurement()
    {
        super();
    }
    
    public Measurement(long a_id, long a_sensor_id, long a_measurementDate, double a_measurement )
    {
        super(a_id, a_measurementDate);
        this.measurement = a_measurement;
        this.sensor_id = a_sensor_id;
    }
}
