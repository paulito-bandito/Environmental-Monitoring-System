package com.ems.datalogging.constants;

/**
 * This class will contain the SQL for the application so it is not tied into the different DAO classes and impossible to read.
 * @author Paul W Walter
 */
public class EmsDataLoggingConstants {
    
    // ---------------------------------------------
    //                  Email Constants
    // ---------------------------------------------
    
    public static String EMAIL_PROPERTY_NAME_FOR_HOST_PORT = "EMAIL_PROPERTY_SMTP_HOST_PORT";
    public static String EMAIL_PROPERTY_NAME_FOR_HOST_ADDRESS = "EMAIL_PROPERTY_SMTP_HOST_NAME";
    public static String EMAIL_PROPERTY_NAME_FOR_EMAIL_ACCOUNT_USERNAME= "EMAIL_PROPERTY_USER_ACCOUNT_EMAIL_ADDRESS";
    public static String EMAIL_PROPERTY_NAME_FOR_EMAIL_ACCOUNT_PASSWORD = "EMAIL_PROPERTY_USER_ACCOUNT_PASSWORD";
     
    
    // ---------------------------------------------
    //                  General Database Constants 
    // ---------------------------------------------
    
    public static final String EMS_INSTANCE_NAME =  "EMS";
    public  static final String PROPERTIES_FILE = "dao.properties"; // the file in the classpath that has the connection info
    
    
    // ---------------------------------------------
    //                  DEVICE 
    // ---------------------------------------------
         
     public static String DEVICE_DAO_INSERT_NEW_DEVICE  =
                
                "INSERT INTO device (device_id, environment_id, device_title, device_description) VALUES (null, ?, ?, ?)";
     
     // Property names for the dao.property file
     public static String DEVICE_DEFAULT_ENVIRONMENT_ID = "DB_PROPERTY_DEFAULT_ENVIRONMENT_ID_THAT_NEW_DEVICES_SHOULD_DEFAULT_TO";
     
    // ---------------------------------------------
    //                  SENSOR 
    // ---------------------------------------------
    
     public static final String SENSOR_DAO_FIND_SENSOR_BY_NETWORK_ADDRESS =  
             
                "SELECT s.sensor_id, d.device_title, d.device_description, s.sensor_type_id, s.sensor_network_address, s.sensor_update_speed, st.sensor_type_name, e.environment_title, e.environment_id " +
                "FROM device d " +
                "INNER JOIN sensor s ON d.device_id = s.sensor_id " +
                "INNER JOIN sensor_type st ON  s.sensor_type_id = st.sensor_type_id " +
                "INNER JOIN environment e ON e.environment_id = d.environment_id " +
                "WHERE s.sensor_network_address = ?";
     
     public static String SENSOR_DAO_INSERT_NEW_DEVICE  =
                
                "INSERT INTO sensor (sensor_id, sensor_type_id, sensor_network_address, sensor_update_speed) VALUES (?, ?, ?, ?)";
     
     // Property names for the dao.property file
     public static String SENSOR_TYPE_PROPERTY_NAME_FOR_TEMPERATURE_HUMIDITY_SOLAR_SENSOR = "DB_PROPERTY_SENSOR_TYPE_ID_FOR_TEMPERATURE_SOLAR_HUMDITY_SENSOR";
     public static String SENSOR_TYPE_PROPERTY_NAME_FOR_HUMIDITY_SENSOR= "DB_PROPERTY_SENSOR_TYPE_ID_FOR_HUMIDITY_SENSOR";
     public static String SENSOR_TYPE_PROPERTY_NAME_FOR_SOLAR_SENSOR= "DB_PROPERTY_SENSOR_TYPE_ID_FOR_SOLAR_SENSOR";
     public static String SENSOR_TYPE_PROPERTY_NAME_FOR_TEMPERATURE_SENSOR = "DB_PROPERTY_SENSOR_TYPE_ID_FOR_TEMPERATURE_SENSOR";
     public static String SENSOR_DEFAULT_UPDATE_SPEED_IN_MILLISECONDS_PROPERTY_NAME = "DB_PROPERTY_SENSOR_DEFAULT_UPDATE_SPEED_IN_MILLISECONDS";
     
//     public static final long SENSOR_TYPE_TEMP_HUMIDITY_SOLAR_SENSOR_ID = 2;
//     public static final long SENSOR_TYPE_TEMPERATURE_ID = 1;
//     public static long SENSOR_DEFAULT_UPDATE_SPEED_IN_MILLISECONDS = 19000;
     
     
    // ---------------------------------------------
    //                  SENSOR METRIC TYPE 
    // ---------------------------------------------
     
     public static final String SENSOR_METRIC_TYPE_DAO_FIND_SENSOR_METRIC_BY_ID =  
             
             "SELECT sensor_metric_type_id, sensor_metric_name, sensor_metric_description " +
             "FROM sensor_metric_type " +
             "WHERE sensor_metric_type_id = ?";
     
    // ---------------------------------------------
    //                  SENSOR METRIC TYPE 
    // ---------------------------------------------
     
     public static final String SENSOR_RANGE_DAO_FIND_SENSOR_RANGES_BY_SENSOR_ID =  
             
             "SELECT sensor_range_id, sensor_id, sensor_channel_num,  sensor_range_title, sensor_range_min, sensor_range_max " +
             "FROM sensor_range " +
             "WHERE sensor_id = ? " +
             "AND sensor_channel_num = ?";
     
    
     // Property names for the dao.property file
     public static String SENSOR_METRIC_TYPE_PROPERTY_NAME_FOR_CELCIUS = "DB_PROPERTY_SENSOR_METRIC_TYPE_ID_FOR_CELCIUS";
     public static String SENSOR_METRIC_TYPE_PROPERTY_NAME_FOR_HUMIDITY = "DB_PROPERTY_SENSOR_METRIC_TYPE_ID_FOR_HUMIDITY";
     public static String SENSOR_METRIC_TYPE_PROPERTY_NAME_FOR_SOLAR = "DB_PROPERTY_SENSOR_METRIC_TYPE_ID_FOR_SOLAR";
     
     
    // ---------------------------------------------
    //                  SENSOR READING
    // ---------------------------------------------
     
     public static final String SENSOR_READING_DAO_INSERT_SENSOR_READING =  
             
            "INSERT INTO sensor_reading (sensor_reading_id, sensor_channel_num, sensor_reading_measurement, sensor_reading_timestamp, sensor_id) " +
            "VALUES (null, ?, ?, ?, ?)";
    
    // ---------------------------------------------
    //                  SENSOR CHANNEL
    // ---------------------------------------------
         
    public static final String SENSOR_CHANNEL_DAO_FIND_CHANNEL_BY_SENSOR_ID = 
            
            "SELECT sensor_id, sensor_channel_num, sensor_metric_type_id " + 
            "FROM sensor_channel " +
            "WHERE sensor_id = ?" +
            "ORDER BY sensor_id, sensor_channel_num";
    
    public static String SENSOR_CHANNEL_DAO_INSERT_CHANNEL = 
            "INSERT INTO sensor_channel (sensor_id, sensor_channel_num, sensor_metric_type_id, sensor_channel_type) " + 
            "VALUES (?, ?, ?, ?)";
    
    public static final int SENSOR_CHANNEL_DAO_CELCIUS_CHANNEL = 0;
    public static final int SENSOR_CHANNEL_DAO_HUMIDITY_CHANNEL = 1;
    public static final int SENSOR_CHANNEL_DAO_SOLAR_CHANNEL = 2;
    
    
    
    // ---------------------------------------------
    //                  SENSOR TYPE
    // ---------------------------------------------
         
    public static final String SENSOR_TYPE_DAO_FIND_BY_ID =
            "SELECT sensor_type_id, sensor_type_name " +
            "FROM sensor_type " +
            "WHERE sensor_type_id = ?";
    
    public static long SENSOR_DEFAULT_UPDATE_SPEED_IN_MILLISECONDS = 1900;
    
    // ---------------------------------------------
    //                  USER
    // ---------------------------------------------
    
    public static String USER_DAO_SELECT_USERS_BY_SENSOR_ID =
            
            "SELECT  u.user_table_id, u.user_username, u.user_password, " + 
            "ur.user_role_title, us.user_status_name  " + 
            "FROM 	user_table u, user_role ur, user_status us, domain d,  " + 
            "           domain_steward ds, environment e, device dev, sensor s  " + 
            "WHERE 	u.user_role_id = ur.user_role_id  " + 
            "AND u.user_status_id = us.user_status_id  " + 
	    "AND u.user_table_id  = ds.user_table_id  " + 
	    "AND ds.domain_id = d.domain_id  " + 
	    "AND d.domain_id = e.domain_id " + 
	    "AND dev.environment_id = e.environment_id " + 
	    "AND s.sensor_id = dev.device_id " + 
	    "AND s.sensor_id = ? " ;
     
    public static String USER_DAO_FIND_BY_ID = 
            "SELECT u.user_table_id, u.user_username, u.user_password, ur.user_role_title, us.user_status_name " +
            "FROM user_table u, user_role ur, user_status us " +
            "WHERE u.user_role_id = ur.user_role_id " +
            "AND u.user_status_id = us.user_status_id " +
            "AND u.user_table_id = ?";
    
    public static String USER_DAO_LIST_ALL_BY_ID = 
            "SELECT u.user_table_id, u.user_username, u.user_password, ur.user_role_title, us.user_status_name " +
            "FROM user_table u, user_role ur, user_status us " +
            "WHERE u.user_role_id = ur.user_role_id " +
            "AND u.user_status_id = us.user_status_id " +
            "ORDER BY u.user_table_id";
    
    public static String USER_DAO_FIND_BY_DOMAIN_ID = 
            "SELECT DISTINCT u.user_table_id, u.user_username, u.user_password, ur.user_role_title, us.user_status_name " +
            "FROM user_table u, user_role ur, user_status us, domain d, domain_steward ds " +
            "WHERE u.user_role_id = ur.user_role_id " +
            "AND u.user_status_id = us.user_status_id " +
            "AND u.user_table_id  = ds.user_table_id " +
            "AND ds.domain_id = d.domain_id AND d.domain_id = ?";
    
    // ---------------------------------------------
    //                  CAMERA DAO
    // ---------------------------------------------
    
    public static String CAMERA_DAO_FIND_BY_ID = 
            "SELECT c.camera_id, d.environment_id, d.device_title, d.device_description, c.camera_media_location, camera_snapshot_interval " +
            "FROM device d INNER JOIN camera c ON d.device_id = c.camera_id " +
            "WHERE c.camera_id = ?";
    
    public static String CAMERA_DAO_FIND_BY_NETWORK_ADDRESS = 
            "SELECT c.camera_id, d.environment_id, d.device_title, d.device_description, c.camera_media_location, camera_snapshot_interval " +
            "FROM device d INNER JOIN camera c ON d.device_id = c.camera_id " +
            "WHERE c.camera_media_location = ?";
    
    public static String CAMERA_DAO_LIST_ALL_BY_ID =  
            "SELECT c.camera_id, d.environment_id, d.device_title, d.device_description, c.camera_media_location, camera_snapshot_interval " +
            "FROM device d INNER JOIN camera c ON d.device_id = c.camera_id " +
            "ORDER BY c.camera_id";;
    
            public static String CAMERA_DAO_FIND_BY_ENV_ID = 
            "SELECT c.camera_id, d.environment_id, d.device_title, d.device_description, c.camera_media_location, camera_snapshot_interval " +
            "FROM device d INNER JOIN camera c ON d.device_id = c.camera_id " +
            "WHERE d.environment_id = ?";;
    
    public static String CAMERA_DAO_SNAPSHOT_DIRECTORY_PROPERTY_NAME = "CAMERA_SNAPSHOT_DIRECTORY";
    
    // ---------------------------------------------
    //                  SNAPSHOT DAO
    // ---------------------------------------------
    
    public static String SNAPSHOT_DAO_FIND_BY_SNAPSHOT_ID = 
            "SELECT camera_snapshot_id, camera_id, camera_snapshot_timestamp, camera_snapshot_filename " +
            "FROM camera_snapshot " +
            "WHERE camera_snapshot_id = ?";
    
    public static String SNAPSHOT_DAO_LIST_ALL_BY_ID = 
            "SELECT camera_snapshot_id, camera_id, camera_snapshot_timestamp, camera_snapshot_filename " +
            "FROM camera_snapshot " +
            "ORDER BY camera_snapshot_id";
    
    public static String SNAPSHOT_DAO_FIND_BY_CAMERA_ID = 
            "SELECT camera_snapshot_id, camera_id, camera_snapshot_timestamp, camera_snapshot_filename " +
            "FROM camera_snapshot " +
            "WHERE camera_id = ?";
    
    public static String SNAPSHOT_DAO_INSERT_SNAPSHOT = 
            "INSERT INTO camera_snapshot (camera_snapshot_id, camera_id, camera_snapshot_timestamp, camera_snapshot_filename) " +
            "VALUES (null, ?, ?, ?)";
    
    public static String SNAPSHOT_DAO_UPDATE_SNAPSHOT = 
            "UPDATE users SET camera_id = ?, camera_snapshot_filename = ?, camera_snapshot_timestamp = ? " +
            "WHERE camera_snapshot_id = ?";
    
    // ---------------------------------------------
    //                  CONTACT DATA DAO
    // ---------------------------------------------
    
    public static String CONTACT_DATA_DAO_FIND_BY_CONTACT_DATA_ID = 
            "SELECT contact_data_id, contact_data_title, contact_data_address, contact_data_description, contact_data_type_id, user_id  " +
            "FROM contact_data " +
            "WHERE contact_data_id = ?";
    
    public static String CONTACT_DATA_DAO_LIST_ALL_ORDER_BY_ID =
            "SELECT contact_data_id, contact_data_title, contact_data_address, contact_data_description, contact_data_type_id, user_id  " +
            "FROM contact_data " +
            "ORDER BY contact_data_id";
    
    public static String CONTACT_DATA_DAO_FIND_BY_USER_ID = 
            "SELECT DISTINCT contact_data_id, contact_data_title, contact_data_address, contact_data_description, contact_data_type_id, user_id  " +
            "FROM contact_data " +
            "WHERE user_id = ?";
     
    // ---------------------------------------------
    //                  CONTACT DATA TYPE DAO
    // ---------------------------------------------
    
    public static String CONTACT_DATA_TYPE_DAO_FIND_BY_CONTACT_DATA_TYPE_ID =
            "SELECT contact_data_type_id, contact_data_type_title, contact_data_type_desc " +
            "FROM contact_data_type " +
            "WHERE contact_data_type_id = ?";
    
    public static String CONTACT_DATA_TYPE_DAO_LIST_ALL_ORDER_BY_ID = 
            "SELECT contact_data_type_id, contact_data_type_title, contact_data_type_desc " +
            "FROM contact_data_type " +
            "ORDER BY contact_data_type_id";
    
    
    
    
    
   
    

}
