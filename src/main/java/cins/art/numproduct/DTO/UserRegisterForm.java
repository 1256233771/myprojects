package cins.art.numproduct.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 登录表单验证
 */
@Data
public class UserRegisterForm {

    @NotNull(message = "邮箱号必传")
    private String emailAddress;
    @NotNull(message = "密码必传")
    private String password;
    @NotNull(message = "验证码必传")
    private String code;
}
