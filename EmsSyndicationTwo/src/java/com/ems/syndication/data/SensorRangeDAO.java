/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.data;

import com.ems.syndication.business.SensorRange;
import com.ems.syndication.constants.EmsSyndicationConstants;
import com.ems.utils.DAOException;
import com.ems.utils.DAOFactory;
import com.ems.utils.DAOUtil;
import com.ems.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Paul W Walter
 */
public class SensorRangeDAO  implements IDAOInterface{

    private SyndicationDAOFactory daoFactory;
    
    public SensorRangeDAO(SyndicationDAOFactory a_daoFactory)
    {
        this.daoFactory = a_daoFactory;
    }
    
    public Object mapData(ResultSet resultSet) throws SQLException {
        
    //"    domain.domain_id, e.environment_id, sen.sensor_id, sensorReading.sensor_reading_id, sensorReading.sensor_reading_measurement, " +
    //" 	sensorReading.sensor_reading_timestamp, sensorRange.sensor_range_id, sensorRange.sensor_range_priority, sensorRange.sensor_range_title, sensorRange.sensor_range_min,  " +
    //" 	sensorRange.sensor_range_max
        
//        long domainID = resultSet.getLong("domain_id");
//        long envID = resultSet.getLong("environment_id");
       
//        long sensorReadingId = resultSet.getLong("sensor_reading_id");
//        Double sensorReadingMeasurement = resultSet.getDouble("sensor_reading_measurement");
//        Timestamp sensorReadingTimestamp = resultSet.getTimestamp("sensor_reading_timestamp");
       
        long sensorId = resultSet.getLong("sensor_id");
        int sensorChannelInt = resultSet.getInt("sensor_channel_num");
        long sensorRangeId = resultSet.getLong("sensor_range_id");
        int sensorRangePriority = resultSet.getInt("sensor_range_priority");
        String sensorRangeTitle = resultSet.getString("sensor_range_title");
        double sensorRangeMin = resultSet.getDouble("sensor_range_min");
        double sensorRangeMax = resultSet.getDouble("sensor_range_max");
        
        // Create a new Sensor Measurement TO
        return new SensorRange(sensorRangeId, sensorRangeTitle, "", sensorRangeMin, sensorRangeMax, sensorRangePriority, sensorId, sensorChannelInt );
    }

    public DAOFactory getDAOFactory() {
       return daoFactory;
    }

    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ArrayList <SensorRange> getCurrentProblemSensorRangesForDomainID(long a_domainID) throws DAOException
    {
         return (ArrayList <SensorRange>)DAOUtil.listUtil(EmsSyndicationConstants.SENSOR_RANGE_DAO_GET_SENSOR_RANGES_FOR_DOMAIN_ID, this, a_domainID);
    }
    
    public ArrayList <SensorRange> getCurrentProblemSensorRangesForEnvironmentID(long a_envID) throws DAOException
    {
         return (ArrayList <SensorRange>)DAOUtil.listUtil(EmsSyndicationConstants.SENSOR_RANGE_DAO_GET_SENSOR_RANGES_FOR_ENVIRONMENT_ID, this, a_envID);
    }
    
     public ArrayList <SensorRange> getCurrentProblemSensorRangesForSensorChannel(long a_sensorID, int a_sensor_channel_num) throws DAOException
    {
         return (ArrayList <SensorRange>)DAOUtil.listUtil(EmsSyndicationConstants.SENSOR_RANGE_DAO_GET_CURRENT_SENSOR_RANGES_FOR_SENSOR_ID_AND_SENSOR_CHANNEL_NUM, this, a_sensorID, a_sensor_channel_num);
    }

    ArrayList<SensorRange> getAllSensorRangesForSensorChannel(long a_sensorID, int a_sensorChanelNum) throws DAOException {
        return (ArrayList <SensorRange>)DAOUtil.listUtil(EmsSyndicationConstants.SENSOR_RANGE_DAO_GET_SENSOR_RANGES_FOR_SENSOR_CHANNEL_ID_AND_SENSOR_CHANNEL_NUM, this, a_sensorID, a_sensorChanelNum);
    }
    
//     public ArrayList <SensorRange> getCurrentProblemSensorRangesForEnvironmentID(long a_domainID)
//    {
//        
//    }
    
}
