package com.publishing.controller;


import com.publishing.service.RegisteredUserService;
import javafx.beans.binding.ObjectExpression;
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
        return userService.getById(1L);
    }
}
