/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.data;


import com.ems.syndication.business.ACaptureData;
import com.ems.syndication.business.Camera;
import com.ems.syndication.business.Environment;
import com.ems.syndication.business.NoteTaker;
import com.ems.syndication.business.SensorChannel;
import com.ems.syndication.constants.EmsSyndicationConstants;
import com.ems.utils.DAOException;
import com.ems.utils.DAOFactory;
import com.ems.utils.DAOUtil;
import com.ems.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Paul W Walter
 */
public class CaptureDeviceDAO implements IDAOInterface {

    private SyndicationDAOFactory daoFactory;
    private static String LIST_DEVICES_SQL = EmsSyndicationConstants.DEVICE_DAO_GET_DEVICES_FOR_ENVIRONMENT_ID;
    private static final String WRONG_DEVICE_TYPE_ERROR_MESSAGE = "Error obtaining the device's type.";

    public CaptureDeviceDAO(SyndicationDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public Object mapData(ResultSet resultSet) throws SQLException {
        //DeviceTO(long theID, String name, String description, long creationStartDate, double updateInterval)
        
        long deviceId = resultSet.getLong("device_id");
        long cameraId = resultSet.getLong("camera_id");
        long sensorId = resultSet.getLong("sensor_id");
        int sensorChannelNum = resultSet.getInt("sensor_channel_num");
        String sensorMetricName = resultSet.getString("sensor_metric_name");
        String sensorTypeName = resultSet.getString("sensor_type_name");
        
        
        String theName = resultSet.getString("device_title");
        String theDesc = resultSet.getString("device_description");
        
        ACaptureData returnObj = null;
        
        // Check to see if the 'Device Type' is of type 'Camera' or 'Sensor'
        if(cameraId > 0)
        {
            
            // Create a Camera object
            returnObj = new Camera(deviceId, theName, theDesc);
        } 
        else if(sensorId > 0)
        {
            // Create a sensor object
            returnObj = new SensorChannel(deviceId, theName, theDesc, sensorChannelNum, sensorTypeName, sensorMetricName);
            
        } 
//        else{
//            // We don't actually know what type this device is. Throw an exception.
//            throw new SQLException(WRONG_DEVICE_TYPE_ERROR_MESSAGE);
//        }
            
        
        
        return returnObj;
            
    }

    public DAOFactory getDAOFactory() {
        return daoFactory;
    }

    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Get the devices used in a specific domain. The userToken is needed to access the data.
     * @param userToken
     * @param environmentID
     * @return
     */
    public ArrayList <ACaptureData> getCaptureDevices( Environment env) throws DAOException
    {
        System.err.println(" - Getting Devices ------------------------------------------ "  );
        //
        // Create a note taker capture from the environment
        // this is a proxy object to denote that we can take notes.
        //
        NoteTaker noteTaker = new NoteTaker(env.id, env.title, "A collector of notes");
        System.err.println(" -  Creating Note Taker: " + noteTaker.id);
        
        ArrayList <ACaptureData> listOfCaptureDevices;
        
        listOfCaptureDevices = (ArrayList <ACaptureData>)DAOUtil.listUtil(LIST_DEVICES_SQL, this, env.id); 
        
        for(int i= 0; i<listOfCaptureDevices.size(); i++)
        {
            System.err.println(" -  device: " + listOfCaptureDevices.get(i).id);
        }
        listOfCaptureDevices.add(noteTaker);
        
        return listOfCaptureDevices;
    }

}
