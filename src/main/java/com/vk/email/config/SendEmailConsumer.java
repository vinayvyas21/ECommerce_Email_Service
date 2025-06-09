package com.vk.email.config;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.email.dto.SendEmailDto;

/**
 * SendEmailConsumer is a service that listens to the Kafka topic "sendEmail"
 */
@Service
public class SendEmailConsumer {
	private ObjectMapper objectMapper;
	private EmailUtil emailUtil;

	public SendEmailConsumer(ObjectMapper objectMapper, EmailUtil emailUtil) {
		this.objectMapper = objectMapper;
		this.emailUtil = emailUtil;
	}

	/**
	 * This method listens to the Kafka topic "sendEmail" and processes the incoming
	 * messages. It deserializes the message into a SendEmailDto object and sends an
	 * email using the EmailUtil.
	 * 
	 * @param message The JSON string message received from the Kafka topic.
	 * @throws RuntimeException if there is an error during JSON processing.
	 */
	@KafkaListener(topics = "sendEmail", groupId = "emailService")
	public void handleSendEmailMessage(String message) {
		// Code to send an Email to the user.
		SendEmailDto sendEmailDto = null;
		try {
			sendEmailDto = objectMapper.readValue(message, SendEmailDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		// Send an Email.
		// SMTP -> Simple Mail Transfer Protocol.
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.port", "587"); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("temp39766@gmail.com", "wddohrzomjejssjd");
			}
		};
		Session session = Session.getInstance(props, auth);

		emailUtil.sendEmail(session, sendEmailDto.getTo(), sendEmailDto.getSubject(), sendEmailDto.getBody());
	}
}