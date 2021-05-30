package com.publishing.common.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class SubmitArticleDto implements Serializable {
    @NotBlank(message = "标题不能为空")
    @Size(max = 255, message = "标题长度错误")
    private String title;

    @NotBlank(message = "作者不能为空")
    @Size(max = 255, message = "作者名字长度错误")
    private String author;

    private String[] tag;

    @NotBlank(message = "简介不能为空")
    @Size(max = 255, message = "简介过长")
    private String info;

    private MultipartFile file;
}
