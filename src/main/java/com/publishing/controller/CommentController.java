package com.publishing.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.publishing.common.lang.Result;
import com.publishing.entity.Comment;
import com.publishing.entity.Passage;
import com.publishing.entity.RegisteredUser;
import com.publishing.mapper.CommentMapper;
import com.publishing.mapper.PassageMapper;
import com.publishing.service.CommentService;
import com.publishing.service.PassageService;
import com.publishing.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 聊天记录 前端控制器
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-09
 */
@RestController
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PassageService passageService;

    @Autowired
    private RegisteredUserService userService;

    @RequestMapping("/getComment")
    public Result getComment(@RequestParam("page") Long page,
                              @RequestParam("pageSize") Long pageSize,
                              @RequestParam("article_id") String articleName) {
//        System.out.println(page + " " + pageSize);
        Long startPage = (page - 1) * pageSize;
//        int endPage = page * pageSize;
        List<Comment> commentList = commentMapper.selectByPage(startPage, pageSize, passageService.getOne(new QueryWrapper<Passage>().eq("title", articleName)).getId());
        for (Comment c : commentList) {
            c.setContent(userService.getOne(new QueryWrapper<RegisteredUser>().eq("id", c.getWriterId())).getUsername()+" "+c.getContent());
        }
        return Result.succeed(201, "", MapUtil.builder()
                .put("comment_list", commentList)
                .put("total_num", commentMapper.selectCount())
                .map());
    }

    @RequestMapping("/addComment")
    public Result addComment(@RequestParam("article_id") String articleName,
                             @RequestParam("text") String content,
                             @RequestParam("user_id") Long userId) {
        commentService.save(new Comment(userId, passageService.getOne(new QueryWrapper<Passage>().eq("title", articleName)).getId(), content));
        return Result.succeed(200, "评论成功！", null);
    }
}
