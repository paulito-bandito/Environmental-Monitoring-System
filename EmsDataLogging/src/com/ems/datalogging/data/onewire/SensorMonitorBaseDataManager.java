/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.datalogging.data.onewire;

import com.ems.datalogging.business.ContactDataTO;
import com.ems.datalogging.business.SensorChannel;
import com.ems.datalogging.business.SensorRange;
import com.ems.datalogging.business.UserTO;
import com.ems.datalogging.business.SensorTO;
import com.ems.datalogging.business.SensorTypeTO;
import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.db.EmsDataLoggingDbDAOFactory;
import com.ems.datalogging.data.db.SensorDAO;
import com.ems.datalogging.data.db.SensorMetricTypeDAO;
import com.ems.datalogging.data.db.SensorReadingDAO;
import com.ems.datalogging.data.db.SensorTypeDAO;
import com.ems.datalogging.data.db.UserDAO;
import com.ems.datalogging.data.email.EmailDAO;
import com.ems.datalogging.data.utils.DAOException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Paul W Walter
 */
public abstract class SensorMonitorBaseDataManager 
{
    protected SensorReadingDAO sensorReadingDAO;
    protected SensorMetricTypeDAO sensorMetricTypeDAO;
    protected SensorDAO sensorDAO;
    protected SensorTypeDAO sensorTypeDAO;
    protected EmailDAO emailDAO;
    protected SensorTO sensor;
    protected EmsDataLoggingDbDAOFactory emsDaoFactory;
    protected UserDAO userDAO;
    
    // -------------
    //   Constructor 
    // -------------
    
    public SensorMonitorBaseDataManager() throws DAOException
    {
        emsDaoFactory = EmsDataLoggingDbDAOFactory.getInstance(EmsDataLoggingConstants.EMS_INSTANCE_NAME);        
        sensorReadingDAO = emsDaoFactory.getSensorReadingDAO();
        emailDAO = emsDaoFactory.getEmailDAO();
        sensorDAO = emsDaoFactory.getSensorDAO();
        userDAO = emsDaoFactory.getUserDAO();
        sensorMetricTypeDAO = emsDaoFactory.getSensorMetricTypeDAO();
        sensorTypeDAO = emsDaoFactory.getSensorTypeDAO();
    }
     
    // -------------
    //   Getters 
    // -------------
    
        
   /**
    * Used to get the Default Update speed from the EmsDataLoggingDbDAOFactory
    * @return
    */
    public abstract long getSensorDefaultUpdateSpeed();
    
    /**
    * Used to get the  Sensor Metric Type Id for a Temperature measurement in Celcius from the EmsDataLoggingDbDAOFactory
    * @return
    */
    public abstract long getSensorMetricTypeIdForCelcius();
    
    /**
    * Used to get the  Sensor Metric Type Id for a Humidity measurement from the EmsDataLoggingDbDAOFactory
    * @return
    */
    public abstract long getSensorMetricTypeIdForHumidity();
    
    /**
    * Used to get the Sensor Metric Type Id for a Solar measurement from the EmsDataLoggingDbDAOFactory
    * @return
    */
    public abstract long getSensorMetricTypeIdForSolar() ;
    
    /**
    * Used to get the Senor Type Id for a Temperature sensor from the EmsDataLoggingDbDAOFactory
    * @return
    */
    public abstract long getSensorTypeIdForTemperatureSensor();
    
    /**
    * Used to get the Sensor Type Id for a Temperature - Solar - Humdity sensor from the EmsDataLoggingDbDAOFactory
    * @return
    */
    public abstract long getSensorTypeIdForTemperatureSolarHumiditySensor();

    
    // -------------
    //   Actions 
    // -------------
   
    /**
     * 
     * @param networkAddress
     * @return
     */
    public SensorTO findSensor(String a_networkAddress) throws DAOException
    {
        return sensorDAO.findByNetworkAddress(a_networkAddress);
    }
    /**
     * This will record and check the measurement to see if it is falling outside the specified range of what is acceptable.
     * @param a_metric
     * @param a_channel
     */
    public void loadAndCheckMeasurements( int a_channel, Timestamp a_timestamp, Double a_measurement) throws Exception
    {
        
        // record measurement
        sensorReadingDAO.logASensorReading(this.sensor.getTheId(), a_channel, a_timestamp, a_measurement );               
        
        // try and get the sensor channel
        SensorChannel thisChannel = this.sensor.getSensorChannels().get(a_channel);
        ArrayList<SensorRange> sensorRanges= thisChannel.getSensorRanges();
        ArrayList<SensorRange> listOfRangesThatAreProblems = new ArrayList<SensorRange>();
        boolean doWeNeedToCallStewards = false;
        
        // check ranges
        for(int i=0; i< sensorRanges.size(); i++)
        {
            SensorRange thisRange = sensorRanges.get(i);                   
            
            // We are seeing if this measurement falls between the max and the minimum, that is why I am using a && operator.
            if((a_measurement < thisRange.getMax()) && (a_measurement > thisRange.getMin()))
            {
                // Check to see if it above the specified max
                doWeNeedToCallStewards = true;
                listOfRangesThatAreProblems.add(thisRange);
            }
        }
        
        // check to see if we need to call the Stewards
        if(doWeNeedToCallStewards)
        {
            this.emailStewards(listOfRangesThatAreProblems, a_measurement, thisChannel);
        }
        
    }
    /**
     * 
     * @param peopleToEmail
     */
    public void emailStewards(ArrayList<SensorRange> listOfSensorRangesThatHaveBeenTransgressed, Double a_measurement, SensorChannel a_sensorChannel) throws Exception
    {
        // Create message to send
        String msgSubject = "EMS: Errors detected for Environment "+this.sensor.getEnvironmentID()+" '" + this.sensor.getEnvironmentName() + "'...";
        String msgContent = "For sensor, '" + this.sensor.getTheTitle() +"' with an ID of "+ this.sensor.getTheId()+", " +
                "on channel " + a_sensorChannel.getChannelNum() + " measuring " + a_sensorChannel.getSensorMetricType().getTheName() +
                ", here are the errors that were detected:\n";
        
        // For sensor ranges print out what has been found.
        for(int i=0; i< listOfSensorRangesThatHaveBeenTransgressed.size(); i++)
        {
            SensorRange thisRange = listOfSensorRangesThatHaveBeenTransgressed.get(i);                   
            
            msgContent += "\n\t\t" +thisRange.getTitle() + "\n";
            msgContent += "\t\tMax value: " +thisRange.getMax() + "\n";
            msgContent += "\t\tMin value: " +thisRange.getMin() + "\n";
            msgContent += "\t\tActual value found: " + a_measurement + "\n";
            
        }
        
        // Create list of email addresses to pass
        ArrayList <String> emailsToSendTo = new ArrayList<String>();
        ArrayList <UserTO> users = this.sensor.getCallList();
        
        for(int i=0; i< users.size(); i++)
        {
            //emailsToSendTo.add(users.get(i).);
            ArrayList <ContactDataTO> userContactData = users.get(i).getContactData();
            
            // loop through contact data
             for(int y=0; y< userContactData.size(); y++)
             {
                 emailsToSendTo.add(userContactData.get(y).getContactDataAddress());
             }
            
            
        }
        
       emailDAO.SendMessage(emailsToSendTo, msgSubject,  msgContent, null);
    }
    
    /**
     * This method will find the sensor in the database, if it doesn't find it
     * it will log this sensor. The reason it is abstract is because different
     * sensors will have a different number of channels to configure in the
     * database.
     * 
     * @param a_sensorNetworkAddress
     * @throws com.ems.datalogging.data.utils.DAOException
     */
    abstract void init(String aNetworkAddress, String aTitle, String aDesc, double aUpdateSpeed) throws DAOException ;
    
     /**
     * This abstract method is responsible for creating a new data 
     * entity of this in the database with all channels intact
     * 
     * @param networkAddress
     * @param aTitle
     * @param aDesc
     * @param updateSpeed
     * @return
     */
    public abstract SensorTO logSensor(String networkAddress, String aTitle, String aDesc, double updateSpeed ) throws DAOException;
    
}
