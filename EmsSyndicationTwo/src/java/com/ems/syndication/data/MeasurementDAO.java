/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.data;


import com.ems.syndication.business.Measurement;
import com.ems.syndication.constants.EmsSyndicationConstants;
import com.ems.utils.DAOException;
import com.ems.utils.DAOFactory;
import com.ems.utils.DAOUtil;
import com.ems.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Paul W Walter
 */
public class MeasurementDAO implements IDAOInterface{
    
    private SyndicationDAOFactory daoFactory;
    private static final String GET_SENSOR_MEASUREMENTS_FOR_CONTIGUOUS_CHUNK = EmsSyndicationConstants.SENSORREADING_DAO_GET_READINGS_FOR_SPECIFIC_CONTIGUOUS_CHUNK;
    private static final String GET_SENSOR_MEASUREMENT_MOST_CURRENT = EmsSyndicationConstants.SENSORREADING_DAO_GET_MOST_CURRENT_READING_FOR_SPECIFIC_SENSOR_AND_CHANNEL_NUM;
        
    public MeasurementDAO(SyndicationDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public Object mapData(ResultSet resultSet) throws SQLException {
        
        long readingID = resultSet.getLong("sensor_reading_id");
        long sensorId = resultSet.getLong("sensor_id");
        int channelNum = resultSet.getInt("sensor_channel_num");
        Timestamp timestamp = resultSet.getTimestamp("sensor_reading_timestamp");
        Double sensorMeasurement =resultSet.getDouble("sensor_reading_measurement");
        
        // Create a new Sensor Measurement TO
        return new Measurement(readingID, sensorId, timestamp.getTime(), sensorMeasurement);
        
    }

    public DAOFactory getDAOFactory() {
          return this.daoFactory;
    }

    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Will return a subset of sensor measurements, usinging the userToken as authentification. 
     * 
     * Using the 'numberStart' and 'numberStop' attributes you can specify how big a chunk starting
     * at what measurement you wish to obtain. For example if you wish to only have the first 2 measurements
     * associated with a specific sensor you would set 'numberStart' to 1 and 'numberStop' to 2.
     * 
     * @param userToken
     * @param DeviceId
     * @param numberStart
     * @param numberStop
     * @return
     */
    public ArrayList <Measurement> getSensorMeasurements( long aSensorId, int aSensorChannel, int numberStart, int numberStop) throws DAOException
    {
        return (ArrayList <Measurement>)DAOUtil.listUtil(GET_SENSOR_MEASUREMENTS_FOR_CONTIGUOUS_CHUNK, this, aSensorId, aSensorChannel, numberStart, numberStop);
        
    }
    
    /**
     * Returns the most current measurement availible.
     * @param aSensorId
     * @param aSensorChannel
     * @return
     * @throws com.ems.utils.DAOException
     */
    public Measurement getMostCurrentSensorMeasurement( long aSensorId, int aSensorChannel) throws DAOException
    {
        return (Measurement)DAOUtil.findUtil(GET_SENSOR_MEASUREMENT_MOST_CURRENT, this, aSensorId, aSensorChannel);
        
    }
}
