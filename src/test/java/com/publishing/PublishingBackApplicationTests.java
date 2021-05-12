package com.publishing;

import com.publishing.mail.SSLEmail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class PublishingBackApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
    }

    @Test
    void testDataSource() {
        System.out.println(dataSource);
    }

    @Test
    void SSLEEmailTest() {
        SSLEmail sslEmail = new SSLEmail("master@sepublishing.xyz"
                , "master123"
                , "smtpout.secureserver.net", 465);
        sslEmail.sendEmail("18377332@buaa.edu.cn", "test", "hello from pure java");
        sslEmail.sendEmail("zc2320092610@163.com", "test", "hello from pure java");
    }

}
