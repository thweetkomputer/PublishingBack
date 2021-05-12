package com.publishing.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SSLEmail {

    /**
     Outgoing Mail (SMTP) Server
     requires TLS or SSL: smtp.gmail.com (use authentication)
     Use Authentication: Yes
     Port for SSL: 465
     */
    private String fromEmail;
    private String personal;
    private String password;
    private String smtpServer;
    private int port;
    private Session session;
    private EmailUtil emailUtil;

    public SSLEmail(String fromEmail, String password, String smtpServer, int port) {
        this.fromEmail = fromEmail;
        this.password = password;
        this.smtpServer = smtpServer;
        this.port = port;
        System.out.println("SSLEmail Start");

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.socketFactory.port", Integer.valueOf(port).toString());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", Integer.valueOf(port).toString());
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");
        emailUtil = new EmailUtil(fromEmail, personal, fromEmail);
    }

    public void sendEmail(String toEmail, String subject, String body) {
        emailUtil.sendEmail(session, toEmail, subject, body);
    }

}