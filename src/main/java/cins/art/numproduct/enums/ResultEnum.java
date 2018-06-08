package cins.art.numproduct.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PARAM_ERROR(0,"参数错误"),
    NOT_EXIST(1,"用户不存在"),
    OPERATE_ERROR(2,"操作错误"),
    ADDRESS_WRONG(3,"邮箱地址不是有效地址"),
    PASSWORD_WRONG(4,"密码错误"),
    EMAIL_REGISTED(5,"邮箱已被注册"),
    SEND_EMAIL_FAIL(6,"邮件发送失败"),
    CREATE_USER_ADDRESS_FAIL(7,"系统错误,生成用户唯一标识失败"),
    SYSTEM_ERROR(500,"系统错误"),
    AUTHENTICATION_REQUIRE(401,"访问的服务需要身份认证,请引导用户到登录页"),
    PIC_UPLOAD_FILE_ERROR(9,"上传图片失败"),
    VIDEO_UPLOAD_FILE_ERROR(10,"上传视频文件失败"),
    ADD_THEME_ERROR(11,"添加主题数据失败"),
    NO_SUCH_CATEGORY(12,"没有对应的分类"),
    NO_SUCH_PRODUCT(13,"没有对应条件的作品"),
    PRODUCT_NOT_EXIST(14,"商品不存在"),
    OVER_PERMISSION(15,"越权操作"),
    CHANGE_INFO_FAIL(16,"修改信息失败"),
    DB_NOT_ENOUGH(17,"库存数据不足"),
    ADD_USER_FAIL(18,"系统错误,用户添加失败"),
    UPDATE_PRODUCT_INFO_ERROR(19,"更新作品信息失败"),
    UPLOAD_THEME_FAIL(20,"上传主题文件失败"),
    UPLOAD_FAIL(21,"上传失败"),
    FLUSH_CAT_ERROR(22,"清空购物车失败"),
    ADD_PRODUCT_TO_CAT_ERROR(23,"添加购物车失败"),
    USER_CAT_IS_NULL(24,"购物车为空"),
    MOVE_OUT_CAT_ERROR(25,"移除购物车商品失败"),
    PRODUCT_HAD_IN_CAT(26,"该商品已在购物车中存在"),
    PRODUCT_DEAL_ERROR(27,"系统内部错误,交易失败"),
    PRODUCT_IS_NOT_SELLING(28,"商品是非卖品"),
    KEY_REPEAT(29,"主键重复"),
    CAN_NOT_ADD_TO_CAT(29,"商品已拥有,不能添加到购物车"),
    CAT_DETAIL_NOT_EXIST(30,"购物车内容信息不存在"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
