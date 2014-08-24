package com.ems.datalogging.data.utils;

import com.ems.datalogging.data.db.ContactDataTypeDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



/**
 * This class represents a DAO factory for a relational database. The database is generic and depends if you have the correct drivers installed, and that your properties are configured correctly 
 * <p>You can use {@link #getInstance(String)}
 * to obtain an instance for the given database name. You can obtain DAO's for the database instance
 * using the DAO getters.
 * <p>This class requires a properties file named 'dao.properties' in the classpath with under each
 * the following properties:
 * <pre>
 * name.driver *
 * name.url *
 * name.username
 * name.password
 * </pre>
 * Those marked with * are required, others are optional and can be left away or empty.
 * <ul>
 * <li>The 'name' must represent the database name in {@link #getInstance(String)}.</li>
 * <li>The 'name.driver' must represent the full qualified class name of the JDBC driver.</li>
 * <li>The 'name.url' must represent the JDBC URL of the database.</li>
 * <li>The 'name.username' must represent the username of the database login.</li>
 * <li>The 'name.password' must represent the password of the database login.</li>
 * </ul>
 * Here is a basic example of valid properties for a database with the name 'javabase':
 * <pre>
 * javabase.driver = com.mysql.jdbc.Driver
 * javabase.url = jdbc:mysql://localhost:3306/javabase
 * javabase.username = java
 * javabase.password = d$7hF_r!9Y
 * </pre>
 * Here is a basic use example:
 * <pre>
 * DAOFactory javabase = DAOFactory.getInstance("javabase");
 * UserDAO userDAO = javabase.getUserDAO();
 * </pre>
 *
 * @author BalusC, modified by Paul Walter
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public class DAOFactory {

    // Constants ----------------------------------------------------------------------------------

//    private static final String PROPERTY_DRIVER = "DB_DRIVER";
//    private static final String PROPERTY_HOST_NAME = "DB_HOST";
//    private static final String PROPERTY_USERNAME = "username";
//    private static final String PROPERTY_PASSWORD = "password";
    
	private static final String DB_HOST = "DB_HOST";
	private static final String DB_USER = "DB_USER";
	private static final String DB_PASSWORD = "DB_PASS";
	private static final String DB_NAME = "DB_NAME";
	private static final String TYPE_MYSQL = "TYPE_MYSQL";
	private static final String DB_DRIVER = "DB_DRIVER";
        private static final String DB_DRIVER_CONNECT_STRING = "DB_DRIVER_CONNECT_STRING";
	private static final String DEFAULT_PORT = "DEFAULT_PORT";
        
	
	//private final String JDBC_PREFIX = "jdbc:";
	
    
    private static final Map<String, DAOFactory> INSTANCES = new HashMap<String, DAOFactory>();
    private static final boolean DEBUG = false;

    // Vars ---------------------------------------------------------------------------------------

//    private String url;
//    private String username;
//    private String password;
    
        protected String hostName = null;
	protected String userName = null;
	protected String pwd = null;
	protected String databaseName = null;	
	protected String dbDriver = null;
	protected String dbPort = null;
        protected String driverConnectionString = null;
        protected DAOProperties properties;
        //private String typeMysql = null;
        
    // Constructors -------------------------------------------------------------------------------

	
	
    /**
     * Construct a DAOFactory instance for the given database name.
     * @param name The database name to construct a DAOFactory instance for.
     * @throws DAOConfigurationException If the properties file is missing in the classpath or
     * cannot be loaded, or if a required property is missing in the properties file, or if the
     * JDBC driver class is missing in the classpath.
     */
    protected DAOFactory(String name) throws DAOConfigurationException {
        
        // GET PROPERTIES
    	properties = new DAOProperties(name);
    	
    	// String driverClassName = properties.getProperty(DB_DRIVER, true);
    	 this.databaseName = properties.getProperty(DB_NAME, true);
    	 this.dbDriver =  properties.getProperty(DB_DRIVER, true);
    	 this.dbPort = properties.getProperty(DEFAULT_PORT, true);
    	 this.hostName = properties.getProperty(DB_HOST, true);
    	 this.pwd = properties.getProperty(DB_PASSWORD, true);
    	 this.userName = properties.getProperty(DB_USER, true);    	 
         this.driverConnectionString = properties.getProperty(DB_DRIVER_CONNECT_STRING, true);
         //this.typeMysql = properties.getProperty(TYPE_MYSQL, true);

        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            throw new DAOConfigurationException(
                "Driver class '" + dbDriver + "' is missing in classpath.", e);
        }
        
    }

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns a DAOFactory instance for the given database name.
     * @param name The database name to return a DAOFactory instance for.
     * @return A DAOFactory instance for the given database name.
     * @throws DAOConfigurationException If the database name is null, or if the properties file is
     * missing in the classpath or cannot be loaded, or if a required property is missing in the
     * properties file, or if the JDBC driver class is missing in the classpath.
     */
    public static DAOFactory getInstance(String name) throws DAOConfigurationException {
        if (name == null) {
            throw new DAOConfigurationException("Database name is null.");
        }
        DAOFactory instance = INSTANCES.get(name);
        if (instance == null) {
            instance = new DAOFactory(name);
            INSTANCES.put(name, instance);
        }
        return instance;
    }

    /**
     * Returns a connection to the database. Package private so that it can be used inside the DAO
     * package only.
     * @return A connection to the database.
     * @throws SQLException If acquiring the connection fails.
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        
 
			
			String connectionString = "";
		
//			if( typeMysql == TYPE_MYSQL ) {
//				Class.forName( dbDriver );
//				
//				if( dbPort.equals( "" ) ) {
//					dbPort = dbPort;
//				}
//				
			//connectionString += "mysql://" + hostName + ":" + dbPort + "/" + databaseName;
                        connectionString += driverConnectionString + "//" +  hostName + ":" + dbPort + "/" + databaseName;
//			}
			
			conn = DriverManager.getConnection( connectionString, userName, pwd );
		
			return conn;
        
        
    }

   
    
    
    // DAO getters --------------------------------------------------------------------------------


//    public ContactDataTypeDAO getContactDataTypeDAO() {
//        return new ContactDataTypeDAO(this);
//    }
   
    
}