package com.publishing.mail;

public class SSLEmailTest {
    public static void main(String[] args) {
        SSLEmail sslEmail = new SSLEmail("master@sepublishing.xyz"
                , "master123"
                , "smtpout.secureserver.net", 465);
        sslEmail.sendEmail("601862884@qq.com", "test", "hello from pure java");

    }
}
