package com.find.Util.Enum.EnumImp;

public enum CustomErrorCodeEnum {

    /* 参数错误：10001-19999 */
//    PARAM_IS_INVALID(10001, "参数无效"),
//    PARAM_IS_BLANK(10002, "参数为空"),
//    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
//    PARAM_NOT_COMPLETE(10004, "参数缺失"),
    PARAM_VERIFY_ERROR(10005, "参数未通过验证，缺失、为空或无效，请检查入参"),
    PARAM_NOT_TOKEN(10006,"请求参数未提供token，请追加token参数"),
    PARAM_INVALID_TOKEN(10007,"token无效，请使用正确的已注册的token"),

    GEOJSON_CANT_PARSE(11002,"无法解析为合法的geojson类型"),
    GEOJSON_TYPE_NOT_FIX(11001,"geojson的类型不符"),

    /* 用户错误：20001-29999*/
//    USER_NOT_LOGGED_IN(20001, "用户未登录"),
//    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),
    USER_NOT_LOGIN(20006, "用户尚未登录"),

    /* 业务错误：30001-39999 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "某业务出现问题"),
    FRIEND_CANT_ADD_SELF(30002,"不能添加自己为好友"),
    FRIEND_IS_EXIST(30003,"好友已经添加，不能重复添加"),
    FRIEND_REQUEST_NOT_EXIST(30004,"该好友请求不存在"),
    MESSAGE_IS_BLANK(30005,"信息不能为空"),
    NO_FRINEND_RELATION(30006,"不存在好友关系"),
    NO_MESSAGE(30007,"消息不存在"),
    NO_USER_TAG(30008,"用户标签不存在"),
    FACE_IMAGE_UPLOAD_ERROR(30009,"用户照片上传失败"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),

    /* 数据错误：50001-599999 */
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),
    DATA_NOT_MATCHING(50004,"数据不匹配"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限"),

    /* sql错误 : 80001-89999*/
    DATABASE_GEO_COLUMN_TO_PO_FALSE(80001, "数据库到po过程中，geometry字段转换失败");

    public final Integer code;
    public final String msg;

    CustomErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int code() {
        return this.code;
    }


    public String msg() {
        return this.msg;
    }

    public static CustomErrorCodeEnum valueOf(int code) {
        for (CustomErrorCodeEnum customErrorCodeEnum : values()) {
            if (customErrorCodeEnum.code == code) {
                return customErrorCodeEnum;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
