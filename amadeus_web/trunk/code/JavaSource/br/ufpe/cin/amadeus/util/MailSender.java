package br.ufpe.cin.amadeus.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.ufpe.cin.amadeus.system.access_control.user.User;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class MailSender {

 	public static void sendMail(User user, String subject,
 			String text) throws MessagingException {
 		sendMail(user.getPerson().getEmail(), subject, text);
 	}
 	
 	public static void sendMail(Person person, String subject,
 			String text) throws MessagingException {
 		sendMail(person.getEmail(), subject, text);
 	}

 	public static void sendMail(String recipient, String subject,
 			String text) throws MessagingException {
	    Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.cin.ufpe.br");

	    Session session = Session.getDefaultInstance(props, null);
	    Message msg = new MimeMessage(session);

	    InternetAddress addressFrom = new InternetAddress("amadeus@cin.ufpe.br");
	    msg.setFrom(addressFrom);
	    InternetAddress[] addressTo = {new InternetAddress(recipient)}; 

	    msg.setRecipients(Message.RecipientType.TO, addressTo);
	    msg.setSubject(subject);
	    msg.setSentDate(new Date());
	    msg.setText(text);

	    Transport.send(msg);
 	}
 	
}
