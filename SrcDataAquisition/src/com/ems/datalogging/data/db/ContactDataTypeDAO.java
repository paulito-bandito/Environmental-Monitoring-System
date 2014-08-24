package com.ems.datalogging.data.db;

import com.ems.datalogging.business.ContactDataTypeTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import com.ems.datalogging.data.utils.DAOUtil;
import com.ems.datalogging.data.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Handles database requests for the ContactDataType objects.
 * <br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 * 
 *  
 */
@SuppressWarnings("unchecked")    
public class ContactDataTypeDAO implements IDAOInterface {
   
    private static final boolean DEBUG = false;
    private static final String SQL_FIND_BY_ID = EmsDataLoggingConstants.CONTACT_DATA_TYPE_DAO_FIND_BY_CONTACT_DATA_TYPE_ID;
    private static final String SQL_LIST_ALL_ORDER_BY_ID = EmsDataLoggingConstants.CONTACT_DATA_TYPE_DAO_LIST_ALL_ORDER_BY_ID;
     
    // Vars ---------------------------------------------------------------------------------------

    private EmsDataLoggingDbDAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an User DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this User DAO for.
     */
    ContactDataTypeDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the ContactDataType from the database matching the given ID, otherwise null.
     * @param id The ID of the ContactDataType to be returned.
     * @return The ContactDataType from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    public ContactDataTypeTO find(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }
     /**
     * Returns an ArrayList of all ContactDataTypes in the database.
     * @return ArrayList <ContactDataType>
     * @throws DAOException If something fails at database level.
     */
    public ArrayList <ContactDataTypeTO> list() throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList<ContactDataTypeTO>)DAOUtil.listUtil(SQL_LIST_ALL_ORDER_BY_ID,(IDAOInterface) this);   
    }

    /**
     * A wrapper method for DAOUtil.findUtil() that returns the ContactDataType from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     * @see DAOUtil.findUtil()
     */
    private ContactDataTypeTO find(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ContactDataTypeTO)DAOUtil.findUtil(sql,(IDAOInterface) this, values);   
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
    public Object mapData(ResultSet resultSet) throws SQLException {
        return new ContactDataTypeTO(
            Long.valueOf(resultSet.getLong("contact_data_type_id")),
            resultSet.getString("contact_data_type_title"),
            resultSet.getString("contact_data_type_desc")
           );
    }
    
    
    /**
    * Not supported yet
    * @throws data.DAOException
    */
    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
