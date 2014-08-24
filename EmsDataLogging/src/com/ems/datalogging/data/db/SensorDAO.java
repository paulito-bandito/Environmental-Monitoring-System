package com.ems.datalogging.data.db;


import com.ems.datalogging.business.DeviceTO;
import com.ems.datalogging.business.SensorTO;
import com.ems.datalogging.business.SensorChannel;
import com.ems.datalogging.business.SensorMetricType;
import com.ems.datalogging.business.SensorTypeTO;
import com.ems.datalogging.business.UserTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ems.datalogging.data.utils.DAOUtil;
import com.ems.datalogging.data.utils.IDAOInterface;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Handles database requests for the Sensor objects.<br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 *   
 */
@SuppressWarnings("unchecked")    
public class SensorDAO implements IDAOInterface {
   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    private static final boolean DEBUG = false;
    private EmsDataLoggingDbDAOFactory daoFactory;
    private static final String SQL_FIND_BY_NETWORK_ADDRESS = EmsDataLoggingConstants.SENSOR_DAO_FIND_SENSOR_BY_NETWORK_ADDRESS;
    private static final String SQL_INSERT = EmsDataLoggingConstants.SENSOR_DAO_INSERT_NEW_DEVICE;
  
    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Contact Data DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this User DAO for.
     */
    SensorDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the Sensors from the database matching the given Sensor ID, otherwise null.
     * @param id The ID of the Sensor to be returned.
     * @return The Sensor from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    public SensorTO findByNetworkAddress(String networkAddress) throws DAOException {
        return find(SQL_FIND_BY_NETWORK_ADDRESS, networkAddress);
    }    
   
    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private SensorTO find(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (SensorTO)DAOUtil.findUtil(sql, this, values);   
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
    * Maps the incoming db data to a SensorRangeScript object
    * @param resultSet
    * @return Object
    * @throws SQLException If something fails at database level.
    * @see EmsDataObjectInterface   
    */
    public Object mapData(ResultSet resultSet) throws SQLException {
        try {

            // GET VARS
            Long aId = resultSet.getLong("sensor_id");
            String aEnvName = resultSet.getString("environment_title");
            long aEnvId = resultSet.getLong("environment_id");
            String aTitle = resultSet.getString("device_title");
            String aDesc = resultSet.getString("device_description");
            long sensorTypeId = resultSet.getLong("sensor_type_id");
            String sensorName = resultSet.getString("sensor_type_name");
            String networkAddress = resultSet.getString("sensor_network_address");
            double sensorUpdateSpeed = resultSet.getDouble("sensor_update_speed");

            SensorTypeTO sensorType = new SensorTypeTO();
            SensorMetricType thisMetricType = new SensorMetricType();

            // GET THE CONTACT DATA TYPE OBJECT -----------------------------------
            EmsDataLoggingDbDAOFactory ems = EmsDataLoggingDbDAOFactory.getInstance(DAO_FACTORY_INSTACE_NAME);

            // GET SENSOR TYPE
            SensorTypeDAO sensorTypeDAO = ems.getSensorTypeDAO();

            // Get a contact data type by id
            sensorType = sensorTypeDAO.find(sensorTypeId);

            // GET CHANNELS
            SensorChannelDAO sensorChannelDAO = ems.getSenorChannelDAO();
            
            ArrayList <SensorChannel> listOfChannels = sensorChannelDAO.getChannelsForSpecificSensor(aId);
            
            // Get people to call when things go wrong.
            UserDAO userDAO = ems.getUserDAO();
            ArrayList <UserTO> listOfUsers =  userDAO.listForGivenSensorID(aId);
           
            // CONSTRUCT THE CONTACT DATA OBJ
            return new SensorTO(aId, aTitle, aDesc, networkAddress, sensorUpdateSpeed, sensorType, listOfChannels, listOfUsers, aEnvId, aEnvName);
            
        } catch (DAOException ex) {
            Logger.getLogger(SensorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

         /**
     * Create the given snapshot in the database. The ID must be null or -1, otherwise it will throw
     * IllegalArgumentException. If the user ID value is unknown, rather use {@link #save(User)}.
     * After creating, the DAO will set the obtained ID in the given user.
     * @param user The user to be created in the database.
     * @throws IllegalArgumentException If the user ID is not null.
     * @throws DAOException If something fails at database level.
     */
    public void create(SensorTO a_sensor) throws IllegalArgumentException, DAOException {
        if (a_sensor.getTheId() != -1) {
            throw new IllegalArgumentException("Sensor is already created, the user ID is not -1.");
        }
        
        //
        // 1.) Save a device 
        //
        DeviceTO thisDevice = new DeviceTO(-1, a_sensor.getTheTitle(), a_sensor.getTheDesc(), daoFactory.getDeviceDefaultEnvironmentID());
        DeviceDAO deviceDao = daoFactory.getDeviceDAO();
        deviceDao.save(thisDevice);
        
        //
        // SET THE ID OF THE INCOMING OBJECT. 
        //      Since we are passing by reference, this will be reflected in the
        //      original object.
        //
        a_sensor.setTheId( thisDevice.getTheId());
        
        //
        // 2.) Save a sensor 
        //
        Object[] sensorValues = {
            a_sensor.getTheId(),
            a_sensor.getSensorType().getTheId(),
            a_sensor.getNetworkAddress(),
            a_sensor.getUpdateSpeed()   
        };

        //
        // IDENTIFY WHICH COLUMN YOU WANT A REFERENCE TO ONCE THIS METHOD RETURNS,
        //      So we can update that object to have the correct id associated with it.
        //
        String targetGeneratedColumn = "sensor_id";
        long theId = DAOUtil.createUtil(SQL_INSERT, this, sensorValues, targetGeneratedColumn  );   
                  
        //
        // 3.) Save channels once we have the sensor ID.
        //
        SensorChannelDAO sensorChannelDAO = daoFactory.getSenorChannelDAO();
        ArrayList<SensorChannel> listOfChannels = a_sensor.getSensorChannels();
        if(listOfChannels != null)
        {
            for(int i=0; i<listOfChannels.size(); i++)
            {
                sensorChannelDAO.save(listOfChannels.get(i));
            }  
        }
        
        
        
        
             
        
    }
     /**
     * Update the given user in the database. The user ID must not be null, otherwise it will throw
     * IllegalArgumentException. If the user ID value is unknown, rather use {@link #save(User)}.
     * @param user The user to be updated in the database.
     * @throws IllegalArgumentException If the user ID is null.
     * @throws DAOException If something fails at database level.
     */
    public void update(SensorTO snapshot) throws DAOException, IllegalArgumentException{
        if (snapshot.getTheId() == -1) {
            throw new IllegalArgumentException("Sensor is not created yet, the ID is -1.");
        }

        Object[] values = {
            snapshot.getTheId(),
            snapshot.getNetworkAddress(),
            snapshot.getUpdateSpeed(),
            snapshot.getSensorType()            
        };        
    }

    /**
     * Save the given user in the database. If the user ID is null, then it will invoke
     * {@link #create(Object)}, else it will invoke {@link #update(Object)}.
     * @param user The user to be saved in the database.
     * @throws DAOException If something fails at database level.
     */
    public void save(Object sensor) throws DAOException {
        if (((SensorTO)sensor).getTheId() == -1) {
            create((SensorTO)sensor);
        } else {
            update((SensorTO)sensor);
        }
    }

    
}
