package co.in.util;


import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import co.in.Exception.ApplicationException;
/**
 * The Class EmailUtility.
 */

public class EmailUtility {
/**
* Create Resource Bundle To Read Properties File
*/


	static ResourceBundle rb = ResourceBundle.getBundle("co.in.Bundle.system");
	
	
/**
* Email Server
*/
	private static final String SMTP_HOST_NAME = rb.getString("smtp.server");
/**
* Email Server Port
*/
	private static final String SMTP_PORT = rb.getString("smtp.port");
/**
* SessionFactory,A session is a Connection to Email Server
*/
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
/**
* Administretor's Email Id By Which All Messages will be Send
*/
	private static final String emailFromAddress = rb.getString("email.login");
/**
* Admistrators Email Password
*/
	private static final String emailPassword = rb.getString("email.pwd");
/**
* Email server properties
*/
	private static Properties prop = new Properties();
/**
* Static blocks to initialize static parameters
*/
	static
  {
	prop.put("mail.smtp.host",SMTP_HOST_NAME);
	prop.put("mail.smtp.auth", "true");
	prop.put("mail.debug", "true");
	prop.put("mail.smtp.port", SMTP_PORT);
	prop.put("mail.smtp.socketFactory.port", SMTP_PORT);
	prop.put("mail.smtp.socketFactory.class", SSL_FACTORY);
	prop.put("mail.smtp.socketFactory.fallback", "false");
  }
	
	
/**
* Sends an Email
* @throws ApplicationException :ApplicationException
* @param emailMessageDTO 
*/
	public static void sendMail(EmailMessage emailMessageDTO)throws ApplicationException
	{
		System.out.println("send mail class of utility...........");
		try
		{

// Connection to Mail Server
			
			
			Session session = Session.getDefaultInstance(prop,new javax.mail.Authenticator()
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(emailFromAddress,emailPassword);
				}
			});
			System.out.println("msg has been send (emailutility)");
// Make debug mode true to display debug messages at console
			session.setDebug(true);

// Create a message

			Message msg = new MimeMessage(session);
			InternetAddress addressFrom = new InternetAddress(emailFromAddress);
			msg.setFrom(addressFrom);

// Set TO addresses
			
			
			String[] emailIds = new String[0];

			if (emailMessageDTO.getTo() != null)
			{
				emailIds = emailMessageDTO.getTo().split(",");
			}

// Set CC addresses
			
			String[] emailIdsCc = new String[0];

			if (emailMessageDTO.getCc() != null)
			{
				emailIdsCc = emailMessageDTO.getCc().split(",");
			}

// Set BCC addresses
			String[] emailIdsBcc = new String[0];

			if (emailMessageDTO.getBcc() != null)
			{
				emailIdsBcc = emailMessageDTO.getBcc().split(",");
			}

			InternetAddress[] addressTo = new InternetAddress[emailIds.length];

			for (int i = 0; i < emailIds.length; i++)
			{
				addressTo[i] = new InternetAddress(emailIds[i]);
			}

			InternetAddress[] addressCc = new InternetAddress[emailIdsCc.length];

			for (int i = 0; i < emailIdsCc.length; i++)
			{
				addressCc[i] = new InternetAddress(emailIdsCc[i]);
			}

			InternetAddress[] addressBcc = new InternetAddress[emailIdsBcc.length];

			for (int i = 0; i < emailIdsBcc.length; i++)
			{
				addressBcc[i] = new InternetAddress(emailIdsBcc[i]);
			}

			if (addressTo.length > 0)
			{
				msg.setRecipients(Message.RecipientType.TO, addressTo);
			}

			if (addressCc.length > 0)
			{
				msg.setRecipients(Message.RecipientType.CC, addressCc);
			}

			if (addressBcc.length > 0)
			{
				msg.setRecipients(Message.RecipientType.BCC, addressBcc);
			}

// Setting the Subject and Content Type
			
			msg.setSubject(emailMessageDTO.getSubject());

// Set message MIME type
			
			switch (emailMessageDTO.getMessageType())
			{
			case EmailMessage.HTML_MSG:
				msg.setContent(emailMessageDTO.getMessage(), "text/html");
				break;
			case EmailMessage.TEXT_MSG:
				msg.setContent(emailMessageDTO.getMessage(), "text/plain");
				break;

			}

	// Send the mail
			
			Transport.send(msg);

		} catch (Exception ex)

		{
			ex.printStackTrace();
			throw new ApplicationException("Email " + ex.getMessage());
		}
       	  }

   	}