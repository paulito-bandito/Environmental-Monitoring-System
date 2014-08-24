package com.ems.datalogging.data.db;


import com.ems.datalogging.business.SensorChannel;
import com.ems.datalogging.business.SensorMetricType;
import com.ems.datalogging.business.SensorRange;
import com.ems.datalogging.business.SensorTypeTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import com.ems.datalogging.data.utils.DAOUtil;
import com.ems.datalogging.data.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 Handles database requests for the Sensor Type objects.
 * <br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 */
@SuppressWarnings("unchecked")    
public class SensorChannelDAO implements IDAOInterface{

   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    private static final boolean DEBUG = false;
    private EmsDataLoggingDbDAOFactory daoFactory;
    private static final String SQL_FIND_BY_ID = EmsDataLoggingConstants.SENSOR_CHANNEL_DAO_FIND_CHANNEL_BY_SENSOR_ID;
    private static final String SQL_INSERT = EmsDataLoggingConstants.SENSOR_CHANNEL_DAO_INSERT_CHANNEL;
   
    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an SensorMetricType DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this Sensor Type DAO for.
     */
    SensorChannelDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    // Actions ------------------------------------------------------------------------------------

    

    ArrayList<SensorChannel> getChannelsForSpecificSensor(Long aId) throws DAOException {
        return list(SQL_FIND_BY_ID, aId);
    }
    
   
    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private ArrayList<SensorChannel> list(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList<SensorChannel>)DAOUtil.listUtil(sql, this, values);   
    }

    // IMPLEMENTED METHODS FROM EmsDataObjectInterface ------------------------------------------------------------------------------------
   
    /**
    * Returns the reference to its DAO Factory Object associated with this class
    * @return DAOFactory  
    * @see EmsDataObjectInterface   
    */ 
    public EmsDataLoggingDbDAOFactory getDAOFactory()
    {
        return this.daoFactory;
    }
    /**
    * Maps the incoming db data to an object
    * @param resultSet
    * @return Object
    * @throws SQLException If something fails at database level.
    * @see EmsDataObjectInterface   
    */
    public Object mapData(ResultSet resultSet) throws SQLException {
        try {

            // GET VARS
            SensorChannel returnObj = null;
            long theId = resultSet.getLong("sensor_id");
            int channelNum = resultSet.getInt("sensor_channel_num");
            long theSensorMetricTypeID = resultSet.getInt("sensor_metric_type_id");
            long theSensorTypeID = resultSet.getInt("sensor_metric_type_id");

            // Get the sensor range
            SensorRangeDAO sensorRangeDAO = daoFactory.getSensorRangeDAO();
            ArrayList<SensorRange> sensorRanges = sensorRangeDAO.listForSpecificSensorChannel(theId, channelNum);

            // get the metric type
            SensorMetricTypeDAO sensorMetricTypeDAO = daoFactory.getSensorMetricTypeDAO();
            SensorMetricType sensorMetricType = sensorMetricTypeDAO.find(theSensorMetricTypeID);
            
            // get the sensor type
            SensorTypeDAO sensorTypeDAO = daoFactory.getSensorTypeDAO();
            SensorTypeTO sensorType = sensorTypeDAO.find(theSensorTypeID);

            // CONSTRUCT THE CONTACT DATA OBJ
            returnObj = new SensorChannel(theId, channelNum, sensorMetricType, sensorRanges, sensorType);
            
            return returnObj;
            
        } catch (DAOException ex) {
            Logger.getLogger(SensorChannelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    /**
    * Not supported yet
    * @throws data.DAOException
    */
    public void save(Object incomingObj) throws DAOException {
        
        SensorChannel a_sensorChannel = (SensorChannel)incomingObj;
        if (a_sensorChannel.getSensorId() == -1) {
            throw new IllegalArgumentException("Sensor Channel's Sensor ID is negative one, you need more info to create a SensorChannel (the sensorID).");
        }
        
         //
        // 2.) Save a sensor channel
        //
        Object[] sensorChannelValues = {
            a_sensorChannel.getSensorId(),
            a_sensorChannel.getChannelNum(),
            a_sensorChannel.getSensorMetricType().getTheId(),   
            a_sensorChannel.sensorChannelTypeTO.getTheId()
        };

        //
        // IDENTIFY WHICH COLUMN YOU WANT A REFERENCE TO ONCE THIS METHOD RETURNS,
        //      So we can update that object to have the correct id associated with it.
        //
        String targetGeneratedColumn = "sensor_id";
        long theId = DAOUtil.createUtil(SQL_INSERT, this, sensorChannelValues, targetGeneratedColumn  ); 
        a_sensorChannel.setSensorId(theId);
        
        //
        // 3.) Save the sensor ranges
        //
        ArrayList<SensorRange> listOfSensorRanges = a_sensorChannel.getSensorRanges();
        SensorRangeDAO sensorRangeDAO = this.daoFactory.getSensorRangeDAO();
        if(listOfSensorRanges != null)
        {
            for(int i=0; i<listOfSensorRanges.size(); i++)
            {
                sensorRangeDAO.save(listOfSensorRanges.get(i));
            }  
        }
        
    }

}

