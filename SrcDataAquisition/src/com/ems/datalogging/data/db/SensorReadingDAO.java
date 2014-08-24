package com.ems.datalogging.data.db;

import com.ems.datalogging.business.SensorReading;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import com.ems.datalogging.data.utils.DAOUtil;
import com.ems.datalogging.data.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;



/**
 * Handles database requests for the SensorReading objects.<br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 *   
 */
@SuppressWarnings("unchecked")    
public class SensorReadingDAO implements IDAOInterface {
   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = "ems";
    private static final boolean DEBUG = false;
    private EmsDataLoggingDbDAOFactory daoFactory;
    private static final String SQL_INSERT = EmsDataLoggingConstants.SENSOR_READING_DAO_INSERT_SENSOR_READING;
 
    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Contact Data DAO for the given EmsDataLoggingDbDAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The EmsDataLoggingDbDAOFactory to construct this User DAO for.
     */
    SensorReadingDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the SensorReading from the database matching the given ID, otherwise null.
     * @param id The ID of the SensorRangeScript to be returned.
     * @return The SensorRangeScript from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
//    public SensorReading find(long id) throws DAOException {
//        return find(SQL_FIND_BY_ID, id);
//    }    
   /**
     * Returns an list of all SensorReading objects in the database
     * @return ArrayList of SensorRangeScript objects.
     * @throws DAOException If something fails at database level.
     */
//    public ArrayList <SensorReading> list() throws DAOException {
//       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
//       return (ArrayList<SensorReading>)DAOUtil.listUtil(SQL_LIST_ALL_ORDER_BY_ID, this);   
//    }
    
    /**
     * Returns an list of all SensorReading objects in the database for a specific sensor id
     * @return ArrayList <SensorReading>
     * @throws DAOException If something fails at database level.
     */
//    public ArrayList <SensorReading> listForSpecificSensor(long aSensorId) throws DAOException {
//       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
//       return (ArrayList<SensorReading>)DAOUtil.listUtil(SQL_LIST_FOR_SPECIFIC_SENSOR, this, aSensorId);   
//    }
    /**
     * Returns the SensorReading from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private SensorReading find(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (SensorReading)DAOUtil.findUtil(sql, this, values);   
    }

    // IMPLEMENTED METHODS FROM EmsDataObjectInterface ------------------------------------------------------------------------------------
   
    /**
    * Returns the reference to its DAO Factory Object associated with this class
    * @return EmsDataLoggingDbDAOFactory  
    * @see EmsDataObjectInterface   
    */ 
    public EmsDataLoggingDbDAOFactory getDAOFactory()
    {
        return this.daoFactory;
    }
    /**
    * Maps the incoming db data to a SensorReading object
    * @param resultSet
    * @return Object
    * @throws SQLException If something fails at database level.
    * @see EmsDataObjectInterface   
    */
    public Object mapData(ResultSet resultSet) throws SQLException {
         // GET VARS
         Long   aId             = resultSet.getLong("sensor_reading_id");
         Long   aSensorId       = resultSet.getLong("sensor_id");
         int   aChannelNum      = resultSet.getInt("channel_num");
         Timestamp aTimestamp   = resultSet.getTimestamp("sensor_reading_timestamp");
         double aMeasurement    = resultSet.getDouble("sensor_reading_measurement");
         
        // CONSTRUCT THE CONTACT DATA OBJ
        return new SensorReading(aId, aSensorId, aChannelNum, aTimestamp, aMeasurement);
           
    }

    /**
     * Create the given snapshot in the database. The ID must be null or -1, otherwise it will throw
     * IllegalArgumentException. If the user ID value is unknown, rather use {@link #save(User)}.
     * After creating, the DAO will set the obtained ID in the given user.
     * @param user The user to be created in the database.
     * @throws IllegalArgumentException If the user ID is not null.
     * @throws DAOException If something fails at database level.
     */
    public void create(SensorReading theReading) throws IllegalArgumentException, DAOException {
        if (theReading.getTheId() != -1) {
            throw new IllegalArgumentException("Snapshot is already created, the user ID is not -1.");
        }

        //
        //MAP THE ATTRIBUTES
        //
        
        Object[] values = {
            theReading.getChannelNum(),
            theReading.getMeasurement(),            
            theReading.getTimestamp(),
            theReading.getSensorId()
        };

        //
        // IDENTIFY WHICH COLUMN YOU WANT A REFERENCE TO ONCE THIS METHOD RETURNS,
        //      So we can update that object to have the correct id associated with it.
        //
        String targetGeneratedColumn = "sensor_reading_id";
        long theId = DAOUtil.createUtil(SQL_INSERT, this, values, targetGeneratedColumn );   
        
        //
        // SET THE ID OF THE INCOMING OBJECT. 
        //      Since we are passing by reference, this will be reflected in the
        //      original object.
        //
        theReading.setTheId(theId);
             
        
    }
     /**
     * Update the given user in the database. The user ID must not be null, otherwise it will throw
     * IllegalArgumentException. If the user ID value is unknown, rather use {@link #save(User)}.
     * @param user The user to be updated in the database.
     * @throws IllegalArgumentException If the user ID is null.
     * @throws DAOException If something fails at database level.
     */
    public void update(SensorReading reading) throws DAOException, IllegalArgumentException{
        if (reading.getTheId() == -1) {
            throw new IllegalArgumentException("Snapshot is not created yet, the user ID is -1.");
        }

       Object[] values = {
            reading.getSensorId(),
            reading.getTimestamp(),
            reading.getMeasurement(),
            reading.getTheId()            
        };        
        
       DAOUtil.updateUtil(SQL_INSERT, this, values );   
        
    }

    /**
     * Save the given user in the database. If the user ID is null, then it will invoke
     * {@link #create(Object)}, else it will invoke {@link #update(Object)}.
     * @param user The user to be saved in the database.
     * @throws DAOException If something fails at database level.
     */
    public void save(Object thisReading) throws DAOException {
        SensorReading reading = (SensorReading)thisReading;
        if (reading.getTheId() == -1) {
            create(reading);
        } else {
            update(reading);
        }
    }
    
    /**
     * This will query the oneWireNetwork for a sensor, and try to take a reading from it.
     * @param networkAddress
     * @param aSensorId
     * @return
     * @throws java.lang.Exception
     */
    public SensorReading logASensorReading (long a_sensorId, int a_channel, Timestamp a_timestamp, double a_sensorMeasurement ) throws Exception
    {
        // GET A NEW READING
        SensorReading thisReading = new SensorReading(-1, a_sensorId, a_channel, a_timestamp, a_sensorMeasurement);
        
        // SAVE THIS READING TO THE DATABASE
        save(thisReading);
        
        // RETURN THE READING         
        return thisReading;
    }

    
}
