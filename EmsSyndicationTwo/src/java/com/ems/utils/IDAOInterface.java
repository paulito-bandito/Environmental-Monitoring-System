
package com.ems.utils;

import java.sql.ResultSet;
import java.sql.SQLException;


/*
 * This interface will make sure that the developer creates his data objects correctly so the DAOUtil methods listUtil(), and findUtil(), will work correcly when called
 * @author Paul Walter
 * @see DAOUtil 
 */
public interface IDAOInterface {
    /**
    * Maps the incoming db data to a generic Object
    * @param resultSet The result set our data will be in
    * @return ContactData
    * @throws SQLException If something fails at database level.
    */
    Object mapData(ResultSet resultSet) throws SQLException;
    
    /**
    * Returns the reference to its DAO Factory Object associated with this class
    * @return DAOFactory     
    */ 
    DAOFactory getDAOFactory();
    
   /**
    * Either saves or updates an object in conjunction with DAOUtils.java. 
    * If the ID of the thing you are creating exists, then update else create  
    * @throws enterprisemanager.common.data.util.DAOException
    */
    void save(Object incomingObj) throws DAOException;
}
