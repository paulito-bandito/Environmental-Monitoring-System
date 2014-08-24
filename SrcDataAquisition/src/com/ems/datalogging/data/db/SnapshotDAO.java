package com.ems.datalogging.data.db;


import com.ems.datalogging.business.SnapshotTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.utils.DAOException;
import com.ems.datalogging.data.utils.DAOUtil;
import com.ems.datalogging.data.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 Handles database requests for the Snapshot objects.
 * <br/>Note that warnings regarding "unchecked" conversions are being suppressed
 * @author Paul Walter 
 */
@SuppressWarnings("unchecked")    
public class SnapshotDAO implements IDAOInterface{

   
    // Vars ---------------------------------------------------------------------------------------
    private static final String DAO_FACTORY_INSTACE_NAME = EmsDataLoggingConstants.EMS_INSTANCE_NAME;
    private static final boolean DEBUG = false;
    private EmsDataLoggingDbDAOFactory daoFactory;
    //private SnapshotterBuggyDAO thisSnapshotter;
    
    private static final String SQL_FIND_BY_ID = EmsDataLoggingConstants.SNAPSHOT_DAO_FIND_BY_SNAPSHOT_ID;
    private static final String SQL_LIST_ALL_ORDER_BY_ID = EmsDataLoggingConstants.SNAPSHOT_DAO_LIST_ALL_BY_ID;
    private static final String SQL_FIND_BY_CAMERA_ID = EmsDataLoggingConstants.SNAPSHOT_DAO_FIND_BY_CAMERA_ID;
    private static final String SQL_INSERT = EmsDataLoggingConstants.SNAPSHOT_DAO_INSERT_SNAPSHOT;
    private static final String SQL_UPDATE = EmsDataLoggingConstants.SNAPSHOT_DAO_UPDATE_SNAPSHOT;
        
    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an Contact Data DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this Snapshot DAO for.
     */
    SnapshotDAO(EmsDataLoggingDbDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns a specific Snapshot from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the ContactData to be returned.
     * @return The ContactData from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    public SnapshotTO find(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }
    
    /**
     * Returns an ArrayList of Snapshot objects that match a given Camera ID from the database matching the given ContactData ID, otherwise null.
     * @param id The ID of the Domain you wish to search for.
     * @return An ArrayList of Snapshot Objects from the database matching the given Domain ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
   public ArrayList <SnapshotTO> listForGivenCameraId(Long id) throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList <SnapshotTO>)DAOUtil.listUtil(SQL_FIND_BY_CAMERA_ID,this, id);
    }
    
   /**
     * Returns an list of all Snapshot objects in the database
     * @return An ArrayList of ContactData objects.
     * @throws DAOException If something fails at database level.
     */
    public ArrayList <SnapshotTO> list() throws DAOException {
       // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (ArrayList<SnapshotTO>)DAOUtil.listUtil(SQL_LIST_ALL_ORDER_BY_ID, this);   
    }
    
    
    /**
     * Returns the Snapshot from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The Snapshot from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private SnapshotTO find(String sql, Object... values) throws DAOException {
        // MAKE THE PROPER CAST FOR THE ARRAY LIST 
       return (SnapshotTO)DAOUtil.findUtil(sql, this, values);   
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
    
    
    
    
    
    
//    public Snapshot takePhysicalSnapshot(Camera thisCamera) throws Exception
//    {

//        // VARS
//        Timestamp theTimeStamp = DAOUtil.getCurTimestamp();
//        Date theCurrDate    = DAOUtil.getCurDate();
//        long theMillis  = DAOUtil.getCurTimeInMillis();
//        String fileFormat = ".jpg";
//        String fileName = "ems_" + thisCamera.getId() + "_" + theMillis + fileFormat;
//     
//        // CONSTRUCT A NEW SNAPSHOT
//        Snapshot newSnapshot = new Snapshot(-1, thisCamera.getId(), fileName ,  theTimeStamp);   
//     
//        //SAVE IT TO THE DATABASE
//        save(newSnapshot);
//        
//        // TAKE A PHYSICAL SNAPSHOT
//        SnapshotterBuggyDAO thisSnapshotter = daoFactory.getSnapshotterDAO();
//        thisSnapshotter.takeSnapshot(thisCamera, fileName);
//        
//        return newSnapshot;
//        return null;
//    }
    
    
    
    
     /**
     * Create the given snapshot in the database. The ID must be null or -1, otherwise it will throw
     * IllegalArgumentException. If the user ID value is unknown, rather use {@link #save(User)}.
     * After creating, the DAO will set the obtained ID in the given user.
     * @param user The user to be created in the database.
     * @throws IllegalArgumentException If the user ID is not null.
     * @throws DAOException If something fails at database level.
     */
    public void create(SnapshotTO snapshot) throws IllegalArgumentException, DAOException {
        if (snapshot.getId() != -1) {
            throw new IllegalArgumentException("Snapshot is already created, the user ID is not -1.");
        }

        //
        //MAP THE ATTRIBUTES
        //
        Object[] values = {
            snapshot.getCameraId(),            
            snapshot.getTimestamp(),
            snapshot.getFileName()
        };

        //
        // IDENTIFY WHICH COLUMN YOU WANT A REFERENCE TO ONCE THIS METHOD RETURNS,
        //      So we can update that object to have the correct id associated with it.
        //
        String targetGeneratedColumn = "camera_snapshot_id";
        long theId = DAOUtil.createUtil(SQL_INSERT, this, values, targetGeneratedColumn );   
        
        //
        // SET THE ID OF THE INCOMING OBJECT. 
        //      Since we are passing by reference, this will be reflected in the
        //      original object.
        //
        snapshot.setId(theId);
             
        
    }
     /**
     * Update the given user in the database. The user ID must not be null, otherwise it will throw
     * IllegalArgumentException. If the user ID value is unknown, rather use {@link #save(User)}.
     * @param user The user to be updated in the database.
     * @throws IllegalArgumentException If the user ID is null.
     * @throws DAOException If something fails at database level.
     */
    public void update(SnapshotTO snapshot) throws DAOException, IllegalArgumentException{
        if (snapshot.getId() == -1) {
            throw new IllegalArgumentException("Snapshot is not created yet, the user ID is -1.");
        }

        Object[] values = {
            snapshot.getCameraId(),
            snapshot.getFileName(),
            snapshot.getTimestamp(),
            snapshot.getId()            
        };        
    }

    /**
     * Save the given user in the database. If the user ID is null, then it will invoke
     * {@link #create(Object)}, else it will invoke {@link #update(Object)}.
     * @param user The user to be saved in the database.
     * @throws DAOException If something fails at database level.
     */
    public void save(SnapshotTO snapshot) throws DAOException {
        if (snapshot.getId() == -1) {
            create(snapshot);
        } else {
            update(snapshot);
        }
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
        SnapshotTO returnSnapshotObj = null;
        
        long snapshotId = Long.valueOf(resultSet.getLong("camera_snapshot_id"));
        long cameraId   = Long.valueOf(resultSet.getLong("camera_id"));
        Timestamp theTimestamp= resultSet.getTimestamp("camera_snapshot_timestamp");
        String fileName = resultSet.getString("camera_snapshot_filename");
           
   
        returnSnapshotObj = new SnapshotTO(snapshotId, cameraId, fileName ,  theTimestamp);         
        return returnSnapshotObj;
    }

    /**
    * Not supported yet
    * @throws data.DAOException
    */
    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}