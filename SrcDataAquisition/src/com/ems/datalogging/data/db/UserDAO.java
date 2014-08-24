package com.ems.datalogging.data.db;

import com.ems.datalogging.business.ContactDataTO;
import com.ems.datalogging.business.UserTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import com.ems.datalogging.data.utils.DAOUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ems.datalogging.data.utils.IDAOInterface;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 Handles database requests for the ContactDataType objects.
 * <br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 */
@SuppressWarnings("unchecked")    
public class UserDAO implements IDAOInterface{

   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    private static final boolean DEBUG = false;
    
    private EmsDataLoggingDbDAOFactory daoFactory;
    
    private static final String SQL_FIND_BY_ID = EmsDataLoggingConstants.USER_DAO_FIND_BY_ID;
    private static final String SQL_LIST_ALL_ORDER_BY_ID = EmsDataLoggingConstants.USER_DAO_LIST_ALL_BY_ID;
    private static final String SQL_FIND_BY_DOMAIN_ID = EmsDataLoggingConstants.USER_DAO_FIND_BY_DOMAIN_ID;
    private static final String SQL_FIND_BY_SENSOR_ID = EmsDataLoggingConstants.USER_DAO_SELECT_USERS_BY_SENSOR_ID;
    
    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Contact Data DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this User DAO for.
     */
    UserDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns a specific User from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the ContactData to be returned.
     * @return The ContactData from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    public UserTO find(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }
    
    /**
     * Returns an ArrayList of User objects that match a given Domain ID from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the Domain you wish to search for.
     * @return An ArrayList of User Objects from the database matching the given Domain ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
   public ArrayList <UserTO> listForGivenDomainId(Long id) throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList <UserTO>)DAOUtil.listUtil(SQL_FIND_BY_DOMAIN_ID,this, id);
    }
    
   /**
     * Returns an list of all User objects in the database
     * @return An ArrayList of ContactData objects.
     * @throws DAOException If something fails at database level.
     */
    public ArrayList <UserTO> list() throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList<UserTO>)DAOUtil.listUtil(SQL_LIST_ALL_ORDER_BY_ID, this);   
    }

    public ArrayList<UserTO> listForGivenSensorID(Long aId) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
        return (ArrayList <UserTO>)DAOUtil.listUtil(SQL_FIND_BY_SENSOR_ID,this, aId);
    }
    
    
    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private UserTO find(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (UserTO)DAOUtil.findUtil(sql, this, values);   
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
        UserTO returnUserObj = null;
        long userId = Long.valueOf(resultSet.getLong("user_table_id"));
        String userName= resultSet.getString("user_username");
        String password= resultSet.getString("user_username");
        String status_name = resultSet.getString("user_status_name");       
        String role= resultSet.getString("user_role_title");
        ArrayList<ContactDataTO> contactData = null;
        
        // GET THE CORRECT CONTACT DATA
            EmsDataLoggingDbDAOFactory ems = EmsDataLoggingDbDAOFactory.getInstance(DAO_FACTORY_INSTACE_NAME);
        
        // Obtain ContactDataTypeDAO.
           ContactDataDAO contactDataDAO = ems.getContactDataDAO();
            try {
                // Get a contact data type by id
                contactData = contactDataDAO.listForGivenUserId(userId);
            } catch (DAOException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        // CONSTRUCT THE CONTACT DATA OBJ
            returnUserObj = new UserTO(userId,userName,  password, status_name, role, contactData); 
        return returnUserObj;
    }

    /**
    * Not supported yet
    * @throws data.DAOException
    */
    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
