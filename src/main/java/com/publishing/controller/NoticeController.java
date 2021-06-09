package com.publishing.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.publishing.common.lang.Result;
import com.publishing.entity.Notice;
import com.publishing.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-13
 */
@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/getNotice")
    public Result getNotice(@RequestParam("page") int page,
                            @RequestParam("pageSize") int pageSize,
                            @RequestParam("user_id") Long userId) {
        List<Notice> noticeList = noticeService.list(new QueryWrapper<Notice>().eq("receiver_id", userId));
        Collections.reverse(noticeList);
        int startPage = (page - 1) * pageSize;
        return Result.succeed(MapUtil.builder()
                .put("notice_list", noticeList.subList(startPage, Math.min(startPage + pageSize, noticeList.size())))
                .put("total_num", noticeList.size())
                .map());
    }

    @RequestMapping("/readNotice")
    public Result readNotice(@RequestParam("notice_id") Long noticeId) {
        Notice notice = noticeService.getById(noticeId);
        notice.setHasRead(1);
        noticeService.updateById(notice);
        return Result.succeed(null);
    }
}
