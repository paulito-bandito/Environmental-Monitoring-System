REM ----------------------------------------------------------------------------------------
REM 	AUTHOR: 	Paul Walter
REM	DATE:		Aug 3rd 2008
REM 	DESCRIPTION: 	This is a DDL creation script for the Environment Monitoring System 
REM			I am building for my masters project
REM ----------------------------------------------------------------------------------------

REM --------------------------------
REM 	CONNECT AS THE DEVELOPER
REM --------------------------------

	REM connect theDeveloper/createStuff;

REM -------------------------------------
REM 	DROP tables, sequences, and triggers
REM ------------------------------------- 

	REM ----------------	
	REM DEVELOPER SCHEMA
	REM ----------------	

	DROP TABLE theDeveloper.USER_ROLE 		CASCADE CONSTRAINTS;
	DROP TABLE theDeveloper.USER_STATUS 		CASCADE CONSTRAINTS;
	DROP TABLE theDeveloper.CONTACT_DATA_TYPE	CASCADE CONSTRAINTS;
	DROP TABLE theDeveloper.SENSOR_TYPE		CASCADE CONSTRAINTS;
	DROP TABLE theDeveloper.SENSOR_METRIC_TYPE	CASCADE CONSTRAINTS;
	
	REM ----------------	
	REM END USER SCHEMA
	REM ----------------	


	DROP SYNONYM endUser.USER_ROLE;
	DROP SYNONYM endUser.USER_STATUS;
	DROP SYNONYM endUser.CONTACT_DATA_TYPE;
	DROP SYNONYM endUser.SENSOR_TYPE;
	DROP SYNONYM endUser.SENSOR_METRIC_TYPE;

	DROP TABLE endUser.USER_TABLE		CASCADE CONSTRAINTS;
	DROP TABLE endUser.CONTACT_DATA		CASCADE CONSTRAINTS;
	DROP TABLE endUser.DOMAIN		CASCADE CONSTRAINTS;
	DROP TABLE endUser.DOMAIN_STEWARD	CASCADE CONSTRAINTS;
	DROP TABLE endUser.ENVIRONMENT		CASCADE CONSTRAINTS;
	DROP TABLE endUser.ENVIRONMENT_NOTE	CASCADE CONSTRAINTS;
	DROP TABLE endUser.DEVICE		CASCADE CONSTRAINTS;
	DROP TABLE endUser.CAMERA		CASCADE CONSTRAINTS;
	DROP TABLE endUser.CAMERA_SNAPSHOT	CASCADE CONSTRAINTS;
	DROP TABLE endUser.SENSOR		CASCADE CONSTRAINTS;
	DROP TABLE endUser.SENSOR_CHANNEL	CASCADE CONSTRAINTS;
	DROP TABLE endUser.SENSOR_READING	CASCADE CONSTRAINTS;
	DROP TABLE endUser.SENSOR_RANGE		CASCADE CONSTRAINTS;
	DROP TABLE endUser.ASSOCIATED_SCRIPT	CASCADE CONSTRAINTS;
	DROP TABLE endUser.SENSOR_RANGE_SCRIPT  CASCADE CONSTRAINTS;
	

	
	DROP SEQUENCE endUser.seq_sensor_range_script_id;
	DROP SEQUENCE endUser.seq_associated_script_id;
	DROP SEQUENCE endUser.seq_device_id;
	DROP SEQUENCE endUser.trg_sensor_id 
	DROP SEQUENCE endUser.trg_sensor_reading_id 
	DROP SEQUENCE endUser.seq_sensor_range_id;
	DROP SEQUENCE endUser.seq_camera_snapshot_id;
	DROP SEQUENCE endUser.seq_environment_id;
	DROP SEQUENCE endUser.seq_environment_note_id;
	DROP SEQUENCE endUser.seq_domain_id;
	DROP SEQUENCE endUser.seq_domain_steward_id;
	DROP SEQUENCE endUser.seq_user_table_id;
	DROP SEQUENCE endUser.seq_contact_data_id;

	
	
	
REM -------------------------------------
REM 	CREATE TABLE
REM ------------------------------------- 
	
	REM ----------------	
	REM DEVELOPER SCHEMA
	REM ----------------	


	create table theDeveloper.USER_ROLE(
		user_role_id NUMBER (6),
		CONSTRAINT user_role_id_pk PRIMARY KEY(user_role_id),
		user_role_title VARCHAR2(80)) tablespace EMS_DATA;
	create table theDeveloper.USER_STATUS(
		user_status_id NUMBER (6),
		CONSTRAINT user_status_id_pk PRIMARY KEY(user_status_id),
		user_status_name VARCHAR2(80),
		user_status_description VARCHAR2(200))tablespace EMS_DATA;
	create table theDeveloper.CONTACT_DATA_TYPE(
		contact_data_type_id NUMBER(6),
		CONSTRAINT contact_data_type_id_pk PRIMARY KEY(contact_data_type_id),
		contact_data_type_title VARCHAR2(80),
		contact_data_type_desc	VARCHAR2(200))tablespace EMS_DATA;
	create table theDeveloper.SENSOR_TYPE(
		sensor_type_id NUMBER(6),
		CONSTRAINT sensor_type_id_pk PRIMARY KEY(sensor_type_id),
		sensor_type_name VARCHAR2(80))tablespace EMS_DATA;
	create table theDeveloper.SENSOR_METRIC_TYPE(
		sensor_metric_type_id NUMBER(6),
		CONSTRAINT sensor_metric_type_id_pk PRIMARY KEY(sensor_metric_type_id),
		sensor_metric_name VARCHAR2(80),
		sensor_metric_description VARCHAR2(200))tablespace EMS_DATA;

	REM ----------------------------------------------------	
	REM ENDUSER SCHEMA
	REM 	
	REM	0.) Create reference rights
	REM 	1.) Create Synonyms for tables in theDeveloper
	REM     2.) Create tables for endUser
	REM -----------------------------------------------------	

	GRANT SELECT, REFERENCES on theDeveloper.USER_ROLE to endUser;
	GRANT SELECT, REFERENCES on theDeveloper.USER_STATUS to endUser;
	GRANT SELECT, REFERENCES on theDeveloper.SENSOR_METRIC_TYPE to endUser;
	GRANT SELECT, REFERENCES on theDeveloper.SENSOR_TYPE to endUser;
	GRANT SELECT, REFERENCES on theDeveloper.CONTACT_DATA_TYPE to endUser;

	create  synonym endUser.SENSOR_METRIC_TYPE
		for theDeveloper.SENSOR_METRIC_TYPE;
	create synonym endUser.SENSOR_TYPE
		for theDeveloper.SENSOR_TYPE;
	create synonym endUser.CONTACT_DATA_TYPE
		for theDeveloper.CONTACT_DATA_TYPE;
	create synonym endUser.USER_ROLE
		for theDeveloper.USER_ROLE;
	create synonym endUser.USER_STATUS
		for theDeveloper.USER_STATUS;
 
	create table endUser.USER_TABLE(
		user_table_id NUMBER (6),
		CONSTRAINT user_table_id_pk PRIMARY KEY(user_table_id),
		user_role_id NUMBER(6),
		CONSTRAINT user_role_id_fk FOREIGN KEY (user_role_id ) REFERENCES endUser.USER_ROLE(user_role_id) ON DELETE CASCADE,
		user_status_id NUMBER(6),
		CONSTRAINT user_status_id_fk FOREIGN KEY (user_status_id ) REFERENCES endUser.USER_STATUS(user_status_id) ON DELETE CASCADE,
		user_username VARCHAR2(80) UNIQUE,
		user_password VARCHAR2 (80),
		user_currentToken VARCHAR2 (40) default null)tablespace EMS_DATA;
	create table endUser.CONTACT_DATA(
		contact_data_id NUMBER(6),
		CONSTRAINT contact_data_id_pk PRIMARY KEY(contact_data_id),		
		contact_data_title VARCHAR2(80)  UNIQUE,
		contact_data_address VARCHAR2(200),
		contact_data_description VARCHAR2(200),
		contact_data_type_id NUMBER(6),
		CONSTRAINT contact_data_type_id_fk FOREIGN KEY (contact_data_type_id ) REFERENCES endUser.CONTACT_DATA_TYPE(contact_data_type_id) ON DELETE CASCADE,
		user_id NUMBER(6),
		CONSTRAINT user_id_for_contact_data_fk FOREIGN KEY (user_id ) REFERENCES endUser.USER_TABLE(user_table_id) ON DELETE CASCADE)tablespace EMS_DATA;
	create table endUser.DOMAIN(
		domain_id NUMBER(6),
		CONSTRAINT domain_id_pk PRIMARY KEY(domain_id),
		domain_name VARCHAR2(80) UNIQUE,
		domain_description VARCHAR2(300))tablespace EMS_DATA;
	create table endUser.DOMAIN_STEWARD(
		domain_steward_user_id NUMBER (6),
		domain_id NUMBER (6),
		user_table_id NUMBER (6),
		CONSTRAINT domain_steward_user_id_pk PRIMARY KEY(domain_steward_user_id),		
		CONSTRAINT domain_id_for_steward_fk FOREIGN KEY (domain_id ) REFERENCES endUser.DOMAIN(domain_id) ON DELETE CASCADE,
		CONSTRAINT user_id_for_domain_steward_fk FOREIGN KEY (user_table_id ) REFERENCES endUser.USER_TABLE(user_table_id) ON DELETE CASCADE)tablespace EMS_DATA;
	create table endUser.ENVIRONMENT(
		environment_id NUMBER(6),
		domain_id NUMBER (6),
		environment_title VARCHAR2(80) UNIQUE,		
		environment_description VARCHAR2(200),
		environment_creation_date DATE,
		environment_x_coord NUMBER(6) DEFAULT -1,
		environment_y_coord NUMBER (6) DEFAULT -1,
		CONSTRAINT environment_id_pk PRIMARY KEY(environment_id),		
		CONSTRAINT domain_id_fk FOREIGN KEY (domain_id ) REFERENCES endUser.DOMAIN(domain_id) ON DELETE CASCADE)tablespace EMS_DATA;
	create table endUser.ENVIRONMENT_NOTE(
		environment_note_id NUMBER(6),
		environment_note_message VARCHAR2(1000),
		environment_note_timestamp TIMESTAMP(6),
		environment_id NUMBER(6),
		user_table_id NUMBER(6),
		CONSTRAINT environment_note_id_pk PRIMARY KEY(environment_note_id),		
		CONSTRAINT environment_id_for_env_note_fk FOREIGN KEY (environment_id ) REFERENCES endUser.ENVIRONMENT(environment_id) ON DELETE CASCADE,
		CONSTRAINT user_id_for_env_note_fk FOREIGN KEY (user_table_id) REFERENCES endUser.USER_TABLE(user_table_id) ON DELETE CASCADE)tablespace EMS_DATA;
	create table endUser.DEVICE(
		device_id NUMBER(6),
		environment_id NUMBER(6),
		device_title VARCHAR2(80),
		device_description VARCHAR2(600),
		CONSTRAINT device_id_pk PRIMARY KEY(device_id),		
		CONSTRAINT environment_id_for_device_fk FOREIGN KEY (environment_id ) REFERENCES endUser.ENVIRONMENT(environment_id) ON DELETE CASCADE)tablespace EMS_DATA;
	create table endUser.CAMERA(
		camera_id NUMBER(6),
		camera_media_location VARCHAR2(200),
		camera_snapshot_interval NUMBER(6),
		CONSTRAINT camera_id_pk PRIMARY KEY(camera_id),		
		CONSTRAINT camera_id_pk_fk FOREIGN KEY (camera_id) REFERENCES endUser.DEVICE(device_id) ON DELETE CASCADE)tablespace EMS_DATA;
	create table endUser.CAMERA_SNAPSHOT(
		camera_snapshot_id NUMBER(6),
		camera_id NUMBER(6),
		camera_snapshot_timestamp TIMESTAMP(6),
		camera_snapshot_filename VARCHAR2(200),
		CONSTRAINT camera_snapshot_id_pk PRIMARY KEY(camera_snapshot_id),		
		CONSTRAINT camera_id_for_snapshot_fk FOREIGN KEY (camera_id ) REFERENCES endUser.CAMERA(camera_id) ON DELETE CASCADE)tablespace EMS_DATA;
	create table endUser.SENSOR_RANGE_SCRIPT(
		sensor_range_script_id NUMBER(6),
		CONSTRAINT sensor_range_script_id_pk PRIMARY KEY(sensor_range_script_id),
		sensor_range_script_xml VARCHAR2(3000),
		sensor_range_script_title VARCHAR2(80) UNIQUE,
		sensor_range_script_desc VARCHAR2(200))tablespace EMS_DATA;
	create table endUser.SENSOR(
		sensor_id NUMBER(6),
		sensor_type_id NUMBER(6),
		sensor_network_address VARCHAR2(300),
		sensor_update_speed NUMBER(6),
		CONSTRAINT sensor_id_pk PRIMARY KEY(sensor_id),		
		CONSTRAINT sensor_id_fk FOREIGN KEY (sensor_id ) REFERENCES endUser.DEVICE(device_id) ON DELETE CASCADE,
		CONSTRAINT sensor_type_id_fk FOREIGN KEY (sensor_type_id ) REFERENCES endUser.SENSOR_TYPE(sensor_type_id) ON DELETE CASCADE)tablespace EMS_DATA;
	create table endUser.SENSOR_CHANNEL(
		sensor_id NUMBER(6),
		sensor_channel_num NUMBER (6) NOT NULL,
		sensor_channel_type NUMBER (6) NOT NULL,
		sensor_metric_type_id NUMBER(6) NOT NULL,		
		CONSTRAINT sensor_id_chan_pk PRIMARY KEY(sensor_id, sensor_channel_num),	
		CONSTRAINT sensor_id_for_sen_chan_fk FOREIGN KEY (sensor_id ) REFERENCES endUser.SENSOR(sensor_id) ON DELETE CASCADE,
		CONSTRAINT sensor_type2_id_fk FOREIGN KEY (sensor_channel_type ) REFERENCES endUser.SENSOR_TYPE(sensor_type_id) ON DELETE CASCADE,
		CONSTRAINT sensor_metric_type_id_fk FOREIGN KEY (sensor_metric_type_id ) REFERENCES endUser.SENSOR_METRIC_TYPE(sensor_metric_type_id) ON DELETE CASCADE
		)tablespace EMS_DATA;		
	create table endUser.SENSOR_READING(
		sensor_reading_id NUMBER(6),
		sensor_id NUMBER(6),
		sensor_channel_num NUMBER(6),
		sensor_reading_timestamp TIMESTAMP,
		sensor_reading_measurement NUMBER,
		CONSTRAINT sensor_reading_id PRIMARY KEY(sensor_reading_id, sensor_id, sensor_channel_num),		
		CONSTRAINT sensor_id_for_reading_fk FOREIGN KEY (sensor_id, sensor_channel_num ) REFERENCES endUser.SENSOR_CHANNEL(sensor_id, sensor_channel_num) ON DELETE CASCADE)tablespace EMS_DATA;
	create table endUser.SENSOR_RANGE(
		sensor_range_id NUMBER(6),
		sensor_id NUMBER(6),
		sensor_channel_num NUMBER(6),
		sensor_range_title VARCHAR2(80),
		sensor_range_priority NUMBER(2) DEFAULT 0,
		sensor_range_min NUMBER(15,4),
		sensor_range_max NUMBER(15,4),
		CONSTRAINT sensor_range_id_pk PRIMARY KEY(sensor_range_id),		
		CONSTRAINT sensor_id_for_range_fk FOREIGN KEY (sensor_id, sensor_channel_num ) REFERENCES endUser.SENSOR_CHANNEL(sensor_id, sensor_channel_num) ON DELETE CASCADE)tablespace EMS_DATA;
	create table endUser.ASSOCIATED_SCRIPT(
		associated_script_id NUMBER(6),
		sensor_range_script_id NUMBER(6),
		sensor_range_id NUMBER(6),
		CONSTRAINT associated_script_id_pk PRIMARY KEY(associated_script_id),		
		CONSTRAINT range_script_id_fk FOREIGN KEY (sensor_range_script_id ) REFERENCES endUser.SENSOR_RANGE_SCRIPT(sensor_range_script_id) ON DELETE CASCADE,
		CONSTRAINT sensor_range_id_for_assoc_fk FOREIGN KEY (sensor_range_id ) REFERENCES endUser.SENSOR_RANGE(sensor_range_id) ON DELETE CASCADE)tablespace EMS_DATA;
	
REM -------------------------------------
REM 	Insert sequences
REM ------------------------------------- 
	
	CREATE SEQUENCE endUser.seq_sensor_range_script_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_associated_script_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_device_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_sensor_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_sensor_reading_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_sensor_range_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_camera_snapshot_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_environment_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_environment_note_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_domain_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_domain_steward_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_user_table_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;
	CREATE SEQUENCE endUser.seq_contact_data_id
    		START WITH 20
    		INCREMENT BY 1
    		NOMAXVALUE
    		NOCYCLE
    		CACHE 20;

REM -------------------------------------
REM 	Create Triggers
REM ------------------------------------- 
	
	CREATE TRIGGER endUser.trg_sensor_range_script_id before INSERT ON endUser.sensor_range_script FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."SENSOR_RANGE_SCRIPT_ID" IS NULL THEN
      				SELECT endUser.seq_sensor_range_script_id.nextval
      				INTO :NEW."SENSOR_RANGE_SCRIPT_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_device_id before INSERT ON endUser.device FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."DEVICE_ID" IS NULL THEN
      				SELECT endUser.seq_device_id.nextval
      				INTO :NEW."DEVICE_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_sensor_id before INSERT ON endUser.sensor FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."SENSOR_ID" IS NULL THEN
      				SELECT endUser.seq_sensor_id.nextval
      				INTO :NEW."SENSOR_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_sensor_reading_id before INSERT ON endUser.sensor_reading FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."SENSOR_READING_ID" IS NULL THEN
      				SELECT endUser.seq_sensor_reading_id.nextval
      				INTO :NEW."SENSOR_READING_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_sensor_range_id before INSERT ON endUser.sensor_range FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."SENSOR_RANGE_ID" IS NULL THEN
      				SELECT endUser.seq_sensor_range_id.nextval
      				INTO :NEW."SENSOR_RANGE_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_camera_snapshot_id before INSERT ON endUser.camera_snapshot FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."CAMERA_SNAPSHOT_ID" IS NULL THEN
      				SELECT endUser.seq_camera_snapshot_id.nextval
      				INTO :NEW."CAMERA_SNAPSHOT_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_environment_id before INSERT ON endUser.environment FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."ENVIRONMENT_ID" IS NULL THEN
      				SELECT endUser.seq_environment_id.nextval
      				INTO :NEW."ENVIRONMENT_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_environment_note_id before INSERT ON endUser.environment_note FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."ENVIRONMENT_NOTE_ID" IS NULL THEN
      				SELECT endUser.seq_environment_note_id.nextval
      				INTO :NEW."ENVIRONMENT_NOTE_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_domain_id before INSERT ON endUser.domain FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."DOMAIN_ID" IS NULL THEN
      				SELECT endUser.seq_domain_id.nextval
      				INTO :NEW."DOMAIN_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_domain_steward_id before INSERT ON endUser.domain_steward FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."DOMAIN_STEWARD_USER_ID" IS NULL THEN
      				SELECT endUser.seq_domain_steward_id.nextval
      				INTO :NEW."DOMAIN_STEWARD_USER_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_user_table_id before INSERT ON endUser.user_table FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."USER_TABLE_ID" IS NULL THEN
      				SELECT endUser.seq_user_table_id.nextval
      				INTO :NEW."USER_TABLE_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/
	CREATE TRIGGER endUser.trg_contact_data_id before INSERT ON endUser.contact_data FOR EACH ROW
	BEGIN
  		IF inserting THEN
    			IF :NEW."CONTACT_DATA_ID" IS NULL THEN
      				SELECT endUser.seq_contact_data_id.nextval
      				INTO :NEW."CONTACT_DATA_ID"
      				FROM dual;
    			END IF;
  		END IF;
	END;
	/	

REM -------------------------------------
REM 	INSERT BASIC DATA (theDeveloper)
REM ------------------------------------- 	

	INSERT INTO theDeveloper.USER_ROLE VALUES (1, 'Technician');
	INSERT INTO theDeveloper.USER_ROLE VALUES (2, 'Steward');
	INSERT INTO theDeveloper.USER_ROLE VALUES (3, 'Observer');

	INSERT INTO theDeveloper.USER_STATUS VALUES (1, 'Active', 'Is allowed to use the system');
	INSERT INTO theDeveloper.USER_STATUS VALUES (2, 'Inactive', 'Is not allowed to use the system');

	INSERT INTO theDeveloper.CONTACT_DATA_TYPE VALUES(1, 'Text Message Address', 'This is an email address that will forward to a phone');
	INSERT INTO theDeveloper.CONTACT_DATA_TYPE VALUES(2, 'Email Address', 'An email address');

	INSERT INTO theDeveloper.SENSOR_TYPE VALUES(1, 'Temperature');
	INSERT INTO theDeveloper.SENSOR_TYPE VALUES(2, 'Temperature/Solar/Humdity');
	INSERT INTO theDeveloper.SENSOR_TYPE VALUES(3, 'Solar');
	INSERT INTO theDeveloper.SENSOR_TYPE VALUES(4, 'Humdity');

	INSERT INTO theDeveloper.SENSOR_METRIC_TYPE VALUES(1, 'Degrees Celcius', 'The degrees in Celcius.');
	INSERT INTO theDeveloper.SENSOR_METRIC_TYPE VALUES(2, 'Percent Humidity', 'The percentage of the air that is water.');
	INSERT INTO theDeveloper.SENSOR_METRIC_TYPE VALUES(3, 'Percent Sun', 'The percentage of sun detected. 100 is a exceptionally sunny day, and zero is no light.');	
	

REM -------------------------------------
REM 	Insert Project Data (endUser)
REM ------------------------------------- 	

	INSERT INTO endUser.SENSOR_RANGE_SCRIPT VALUES(1, '<method>thingsAreNominal()<method>', 'Things are nominal', 'This is a test script that will get called when things are ok, parameters are within normal bounds');
	INSERT INTO endUser.SENSOR_RANGE_SCRIPT VALUES(2, '<method>notifyStewards()<method>', 'Notify Stewards', 'This script will notify all stewards associated to a domain');

	INSERT INTO endUser.USER_TABLE VALUES (1, 1, 1, 'Technician 1', 'tech', '');
	INSERT INTO endUser.USER_TABLE VALUES (2, 1, 1, 'Technician 2', 'tech', '');
	INSERT INTO endUser.USER_TABLE VALUES (3, 1, 1, 'Technician 3', 'tech', '');
	INSERT INTO endUser.USER_TABLE VALUES (4, 2, 1, 'Steward_1', 'stew', '');
	INSERT INTO endUser.USER_TABLE VALUES (5, 2, 1, 'Steward_2', 'stew', '');
	INSERT INTO endUser.USER_TABLE VALUES (6, 2, 1, 'Steward_3', 'stew', '');
	INSERT INTO endUser.USER_TABLE VALUES (7, 3, 1, 'Observer_1', 'observe', '');
	INSERT INTO endUser.USER_TABLE VALUES (8, 3, 1, 'Observer_2', 'observe', '');
	INSERT INTO endUser.USER_TABLE VALUES (9, 3, 1, 'Observer_3', 'observe', '');

	INSERT INTO endUser.CONTACT_DATA VALUES(1, 'Steward 1s email address', 'paulywaltero@gmail.com', 'General email addy', 2, 1);
	INSERT INTO endUser.CONTACT_DATA VALUES(2, 'Steward 1s cell phone', 'paulywaltero@gmail.com', 'General email addy', 1, 1);	
	INSERT INTO endUser.CONTACT_DATA VALUES(3, 'Steward 2s email address', 'paulywaltero@gmail.com', 'General email addy', 2, 2);
	INSERT INTO endUser.CONTACT_DATA VALUES(4, 'Steward 2s cell phone', 'paulywaltero@gmail.com', 'General email addy', 1, 2);
	INSERT INTO endUser.CONTACT_DATA VALUES(5, 'Steward 3s email address', 'paulywaltero@gmail.com', 'General email addy', 2, 3);
	INSERT INTO endUser.CONTACT_DATA VALUES(6, 'Steward 3s cell phone', 'paulywaltero@gmail.com', 'General email addy', 1, 3);

	INSERT INTO endUser.DOMAIN VALUES(1, 'Domain A', 'Domain A is the first domain that exists');
	INSERT INTO endUser.DOMAIN VALUES(2, 'Domain B', 'Domain B is the second domain that exists');
	INSERT INTO endUser.DOMAIN VALUES(3, 'Domain C', 'Domain C is the third domain that exists');

	REM ------------------------
	REM Insert the Environments
	REM ------------------------

	INSERT INTO endUser.ENVIRONMENT VALUES(1, 1, 'Environment A1', 'A description of environment A1', '26-JUN-08', 292, 138);
	INSERT INTO endUser.ENVIRONMENT VALUES(2, 1, 'Environment A2', 'A description of environment A2', '26-JUL-08', 190, 400);
	INSERT INTO endUser.ENVIRONMENT VALUES(3, 1, 'Environment A3', 'A description of environment A3', '26-AUG-08', 400, 400);
	INSERT INTO endUser.ENVIRONMENT VALUES(4, 2, 'Environment B1', 'A description of environment B1', '26-JUN-08', -1, -1);
	INSERT INTO endUser.ENVIRONMENT VALUES(5, 2, 'Environment B2', 'A description of environment B2', '26-JUL-08', -1, -1);
	INSERT INTO endUser.ENVIRONMENT VALUES(6, 2, 'Environment B3', 'A description of environment B3', '26-AUG-08', -1, -1);
	INSERT INTO endUser.ENVIRONMENT VALUES(7, 3, 'Environment C1', 'A description of environment C1', '26-JUN-08', -1, -1);
	INSERT INTO endUser.ENVIRONMENT VALUES(8, 3, 'Environment C2', 'A description of environment C2', '26-JUL-08', -1, -1);
	INSERT INTO endUser.ENVIRONMENT VALUES(9, 3, 'Environment C3', 'A description of environment C3', '26-AUG-08', -1, -1);

	INSERT INTO endUser.environment_note VALUES(1, 'This is a message about environment 1', LOCALTIMESTAMP, 1, 1);
	INSERT INTO endUser.environment_note VALUES(2, 'This is another a message about environment 1', LOCALTIMESTAMP, 1, 1);
	INSERT INTO endUser.environment_note VALUES(3, 'This is yet another environment 1', LOCALTIMESTAMP, 1, 1);
	INSERT INTO endUser.environment_note VALUES(4, 'This is a message about environment 2', LOCALTIMESTAMP, 2, 2);
	INSERT INTO endUser.environment_note VALUES(5, 'This another is a message about environment 2', LOCALTIMESTAMP, 2, 2);
	INSERT INTO endUser.environment_note VALUES(6, 'This is a message about environment 3', LOCALTIMESTAMP, 3, 3);
	REM ------------------------
	REM Insert the Domain Stewards (associate a domain to a USER)
	REM ------------------------

	INSERT INTO endUser.DOMAIN_STEWARD VALUES(1, 1, 1);
	INSERT INTO endUser.DOMAIN_STEWARD VALUES(2, 1, 2);
	INSERT INTO endUser.DOMAIN_STEWARD VALUES(3, 1, 3);
	INSERT INTO endUser.DOMAIN_STEWARD VALUES(4, 2, 4);
	INSERT INTO endUser.DOMAIN_STEWARD VALUES(5, 2, 5);
	INSERT INTO endUser.DOMAIN_STEWARD VALUES(6, 3, 6);
	INSERT INTO endUser.DOMAIN_STEWARD VALUES(7, 3, 1);

	REM ------------------------
	REM  	CAMERA
	REM ------------------------

	INSERT INTO endUser.DEVICE VALUES(1,1,'Camera For Paul', 'This is a camera on his desk');
	INSERT INTO endUser.CAMERA VALUES(1,'vfw://0', 5.5);
	INSERT INTO endUser.CAMERA_SNAPSHOT VALUES(1, 1, LOCALTIMESTAMP, 'pic1.jpg');
	INSERT INTO endUser.CAMERA_SNAPSHOT VALUES(2, 1, LOCALTIMESTAMP, 'pic2.jpg');
	INSERT INTO endUser.CAMERA_SNAPSHOT VALUES(3, 1, LOCALTIMESTAMP, 'pic3.jpg');	

	REM ------------------------
	REM  	SENSOR
	REM ------------------------

	REM The sensors will log themselves.

	REM -----------------------
	REM      COMMIT CHANGES
	REM -----------------------

	commit;
