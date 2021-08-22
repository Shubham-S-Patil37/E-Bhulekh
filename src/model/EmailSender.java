package model;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailSender {
    public static void sendEmail(String recipientEmail, String subject, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
                        return new PasswordAuthentication("ebhulekh.registerme@gmail.com", "registerME@");
                    }
                });
        try {
            Message msg = new MimeMessage(session);
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            msg.setSubject(subject);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/plain");    //we can use "text/plain" or any other format

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            Transport.send(msg);
        } catch (MessagingException e) { System.err.println(""+e);}
        
    }
    
    public static void main(String[] args) {
        sendEmail("","","");
    }
}