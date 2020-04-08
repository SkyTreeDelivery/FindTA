package com.find.Util.Enum;

public enum ChatHandlerEnum {

    CREATE_CONNECTION(1,"创建连接"),
    SAVE_MESSAGE(2,"保存信息"),
    SIGN_MESSAGE(3,"签收信息"),
    HEART_TEST(4,"心跳测试");

    public final Integer code;
    public final String msg;

    ChatHandlerEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
