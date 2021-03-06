package com.publishing.controller;

import cn.hutool.Hutool;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.publishing.common.dto.ChangePassDto;
import com.publishing.common.dto.LoginDto;
import com.publishing.common.dto.SignupDto;
import com.publishing.common.lang.Result;
import com.publishing.entity.RegisteredUser;
import com.publishing.service.RegisteredUserService;
import com.publishing.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class AccountController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    RegisteredUserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        RegisteredUser user = userService.getOne(new QueryWrapper<RegisteredUser>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");

        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            System.out.println(SecureUtil.md5(loginDto.getPassword()));
            return Result.fail("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        Map<Object, Object> map = MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("email", user.getEmail())
                .put("avatar", user.getAvatar())
                .put("gender", user.getGender())
                .put("phone", user.getPhone())
                .put("description", user.getDescription())
                .put("birth", user.getBirth())
                .put("isVip", user.getIsVip())
                .map();
        // 作者是1， 审稿人是2， 编辑是3
        if (user.getIsWriter() == 1) {
            map.put("identity", 1);
        } else if (user.getIsReviewer() == 1) {
            map.put("identity", 2);
        } else if (user.getIsEdit() == 1) {
            map.put("identity", 3);
        }
        return Result.succeed(200, "登录成功", map);
    }

    @PostMapping("/signup")
    public Result signup(@Validated @RequestBody SignupDto signupDto) {


        RegisteredUser user = userService.getOne(new QueryWrapper<RegisteredUser>().eq("username", signupDto.getUsername()));

        if (user != null) {
            return Result.fail("用户名已存在");
        }

        if (!signupDto.getPass().equals(signupDto.getCheckPass())) {
            return Result.fail("两次密码不相同");
        }

        System.out.println(signupDto.getCheckCode());
        System.out.println(signupDto.getEmail());
        System.out.println(redisTemplate.opsForValue().get(signupDto.getEmail()));
        if (!signupDto.getCheckCode().equals(redisTemplate.opsForValue().get(signupDto.getEmail()))) {
            return Result.fail("验证码错误或已过期");
        }
        userService.save(new RegisteredUser(signupDto.getUsername(), SecureUtil.md5(signupDto.getPass()), signupDto.getEmail(), 0));
//        userMapper.addUser(new User(signupDto.getUsername(), SecureUtil.md5(signupDto.getPass()), signupDto.getEmail(), 0));

        return Result.succeed(200, "注册成功，请重新登录！", MapUtil.builder()
                .put("username", signupDto.getUsername())
                .put("result", "注册成功")
                .put("email", signupDto.getEmail())
                .map()
        );

    }

    @RequestMapping("/addReviewer")
    public Result addReviewer(@RequestParam("username") String username, @RequestParam("email") String email) {
        RegisteredUser user = userService.getOne(new QueryWrapper<RegisteredUser>().eq("username", username));
        if (username == null || username.equals("")) {
            return Result.fail("用户名不能为空");
        }
        if (email == null || email.equals("")) {
            return Result.fail("邮箱不能为空");
        }
        if (user != null) {
            return Result.fail("用户名已存在");
        }
        if (!Pattern.compile("^.+@.+\\.+.+").matcher(email).matches()) {
            return Result.fail("邮箱格式不正确");
        }
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setUsername(username);
        registeredUser.setEmail(email);
        registeredUser.setIsWriter(0);
        registeredUser.setIsEdit(0);
        registeredUser.setPassword(SecureUtil.md5("123456"));
        registeredUser.setIsReviewer(1);
        userService.save(registeredUser);
        return Result.succeed(200, "添加成功", null);
    }

    @RequestMapping("/addWriter")
    public Result addWriter(@RequestParam("username") String username, @RequestParam("email") String email) {
        RegisteredUser user = userService.getOne(new QueryWrapper<RegisteredUser>().eq("username", username));
        if (username == null || username.equals("")) {
            return Result.fail("用户名不能为空");
        }
        if (email == null || email.equals("")) {
            return Result.fail("邮箱不能为空");
        }
        if (user != null) {
            return Result.fail("用户名已存在");
        }
        if (!Pattern.compile("^.+@.+\\.+.+").matcher(email).matches()) {
            return Result.fail("邮箱格式不正确");
        }
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setUsername(username);
        registeredUser.setEmail(email);
        registeredUser.setIsWriter(1);
        registeredUser.setIsEdit(0);
        registeredUser.setPassword(SecureUtil.md5("123456"));
        registeredUser.setIsReviewer(0);
        userService.save(registeredUser);
        return Result.succeed(200, "添加成功", null);
    }

    @PostMapping("/changePass")
    public Result changePassword(@Validated @RequestBody ChangePassDto changePassDto) {


        RegisteredUser user = userService.getOne(new QueryWrapper<RegisteredUser>().eq("username", changePassDto.getUsername()).eq("email", changePassDto.getEmail()));

        if (user == null) {
            return Result.fail("用户名不存在或邮箱错误");
        }

        if (!changePassDto.getPass().equals(changePassDto.getCheckPass())) {
            return Result.fail("两次密码不相同");
        }

        System.out.println(changePassDto.getCheckCode());
        System.out.println(changePassDto.getEmail());
        System.out.println(redisTemplate.opsForValue().get(changePassDto.getEmail()));
        if (!changePassDto.getCheckCode().equals(redisTemplate.opsForValue().get(changePassDto.getEmail()))) {
            return Result.fail("验证码错误或已过期");
        }
        userService.updateById(user.setPassword(SecureUtil.md5(changePassDto.getPass())));
//        userMapper.addUser(new User(signupDto.getUsername(), SecureUtil.md5(signupDto.getPass()), signupDto.getEmail(), 0));

        return Result.succeed(200, "修改成功，请重新登录！", MapUtil.builder()
                .put("username", changePassDto.getUsername())
                .put("result", "修改成功")
                .put("email", changePassDto.getEmail())
                .map()
        );

    }

    @PostMapping("/editInfo")
    public Result editInfo(@RequestParam("description") String description, @RequestParam("user_id") Long userId) {
        RegisteredUser user = userService.getById(userId);
        user.setDescription(description);
        try {
            userService.updateById(user);
        } catch (Exception e) {
            return Result.fail("简介内容过长");
        }
        Map<Object, Object> map = MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("email", user.getEmail())
                .put("avatar", user.getAvatar())
                .put("gender", user.getGender())
                .put("phone", user.getPhone())
                .put("description", user.getDescription())
                .put("birth", user.getBirth())
                .put("isVip", user.getIsVip())
                .map();
        // 作者是1， 审稿人是2， 编辑是3
        if (user.getIsWriter() == 1) {
            map.put("identity", 1);
        } else if (user.getIsReviewer() == 1) {
            map.put("identity", 2);
        } else if (user.getIsEdit() == 1) {
            map.put("identity", 3);
        }
        return Result.succeed(200, "修改成功", map);
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succeed(null);
    }
}
