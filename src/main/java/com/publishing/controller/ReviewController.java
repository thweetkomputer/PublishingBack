package com.publishing.controller;


import com.publishing.common.lang.Result;
import com.publishing.entity.Notice;
import com.publishing.entity.Passage;
import com.publishing.entity.Review;
import com.publishing.mapper.PassageMapper;
import com.publishing.service.NoticeService;
import com.publishing.service.PassageService;
import com.publishing.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-27
 */
@RestController
public class ReviewController {

    @Autowired
    private PassageMapper passageMapper;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/distributePassage")
    public Result distributePassage(@RequestParam("article_id") Long passageId,
                                    @RequestParam("reviewer_id1") Long reviewerId1,
                                    @RequestParam("reviewer_id2") Long reviewerId2,
                                    @RequestParam("reviewer_id3") Long reviewerId3) {
        if (reviewerId1 == null
                || reviewerId2 == null
                || reviewerId3 == null
                || reviewerId1.equals(reviewerId2)
                || reviewerId1.equals(reviewerId3)
                || reviewerId2.equals(reviewerId3)) {
            return Result.fail("请选择三个不同的审稿人");
        }
        Passage passage = passageMapper.selectById(passageId);
        passage.setDistributed(1);
        passageMapper.updateById(passage);
        reviewService.save(new Review(reviewerId1, passageId));
        noticeService.save(new Notice(reviewerId1, "您有新的待审阅文章，文章Id为+"+passageId+"，标题为《"+passage.getTitle()+"》."));
        reviewService.save(new Review(reviewerId2, passageId));
        noticeService.save(new Notice(reviewerId2, "您有新的待审阅文章，文章Id为+"+passageId+"，标题为《"+passage.getTitle()+"》."));
        reviewService.save(new Review(reviewerId3, passageId));
        noticeService.save(new Notice(reviewerId3, "您有新的待审阅文章，文章Id为+"+passageId+"，标题为《"+passage.getTitle()+"》."));

        return Result.succeed(200, "分配成功", null);
    }
}
