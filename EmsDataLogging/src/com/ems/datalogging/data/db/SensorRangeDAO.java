package com.ems.datalogging.data.db;

import com.ems.datalogging.business.SensorRange;
import com.ems.datalogging.business.SensorRangeScript;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import com.ems.datalogging.data.utils.DAOUtil;
import com.ems.datalogging.data.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles database requests for the Sensor objects.<br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 *   
 */
@SuppressWarnings("unchecked")    
public class SensorRangeDAO implements IDAOInterface {
   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    private static final boolean DEBUG = false;
    private EmsDataLoggingDbDAOFactory daoFactory;
    private static final String SQL_LIST_FOR_SPECIFIC_SENSOR = EmsDataLoggingConstants.SENSOR_RANGE_DAO_FIND_SENSOR_RANGES_BY_SENSOR_ID;
        
    
    
    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Contact Data DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this User DAO for.
     */
    SensorRangeDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the SensorRanges from the database matching the given SensorRange ID, otherwise null.
     * @param id The ID of the SensorRange to be returned.
     * @return The SensorRange from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
//    public SensorRange find(long id) throws DAOException {
//        return find(SQL_FIND_BY_ID, id);
//    }    
   /**
     * Returns an list of all SensorRange objects in the database
     * @return ArrayList of SensorRangeRangeScript objects.
     * @throws DAOException If something fails at database level.
     */
//    public ArrayList <SensorRange> list() throws DAOException {
//       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
//       return (ArrayList<SensorRange>)DAOUtil.listUtil(SQL_LIST_ALL_ORDER_BY_ID, this);   
//    }
    
    /**
     * Returns an list of all SensorRange objects in the database for a specific SensorRange id
     * @return ArrayList <SensorRange>
     * @throws DAOException If something fails at database level.
     */
    public ArrayList <SensorRange> listForSpecificSensorChannel(long aSensorRangeId, int aSensorChannel) throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList<SensorRange>)DAOUtil.listUtil(SQL_LIST_FOR_SPECIFIC_SENSOR, this, aSensorRangeId, aSensorChannel);   
    }
    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private SensorRange find(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (SensorRange)DAOUtil.findUtil(sql, this, values);   
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
    * Maps the incoming db data to a EnvironmentRangeScript object
    * @param resultSet
    * @return Object
    * @throws SQLException If something fails at database level.
    * @see EmsDataObjectInterface   
    */
    public Object mapData(ResultSet resultSet) throws SQLException {
        
        
        // GET VARS
             long   aId             = resultSet.getLong("sensor_range_id");
             long   aSensorId       = resultSet.getLong("sensor_id");
             String aTitle          = resultSet.getString("sensor_range_title");
             double aMinThreshold   = resultSet.getDouble("sensor_range_min");
             double aMaxThreshold   = resultSet.getDouble("sensor_range_max");
             int    aChannelNum     = resultSet.getInt("sensor_channel_num");
             
         
             ArrayList <SensorRangeScript> theScripts = new ArrayList <SensorRangeScript>();
         
        // GET THE CONTACT DATA TYPE OBJECT
            EmsDataLoggingDbDAOFactory ems = EmsDataLoggingDbDAOFactory.getInstance(DAO_FACTORY_INSTACE_NAME);
           
//         // OBTAIN Sensor LIST.
//            SensorRangeScriptDAO sensorRangeScriptDAO = ems.getSensorRangeScriptDAO();
//        
//         // GET ASSOCIATED SENSOR RANGE SCRIPTS
//             try{
//                 theScripts = sensorRangeScriptDAO.listForSpecificSensorRangeId(aId);
//             }catch(Exception exp)
//             {
//                 System.err.println("Error obtaining the list of SensorRangeScripts: " + exp.getMessage());
//             }
             
        // CONSTRUCT THE CONTACT DATA OBJ
            //return new SensorRange(aId, aTitle, aMinThreshold, aMaxThreshold, theScripts); 
            return new SensorRange(aId, aSensorId, aChannelNum, aTitle, aMinThreshold, aMaxThreshold, null); 
          
           
    }

    /**
    * Either saves or updates a Transfer Object in conjunction with DAOUtils.java. 
    * If the ID of the thing you are creating exists, then update else create  
    * @throws data.DAOException
    */
    public void save(Object incomingObj) throws DAOException {
           throw new UnsupportedOperationException("Not supported yet.");
    }
    
}