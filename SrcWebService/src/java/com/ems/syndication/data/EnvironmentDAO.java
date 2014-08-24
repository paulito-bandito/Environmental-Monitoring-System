/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.data;


import com.ems.syndication.business.Environment;
import com.ems.syndication.constants.EmsSyndicationConstants;
import com.ems.utils.DAOException;
import com.ems.utils.DAOFactory;
import com.ems.utils.DAOUtil;
import com.ems.utils.IDAOInterface;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paul W Walter
 */
public class EnvironmentDAO implements IDAOInterface{

    private SyndicationDAOFactory daoFactory;
    
    private static final String LIST_ENVIRONMENTS_SQL = EmsSyndicationConstants.ENVIRONMENT_DAO_GET_ENVIRONMENTS_FOR_DOMAIN_ID;

    public EnvironmentDAO(SyndicationDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
        
    public Object mapData(ResultSet resultSet) throws SQLException {
        
        Environment returnObj = null;
        
        long theId = resultSet.getLong("environment_id");
        long theDomainId = resultSet.getLong("domain_id");
        long x = resultSet.getLong("environment_x_coord");
        long y = resultSet.getLong("environment_Y_coord");
        String theName = resultSet.getString("environment_title");
        String theDesc = resultSet.getString("environment_description");
        Date creationDate = resultSet.getDate("environment_creation_date");
                
        // CONSTRUCT THE CONTACT DATA OBJ
        returnObj = new Environment(theId, theDomainId, theName, theDesc, creationDate.getTime(), x, y );
        
        return returnObj;
    }

    public DAOFactory getDAOFactory() {
           return this.daoFactory;
    }

    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList <Environment> getEnvironments( long domainID) throws DAOException
    {
       // Get list of domains for the usertoken
        ArrayList <Environment> returnList = (ArrayList <Environment>)DAOUtil.listUtil(LIST_ENVIRONMENTS_SQL, this, domainID);
        
        return returnList;
    }
}
