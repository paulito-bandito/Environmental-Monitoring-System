package com.ems.datalogging.data.db;

import com.ems.datalogging.constants.EmsDataLoggingConstants;
import com.ems.datalogging.data.email.EmailDAO;
import com.ems.datalogging.data.utils.DAOConfigurationException;
import com.ems.datalogging.data.utils.DAOFactory;
import com.ems.datalogging.data.utils.DAOProperties;
import java.util.HashMap;
import java.util.Map;



public class EmsDataLoggingDbDAOFactory extends DAOFactory {
	
	protected static final Map<String, EmsDataLoggingDbDAOFactory> INSTANCES = new HashMap<String, EmsDataLoggingDbDAOFactory>();
    
        protected long sensorMetricTypeIdForCelcius = -1;
        protected long sensorMetricTypeIdForHumidity = -1;
        protected long sensorMetricTypeIdForSolar = -1;
        protected long sensorTypeIdForTemperatureSolarHumiditySensor = -1;
        protected long sensorTypeIdForTemperatureSensor = -1;
        protected long sensorTypeIdForSolarSensor = -1;
        protected long sensorTypeIdForHumiditySensor = -1;
        protected long sensorDefaultUpdateSpeed = -1;
        protected long deviceDefaultEnvironmentID;
        protected String cameraSnapshotDirectory;
        protected int email_hostPortNum;
        protected String email_hostAddress;
        protected String email_applicationEmailAccountUserName;
        protected String email_applicationEmailAccountPassword;

        // Getters
        public long getSensorDefaultUpdateSpeed() {
            return sensorDefaultUpdateSpeed;
        }
        
        public long getDeviceDefaultEnvironmentID() {
            return deviceDefaultEnvironmentID;
        }

        public long getSensorMetricTypeIdForCelcius() {
            return sensorMetricTypeIdForCelcius;
        }

        public long getSensorMetricTypeIdForHumidity() {
            return sensorMetricTypeIdForHumidity;
        }

        public long getSensorMetricTypeIdForSolar() {
            return sensorMetricTypeIdForSolar;
        }

        public long getSensorTypeIdForTemperatureSensor() {
            return sensorTypeIdForTemperatureSensor;
        }
        
        public long getSensorTypeIdForHumiditySensor() {
            return sensorTypeIdForHumiditySensor;
        }
        
        public long getSensorTypeIdForSolarSensor() {
            return sensorTypeIdForSolarSensor;
        }

        public long getSensorTypeIdForTemperatureSolarHumiditySensor() {
            return sensorTypeIdForTemperatureSolarHumiditySensor;
        }

        public String getEmail_applicationEmailAccountPassword() {
            return email_applicationEmailAccountPassword;
        }

        public String getEmail_applicationEmailAccountUserName() {
            return email_applicationEmailAccountUserName;
        }

        public String getEmail_hostAddress() {
            return email_hostAddress;
        }

        public int getEmail_hostPortNum() {
            return email_hostPortNum;
        }

        
        
        // Constructor
	private EmsDataLoggingDbDAOFactory( String name ) throws DAOConfigurationException {
		super( name );
                
                // GET PROPERTIES
                DAOProperties theProperties = new DAOProperties(name);

                // SENSOR METRIC TYPE PROPERTIES
                String celciusValue = this.properties.getProperty(EmsDataLoggingConstants.SENSOR_METRIC_TYPE_PROPERTY_NAME_FOR_CELCIUS, true);
                this.sensorMetricTypeIdForCelcius = Long.parseLong(celciusValue);
                String humidityValue = this.properties.getProperty(EmsDataLoggingConstants.SENSOR_METRIC_TYPE_PROPERTY_NAME_FOR_HUMIDITY, true);
                this.sensorMetricTypeIdForHumidity = Long.parseLong(humidityValue);
                String solarValue = this.properties.getProperty(EmsDataLoggingConstants.SENSOR_METRIC_TYPE_PROPERTY_NAME_FOR_SOLAR, true);
                this.sensorMetricTypeIdForSolar = Long.parseLong(solarValue);
                
                // SENSOR TYPES PROPERTIES
                String temperatureSensorID = this.properties.getProperty(EmsDataLoggingConstants.SENSOR_TYPE_PROPERTY_NAME_FOR_TEMPERATURE_SENSOR, true);
                this.sensorTypeIdForTemperatureSensor = Long.parseLong(temperatureSensorID);
                String humditySensorID = this.properties.getProperty(EmsDataLoggingConstants.SENSOR_TYPE_PROPERTY_NAME_FOR_HUMIDITY_SENSOR, true);
                this.sensorTypeIdForHumiditySensor = Long.parseLong(humditySensorID);
                String solarSensorID = this.properties.getProperty(EmsDataLoggingConstants.SENSOR_TYPE_PROPERTY_NAME_FOR_SOLAR_SENSOR, true);
                this.sensorTypeIdForSolarSensor = Long.parseLong(solarSensorID);
                String temperatureHumSolarSensorID = this.properties.getProperty(EmsDataLoggingConstants.SENSOR_TYPE_PROPERTY_NAME_FOR_TEMPERATURE_HUMIDITY_SOLAR_SENSOR, true);
                this.sensorTypeIdForTemperatureSolarHumiditySensor = Long.parseLong(temperatureHumSolarSensorID);
                
                // DEFAULT SENSOR UPDATE SPEED PROPERTIES    
                String sensorDefaultSpeed = this.properties.getProperty(EmsDataLoggingConstants.SENSOR_DEFAULT_UPDATE_SPEED_IN_MILLISECONDS_PROPERTY_NAME, true);
                this.sensorDefaultUpdateSpeed = Long.parseLong(sensorDefaultSpeed);
                
                // DEFAULT SENSOR UPDATE SPEED PROPERTIES    
                String adeviceDefaultEnvironmentID = this.properties.getProperty(EmsDataLoggingConstants.DEVICE_DEFAULT_ENVIRONMENT_ID, true);
                this.deviceDefaultEnvironmentID = Long.parseLong(adeviceDefaultEnvironmentID);
                
                // CAMERA PROPERTIES
                this.cameraSnapshotDirectory = this.properties.getProperty(EmsDataLoggingConstants.CAMERA_DAO_SNAPSHOT_DIRECTORY_PROPERTY_NAME, true);
                
                // EMAIL PROPERTIES
                this.email_hostPortNum = Integer.parseInt(this.properties.getProperty(EmsDataLoggingConstants.EMAIL_PROPERTY_NAME_FOR_HOST_PORT, true));
                this.email_hostAddress = this.properties.getProperty(EmsDataLoggingConstants.EMAIL_PROPERTY_NAME_FOR_HOST_ADDRESS, true);
                this.email_applicationEmailAccountUserName = this.properties.getProperty(EmsDataLoggingConstants.EMAIL_PROPERTY_NAME_FOR_EMAIL_ACCOUNT_USERNAME, true);
                this.email_applicationEmailAccountPassword = this.properties.getProperty(EmsDataLoggingConstants.EMAIL_PROPERTY_NAME_FOR_EMAIL_ACCOUNT_PASSWORD, true);
	}
	
	public static EmsDataLoggingDbDAOFactory getInstance( String name ) {
            if (name == null) {
                throw new DAOConfigurationException("Database name is null.");
            }
            
            EmsDataLoggingDbDAOFactory instance = INSTANCES.get(name);
            
            if (instance == null) {
                instance = new EmsDataLoggingDbDAOFactory(name);
                INSTANCES.put(name, instance);
            }
            return instance;
	}

        /**
         * Get the default sampling rate
         * @return
         */
        public String getCameraSnapshotDirectory()
        {
            return this.cameraSnapshotDirectory;
        }
        /**
         * Get the default sampling rate
         * @return
         */
        public long getSensorDefaultSamplingRate()
        {
            return this.sensorDefaultUpdateSpeed;
        }
        public SensorDAO getSensorDAO()
        {
            return new SensorDAO(this);
        }
        
        public CameraDAO getCameraDAO()
        {
            return new CameraDAO(this);
        }
        
        public SnapshotDAO getSnapshotDAO()
        {
            return new SnapshotDAO(this);
        }
        
        public SensorMetricTypeDAO getSensorMetricTypeDAO()
        {
            return new SensorMetricTypeDAO(this);
        }
        
        public SensorRangeDAO getSensorRangeDAO()
        {
            return new SensorRangeDAO(this);
        }
        
        public SensorReadingDAO getSensorReadingDAO()
        {
            return new SensorReadingDAO(this);
        }

        public SensorTypeDAO getSensorTypeDAO() {
            return new SensorTypeDAO(this);
        }
        
        public EmailDAO getEmailDAO()
        {
            return new EmailDAO(this);
        }

        public SensorChannelDAO getSenorChannelDAO() {
            return new SensorChannelDAO(this);
        }

        public ContactDataDAO getContactDataDAO() {
            return new ContactDataDAO(this);
        }

        public DeviceDAO getDeviceDAO() {
            return new DeviceDAO(this);
        }

        public ContactDataTypeDAO getContactDataTypeDAO() {
           return new ContactDataTypeDAO(this);
        }

        public UserDAO getUserDAO() {
            return new UserDAO(this);
        }

        
//    public DeviceToDAO getDeviceToDAO() {
//        if( aDeviceDAO == null ) {
//    		aDeviceDAO = new DeviceToDAO( this );
//    	}
//    	return aDeviceDAO;
//    }
//
//    public SensorMeasurementToDAO getSensorMeasurementToDAO() {
//        if( aSensorMeasurementDAO == null ) {
//    		aSensorMeasurementDAO = new SensorMeasurementToDAO( this );
//    	}
//    	return aSensorMeasurementDAO;
//    }
//
//    public SnapshotToDAO getSnapshotToDAO() {
//        if( aSnapshotDAO == null ) {
//    		aSnapshotDAO = new SnapshotToDAO( this );
//    	}
//    	return aSnapshotDAO;
//    }
//    
//    public UserToDAO getUserToDAO() {
//        if( aUserDAO == null ) {
//    		aUserDAO = new UserToDAO( this );
//    	}
//    	return aUserDAO;
//    }

    
}
