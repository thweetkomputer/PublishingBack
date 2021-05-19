package com.publishing.util.mail;

public class SSLEmailTest {
    public static void main(String[] args) {
        SSLEmail sslEmail = new SSLEmail("master@sepublishing.xyz"
                , "master123"
                , "smtpout.secureserver.net", 465);
        sslEmail.sendEmail("2320092610@qq.com", "验证码", "hello from pure java");

    }
}
