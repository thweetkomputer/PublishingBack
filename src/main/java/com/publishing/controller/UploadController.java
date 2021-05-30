package com.publishing.controller;

import com.publishing.common.dto.SubmitArticleDto;
import com.publishing.common.lang.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/file")
public class UploadController {

    @Value("${storage.pathname}")
    private String pathname;

    @RequestMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file, @RequestParam("filename") String filename) throws IOException {
        Map<String, Object> map = new HashMap<>();
        if (file != null) {  //如果获取到的文件不为空

            assert filename != null;
            File file_server = new File(pathname, filename);  //创建文件对象
            if (!file_server.getParentFile().exists()) {
                //如果文件父目录不存在，就创建这样一个目录
                file_server.getParentFile().mkdirs();
                System.out.println("创建目录" + file);
            }
            file.transferTo(file_server);
            map.put("status", true);
            map.put("msg", "上传文件成功");
        } else {   //如果获取到的文件为空
            map.put("status", false);
            map.put("msg", "上传文件失败");
            map.put("reason", "文件为空");
        }
        return map;
    }

    @PostMapping("/submitArticle")
    public Result submitArticle(@Validated SubmitArticleDto dto) throws IOException {
        System.out.println(dto.getAuthor());
        System.out.println(dto.getInfo());
        System.out.println(Arrays.toString(dto.getTag()));
        System.out.println(dto.getTitle());
        upload(dto.getFile(), dto.getTitle());
        return Result.succeed(200, "上传成功", null);
    }
}
