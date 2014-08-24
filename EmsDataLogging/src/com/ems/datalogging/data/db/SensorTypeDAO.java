package com.ems.datalogging.data.db;


import com.ems.datalogging.business.SensorTypeTO;
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
public class SensorTypeDAO implements IDAOInterface{

   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    private static final boolean DEBUG = false;
    private EmsDataLoggingDbDAOFactory daoFactory;
    private static final String SQL_FIND_BY_ID = EmsDataLoggingConstants.SENSOR_TYPE_DAO_FIND_BY_ID;
    
    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Sensor Type DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this Sensor Type DAO for.
     */
    SensorTypeDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns a specific {@link ems_classes.SensorType} from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the ContactData to be returned.
     * @return The ContactData from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    public SensorTypeTO find(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }
    
   
      
    
    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private SensorTypeTO find(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (SensorTypeTO)DAOUtil.findUtil(sql, this, values);   
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
        SensorTypeTO returnObj = null;
        long theId = Long.valueOf(resultSet.getLong("sensor_type_id"));
        String theName= resultSet.getString("sensor_type_name");
        
        // CONSTRUCT THE CONTACT DATA OBJ
        returnObj = new SensorTypeTO(theId, theName); 
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

