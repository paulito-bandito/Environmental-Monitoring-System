/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.data;


import com.ems.syndication.business.ACaptureData;
import com.ems.syndication.business.AData;
import com.ems.syndication.business.Camera;
import com.ems.syndication.business.NoteTaker;
import com.ems.syndication.business.SensorChannel;
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
public class DataDAO implements IDAOInterface{
    
    private SyndicationDAOFactory daoFactory;
    protected NoteDAO noteDAO;
    protected SnapshotDAO snapshotDAO;
    protected MeasurementDAO measurementDAO;
    
    
    private static final String GET_SENSOR_MEASUREMENTS_FOR_CONTIGUOUS_CHUNK = EmsSyndicationConstants.SENSORREADING_DAO_GET_READINGS_FOR_SPECIFIC_CONTIGUOUS_CHUNK;

    public DataDAO(SyndicationDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        
        noteDAO = daoFactory.getNoteDAO();
        snapshotDAO = daoFactory.getSnapshotDAO();
        measurementDAO = daoFactory.getMeasurementDAO();
    }
    
    public Object mapData(ResultSet resultSet) throws SQLException {
        
        // this will never get called.
        return null;
        
    }

    public DAOFactory getDAOFactory() {
          return this.daoFactory;
    }

    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Will return a subset of sensor measurements, usinging the userToken as authentification. 
     * 
     * Using the 'numberStart' and 'numberStop' attributes you can specify how big a chunk starting
     * at what measurement you wish to obtain. For example if you wish to only have the first 2 measurements
     * associated with a specific sensor you would set 'numberStart' to 1 and 'numberStop' to 2.
     * 
     * @param userToken
     * @param DeviceId
     * @param numberStart
     * @param numberStop
     * @return
     */public AData getCurrentData( ACaptureData captureData) throws DAOException
    {
        // Check to see what kind of object it is then return the appropriate data.
         
         if(captureData instanceof  Camera)
         {
             return (AData)snapshotDAO.getCurrentSnapshot(captureData.id);
         }else if (captureData instanceof NoteTaker)
         {
             return (AData)noteDAO.getCurrentEnvironmentNote(captureData.id);
         }else if(captureData instanceof SensorChannel)
         {
             SensorChannel thisChan = (SensorChannel)captureData;
            return (AData)measurementDAO.getMostCurrentSensorMeasurement(thisChan.id, thisChan.channelNum);
         }
         return null;
    }
}
