package com.ems.datalogging.data.db;


import com.ems.datalogging.business.DeviceTO;
import com.ems.datalogging.business.SensorTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ems.datalogging.data.utils.DAOUtil;
import com.ems.datalogging.data.utils.IDAOInterface;



/**
 * Handles database requests for the Sensor objects.<br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 *   
 */
@SuppressWarnings("unchecked")    
public class DeviceDAO implements IDAOInterface {
   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    private static final boolean DEBUG = false;
    private EmsDataLoggingDbDAOFactory daoFactory;
    private static final String SQL_INSERT = EmsDataLoggingConstants.DEVICE_DAO_INSERT_NEW_DEVICE;
  
    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Contact Data DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this User DAO for.
     */
    DeviceDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    // Actions ------------------------------------------------------------------------------------

     
   
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
//        try {
//
//            // GET VARS
//            Long aId = Long.valueOf(resultSet.getLong("sensor_id"));
//            String aTitle = resultSet.getString("device_title");
//            String aDesc = resultSet.getString("device_description");
//            long sensorTypeId = Long.valueOf(resultSet.getLong("sensor_type_id"));
//            String sensorName = resultSet.getString("sensor_type_name");
//            String networkAddress = resultSet.getString("sensor_network_address");
//            double sensorUpdateSpeed = resultSet.getDouble("sensor_update_speed");
//
//            SensorType sensorType = new SensorType();
//            SensorMetricType thisMetricType = new SensorMetricType();
//
//            // GET THE CONTACT DATA TYPE OBJECT -----------------------------------
//            EmsDataLoggingDbDAOFactory ems = EmsDataLoggingDbDAOFactory.getInstance(DAO_FACTORY_INSTACE_NAME);
//
//            // GET SENSOR TYPE
//            SensorTypeDAO sensorTypeDAO = ems.getSensorTypeDAO();
//
//            // Get a contact data type by id
//            sensorType = sensorTypeDAO.find(sensorTypeId);
//
//            // GET CHANNELS
//            SensorChannelDAO sensorChannelDAO = ems.getSenorChannelDAO();
//            
//            ArrayList <SensorChannel> listOfChannels = sensorChannelDAO.getChannelsForSpecificSensor(aId);
//           
//            // CONSTRUCT THE CONTACT DATA OBJ
//            return new Sensor(aId, aTitle, aDesc, networkAddress, sensorUpdateSpeed, sensorType, listOfChannels);
//            
//        } catch (DAOException ex) {
//            Logger.getLogger(DeviceDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
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
    public void create(DeviceTO device) throws IllegalArgumentException, DAOException {
        if (device.getTheId() != -1) {
            throw new IllegalArgumentException("Device is already created, the user ID is not -1.");
        }

        //
        //MAP THE ATTRIBUTES
        //
        Object[] values = {
            device.getEnvironmentID(), 
            device.getTitle(),
            device.getDescription()            
        };

        //
        // IDENTIFY WHICH COLUMN YOU WANT A REFERENCE TO ONCE THIS METHOD RETURNS,
        //      So we can update that object to have the correct id associated with it.
        //
        String targetGeneratedColumn = "device_id";
        long theId = DAOUtil.createUtil(SQL_INSERT, this, values, targetGeneratedColumn );   
        
        //
        // SET THE ID OF THE INCOMING OBJECT. 
        //      Since we are passing by reference, this will be reflected in the
        //      original object.
        //
        device.setTheId(theId);
             
        
    }
     /**
     * Update the given user in the database. The user ID must not be null, otherwise it will throw
     * IllegalArgumentException. If the user ID value is unknown, rather use {@link #save(User)}.
     * @param user The user to be updated in the database.
     * @throws IllegalArgumentException If the user ID is null.
     * @throws DAOException If something fails at database level.
     */
    public void update(DeviceTO device) throws DAOException, IllegalArgumentException{
        if (device.getTheId() == -1) {
            throw new IllegalArgumentException("Snapshot is not created yet, the user ID is -1.");
        }

        Object[] values = {
            device.getTheId(),
            device.getEnvironmentID(),
            device.getTitle(),
            device.getDescription()            
        };        
    }

    /**
     * Save the given user in the database. If the user ID is null, then it will invoke
     * {@link #create(Object)}, else it will invoke {@link #update(Object)}.
     * @param user The user to be saved in the database.
     * @throws DAOException If something fails at database level.
     */
    public void save(Object obj) throws DAOException {
        
       if (((DeviceTO)obj).getTheId() == -1) {
            create((DeviceTO)obj);
       } else {
            update((DeviceTO)obj);
       } 
        
    }

    
}
