package com.publishing.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.publishing.common.lang.Result;
import com.publishing.entity.Passage;
import com.publishing.entity.Review;
import com.publishing.mapper.PassageMapper;
import com.publishing.mapper.ReviewMapper;
import com.publishing.service.PassageService;
import com.publishing.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private PassageMapper passageMapper;

    @Autowired
    private PassageService passageService;

    @Autowired
    private ReviewService reviewService;

    @RequestMapping("/newPassages")
    public Result newPassages(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        System.out.println(page + " " + pageSize);
        int startPage = (page - 1) * pageSize;
//        int endPage = page * pageSize;
        return Result.succeed(MapUtil.builder()
                .put("article_list", passageMapper.selectByPage(startPage, pageSize))
                .put("total_num", passageMapper.selectCount())
                .map());
    }

//    @RequestMapping("/countPassages")
//    public Result countPassages() {
//        return Result.succeed(201, "", passageMapper.selectCount());
//    }

    @RequestMapping("/getPassageReviewedUnpublished")
    public Result getPassageReviewedUnpublished(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        int startPage = (page - 1) * pageSize;
        return Result.succeed(MapUtil.builder()
                .put("article_list", passageMapper.selectReviewedUnpublishedByPage(startPage, pageSize))
                .put("total_num", passageMapper.selectCountReviewedUnpublished())
                .map());
    }

    @RequestMapping("/getPassageUnreviewed")
    public Result getPassageUnreviewed(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        int startPage = (page - 1) * pageSize;
        return Result.succeed(MapUtil.builder()
                .put("article_list", passageMapper.selectUnreviewedByPage(startPage, pageSize))
                .put("total_num", passageMapper.selectCountUnreviewed())
                .map());
    }

    @RequestMapping("/getPassageDistributedUnreviewed")
    public Result getPassageDistributedUnreviewed(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam("reviewer_id") Long reviewerId) {
        int startPage = (page - 1) * pageSize;
        List<Passage> passageList = new ArrayList<>();
        List<Review> reviewList = reviewService.list(new QueryWrapper<Review>().eq("reviewer_id", reviewerId));
        for (Review review : reviewList) {
            Passage one = passageService.getOne(new QueryWrapper<Passage>().eq("id", review.getPassageId()).eq("unreviewed", 1));
            if (one != null) {
                passageList.add(one);
            }
        }
        return Result.succeed(MapUtil.builder()
                .put("article_list", passageList.subList(startPage, Math.min(startPage + pageSize, passageList.size())))
                .put("total_num", passageList.size())
                .map());
    }

    @RequestMapping("/getPassageDistributedReviewed")
    public Result getPassageDistributedReviewed(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam("reviewer_id") Long reviewerId) {
        int startPage = (page - 1) * pageSize;
        List<Passage> passageList = new ArrayList<>();
        List<Review> reviewList = reviewService.list(new QueryWrapper<Review>().eq("reviewer_id", reviewerId));
        for (Review review : reviewList) {
            Passage one = passageService.getOne(new QueryWrapper<Passage>().eq("id", review.getPassageId()).eq("unreviewed", 0));
            if (one != null) {
                passageList.add(one);
            }
        }
        return Result.succeed(MapUtil.builder()
                .put("article_list", passageList.subList(startPage, Math.min(startPage + pageSize, passageList.size())))
                .put("total_num", passageList.size())
                .map());
    }

}
