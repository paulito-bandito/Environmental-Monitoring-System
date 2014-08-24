package com.ems.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Utility class for DAO's. This class contains commonly used DAO logic which is been refactored in
 * single static methods. As far it contains a PreparedStatement values setter, several quiet close
 * methods and a MD5 hasher which conforms under each MySQL own md5() function.
 *
 * @author BalusC & modified by Paul Walter
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class DAOUtil {

    // Constructors -------------------------------------------------------------------------------

    private DAOUtil() {
        // Utility class, hide constructor.
    }

    // Actions ------------------------------------------------------------------------------------

    /**
     * Set the given PreparedStatement with the given parameter values.
     * @param preparedStatement The PreparedStatement to set the given parameter values in.
     * @param values The parameter values to be set in the given PreparedStatement.
     * @throws SQLException If something fails during setting the PreparedStatement values.
     */
    public static void setValues(PreparedStatement preparedStatement, Object... values)
        throws SQLException
    {
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i + 1, values[i]);            
        }
    }
   
    /**
     * Returns the Object from the database matching the given SQL query with the given values <br/>
     * Casting can be applied in the method that called it.
     * 
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The Object from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    public static Object findUtil(String sql, IDAOInterface callingObj, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Object returnObject = null;

        try {
            DAOFactory thisDAO = callingObj.getDAOFactory();            
            connection = thisDAO.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            DAOUtil.setValues(preparedStatement, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                returnObject = callingObj.mapData(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(resultSet);
            DAOUtil.close(preparedStatement);
            DAOUtil.close(connection);
        }

        return returnObject;
    }
    
    /**
     * Use this method when you need to manipulate the result set in a special way, and not have it map to a business object. For example, use this if you wish to get a count via sql
     * @param sql
     * @param callingObj
     * @param values
     * @return
     * @throws DAOException
     */
    public static String returnFirstValueAsString( String sql, IDAOInterface callingObj, Object... values) throws DAOException
    {
    	  Connection connection = null;
          PreparedStatement preparedStatement = null;
          ResultSet resultSet = null;
          String returnObject = "";
          long test;

          try {
              DAOFactory thisDAO = callingObj.getDAOFactory();            
              connection = thisDAO.getConnection();
              preparedStatement = connection.prepareStatement(sql);
              DAOUtil.setValues(preparedStatement, values);
              resultSet = preparedStatement.executeQuery();
              //returnObject = resultSet.getString(0);
               test = resultSet.getLong("sensor_range_priority");
              
              
              
          } catch (SQLException e) {
              throw new DAOException(e);
          } finally {
              DAOUtil.close(resultSet);
              DAOUtil.close(preparedStatement);
              DAOUtil.close(connection);
          }

          return returnObject;
    }
   /**
    * For a given SQL statement with ZERO parameters, it Creates an Array of 
    * generic Objects which can be cast in the method that calls it<br>
    * For Example the ContactDataDAO has a method called list() which returns 
    * an ArrayList of type &lt;ContactData&gt; so it has to cast the incoming 
    * generic ArrayList to an ArrayList of type &lt;ContactData&gt;
    * 
    * @param sqlStatment The SQL statement to be executed
    * @param callingObj A reference to the object so the correct mapData() method can be called
    * @throws SQLException If something fails during setting the PreparedStatement values.
    */
   public static ArrayList <?> listUtil(String sqlStatment, IDAOInterface callingObj) throws DAOException 
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object> aList = new ArrayList<Object>();

        try {
            DAOFactory thisDAO = callingObj.getDAOFactory();            
            connection = thisDAO.getConnection();
            preparedStatement = connection.prepareStatement(sqlStatment);
            // DON"T SET ANY PARAMETERS HERE
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                aList.add(callingObj.mapData(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(resultSet);
            DAOUtil.close(preparedStatement);
            DAOUtil.close(connection);
        }

        return aList;  
    }
    /**
    * For a given SQL statement with parameters, it Creates an Array of generic 
    * Objects which can be cast in the method that calls it<br>
    * For Example the ContactDataDAO has a method called list() 
    * which returns an ArrayList of type &lt;ContactData&gt; so 
    * it has to cast the incoming generic ArrayList to an 
    * ArrayList of type &lt;ContactData&gt;
    * 
    * @param sqlStatment The SQL statement to be executed
    * @param callingObj A reference to the object so the correct mapData() method can be called
    * @param values The parameter values to be set in the given PreparedStatement.
    * @throws SQLException If something fails during setting the PreparedStatement values.
    */
    public static ArrayList <?> listUtil(String sqlStatment, IDAOInterface callingObj, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object> aList = new ArrayList<Object>();

        try {
            DAOFactory thisDAO = callingObj.getDAOFactory();            
            connection = thisDAO.getConnection();
            preparedStatement = connection.prepareStatement(sqlStatment);
            if((values!=null))
            {
                DAOUtil.setValues(preparedStatement, values);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                aList.add(callingObj.mapData(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(resultSet);
            DAOUtil.close(preparedStatement);
            DAOUtil.close(connection);
        }

        return aList;        
    }   
     /**
    * For a given SQL statement with ZERO parameters, it Creates an Array of 
    * generic Objects which can be cast in the method that calls it<br>
    * For Example the ContactDataDAO has a method called list() which returns 
    * an ArrayList of type &lt;ContactData&gt; so it has to cast the incoming 
    * generic ArrayList to an ArrayList of type &lt;ContactData&gt;
    * 
    * @param sqlStatment The SQL statement to be executed
    * @param callingObj A reference to the object so the correct mapData() method can be called
    * @throws SQLException If something fails during setting the PreparedStatement values.
    */
   public static Vector <?> vectorUtil(String sqlStatment, IDAOInterface callingObj) throws DAOException 
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Vector<Object> aVector = new Vector<Object>();

        try {
            DAOFactory thisDAO = callingObj.getDAOFactory();            
            connection = thisDAO.getConnection();
            preparedStatement = connection.prepareStatement(sqlStatment);
            // DON"T SET ANY PARAMETERS HERE
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                aVector.add(callingObj.mapData(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(resultSet);
            DAOUtil.close(preparedStatement);
            DAOUtil.close(connection);
        }

        return aVector;  
    }
  
    /**
    * For a given SQL statement with parameters, it Creates an Array of generic 
    * Objects which can be cast in the method that calls it<br>
    * For Example the ContactDataDAO has a method called list() 
    * which returns an ArrayList of type &lt;ContactData&gt; so 
    * it has to cast the incoming generic ArrayList to an 
    * ArrayList of type &lt;ContactData&gt;
    * 
    * @param sqlStatment The SQL statement to be executed
    * @param callingObj A reference to the object so the correct mapData() method can be called
    * @param values The parameter values to be set in the given PreparedStatement.
    * @throws SQLException If something fails during setting the PreparedStatement values.
    */
    public static Vector <?> vectorUtil(String sqlStatment, IDAOInterface callingObj, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Vector<Object> aVector = new Vector<Object>();

        try {
            DAOFactory thisDAO = callingObj.getDAOFactory();            
            connection = thisDAO.getConnection();
            preparedStatement = connection.prepareStatement(sqlStatment);
            if((values!=null))
            {
                DAOUtil.setValues(preparedStatement, values);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                aVector.add(callingObj.mapData(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(resultSet);
            DAOUtil.close(preparedStatement);
            DAOUtil.close(connection);
        }

        return aVector;        
    }   
    /**
     * Create the given object in the database. 
     * <ul>
     *  <li>SQL_INSERT is the insert statement to be executed</li>
     *  <li>callingObj is the object that called this method</li>
     *  <li>'values' are the values to be bound to the sql</li>
     *  <li>generatedColumns are the auto generated values you wish to be returned after the update</li>
     * </ul>
     * @param SQL_INSERT
     * @param callingObj
     * @param values
     * @param generatedColumns
     * @return
     * @throws java.lang.IllegalArgumentException
     * @throws DataObjects.DAOException
     */
    public static long createUtil(String SQL_INSERT, IDAOInterface callingObj, Object[] values, String generatedColumnName) throws IllegalArgumentException, DAOException {
        

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        long returnId = -1; // Return the ID of the newly inserted object

        try {
            DAOFactory daoFactory = callingObj.getDAOFactory();
            connection = daoFactory.getConnection();
            String generatedColumns[] = {generatedColumnName};
            preparedStatement = connection.prepareStatement(SQL_INSERT, generatedColumns);
            DAOUtil.setValues(preparedStatement, values);
            //System.out.println("\t\t\t theValues: " + preparedStatement.);
            int affectedRows = preparedStatement.executeUpdate();
            //int affectedRows = preparedStatement.
            if (affectedRows == 0) {
                throw new DAOException("Creating an object in the database has failed, no rows affected.");
            }
            
            // YOU MUST SPECIFY THE COLUMN NAME FOR THE AUTO GENERATED VALUES YOU ARE TARGETING IN YOUR SQL STATMENT OTHERWISE IT WON"T WORK
            if(generatedColumns != null){
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                   returnId = Long.valueOf(generatedKeys.getLong(1));
                } else {
                    throw new DAOException("Creating object failed, no unique ID obtained.");
                }
            }
            
            // PRINT OUT ANY WARNINGS
            
            SQLWarning wn = preparedStatement.getWarnings();

            while (wn != null)
            {
              System.err.println("Error returned from database: " + wn.getMessage());

              // get the next warning
              wn = wn.getNextWarning();
            }       
            
        } catch (SQLException e) {
            String errorMsg = "";
            errorMsg += "\n\tCalling object: " + callingObj;
            errorMsg += "\n\tSQL statement:  " + SQL_INSERT;
            errorMsg += "\n\tvalues:         ";
            for(int i=0; i<values.length; i++){
                errorMsg += values[i] + ", ";
            }
            errorMsg += "\n\tgenerated col:  " + generatedColumnName;
            errorMsg += "\n\terror message:  " + e;
            Logger.getLogger(DAOUtil.class.getName()).log(Level.SEVERE, errorMsg);
            throw new DAOException(e);
        } finally {
            DAOUtil.close(generatedKeys);
            DAOUtil.close(preparedStatement);
            DAOUtil.close(connection);
        }
        
        return returnId;
    }
    
   /**
    * Update a given entity (object) in the database
    * @param SQL_UPDATE
    * @param callingObj
    * @param values
    * @return
    * @throws DataObjects.DAOException
    */
    
    public static int updateUtil(String SQL_UPDATE, IDAOInterface callingObj, Object... values) throws DAOException
    {
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;

        try {
            DAOFactory daoFactory = callingObj.getDAOFactory();            
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            DAOUtil.setValues(preparedStatement, values);
            ParameterMetaData parameters = preparedStatement.getParameterMetaData();
            affectedRows = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(preparedStatement);
            DAOUtil.close(connection);
        }
        return affectedRows;
    }
    
    
   /**
    * Returns the current date object
    * @return Date
    */
    public static long getCurTimeInMillis()
    {
        Calendar cal = Calendar.getInstance();
        long millis = cal.getTime().getTime(); 
        return millis;
    }
    
    /**
     * Gets current timestamp
     * @return
     */
    public static Timestamp getCurTimestamp()
    {
        java.sql.Timestamp ts = new java.sql.Timestamp(getCurTimeInMillis());
        return ts;
    }
    
    /**
     * Gets current Date
     * @return
     */
    public static Date getCurDate()
    {
        java.sql.Date d = new java.sql.Date(getCurTimeInMillis());
        return d;    
    }
    /**
     * Quietly close the Connection. Any errors will be printed to the stderr.
     * @param connection The Connection to be closed quietly.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Closing Connection failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Quietly close the Statement. Any errors will be printed to the stderr.
     * @param statement The Statement to be closed quietly.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Closing Statement failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Quietly close the ResultSet. Any errors will be printed to the stderr.
     * @param resultSet The ResultSet to be closed quietly.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("Closing ResultSet failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Generate MD5 hash for the given String. MD5 is kind of an one-way encryption. Very useful for
     * hashing passwords before saving in database. This function generates exactly the same hash as
     * MySQL's own md5() function should do.
     * @param string The String to generate the MD5 hash for.
     * @return The 32-char hexadecimal MD5 hash of the given String.
     */
    public static String hashMD5(String string) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = string.getBytes("UTF-8");
            String hash = new BigInteger(1, md5.digest(bytes)).toString(16);
            // The leading bytes might be less than 10 or even zero. Those leading zeroes get lost
            // when constructing the BigInteger with the bytes. We have to add them again in String.
            while (hash.length() < 32) {
                hash = "0" + hash;
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            // Unexpected exception. "MD5" is just hardcoded and supported.
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            // Unexpected exception. "UTF-8" is just hardcoded and supported.
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Converts the date string 'yyyymmddhhmmss' to a valid calendar object
     * @param zacksDateStr
     * @return
     */
    public static Calendar convertDateStringToCalendar(String zacksDateStr)
    {
    	Calendar returnCal = Calendar.getInstance();
    	
    	returnCal.set(Calendar.YEAR, Integer.parseInt(zacksDateStr.substring(0, 4)));
    	returnCal.set(Calendar.MONTH, Integer.parseInt(zacksDateStr.substring(4, 6))-1);  // SUBTRACTING 1, BECAUSE MONTH DOESN"T USE ZERO BASED INDEXING
        returnCal.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(zacksDateStr.substring(6, 8))));
        returnCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(zacksDateStr.substring(8, 10)));
        returnCal.set(Calendar.MINUTE, Integer.parseInt(zacksDateStr.substring(10, 12)));
        returnCal.set(Calendar.MINUTE, Integer.parseInt(zacksDateStr.substring(12, 14)));
        
        return returnCal;
        
    }
    /**
     * Takes in a Calendar and converts it to a 'yyyymmddhhmmss' formatted date string.
     * @param zacksCal
     * @return
     */
    public static String convertCalendarToDateString(Calendar zacksCal)
    {
    	String returnStr = null; // STRING TO BE RETURNED    	
    	String theYear, theMonth, theDay, theHour, theMin, theSec = "";
    	String stringMask = "00";
    	DecimalFormat thisFormatter = new DecimalFormat(stringMask);  // THIS IS TO ADD LEADING ZEROS TO THE DATE STRING
    	
    	
    	theYear = zacksCal.get(Calendar.YEAR) + "";
    	theMonth = thisFormatter.format((long)zacksCal.get(Calendar.MONTH)+1) + "";  // ADD LEADING ZEROS TO ALL ELEMENTS, SO NUMBERS COME OUT AS '01' and not as '1'
    	theDay = thisFormatter.format((long)zacksCal.get(Calendar.DAY_OF_MONTH)) + ""; // ADDING ONE, BECAUSE MONTHS IS OFFSET
    	theHour = thisFormatter.format((long)zacksCal.get(Calendar.HOUR_OF_DAY)) + "";
    	theMin = thisFormatter.format((long)zacksCal.get(Calendar.MINUTE)) + "";
    	theSec = thisFormatter.format((long)zacksCal.get(Calendar.SECOND)) + "";
    	
    	// SET RETURN STRING
    	returnStr = theYear + theMonth + theDay + theHour + theMin + theSec; 
    	
    	return returnStr;
    }

}