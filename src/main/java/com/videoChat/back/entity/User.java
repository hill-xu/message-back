package com.videoChat.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author hill
 * @since 2023-05-30
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface login {}

    public interface register {}

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotNull(groups = {login.class, register.class})
    @NotEmpty(groups = {login.class, register.class})
    private String username;

    @TableField(select = false)
    @NotNull(groups = {login.class, register.class})
    @NotEmpty(groups = {login.class, register.class})
    private String password;

    @NotNull(groups = {register.class})
    @NotEmpty(groups = {register.class})
    private String motto;

//    @NotNull(groups = {register.class})
//    @NotEmpty(groups = {register.class})
    private String headSculpture;

    @NotNull(groups = {register.class})
//    @NotEmpty(groups = {register.class})
    private int age;

    @NotNull(groups = {register.class})
//    @NotEmpty(groups = {register.class})
    private int sex;

    @NotNull(groups = {register.class})
    @NotEmpty(groups = {register.class})
    @Email
    private String email;
}
