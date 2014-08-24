/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.web;


import com.ems.syndication.business.ACaptureData;
import com.ems.syndication.business.AData;
import com.ems.syndication.business.ADescription;
import com.ems.syndication.business.Camera;
import com.ems.syndication.business.Domain;
import com.ems.syndication.business.Environment;
import com.ems.syndication.business.Measurement;
import com.ems.syndication.business.Note;
import com.ems.syndication.business.NoteTaker;
import com.ems.syndication.business.SensorChannel;
import com.ems.syndication.business.SensorRange;
import com.ems.syndication.business.Snapshot;
import com.ems.syndication.business.User;
import com.ems.syndication.constants.EmsSyndicationConstants;
import com.ems.syndication.data.CaptureDeviceDAO;
import com.ems.syndication.data.DataDAO;
import com.ems.syndication.data.EnvironmentDAO;
import com.ems.syndication.data.SensorRangeDAO;
import com.ems.syndication.data.SyndicationDAOFactory;
import com.ems.utils.DAOException;
import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Paul W Walter
 */
@WebService()
public class EmsSyndicator {

    private SyndicationDAOFactory daoFactory;
    
    /**
     * Constructor to populate the DAO Factory.
     */
    public EmsSyndicator()
    {
        // Populate the reference to the DAO factory.
        daoFactory =  SyndicationDAOFactory.getInstance(EmsSyndicationConstants.EMS_INSTANCE_NAME);
    }
    
    /**
     * Hello world method to test the webservice.
     */
    @WebMethod(operationName = "helloWorld")
    public String helloWorld(@WebParam(name = "a_name")
    String a_name) {
        //TODO write your implementation code here:
        return "Hello " + a_name;
    }

    /**
     * Will return a user assocated with a particular username and password.
     * 
     * @param a_name
     * @param a_password
     * @return
     */
    @WebMethod(operationName = "login")
    public User login(@WebParam(name= "a_name") String a_name, @WebParam(name="a_password") String a_password)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    /**
     * This method is responsible for getting all Domains associated with 
     * a User.
     * 
     * @param a_user
     * @return
     */
    @WebMethod(operationName = "getDomains")
    public Domain [] getDomains(@WebParam(name= "a_user") User a_user)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    /**
     * This method is responsible for getting all Environments associated with 
     * a particular domain.
     * @param a_domain
     * @return
     */
     @WebMethod(operationName = "getEnvironments")
    public ArrayList <Environment> getEnvironments(@WebParam(name= "a_domain") Domain a_domain) throws DAOException
    {    
         EnvironmentDAO envDAO = daoFactory.getEnvironmentDAO();
         return  envDAO.getEnvironments(a_domain.id); 
    }
     
    /**
     * This method is responsible for getting all data capture devices for a particular 
     * Environment. 
     * @param a_environment
     * @return
     */ 
    @WebMethod(operationName = "getDataCapturers")
    public ArrayList <ACaptureData> getDataCapturers(@WebParam(name= "a_environment") Environment a_environment) throws DAOException
    {
        CaptureDeviceDAO deviceDAO =  daoFactory.getCaptureDeviceDAO();
        return  deviceDAO.getCaptureDevices(a_environment); 
    }
    
    /**
     * This will return the current measurement for a particular capture device like
     * SensorChannel or Camera. 
     * 
     * @see SensorRange, Camera
     * @param a_iCaptureData
     * @return
     */
    @WebMethod(operationName = "getCurrentData")
    public AData getCurrentData(@WebParam(name= "a_iCaptureData") ACaptureData a_iCaptureData) throws DAOException
    {
        DataDAO dataDAO = daoFactory.getDataDAO();
        return  dataDAO.getCurrentData(a_iCaptureData); 
    }
    
      
    /** 
     * This method will return the data for a capture device.
     * 
     * @param a_iCaptureData
     * @param a_startTimestamp
     * @param a_stopTimestamp
     * @return
     */
    @WebMethod(operationName = "getDataCaptureData")
    public ArrayList <AData> getDataCaptureData(@WebParam(name= "a_iCaptureData") ACaptureData a_iCaptureData, 
            @WebParam(name= "a_startTimestamp") long a_startTimestamp, 
                @WebParam(name= "a_stopTimestamp") long a_stopTimestamp )
    {       
        DataDAO dataDAO = daoFactory.getDataDAO();
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    /**
     * This method is responsible for returning all Sensor Range thresholds that are presently
     * assigned to a particular Sensor Channel. 
     * 
     * @param a_iCaptureData
     * @return
     */
    @WebMethod(operationName = "getAllSensorRanges")
    public SensorRange [] getAllSensorRanges(@WebParam(name= "a_iCaptureData") ACaptureData a_iCaptureData)
    {   
        // Not implemented yet.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
//    @WebMethod(operationName = "getCurrentSensorRangesForEnvironment")
//    public ArrayList <SensorRange> getCurrentSensorRangesForEnvironment(@WebParam(name= "a_envID") long a_envID) throws DAOException
//    {
//        SensorRangeDAO sensorRangeDAO = daoFactory.getSensorRangeDAO();
//        return sensorRangeDAO.getCurrentProblemSensorRangesForEnvironmentID(a_envID);
//    }
    
    /**
     * This method will return the Sensor Ranges currently active depending on the 
     * Sensor Channel's current measurement and what Sensor Range threshold it fall into.
     * 
     * In otherwords it will return information on the current state of the sensor or item that 
     * contains the sensor.
     * 
     * @param a_igetCurrentSensorRanges
     * @return
     * @throws com.ems.utils.DAOException
     */
    @WebMethod(operationName = "getCurrentSensorRanges")
    public ArrayList<SensorRange> getCurrentSensorRanges(@WebParam(name= "a_iCurrentSensorRanges") ADescription a_igetCurrentSensorRanges) throws DAOException
    {
         SensorRangeDAO sensorRangeDAO = daoFactory.getSensorRangeDAO();
        
        ArrayList<SensorRange> returnList = null;
        
        //
        // Get Sensor Ranges for Environment
        //
        if(a_igetCurrentSensorRanges instanceof  Environment)
        {
            returnList = sensorRangeDAO.getCurrentProblemSensorRangesForEnvironmentID(a_igetCurrentSensorRanges.id);
        }
        
        //
        // Get Sensor Ranges for Domain
        //
        else if(a_igetCurrentSensorRanges instanceof  Domain)
        {
            returnList = sensorRangeDAO.getCurrentProblemSensorRangesForDomainID(a_igetCurrentSensorRanges.id);
        }
        
        //
        // Get ranges for Sensor Channels
        //
        else if(a_igetCurrentSensorRanges instanceof  SensorChannel)
        {
            SensorChannel senChan = (SensorChannel)a_igetCurrentSensorRanges;
            returnList = sensorRangeDAO.getCurrentProblemSensorRangesForSensorChannel(senChan.id, senChan.channelNum);
        }
        return  returnList; 
    }
    
    // This is here so JAXB will put the return objects in the WSDL so mappings will happen 
    // correctly when 
    @WebMethod(operationName = "snapShots")
    public Snapshot snapshots()
    {
        return null;
    }
    
    @WebMethod(operationName = "measurements")
    public Measurement measurements()
    {
        return null;
    }
    
    @WebMethod(operationName = "cameras")
    public Camera cameras()
    {
        return null;
    }
    
    @WebMethod(operationName = "noteTakers")
    public NoteTaker noteTakers()
    {
        return null;
    }
    
    @WebMethod(operationName = "notes")
    public Note notes()
    {
        return null;
    }
    
    @WebMethod(operationName = "sensorChannels")
    public SensorChannel sensorChannels()
    {
        return null;
    }

    
}
