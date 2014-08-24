/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.data;

import com.ems.syndication.business.Note;
import com.ems.syndication.business.User;
import com.ems.syndication.constants.EmsSyndicationConstants;
import com.ems.utils.DAOException;
import com.ems.utils.DAOFactory;
import com.ems.utils.DAOUtil;
import com.ems.utils.IDAOInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Paul W Walter
 */
public class NoteDAO implements IDAOInterface {

    private SyndicationDAOFactory daoFactory;
    
    private static final String INSERT_NOTE_SQL = EmsSyndicationConstants.ENVIRONMENT_NOTE_DAO_ADD_NOTE;
    private static final String DELETE_NOTE_SQL = EmsSyndicationConstants.ENVIRONMENT_NOTE_DAO_DELETE_NOTE;
    private static final String LIST_NOTES_SQL  = EmsSyndicationConstants.ENVIRONMENT_NOTE_DAO_LIST_NOTES_FOR_CONTIGUOUS_CHUNK;
    private static final String GET_CURRENT_ENV_NOTE = EmsSyndicationConstants.ENVIRONMENT_NOTE_DAO_GET_CURRENT_NOTE;
    private String INSTANCE_NAME =  "EMS";

    public NoteDAO(SyndicationDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public Object mapData(ResultSet resultSet) throws SQLException 
    {
        
        long noteId = resultSet.getLong("environment_note_id");
        Timestamp timestamp = resultSet.getTimestamp("environment_note_timestamp");
        String msg = resultSet.getString("environment_note_message");
        long userId = resultSet.getLong("user_table_id");
        String userName = resultSet.getString("user_username");
       
        //a_id, String a_username, String a_userType, String a_currentToken
        User thisUser = new User(userId, userName, null, null);

        return new Note(noteId, timestamp.getTime(), msg, thisUser);
  }

    public DAOFactory getDAOFactory() {
           return this.daoFactory;
    }

    public void save(Object incomingObj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * This will create a new environment note in the DB. This method will return the DB id of the newly created Note.
     * 
     * @param userToken
     * @param environmentId
     * @param creationTimestamp
     * @param body
     * @return
     */
    public long addEnvironmentNote( long environmentID, Note theNote) throws IllegalArgumentException, DAOException
    {
        // Check its arguments
        
        if(theNote.id != -1)
        {
            throw new IllegalArgumentException("The note may already exist, it's id wasn't -1 signifying it is a new note it was: " + theNote.id);
        }
        
        
        //
        // Set its values
        //
        Object[] values = {
            theNote.message,
            new Timestamp(theNote.mesurementDate),
            environmentID,
            theNote.author.id
        };

        //
        // IDENTIFY WHICH COLUMN YOU WANT A REFERENCE TO ONCE THIS METHOD RETURNS,
        //      So we can update that object to have the correct id associated with it.
        //"INSERT INTO camera_snapshot (camera_snapshot_id, camera_id, camera_snapshot_timestamp, camera_snapshot_filename) VALUES (null, ?, ?, ?)"
        String targetGeneratedColumn = EmsSyndicationConstants.ENVIRONMENT_NOTE_DAO_ADD_NOTE_ID_COLUMN;
        long theReturnedNoteId = DAOUtil.createUtil(INSERT_NOTE_SQL, this, values, targetGeneratedColumn );   

                    
        
        return (long)theReturnedNoteId;
    }
    
    /**
     * This method will return a subset of environment notes between the start time and the stop time.
     * 
     * Using the 'numberStart' and 'numberStop' attributes you can specify how big a chunk starting
     * at what measurement you wish to obtain. For example if you wish to only have the first 2 notes
     * associated with a specific environment you would set 'numberStart' to 1 and 'numberStop' to 2.
     * 
     * @param userToken
     * @param environmentId
     * @param numberStart
     * @param numberStop
     * @return
     */
    public ArrayList <Note> getEnvironmentNotes( long environmentId, int numberStart, int numberStop) throws DAOException
    {
        return (ArrayList <Note>)DAOUtil.listUtil(LIST_NOTES_SQL, this, environmentId, numberStart, numberStop);    
    }
    
        /**
     * This method will return a subset of environment notes between the start time and the stop time.
     * 
     * Using the 'numberStart' and 'numberStop' attributes you can specify how big a chunk starting
     * at what measurement you wish to obtain. For example if you wish to only have the first 2 notes
     * associated with a specific environment you would set 'numberStart' to 1 and 'numberStop' to 2.
     * 
     * @param userToken
     * @param environmentId
     * @param numberStart
     * @param numberStop
     * @return
     */
    public Note getCurrentEnvironmentNote( long environmentId) throws DAOException
    {
        return (Note)DAOUtil.findUtil(GET_CURRENT_ENV_NOTE, this, environmentId, environmentId);    
    }
    
    /**
     * This will create a new environment note in the DB. This method will return the DB id of the newly created Note.
     * @param userToken
     * @param environmentId
     * @param creationTimestamp
     * @param body
     * @return
     */
    public int removeEnvironmentNote( long aNoteID) throws IllegalArgumentException, DAOException
    {
        if(aNoteID == -1)
        {
            throw new IllegalArgumentException("This note doesn't yet exist");
        }   
        
        int numRowsAffected = DAOUtil.updateUtil(DELETE_NOTE_SQL, this, aNoteID);
        
        return numRowsAffected;
    }
    

}
