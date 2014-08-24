package com.ems.syndication.constants;

/**
 * This class will contain the SQL for the application so it is not tied into the different DAO classes and impossible to read.
 * @author Paul W Walter
 */
public class EmsSyndicationConstants {
    // ---------------------------------------------
    //                  General Database Constants 
    // ---------------------------------------------
    
    public static final String EMS_INSTANCE_NAME =  "EMS";
    public  static final String PROPERTIES_FILE = "dao.properties"; // the file in the classpath that has the connection info
    public static final String ASSETS_FOLDER_LOCATION_PROPERTY_NAME = "ASSETS_DIRECTORY"; // where the assets folder is located so we can see the pictures
    // ---------------------------------------------
    //                  USER 
    // ---------------------------------------------
    
    public static final String USER_DAO_SET_NEW_TOKEN_BY_USERNAME_PASSWORD =  
            
            "UPDATE user_table " + 
            "SET user_currentToken = ? "+
            "WHERE user_table_id IN "+
                "(SELECT DISTINCT u.user_table_id FROM user_table u " +
                "INNER JOIN user_status us ON u.user_status_id = us.user_status_id "+
                "WHERE u.user_username = ? "+
                "AND u.user_password = ? "+
                "AND us.user_status_name = 'Active' "+
                "AND rownum =1) ";
    
    public static final String USER_DAO_SET_NEW_TOKEN_BY_USER_TOKEN =  
            
            "UPDATE user_table " + 
            "SET user_currentToken = ? "+
            "WHERE user_table_id IN "+
                "(SELECT DISTINCT u.user_table_id FROM user_table u " +
                "INNER JOIN user_status us ON u.user_status_id = us.user_status_id "+
                "WHERE u.user_currentToken = ? "+
                "AND us.user_status_name = 'Active' "+
                "AND rownum =1) ";

    public static final String USER_DAO_UPDATE_TOKEN =   
            
            "UPDATE user_table " +
            "SET user_currentToken = ? " +
            "WHERE user_currentToken = ? " +
            "AND user_currentToken != null ";  
   
   public static final String USER_DAO_GET_USER_ID =   
           
           "SELECT user_table_id  " +
           "FROM user_table  " +
           "WHERE user_username = ? " +
           "AND user_password = ? " ;

   public static final String USER_DAO_GET_USER_ID_BY_USERTOKEN =   
           
           "SELECT user_table_id  " +
           "FROM user_table  " +
           "WHERE user_currentToken = ? ";
   
    /**
    * This is the column name that we are interested in from the 'USER_DAO_GET_USER_ID' query
    */
    public static final String USER_DAO_GET_USER_ID_USER_ID_COL = "user_table_id";
   
    public static final String USER_DAO_DELETE_TOKEN =   
            
            "UPDATE user_table "+
            "SET user_currentToken = null "+
            "WHERE user_currentToken = ? ";
    
    public static final String USER_DAO_GET_USER_BY_ID = 
            
            " SELECT user_table_id, user_username  " + 
            " FROM user_table " +
            " WHERE user_table_id = ? ";
    
    // ---------------------------------------------
    //                  DOMAIN 
    // ---------------------------------------------
    
    public static final String DOMAIN_DAO_GET_DOMAIN_LIST_FOR_USER_TOKEN = 
            
            "SELECT d.domain_id, d.domain_name, d.domain_description " + 
            "FROM Domain d INNER JOIN  Domain_Steward ds ON d.domain_id = ds.domain_id " +
            "INNER JOIN user_table u ON ds.user_table_id = u.user_table_id " +
            "WHERE u.user_currentToken = ? " ;

    
    // ---------------------------------------------
    //                  SENSOR RANGE
    // ---------------------------------------------
    
     public static final String SENSOR_RANGE_DAO_GET_SENSOR_RANGES_FOR_DOMAIN_ID =              
//    " SELECT domain.domain_id, e.environment_id, sen.sensor_id, sensorReading.sensor_reading_id, sensorReading.sensor_reading_measurement, " +
//    " 	sensorReading.sensor_reading_timestamp, sensorRange.sensor_range_id, sensorRange.sensor_range_priority, sensorRange.sensor_range_title, sensorRange.sensor_range_min,  " +
//    " 	sensorRange.sensor_range_max  " +
    " SELECT sensorRange.sensor_id, sensorRange.sensor_channel_num, sensorRange.sensor_range_id, " +
    "   sensorRange.sensor_range_priority, sensorRange.sensor_range_title, sensorRange.sensor_range_min,  " +
    " 	sensorRange.sensor_range_max  " +
    " FROM Device dev " +
    " INNER JOIN Sensor sen ON sen.sensor_id = dev.device_id " +
    " INNER JOIN Sensor_Type sensorType ON sen.sensor_type_id = sensorType.sensor_type_id " +
    " INNER JOIN Environment e ON dev.environment_id = e.environment_id " +
    " INNER JOIN Domain domain ON domain.domain_id = e.domain_id " +
    " INNER JOIN Sensor_channel sensorChannel ON sensorChannel.sensor_id = sen.sensor_id " +
    " INNER JOIN Sensor_metric_type sensorMetricType  ON sensorMetricType.sensor_metric_type_id = sensorChannel.sensor_metric_type_id " +
    " INNER JOIN Sensor_Reading sensorReading ON sensorReading.sensor_id = sensorChannel.sensor_id " +
    " AND sensorReading.sensor_channel_num = sensorChannel.sensor_channel_num " +
    " LEFT JOIN Sensor_Range sensorRange ON sensorRange.sensor_channel_num = sensorChannel.sensor_channel_num " +
    " AND sensorRange.sensor_id = sensorChannel.sensor_id " +
    " WHERE e.domain_id = ? " +
    " AND sensorReading.sensor_reading_id IN ( " +
    "     SELECT MAX(sr.sensor_reading_id) " +
    "     FROM Sensor_Reading sr " +
    "     WHERE sr.sensor_id = sensorChannel.sensor_id " +
    "     AND sr.sensor_channel_num = sensorChannel.sensor_channel_num) " +
    " AND sensorRange.sensor_range_min < sensorReading.sensor_reading_measurement " +
    " AND sensorRange.sensor_range_max > sensorReading.sensor_reading_measurement  " +
    " ORDER BY sensorRange.sensor_range_priority DESC " ;
     
     public static final String SENSOR_RANGE_DAO_GET_SENSOR_RANGES_FOR_ENVIRONMENT_ID =              
//    " SELECT domain.domain_id, e.environment_id, sen.sensor_id, sensorReading.sensor_reading_id, sensorReading.sensor_reading_measurement, " +
//    " 	sensorReading.sensor_reading_timestamp, sensorRange.sensor_range_id, sensorRange.sensor_range_priority, sensorRange.sensor_range_title, sensorRange.sensor_range_min,  " +
//    " 	sensorRange.sensor_range_max  " +
    " SELECT sensorRange.sensor_id, sensorRange.sensor_channel_num, sensorRange.sensor_range_id, " +
    "   sensorRange.sensor_range_priority, sensorRange.sensor_range_title, sensorRange.sensor_range_min,  " +
    " 	sensorRange.sensor_range_max  " +
    " FROM Device dev " +
    " INNER JOIN Sensor sen ON sen.sensor_id = dev.device_id " +
    " INNER JOIN Sensor_Type sensorType ON sen.sensor_type_id = sensorType.sensor_type_id " +
    " INNER JOIN Environment e ON dev.environment_id = e.environment_id " +
    " INNER JOIN Domain domain ON domain.domain_id = e.domain_id " +
    " INNER JOIN Sensor_channel sensorChannel ON sensorChannel.sensor_id = sen.sensor_id " +
    " INNER JOIN Sensor_metric_type sensorMetricType  ON sensorMetricType.sensor_metric_type_id = sensorChannel.sensor_metric_type_id " +
    " INNER JOIN Sensor_Reading sensorReading ON sensorReading.sensor_id = sensorChannel.sensor_id " +
    " AND sensorReading.sensor_channel_num = sensorChannel.sensor_channel_num " +
    " LEFT JOIN Sensor_Range sensorRange ON sensorRange.sensor_channel_num = sensorChannel.sensor_channel_num " +
    " AND sensorRange.sensor_id = sensorChannel.sensor_id " +
    " WHERE e.environment_id = ? " +
    " AND sensorReading.sensor_reading_id IN ( " +
    "     SELECT MAX(sr.sensor_reading_id) " +
    "     FROM Sensor_Reading sr " +
    "     WHERE sr.sensor_id = sensorChannel.sensor_id " +
    "     AND sr.sensor_channel_num = sensorChannel.sensor_channel_num) " +
    " AND sensorRange.sensor_range_min < sensorReading.sensor_reading_measurement " +
    " AND sensorRange.sensor_range_max > sensorReading.sensor_reading_measurement  " +
    " ORDER BY sensorRange.sensor_range_priority DESC " ;
     
      public static final String SENSOR_RANGE_DAO_GET_CURRENT_SENSOR_RANGES_FOR_SENSOR_ID_AND_SENSOR_CHANNEL_NUM =
              
    " SELECT sensorRange.sensor_id, sensorRange.sensor_channel_num, sensorRange.sensor_range_id," +
              "sensorRange.sensor_range_priority, sensorRange.sensor_range_title, sensorRange.sensor_range_min, " +
              "sensorRange.sensor_range_max  " +
              "FROM Device dev  " +
              "INNER JOIN Sensor sen ON sen.sensor_id = dev.device_id  " +
              "INNER JOIN Sensor_Type sensorType ON sen.sensor_type_id = sensorType.sensor_type_id " +
              "INNER JOIN Environment e ON dev.environment_id = e.environment_id  " +
              "INNER JOIN Domain domain ON domain.domain_id = e.domain_id  " +
              "INNER JOIN Sensor_channel sensorChannel ON sensorChannel.sensor_id = sen.sensor_id  " +
              "INNER JOIN Sensor_metric_type sensorMetricType  ON sensorMetricType.sensor_metric_type_id = sensorChannel.sensor_metric_type_id  " +
              "INNER JOIN Sensor_Reading sensorReading ON sensorReading.sensor_id = sensorChannel.sensor_id  " +
              "AND sensorReading.sensor_channel_num = sensorChannel.sensor_channel_num  " +
              "LEFT JOIN Sensor_Range sensorRange ON sensorRange.sensor_channel_num = sensorChannel.sensor_channel_num  " +
              "AND sensorRange.sensor_id = sensorChannel.sensor_id  " +
              "WHERE sensorRange.sensor_id = ? " +
              "AND sensorRange.sensor_channel_num = ? " +
              "AND sensorReading.sensor_reading_id IN ( " +
                    "SELECT MAX(sr.sensor_reading_id) " +
                    "FROM Sensor_Reading sr  " +
                    "WHERE sr.sensor_id = sensorChannel.sensor_id " +
                    "AND sr.sensor_channel_num = sensorChannel.sensor_channel_num) " +
              "AND sensorRange.sensor_range_min < sensorReading.sensor_reading_measurement " +
              "AND sensorRange.sensor_range_max > sensorReading.sensor_reading_measurement " +
              "ORDER BY sensorRange.sensor_range_priority DESC"; 
     
     public static final String SENSOR_RANGE_DAO_GET_SENSOR_RANGES_FOR_SENSOR_CHANNEL_ID_AND_SENSOR_CHANNEL_NUM =              
    " SELECT sensorRange.sensor_id, sensorRange.sensor_channel_num, sensorRange.sensor_range_id, " +
    "   sensorRange.sensor_range_priority, sensorRange.sensor_range_title, sensorRange.sensor_range_min,  " +
    " 	sensorRange.sensor_range_max  " +
    " FROM Sensor_Range sensorRange " +
    " WHERE sensorRange.sensor_id = ? " + 
    " AND sensorRange.sensor_channel_num = ? ";
    
    // ---------------------------------------------
    //                  ENVIRONMENT 
    // ---------------------------------------------
    
    public static final String ENVIRONMENT_DAO_GET_ENVIRONMENTS_FOR_DOMAIN_ID = 
            
            "SELECT DISTINCT e.environment_id, e.environment_title, e.environment_description, e.environment_creation_date, e.domain_id, e.environment_x_coord, e.environment_y_coord " + 
            "FROM Environment e " +
            "INNER JOIN Domain d ON e.domain_id = d.domain_id " +
            "INNER JOIN  Domain_Steward ds ON d.domain_id = ds.domain_id " +
            "WHERE d.domain_id = ? "  ;

            
    
    // ---------------------------------------------
    //                  DEVICE 
    // ---------------------------------------------
    
    public static final String DEVICE_DAO_GET_DEVICES_FOR_ENVIRONMENT_ID = 
            
//            " SELECT DISTINCT dev.device_id, cam.camera_id, sen.sensor_id, dev.device_title, dev.device_description " +
//            " FROM Device dev " +
//            " LEFT JOIN Camera cam ON cam.camera_id = dev.device_id " +
//            " LEFT JOIN Sensor sen ON sen.sensor_id = dev.device_id " +
//            " INNER JOIN Environment e ON dev.environment_id = e.environment_id " +
//            " WHERE e.environment_id = ? ";

            " SELECT DISTINCT dev.device_id, cam.camera_id, sen.sensor_id, senChan.sensor_channel_num,  senType.sensor_type_name, " +
            "   senMetType.sensor_metric_name , dev.device_title, dev.device_description " +
            " FROM Device dev  " +
            " LEFT JOIN Camera cam ON cam.camera_id = dev.device_id  " +
            " LEFT JOIN Sensor sen ON sen.sensor_id = dev.device_id " +
            " LEFT JOIN Sensor_Channel senChan ON senChan.sensor_id = dev.device_id " +
            " LEFT JOIN Sensor_Type senType ON senType.sensor_type_id = senChan.sensor_channel_type " +
            " LEFT JOIN Sensor_Metric_Type senMetType ON senMetType.sensor_metric_type_id = senChan.sensor_metric_type_id " +
            " INNER JOIN Environment e ON dev.environment_id = e.environment_id " +
            " WHERE e.environment_id = ? " +
            " ORDER BY dev.device_id, senChan.sensor_channel_num";

    
    // ---------------------------------------------
    //                  DEVICE TYPE 
    // ---------------------------------------------
    
    public static final String DEVICETYPE_DAO_CAMERA_TYPE = "camera";
    public static final String DEVICETYPE_DAO_SENSOR_TYPE = "sensor";
    public static final String DEVICETYPE_DAO_NOTE_TAKER = "noteTaker";
    
    // ---------------------------------------------
    //                  SENSOR READING
    // ---------------------------------------------
    
    public static final String SENSORREADING_DAO_GET_READINGS_FOR_SPECIFIC_CONTIGUOUS_CHUNK = 
            
            " SELECT sr.sensor_reading_id, sc.sensor_id, sc.sensor_channel_num, sr.sensor_reading_timestamp, sr.sensor_reading_measurement " +
            " FROM sensor_reading sr, sensor_channel sc " +
            " WHERE sc.sensor_id = sr.sensor_id " +
            " AND sc.sensor_channel_num = sr.sensor_channel_num" +
            " AND sc.sensor_id = ? " +
            " AND sc.sensor_channel_num = ?" +
            " AND rownum  >= ? " +
            " AND rownum <= ? " +
            " ORDER BY sr.sensor_reading_timestamp ASC ";

    public static String SENSORREADING_DAO_GET_MOST_CURRENT_READING_FOR_SPECIFIC_SENSOR_AND_CHANNEL_NUM =
            
//            " SELECT sr.sensor_reading_id, sc.sensor_id, sc.sensor_channel_num, sr.sensor_reading_timestamp, sr.sensor_reading_measurement " +
//            " FROM sensor_reading sr, sensor_channel sc " +
//            " WHERE sc.sensor_id = sr.sensor_id " +
//            " AND sc.sensor_channel_num = sr.sensor_channel_num " +
//            " AND sc.sensor_id = ?  " +
//            " AND sc.sensor_channel_num = ? " +
//            " AND sr.sensor_reading_timestamp in " +
//            "	(SELECT MAX(sensor_reading_timestamp) " +
//            "	 FROM sensor_reading " +
//            "	 WHERE sensor_id = ?)";
    
     " SELECT sr.sensor_reading_id, sc.sensor_id, sc.sensor_channel_num, sr.sensor_reading_timestamp, sr.sensor_reading_measurement " +
            " FROM sensor_reading sr, sensor_channel sc  " +
            " WHERE sc.sensor_id = sr.sensor_id  " +
            " AND sc.sensor_channel_num = sr.sensor_channel_num  " +
            " AND sc.sensor_id = ?   " +
            " AND sc.sensor_channel_num = ?  " +
            " AND sr.sensor_reading_timestamp in " +
            " (SELECT MAX(sensor_reading_timestamp) " +
            "       FROM sensor_reading  " +
            "       WHERE sensor_id = sc.sensor_id " +
            "       AND sensor_channel_num = sc.sensor_channel_num) ";
    
    // ---------------------------------------------
    //                  SNAPSHOT
    // ---------------------------------------------
    
    public static final String SNAPSHOT_DAO_GET_SNAPSHOTS_FOR_SPECIFIC_CONTIGUOUS_CHUNK = 
            
            " SELECT snap. camera_snapshot_id, snap.camera_snapshot_filename, snap.camera_snapshot_timestamp, cam.camera_id " + 
            " FROM camera_snapshot snap " + 
            " INNER JOIN Camera cam ON snap.camera_id = cam.camera_id " + 
            " WHERE cam.camera_id = ? " + 
            " AND rownum  >= ? " + 
            " AND rownum <= ? " +
            " ORDER BY snap.camera_snapshot_timestamp ASC ";


    public static String SNAPSHOT_DAO_GET_CURRENT_SNAPSHOT_FOR_SPECIFIC_DEVICE= 
            
            " SELECT snap.camera_snapshot_id, snap.camera_snapshot_filename, snap.camera_snapshot_timestamp, snap.camera_id " +
            " FROM camera_snapshot snap " +
            " WHERE snap.camera_id = ?    " +
            " AND snap.camera_snapshot_timestamp IN " +
            "	(SELECT MAX(snap2.camera_snapshot_timestamp) " +
            "	 FROM camera_snapshot snap2 " +
            "	 WHERE snap2.camera_id = ?)";        
    
    // ---------------------------------------------
    //                  NOTE (Environment Note)
    // ---------------------------------------------
    
    public static final String ENVIRONMENT_NOTE_DAO_LIST_NOTES_FOR_CONTIGUOUS_CHUNK = 
            
            " SELECT en.environment_note_id, en.environment_note_message, en.environment_note_timestamp, u.user_table_id, u.user_username " + 
            " FROM environment_note en " +
            " INNER JOIN Environment e ON en.environment_id = e.environment_id " +
            " INNER JOIN user_table u ON en.user_table_id = u.user_table_id " +
            " WHERE en.environment_id = ? " +
            " AND rownum  >= ? " +
            " AND rownum <= ? " +
            " ORDER BY en.environment_note_timestamp ASC ";


    public static final String ENVIRONMENT_NOTE_DAO_GET_CURRENT_NOTE = 
            
            " SELECT en.environment_note_id, en.environment_note_message, en.environment_note_timestamp, u.user_table_id, u.user_username " +
            " FROM environment_note en " +
            " INNER JOIN Environment e ON en.environment_id = e.environment_id" +
            " INNER JOIN user_table u ON en.user_table_id = u.user_table_id   " +
            " WHERE en.environment_id = ?  " +
            " AND en.environment_note_timestamp IN " +
            "	(SELECT max(sen.environment_note_timestamp) " +
            "	 FROM environment_note sen " +
            "	 WHERE  sen.environment_id = ?) " ;
            
    public static final String ENVIRONMENT_NOTE_DAO_ADD_NOTE = 
            " INSERT INTO environment_note " + 
            " (environment_note_message, environment_note_timestamp, environment_id, user_table_id) " +
            " VALUES (?, ?, ?, ?) ";

    /**
     * THis is the column we are interested in obtaining once the insertion goes through.
     */
    public static final String ENVIRONMENT_NOTE_DAO_ADD_NOTE_ID_COLUMN = "environment_note_id";
    
    public static final String ENVIRONMENT_NOTE_DAO_DELETE_NOTE = 
            
//            " DELETE en FROM environment_note en " + 
//            " INNER JOIN user_table u ON en.user_table_id = u.user_table_id " + 
//            " WHERE en.environment_note_id = ? " + 
//            " AND u.user_currentToken = ? ";

//            " DELETE FROM environment_note en " + 
//            " WHERE  en.environment_note_id = ? " + 
//            " AND en.user_table_id IN (SELECT u.user_table_id FROM user_table u WHERE u.user_currentToken = ?) " ;
            
            " DELETE FROM environment_note en " + 
            " WHERE  en.environment_note_id = ? ";
    
    // ---------------------------------------------
    //                  SENSOR_CHANNEL
    // ---------------------------------------------
    
    public static String SENSOR_CHANNEL_DAO_FIND_BY_SENSOR_ID  = 
            "SELECT sc.sensor_id, sc.sensor_channel_num, smt.sensor_metric_name " +
            "FROM sensor_channel sc " +
            "INNER JOIN sensor_metric_type smt " +
            "ON smt.sensor_metric_type_id = sc.sensor_metric_type_id " +
            "WHERE sensor_id = ? " +
            "ORDER BY sensor_channel_num ";
    
    
    


    
}
