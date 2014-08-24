package com.ems.datalogging.data.email;

import com.ems.datalogging.data.db.EmsDataLoggingDbDAOFactory;
import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;

/**
 * 
 */
public class EmailDAO {

    protected String SMTP_HOST_NAME = "smtp.gmail.com";
    protected int SMTP_HOST_PORT = 465;
    protected String SMTP_AUTH_USER = "emsmailaccount@gmail.com";
    protected String SMTP_AUTH_PWD  = "paul1234";
    protected EmsDataLoggingDbDAOFactory daoFactory;
    
    public EmailDAO(EmsDataLoggingDbDAOFactory aDaoFac)
    {
        // set daofactory
        daoFactory = aDaoFac;
        
        // set the local vars according to the values in the dao factory
        SMTP_HOST_NAME = daoFactory.getEmail_hostAddress();
        SMTP_HOST_PORT = daoFactory.getEmail_hostPortNum();
        SMTP_AUTH_USER = daoFactory.getEmail_applicationEmailAccountUserName();
        SMTP_AUTH_PWD = daoFactory.getEmail_applicationEmailAccountPassword();        
    }
   
    public void SendMessage(ArrayList<String>a_listOfRecipientsEmailAddresses, String a_msgSubject, String a_msgContent, ArrayList<String> a_listOfFilePathsToInclude ) throws Exception{
        
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false"); // this piece of code is used to prevent hang ups, otherwise the method can hang because it will wait for a response from the server

        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(a_msgSubject);
        //message.setContent("Here are some words.", "text/plain");
        
        // create the Multipart and add its parts to it
        Multipart mp = new MimeMultipart();
        
        // create and fill the first message part
        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText(a_msgContent);
        mp.addBodyPart(mbp1);

        // Attach all files included here to the message as well
        if(a_listOfFilePathsToInclude != null)
        {
            for(int i=0; i<a_listOfFilePathsToInclude.size(); i++)
            {
                // create the second message part
                MimeBodyPart mbp2 = new MimeBodyPart();

                // attach the file to the message
                mbp2.attachFile(a_listOfFilePathsToInclude.get(i));

                // attach to the main message
                mp.addBodyPart(mbp2);
            }
        }
        
        
        // add the Multipart to the message
        message.setContent(mp);        
        
        
        
        // Attach all recipients here to the message as well
        for(int i=0; i<a_listOfRecipientsEmailAddresses.size(); i++)
        {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(a_listOfRecipientsEmailAddresses.get(i)));
        }        
        
        transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
    
//     public static void main(String[] args) throws Exception{
//       ArrayList<String> aListOfFilePaths = new ArrayList<String>();
//       aListOfFilePaths.add("c:\\Temp\\image_00000000000141000000.jpg");
//       aListOfFilePaths.add("c:\\Temp\\image_00000000001132000000.jpg");
//       aListOfFilePaths.add("c:\\Temp\\image_00000000001532000000.jpg");
//       
//       ArrayList<String> aListOfUserEmailAddresses = new ArrayList<String>();
//       aListOfUserEmailAddresses.add("pww4298@rit.edu");
//       aListOfUserEmailAddresses.add("emsmailaccount@gmail.com");
//       new EmailDAO().SendMessage(aListOfUserEmailAddresses,"Here is some text to send.", aListOfFilePaths);
//    }

}
