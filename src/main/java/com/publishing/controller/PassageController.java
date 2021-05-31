package com.publishing.controller;


import com.publishing.common.lang.Result;
import com.publishing.service.PassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Value("${storage.pathname}")
    private String pathname;

    @RequestMapping("/getPassage")
    public Result getPassage() throws IOException {
        File file = new File(pathname + "xuqiuguigeshuomingshu.pdf");
        FileInputStream inputStream = new FileInputStream(file);
//        return Result.succeed(201, "", JSONUtil.(new MockMultipartFile(file.getName(), file.getName(), "multipart/form-data", inputStream).getBytes()));
        return null;
    }
}
