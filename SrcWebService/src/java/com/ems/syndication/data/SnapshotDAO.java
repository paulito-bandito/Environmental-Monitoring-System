/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.data;

import com.ems.syndication.business.Snapshot;
import com.ems.syndication.constants.EmsSyndicationConstants;
import com.ems.utils.DAOException;
import com.ems.utils.DAOFactory;
import com.ems.utils.DAOUtil;
import com.ems.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Paul W Walter
 */
public class SnapshotDAO implements IDAOInterface{

    private SyndicationDAOFactory daoFactory;
    
    protected static final String GET_SNAPSHOTS_SQL_BY_CONTIGUOUS_CHUNK = EmsSyndicationConstants.SNAPSHOT_DAO_GET_SNAPSHOTS_FOR_SPECIFIC_CONTIGUOUS_CHUNK;
    protected static final String GET_CURRENT_SNAPSHOT = EmsSyndicationConstants.SNAPSHOT_DAO_GET_CURRENT_SNAPSHOT_FOR_SPECIFIC_DEVICE;
    
    public SnapshotDAO(SyndicationDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
        
    public Object mapData(ResultSet resultSet) throws SQLException {
        //long id, long serializedDate, String pictureFileName, long cameraID
        //snap. camera_snapshot_id, snap.camera_snapshot_filename, snap.camera_snapshot_timestamp, cam.camera_id
        
        long snapshotId = resultSet.getLong("camera_snapshot_id");
        long cameraId = resultSet.getLong("camera_id");
        Timestamp timestamp = resultSet.getTimestamp("camera_snapshot_timestamp");
        String fileName =resultSet.getString("camera_snapshot_filename");
        String urlAndPathName = daoFactory.getAssetsURL() + fileName;
        
        return new Snapshot(snapshotId, timestamp.getTime(), urlAndPathName);
    }

    public DAOFactory getDAOFactory() {
          return this.daoFactory;
    }

    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Will return a subset of snapshotTo, usinging the userToken as authentification. 
     * 
     * Using the 'numberStart' and 'numberStop' attributes you can specify how big a chunk starting
     * at what measurement you wish to obtain. For example if you wish to only have the first 2 measurements
     * associated with a specific camera you would set 'numberStart' to 1 and 'numberStop' to 2.
     * 
     * @param userToken
     * @param DeviceId
     * @param timeStart
     * @param timeStop
     * @return
     */public ArrayList <Snapshot> getSnapshots( long DeviceId, int numberStart, int numberStop) throws DAOException
    {
         return (ArrayList <Snapshot>)DAOUtil.listUtil(GET_SNAPSHOTS_SQL_BY_CONTIGUOUS_CHUNK, this, DeviceId, numberStart, numberStop);
         
    }
     
     public Snapshot getCurrentSnapshot( long camera_Id) throws DAOException
    {
         return (Snapshot)DAOUtil.findUtil(GET_CURRENT_SNAPSHOT, this, camera_Id, camera_Id);
         
    }
}
