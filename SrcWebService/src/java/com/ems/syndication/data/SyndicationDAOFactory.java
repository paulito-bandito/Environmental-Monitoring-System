/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.data;

import com.ems.syndication.constants.EmsSyndicationConstants;
import com.ems.utils.DAOConfigurationException;
import com.ems.utils.DAOFactory;

/**
 *
 * @author Paul W Walter
 */
public class SyndicationDAOFactory extends DAOFactory{

        protected EnvironmentDAO environmentDAO;
        protected CaptureDeviceDAO captureDeviceDAO;
        protected DataDAO dataDAO;
        protected NoteDAO noteDAO;
        protected SnapshotDAO snapshotDAO;
        protected SensorRangeDAO sensorRangeDAO;
        
        protected static String assetsFolderPath = "";
        protected MeasurementDAO measurementDAO;
    
        private SyndicationDAOFactory( String name ) throws DAOConfigurationException {
		super( name );
                
                assetsFolderPath = properties.getProperty(EmsSyndicationConstants.ASSETS_FOLDER_LOCATION_PROPERTY_NAME, true);
	}
	
	public static SyndicationDAOFactory getInstance( String name ) {
            if (name == null) {
                 throw new DAOConfigurationException("Instance name is null.");
            }
	
            SyndicationDAOFactory instance = (SyndicationDAOFactory)INSTANCES.get(name);
            
            if (instance == null) {
                instance = new SyndicationDAOFactory(name);
                INSTANCES.put(name, instance);
            }
            
            return instance;
	}
        
        public EnvironmentDAO getEnvironmentDAO()
        {
            if(environmentDAO == null)
            {
                environmentDAO = new EnvironmentDAO(this);
            }
            
            return environmentDAO;
        }

        public CaptureDeviceDAO getCaptureDeviceDAO() {
            if(captureDeviceDAO == null)
            {
                captureDeviceDAO = new CaptureDeviceDAO(this);
            }

            return captureDeviceDAO;
        }
    
        public DataDAO getDataDAO() {
            if(dataDAO == null)
            {
                dataDAO = new DataDAO(this);
            }

            return dataDAO;
        }
        
        public NoteDAO getNoteDAO() {
            if(noteDAO == null)
            {
                noteDAO = new NoteDAO(this);
            }

            return noteDAO;
        }
        
        public SnapshotDAO getSnapshotDAO() {
            if(snapshotDAO == null)
            {
                snapshotDAO = new SnapshotDAO(this);
            }

            return snapshotDAO;
        }
        
        public String getAssetsURL()
        {
            return assetsFolderPath;
        }

        public MeasurementDAO getMeasurementDAO() {
            if(measurementDAO == null)
            {
                measurementDAO = new MeasurementDAO(this);
            }

            return measurementDAO;
        }
        
        public SensorRangeDAO getSensorRangeDAO() {
            if(sensorRangeDAO == null)
            {
                sensorRangeDAO = new SensorRangeDAO(this);
            }

            return sensorRangeDAO;
        }
    }
