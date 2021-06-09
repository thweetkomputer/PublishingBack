package com.publishing.controller;


import cn.hutool.core.map.MapUtil;
import com.publishing.common.lang.Result;
import com.publishing.entity.Notice;
import com.publishing.entity.Passage;
import com.publishing.service.NoticeService;
import com.publishing.service.PassageService;
import com.publishing.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-09
 */
@RestController
public class PassageController {

    @Autowired
    private PassageService passageService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private RegisteredUserService userService;

    @Value("${storage.pathname}")
    private String pathname;

    @RequestMapping("/getPassage")
    public Result getPassage(@RequestParam("article_title") Long id) throws IOException {
        File file = new File(pathname + id + ".pdf");
        FileInputStream inputStream = new FileInputStream(file);
        return Result.succeed(MapUtil.builder()
                .put("description", passageService.getById(id).getDescription())
                .put("title", passageService.getById(id).getTitle())
                .put("type", userService.getById(passageService.getById(id).getWriterId()).getUsername())
                .map());
    }

    @RequestMapping("/publishPassage")
    public Result publishPassage(@RequestParam("article_id") Long id) {
        Passage passage = passageService.getById(id);
        passage.setPublished(1);
        passageService.updateById(passage);
        noticeService.save(new Notice(passage.getWriterId(), "您的文章Id为"+id+"，题目为 "+ passage.getTitle()+ " 的文章已经出版啦！"));
        return Result.succeed(200, "出版成功", null);
    }

    @RequestMapping("/deletePassage")
    public Result deletePassage(@RequestParam("article_id") Long id) {
        Passage passage = passageService.getById(id);
        Long writerId = passage.getWriterId();
        passageService.removeById(passage);
        noticeService.save(new Notice(writerId, "很遗憾，您的文章Id为"+id+"，题目为 "+ passage.getTitle()+ " 的文章未能出版。"));
        return Result.succeed(200, "删除成功", null);
    }
}
