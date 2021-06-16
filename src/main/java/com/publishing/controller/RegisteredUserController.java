package com.publishing.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.publishing.common.lang.Result;
import com.publishing.entity.Passage;
import com.publishing.entity.RegisteredUser;
import com.publishing.mapper.RegisteredUserMapper;
import com.publishing.service.RegisteredUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-09
 */
@RestController
public class RegisteredUserController {
    @Autowired
    private RegisteredUserService userService;

    @Autowired
    private RegisteredUserMapper userMapper;

    @RequiresAuthentication
    @GetMapping("/index")
    public Object index() {
        return Result.succeed(200, "你为什么要超管的信息？", userService.getById(1L));
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody RegisteredUser user) {
        return Result.succeed(user);
    }

    @PostMapping("/getReviewer")
    public Object getReviewer () {
        return Result.succeed(userService.list(new QueryWrapper<RegisteredUser>().eq("is_reviewer", 1)));
    }

    @RequestMapping("/getReviewerList")
    public Result getReviewerList(@RequestParam("page") int page,
                                 @RequestParam("pageSize") int pageSize) {
        System.out.println(page + " " + pageSize);
        int startPage = (page - 1) * pageSize;
//        int endPage = page * pageSize;
        List<RegisteredUser> reviewerList = userService.list(new QueryWrapper<RegisteredUser>().eq("is_reviewer", 1));
        return Result.succeed(MapUtil.builder()
                .put("article_list", reviewerList.subList(startPage, Math.min(startPage + pageSize, reviewerList.size())))
                .put("total_num", reviewerList.size())
                .map());
    }

    @RequestMapping("/getWriterList")
    public Result getWriterList(@RequestParam("page") int page,
                                 @RequestParam("pageSize") int pageSize) {
        System.out.println(page + " " + pageSize);
        int startPage = (page - 1) * pageSize;
//        int endPage = page * pageSize;
        List<RegisteredUser> reviewerList = userService.list(new QueryWrapper<RegisteredUser>().eq("is_writer", 1));
        return Result.succeed(MapUtil.builder()
                .put("article_list", reviewerList.subList(startPage, Math.min(startPage + pageSize, reviewerList.size())))
                .put("total_num", reviewerList.size())
                .map());
    }

    @RequestMapping("/deleteReviewer")
    public Result deleteReviewer(@RequestParam("id") Long id) {
        userMapper.deleteById(id);
        return Result.succeed(200, "删除成功", null);
    }

    @RequestMapping("/deleteWriter")
    public Result deleteWriter(@RequestParam("id") Long id) {
        userMapper.deleteById(id);
        return Result.succeed(200, "删除成功", null);
    }

    @RequestMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam("id") Long id) {
        return Result.succeed(userService.getById(id));
    }


}
