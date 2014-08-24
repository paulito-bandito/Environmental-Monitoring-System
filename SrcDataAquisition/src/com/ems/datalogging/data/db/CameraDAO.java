package com.ems.datalogging.data.db;

import com.ems.datalogging.business.DeviceTO;
import com.ems.datalogging.business.CameraTO;
import com.ems.datalogging.data.jmf.*;
import com.ems.datalogging.business.SnapshotTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import com.ems.datalogging.data.utils.DAOUtil;
import com.ems.datalogging.data.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 Handles database requests for the Camera objects.
 * <br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 */
@SuppressWarnings("unchecked")    
public class CameraDAO implements IDAOInterface{

   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    private static final boolean DEBUG = false;
    
    private EmsDataLoggingDbDAOFactory daoFactory;
    
    private static final String SQL_FIND_BY_ID = EmsDataLoggingConstants.CAMERA_DAO_FIND_BY_ID;
    private static final String SQL_FIND_BY_NETWORK_ADDRESS =EmsDataLoggingConstants.CAMERA_DAO_FIND_BY_NETWORK_ADDRESS;
    private static final String SQL_LIST_ALL_ORDER_BY_ID = EmsDataLoggingConstants.CAMERA_DAO_LIST_ALL_BY_ID;
    private static final String SQL_FIND_BY_ENV_ID = EmsDataLoggingConstants.CAMERA_DAO_FIND_BY_ENV_ID;
        
      

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Contact Data DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this Camera DAO for.
     */
    CameraDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /**
     * This method will return a camera located at a particular media location.
     * @param a_networkAddress
     * @return
     * @throws com.ems.datalogging.data.utils.DAOException
     */
    public CameraTO findByNetworkAddress(String a_networkAddress) throws DAOException {
        return find(SQL_FIND_BY_NETWORK_ADDRESS, a_networkAddress);
    }
    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns a specific Camera from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the ContactData to be returned.
     * @return The ContactData from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    public CameraTO find(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }
    
    /**
     * Returns an ArrayList of Camera objects that match a given Environment ID from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the Domain you wish to search for.
     * @return An ArrayList of Camera Objects from the database matching the given Environment ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
   public ArrayList <CameraTO> listForGivenEnvironmentId(Long id) throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList <CameraTO>)DAOUtil.listUtil(SQL_FIND_BY_ENV_ID,this, id);
    }
    
   /**
     * Returns an ArrayList of Camera objects cast as Devices that match a given Environment ID from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the Domain you wish to search for.
     * @return An ArrayList of Camera Objects from the database matching the given Environment ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
   public ArrayList <DeviceTO> listDevicesForGivenEnvId(Long id) throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList <DeviceTO>)DAOUtil.listUtil(SQL_FIND_BY_ENV_ID,this, id);
    }
    
   /**
     * Returns an list of all Camera objects in the database
     * @return An ArrayList of ContactData objects.
     * @throws DAOException If something fails at database level.
     */
    public ArrayList <CameraTO> list() throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList<CameraTO>)DAOUtil.listUtil(SQL_LIST_ALL_ORDER_BY_ID, this);   
    }
    /**
     * Returns a vector of Cameras for a given environment
     * @param id
     * @return
     * @throws DataObjects.DAOException
     */
    public Vector <CameraTO> vectorForGivenEnvironmentId(Long id) throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (Vector <CameraTO>)DAOUtil.vectorUtil(SQL_FIND_BY_ENV_ID,this, id);
    }
    /**
     * 
     * @param mediaLoc
     * @return
     */
   
            
   /**
     * Returns an ArrayList of Camera objects cast as Devices that match a given Environment ID from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the Domain you wish to search for.
     * @return An ArrayList of Camera Objects from the database matching the given Environment ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
   public Vector <DeviceTO> vectorOfDevicesForGivenEnvId(Long id) throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (Vector <DeviceTO>)DAOUtil.vectorUtil(SQL_FIND_BY_ENV_ID,this, id);
    }    
  
    
    
    /**
     * Returns the Camera from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The Camera from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private CameraTO find(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (CameraTO)DAOUtil.findUtil(sql, this, values);   
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
        CameraTO returnCameraObj = null;
        long cameraId       = resultSet.getLong("camera_id");
        String aTitle       = resultSet.getString("device_title");
        String aDesc        = resultSet.getString("device_description");
        double aUpdateSpeed = resultSet.getDouble("camera_snapshot_interval");       
        String aNetworkAddress  = resultSet.getString("camera_media_location");
        long aEnvironmentID     = resultSet.getLong("environment_id");
        
        // GET THE CORRECT CONTACT DATA
            EmsDataLoggingDbDAOFactory ems = EmsDataLoggingDbDAOFactory.getInstance(DAO_FACTORY_INSTACE_NAME);
          
        // CONSTRUCT THE CONTACT DATA OBJ
            returnCameraObj = new CameraTO(cameraId, aTitle, aDesc, aUpdateSpeed, aNetworkAddress, aEnvironmentID); 
        
            return returnCameraObj;
    }

    /**
    * Not supported yet
    * @throws data.DAOException
    */
    public void save(Object incomingObj) throws DAOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}