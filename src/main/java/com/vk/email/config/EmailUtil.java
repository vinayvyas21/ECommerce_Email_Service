package com.vk.email.config;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

/**
 * Utility class for sending emails. This class provides a method to send an
 * email using the JavaMail API.
 */
@Service
public class EmailUtil {

	/**
	 * Sends an email using the provided session, recipient email, subject, and
	 * body.
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public void sendEmail(Session session, String toEmail, String subject, String body) {
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("temp39766@gmail.com", "NoReply-JD"));
			msg.setReplyTo(InternetAddress.parse("temp39766@gmail.com", false));
			msg.setSubject(subject, "UTF-8");
			msg.setText(body, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);
			System.out.println("Email Sent Successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
