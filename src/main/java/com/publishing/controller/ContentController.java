package com.publishing.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.publishing.common.lang.Result;
import com.publishing.entity.FavorReaderPassage;
import com.publishing.entity.LikeReaderPassage;
import com.publishing.entity.Notice;
import com.publishing.entity.Passage;
import com.publishing.mapper.FavorReaderPassageMapper;
import com.publishing.mapper.LikeReaderPassageMapper;
import com.publishing.mapper.PassageMapper;
import com.publishing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    @Autowired
    private FavorReaderPassageService favorReaderPassageService;

    @Autowired
    private FavorReaderPassageMapper favorReaderPassageMapper;

    @Autowired
    private LikeReaderPassageService likeReaderPassageService;

    @Autowired
    private LikeReaderPassageMapper likeReaderPassageMapper;

    @Autowired
    private PassageService passageService;

    @Autowired
    private NoticeService noticeService;

    public Long getPassageIdByTitle(String title) {
        return passageService.getOne(new QueryWrapper<Passage>().eq("title", title)).getId();
    }

    @RequestMapping("/getUserToArticle")
    public Result getUserToPassage(@RequestParam("user_id") Long readerId, @RequestParam("article_id") String title) {

        Long passageId = getPassageIdByTitle(title);

        return Result.succeed(MapUtil.builder()
                .put("favor", favorReaderPassageMapper.selectList(new QueryWrapper<FavorReaderPassage>().eq("reader_id", readerId).eq("passage_id", passageId)).size() != 0 ? 1 : 0)
                .put("like", likeReaderPassageMapper.selectList(new QueryWrapper<LikeReaderPassage>().eq("reader_id", readerId).eq("passage_id", passageId)).size() != 0 ? 1 : 0)
                .map());
    }

    @RequestMapping("/likeArticle")
    public Result likeArticle(@RequestParam("user_id") Long readerId, @RequestParam("article_id") String title) {

        Long passageId = getPassageIdByTitle(title);
        likeReaderPassageMapper.insert(new LikeReaderPassage(readerId, passageId));
        return Result.succeed(200, "点赞成功", MapUtil.builder().put("like", 1).map());
    }

    @RequestMapping("/favorArticle")
    public Result favorArticle(@RequestParam("user_id") Long readerId, @RequestParam("article_id") String title) {

        Long passageId = getPassageIdByTitle(title);
        favorReaderPassageMapper.insert(new FavorReaderPassage(readerId, passageId));
        return Result.succeed(200, "收藏成功", MapUtil.builder().put("favor", 1).map());
    }

    @RequestMapping("/cancelLikeArticle")
    public Result cancelLikeArticle(@RequestParam("user_id") Long readerId, @RequestParam("article_id") String title) {

        Long passageId = getPassageIdByTitle(title);
        likeReaderPassageMapper.delete(new QueryWrapper<LikeReaderPassage>().eq("reader_id", readerId).eq("passage_id", passageId));
        return Result.succeed(200, "取消点赞成功", MapUtil.builder().put("like", 0).map());
    }

    @RequestMapping("/cancelFavorArticle")
    public Result cancelFavorArticle(@RequestParam("user_id") Long readerId, @RequestParam("article_id") String title) {

        Long passageId = getPassageIdByTitle(title);
        favorReaderPassageMapper.delete(new QueryWrapper<FavorReaderPassage>().eq("reader_id", readerId).eq("passage_id", passageId));
        return Result.succeed(200, "取消收藏成功", MapUtil.builder().put("favor", 0).map());
    }

    @RequestMapping("/submitComplaint")
    public Result submitComplaint(@RequestParam("user_id") Long readerId, @RequestParam("article_id") String title, @RequestParam("report_message") String content) {
        noticeService.save(new Notice(1L, "文章Id为"+getPassageIdByTitle(title)+",题目为 " + title + " 的文章被举报，举报信息为\n  "+content));
        return Result.succeed(200, "举报成功，信息已发送给编辑！", null);
    }
}
