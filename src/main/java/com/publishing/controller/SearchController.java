package com.publishing.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.publishing.common.lang.Result;
import com.publishing.entity.*;
import com.publishing.mapper.FavorReaderPassageMapper;
import com.publishing.mapper.PassageMapper;
import com.publishing.mapper.ReviewMapper;
import com.publishing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SearchController {

    @Autowired
    private PassageMapper passageMapper;

    @Autowired
    private PassageService passageService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RegisteredUserService userService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private FavorReaderPassageService favorReaderPassageService;

    @RequestMapping("/newPassages")
    public Result newPassages(@RequestParam("page") int page,
                              @RequestParam("pageSize") int pageSize) {
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
    public Result getPassageReviewedUnpublished(@RequestParam("page") int page,
                                                @RequestParam("pageSize") int pageSize) {
        int startPage = (page - 1) * pageSize;
        return Result.succeed(MapUtil.builder()
                .put("article_list", passageMapper.selectReviewedUnpublishedByPage(startPage, pageSize))
                .put("total_num", passageMapper.selectCountReviewedUnpublished())
                .map());
    }

    @RequestMapping("/getPassageUnreviewed")
    public Result getPassageUnreviewed(@RequestParam("page") int page,
                                       @RequestParam("pageSize") int pageSize) {
        int startPage = (page - 1) * pageSize;
        return Result.succeed(MapUtil.builder()
                .put("article_list", passageMapper.selectUnreviewedByPage(startPage, pageSize))
                .put("total_num", passageMapper.selectCountUnreviewed())
                .map());
    }

    @RequestMapping("/getPassageDistributedUnreviewed")
    public Result getPassageDistributedUnreviewed(@RequestParam("page") int page,
                                                  @RequestParam("pageSize") int pageSize, @RequestParam("reviewer_id") Long reviewerId) {
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
    public Result getPassageDistributedReviewed(@RequestParam("page") int page,
                                                @RequestParam("pageSize") int pageSize,
                                                @RequestParam("reviewer_id") Long reviewerId) {
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

    @RequestMapping("/searchByWriter")
    public Result searchByWriter(@RequestParam("page") int page,
                                 @RequestParam("pageSize") int pageSize,
                                 @RequestParam("input") String writerName) {
        int startPage = (page - 1) * pageSize;
        List<Passage> passageList = passageService.list(new QueryWrapper<Passage>().eq("writer_id", userService.getOne(new QueryWrapper<RegisteredUser>().eq("username", writerName)).getId()).eq("published", 1));

        return Result.succeed(MapUtil.builder()
                .put("article_list", passageList.subList(startPage, Math.min(startPage + pageSize, passageList.size())))
                .put("total_num", passageList.size())
                .map());
    }

    @RequestMapping("/searchByKeyword")
    public Result searchByKeyword(@RequestParam("page") int page,
                                  @RequestParam("pageSize") int pageSize,
                                  @RequestParam("input") String keyword) {
        int startPage = (page - 1) * pageSize;
        List<Passage> passageList = passageService.list(new QueryWrapper<Passage>().like("title", "%" + keyword + "%").eq("published", 1));
        return Result.succeed(MapUtil.builder()
                .put("article_list", passageList.subList(startPage, Math.min(startPage + pageSize, passageList.size())))
                .put("total_num", passageList.size())
                .map());
    }

    @RequestMapping("/searchByTag")
    public Result searchByTag(@RequestParam("page") int page,
                              @RequestParam("pageSize") int pageSize,
                              @RequestParam("input") String[] tags) {
        int startPage = (page - 1) * pageSize;
        System.out.println(Arrays.toString(tags));
        if (tags == null || tags.length == 0) {
            return Result.fail("请添加一个标签");
        }
//        tags[0] = tags[0].substring(1);
//        tags[tags.length - 1] = tags[tags.length - 1].substring(0, tags[tags.length - 1].length() - 1);
        Set<Long> passageIdSet = new HashSet<>();
        for (String tag : tags) {
            List<Type> typeList = typeService.list(new QueryWrapper<Type>().eq("type", tag));
            System.out.println(tag);
            for (Type type : typeList) {
                passageIdSet.add(type.getPassageId());
            }
        }
        List<Passage> passageList = new ArrayList<>();
        for (Long l : passageIdSet) {
            passageList.add(passageService.getById(l));
        }
        return Result.succeed(MapUtil.builder()
                .put("article_list", passageList.subList(startPage, Math.min(startPage + pageSize, passageList.size())))
                .put("total_num", passageList.size())
                .map());
    }

    @RequestMapping("/searchFavorArticle")
    public Result searchFavorArticle(@RequestParam("page") int page,
                                     @RequestParam("pageSize") int pageSize,
                                     @RequestParam("user_id") int userId) {
        int startPage = (page - 1) * pageSize;
        List<Passage> passageList = new ArrayList<>();
        List<FavorReaderPassage> favorReaderPassages = favorReaderPassageService.list(new QueryWrapper<FavorReaderPassage>().eq("reader_id", userId));
        for (FavorReaderPassage f : favorReaderPassages) {
            passageList.add(passageService.getById(f.getPassageId()));
        }
        return Result.succeed(MapUtil.builder()
                .put("article_list", passageList.subList(startPage, Math.min(startPage + pageSize, passageList.size())))
                .put("total_num", passageList.size())
                .map());
    }

//    @RequestMapping()

}
