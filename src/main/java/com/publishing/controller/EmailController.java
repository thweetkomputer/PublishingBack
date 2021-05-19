package com.publishing.controller;

import com.publishing.common.dto.SignupDto;
import com.publishing.util.mail.SSLEmail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
public class EmailController {
    private SSLEmail sslEmail = new SSLEmail("master@sepublishing.xyz"
            , "master123"
            , "smtpout.secureserver.net", 465);
    Random random = new Random();

    @PostMapping("/sendVerificationCode")
    public void sendVerificationCode(@RequestBody SignupDto Email, HttpSession httpSession) {
        System.out.println(Email.getEmail());
        int checkCode = random.nextInt(8999)+1000;
        httpSession.setAttribute(Email.getEmail(), Integer.toString(checkCode));
        System.out.println(Email.getEmail());
        System.out.println(httpSession.getAttribute(Email.getEmail()));
        sslEmail.sendEmail(Email.getEmail(), "邮箱验证码", "您的验证码是 "+checkCode+" 请用此验证码进行验证登陆，如果不是您的邮箱，请忽略此信息。");
    }
}
