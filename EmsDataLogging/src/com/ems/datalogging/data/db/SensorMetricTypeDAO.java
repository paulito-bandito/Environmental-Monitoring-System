package com.ems.datalogging.data.db;


import com.ems.datalogging.business.SensorMetricType;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import com.ems.datalogging.data.utils.DAOUtil;
import com.ems.datalogging.data.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 Handles database requests for the Sensor Type objects.
 * <br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 */
@SuppressWarnings("unchecked")    
public class SensorMetricTypeDAO implements IDAOInterface{

   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    private static final boolean DEBUG = false;
    private EmsDataLoggingDbDAOFactory daoFactory;
    private static final String SQL_FIND_BY_ID = EmsDataLoggingConstants.SENSOR_METRIC_TYPE_DAO_FIND_SENSOR_METRIC_BY_ID;
//        "SELECT sensor_metric_type_id, sensor_metric_name, sensor_metric_description FROM sensor_metric_type WHERE sensor_metric_type_id = ?";
//    private static final String SQL_LIST_ALL_ORDER_BY_ID =
//       "SELECT sensor_metric_type_id, sensor_metric_name, sensor_metric_description FROM sensor_metric_type nsor_metric_type_id";
    
    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an SensorMetricType DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this Sensor Type DAO for.
     */
    SensorMetricTypeDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns a specific {@link ems_classes.SensorMetricType} from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the ContactData to be returned.
     * @return The ContactData from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    public SensorMetricType find(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }
    
    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private SensorMetricType find(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (SensorMetricType)DAOUtil.findUtil(sql, this, values);   
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
        // GET VARS
        SensorMetricType returnObj = null;
        long theId = Long.valueOf(resultSet.getLong("sensor_metric_type_id"));
        String theName= resultSet.getString("sensor_metric_name");
        String theDesc= resultSet.getString("sensor_metric_description");
        
        // CONSTRUCT THE CONTACT DATA OBJ
        returnObj = new SensorMetricType(theId, theName, theDesc); 
        return returnObj;
    }

    /**
    * Not supported yet
    * @throws data.DAOException
    */
    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

