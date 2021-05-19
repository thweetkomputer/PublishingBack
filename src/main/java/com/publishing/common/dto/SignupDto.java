package com.publishing.common.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class SignupDto implements Serializable {
    @NotBlank(message = "用户名不能为空")
    @Size(max = 15, message = "用户名最大15位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(message = "密码为6-18位", min = 6, max = 18)
    private String pass;

    @NotBlank(message = "验证密码不能为空")
    @Size(message = "验证密码为6-18位", min = 6, max = 18)
    private String checkPass;

    @Email(message = "邮箱格式不正确")
    @Pattern(regexp = ".+@.+\\.+.+", message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    @Size(message = "邮箱过长", max = 250)
    private String email;

    @NotBlank(message = "验证码不能为空")
    @Size(message = "请输入4位验证码", min = 4, max = 4)
    private String checkCode;

}
