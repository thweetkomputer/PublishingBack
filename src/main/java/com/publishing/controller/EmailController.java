package com.publishing.controller;

import com.publishing.common.dto.SignupDto;
import com.publishing.common.lang.Result;
import com.publishing.entity.RegisteredUser;
import com.publishing.util.mail.SSLEmail;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class EmailController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private SSLEmail sslEmail = new SSLEmail("master@sepublishing.xyz"
            , "master123"
            , "smtpout.secureserver.net", 465);
    Random random = new Random();

    @PostMapping("/sendVerificationCode")
    public Result sendVerificationCode(@RequestBody SignupDto Email) {
        System.out.println(Email.getEmail());
        int checkCode = random.nextInt(8999)+1000;
        redisTemplate.opsForValue().set(Email.getEmail(), Integer.toString(checkCode), 60*5, TimeUnit.SECONDS);
        System.out.println(Email.getEmail());
//        System.out.println(httpSession.getAttribute(Email.getEmail()));
        sslEmail.sendEmail(Email.getEmail(), "邮箱验证码", "您的验证码是 "+checkCode+" 请用此验证码进行验证登陆，如果不是您的邮箱，请忽略此信息。");
        return Result.succeed(200, "发送成功", null);
    }
}
