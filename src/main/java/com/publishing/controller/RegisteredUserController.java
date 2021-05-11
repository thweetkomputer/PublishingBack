package com.publishing.controller;


import com.publishing.common.lang.Result;
import com.publishing.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/index")
    public Object index() {
        return Result.succeed(200, "你为什么要超管的信息？", userService.getById(1L));
    }
}
