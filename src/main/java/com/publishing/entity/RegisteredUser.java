package com.publishing.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("registeredUser")
@NoArgsConstructor
public class RegisteredUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String username;


    @Size(max = 18, min = 6, message = "密码长度错误")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Size(max = 11, min = 11, message = "号码长度错误")
    private String phone;

    private Integer isWriter;

    private Integer isVip;

    private Integer isEdit;

    private Integer isReviewer;

    private LocalDateTime lastLogin;

    private Integer status;

    private String description;

    private LocalDate birth;

    private Integer gender;

    private String avatar;


    public RegisteredUser(String username, String password, String email, int i) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = i;
    }
}
