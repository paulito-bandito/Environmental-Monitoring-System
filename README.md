# Environmental-Monitoring-System #

This is a decision support system for maintaining indoor biomes which utilizes sensors, webcams, email notifications

It has the following parts: 

1. Data Logging (all sensors and devices) which runs separately and writes to a database or writes files to a local folder (which you specify in the dao.properties file)
2. Web Syndication: via SOAP Web Services. It accesses the data through the database, or through the folder specified to contain the JPEG images.
3. The Desk Top Client: this is a WPF client that accessess the Web Services. 
4. The Database Script: Needs to be set up on a server and it's IP address specified in the "dao.properties" file in both Web and Data Logging projects (because it is a property file, you don't need to recompile)

## * Please note, this is a prototype and had the following issues! * ##

### 6.4.1 Registering new devices into the system ###

The first issue is that I didn't work into the use case diagram the need for the technician to register sensors. This is addressed regardless in the resulting software: the sensor client scans its network, finds all the network addresses on the network, and sends that list to the server. The server saves any addresses not already in the database into the database. The problem is that it doesn't know what kind of sensor is being reported. The technician needs to go in later and manually update the database. 

In addition to the use case diagram, there needs to be another use case added that illustrates how this interaction will occur. This isn't in the Mock up, and it is a mystery as to what is the best course of action. 

Ideally, when a new device gets registered, it gets listed in the Technician's view of the system as a notification, and then the Technician can make the semantic decision as to what type of sensor this is so it's measurements get assigned the correct metrics (temp, lux, percent humidity). 

### 6.4.2 Scalability ###

Another issue is that this design doesn't scale. It is limited in two ways: the first is that it can only hold as many sensors and web cameras as can be connected to one computer through it's USB and serial ports. Not a very good design, but as this was developed as a proof of concept, it is adequate for now. The second limitation is that it uses an Object Oriented approach which means that multithreading needs to occur and lock shared objects. This would make it very slow at scale. 

In a future design, the developers should look at distributing the network AND either use funcional programming (C#, Scala, or Erland) or use an Actor Model (in Java, specifically the Play Framework). 

### 6.4.3 Cross Platform Capabilities for the Client GUI ###

Since the client is written in WPF it can only be run on windows systems (unless using MONO). In the future this should/could be written in something more compatible like Javascript and HTML. 

###6.4.4 Separate Technician GUI for the Sensor Client ###

In addition to the GUIs that already exist, there should be another one, perhaps command line that walks the Technician through setting up a new Sensor Client node. Something that will list all the sensors and cameras and perhaps let the technician choose from the point of entry, which user this will be registered to, what Environment, and what Domain. 

### 6.4.5 Authentication ###

This is something that needs to be addressed. At the moment, the database doesn't use MD5 hasing for passwords, nor is there a “I forgot my password” functionality.

### 6.4.6 Java Media Framework (JMF) is Dead ###

The JMF is the library I used to interact with a web camera. One of the things discovered during this process is that the JMF is only partially implemented and doesn't have any plans to complete. The incredibly frustrating thing is that the project doesn't say this anywhere. It's only digging through user group posts on the “Stack Overflow” site, that I found this. 

This project may want to look at, in the future, writing the Sensor Client in C++ or Python . Both of these languages have far richer support for interacting with hardware. Java's strength seems to be with business computing (i.e. running a server). It doesn't want to get it's hands dirty with the low-level of hardware. 

C++ also has a library called “Open Layers” which might be worth a look. 

Java has another library which is slated to be complete in the future, but after the way that Java Media Framwork went, I don't have much faith. It's name is Freedom for Media in Java (FMJ).

### 6.4.7 “1 Wire” Framework is Limited ###

This framework was only ever meant as a hobbyist tool and has access to only the limited devices for hobbyists. In the future you need to consider using the “I2C” for sensor networks. It is like “1 Wire” in that it only needs one wire to communicate with sensor nodes, BUT it is an industry standard and is used for mission critical devices, yet retains a reasonable level of complexity.

### 6.4.8 This project needs to be Geospatial. ###

This prototype needs to be Geospatial in the future. At the moment the Technician needs to manually specify (in the databse) the  location of the sensors in the database so they show up in the correct location when rendered to the Technician GUI. In the future the Data Model needs to be changed to incorporate GPS locations as well as indoor GPS capabilities so that maps can be rendered to the screen that are accurate, and not hand-drawn. 

The Sensor Client in the future might need to be GPS aware to help with the data logging aspect of the application. 
