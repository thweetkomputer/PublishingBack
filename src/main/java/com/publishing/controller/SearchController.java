package com.publishing.controller;

import cn.hutool.core.map.MapUtil;
import com.publishing.common.lang.Result;
import com.publishing.mapper.PassageMapper;
import com.publishing.service.PassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

    @Autowired
    private PassageMapper passageMapper;

    @RequestMapping("/newPassages")
    public Result newPassages(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        System.out.println(page + " " + pageSize);
        int startPage = (page - 1) * pageSize;
//        int endPage = page * pageSize;
        return Result.succeed(201, "", MapUtil.builder()
                .put("article_list", passageMapper.selectByPage(startPage, pageSize))
                .put("total_num", passageMapper.selectCount())
                .map());
    }

    @RequestMapping("/countPassages")
    public Result countPassages() {
        return Result.succeed(201, "", passageMapper.selectCount());
    }
}
