package com.ems.datalogging.data.db;

import com.ems.datalogging.business.ContactDataTO;
import com.ems.datalogging.business.ContactDataTypeTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import com.ems.datalogging.data.utils.DAOFactory;
import com.ems.datalogging.data.utils.DAOUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ems.datalogging.data.utils.IDAOInterface;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles database requests for the ContactData objects.<br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 *   
 */
@SuppressWarnings("unchecked")    
public class ContactDataDAO implements IDAOInterface {
   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    private static final boolean DEBUG = false;
    private EmsDataLoggingDbDAOFactory daoFactory;
    private static final String SQL_FIND_BY_ID = EmsDataLoggingConstants.CONTACT_DATA_DAO_FIND_BY_CONTACT_DATA_ID;
    private static final String SQL_LIST_ALL_ORDER_BY_ID = EmsDataLoggingConstants.CONTACT_DATA_DAO_LIST_ALL_ORDER_BY_ID;
    private static final String SQL_FIND_BY_USER_ID = EmsDataLoggingConstants.CONTACT_DATA_DAO_FIND_BY_USER_ID;
        
      

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Contact Data DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this User DAO for.
     */
    ContactDataDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the ContactData from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the ContactData to be returned.
     * @return The ContactData from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    public ContactDataTO find(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }
    
    /**
     * Returns an ArrayList of ContactData objects that match a given userID from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the User you wish to search for.
     * @return An ArrayList of ContactData Objects from the database matching the given user ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
   public ArrayList <ContactDataTO> listForGivenUserId(Long id) throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList <ContactDataTO>)DAOUtil.listUtil(SQL_FIND_BY_USER_ID,this, id);
    }
    
   /**
     * Returns an list of all Contact Data objects in the database
     * @return An ArrayList of ContactData objects.
     * @throws DAOException If something fails at database level.
     */
    public ArrayList <ContactDataTO> list() throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList<ContactDataTO>)DAOUtil.listUtil(SQL_LIST_ALL_ORDER_BY_ID, this);   
    }
    
    
    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private ContactDataTO find(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ContactDataTO)DAOUtil.findUtil(sql, this, values);   
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
    * Maps the incoming db data to a ContactData object
    * @param resultSet
    * @return Object
    * @throws SQLException If something fails at database level.
    * @see EmsDataObjectInterface   
    */
    public Object mapData(ResultSet resultSet) throws SQLException {
         // GET VARS
         ContactDataTO returnContactDataObj = null;
         Long   theId       = Long.valueOf(resultSet.getLong("contact_data_id"));
         String theTitle    = resultSet.getString("contact_data_title");
         String theAddress  = resultSet.getString("contact_data_address");
         String theDesc     =  resultSet.getString("contact_data_description");
         long contactDataTypeId =  Long.valueOf(resultSet.getLong("contact_data_type_id"));
         long theUserId =  Long.valueOf(resultSet.getLong("user_id"));
         ContactDataTypeTO thisCDT = null;
         
         // GET THE CONTACT DATA TYPE OBJECT
            EmsDataLoggingDbDAOFactory ems = EmsDataLoggingDbDAOFactory.getInstance(DAO_FACTORY_INSTACE_NAME);
         // Obtain ContactDataTypeDAO.
           ContactDataTypeDAO contactDataTypeDAO = ems.getContactDataTypeDAO();
            try {
                // Get a contact data type by id
                thisCDT = contactDataTypeDAO.find(contactDataTypeId);
            } catch (DAOException ex) {
                Logger.getLogger(ContactDataDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        // CONSTRUCT THE CONTACT DATA OBJ
            returnContactDataObj = new ContactDataTO(theId, theTitle, theAddress, theDesc, thisCDT, theUserId); 
        return returnContactDataObj;
    }

     /**
    * Not supported yet
    * @throws data.DAOException
    */
    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
