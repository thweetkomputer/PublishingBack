package com.publishing.controller;


import com.publishing.common.lang.Result;
import com.publishing.entity.RegisteredUser;
import com.publishing.service.RegisteredUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-09
 */
@RestController
@RequestMapping("/registered-user")
public class RegisteredUserController {
    @Autowired
    RegisteredUserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Object index() {
        return Result.succeed(200, "你为什么要超管的信息？", userService.getById(1L));
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody RegisteredUser user) {
        return Result.succeed(user);

    }
}
