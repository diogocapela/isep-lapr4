/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author pushdword
 */
public class EmailService {
    public static void sendEmail(String subject, String body, String rcpt) {
        String sender = "lapr4@ret.re";
        String host = "email-smtp.eu-west-1.amazonaws.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(properties,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        "AKIAUQEPRLHRKFBS24X6", "BNJaJvKGj7Se2cCALsB7eQ3rspkVs35uaMN7oklVUTlA");
                }
            });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(rcpt));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (AddressException e) {
            System.err.println(e);
        } catch (MessagingException e) {
            System.err.println(e);
        }
    }
}
