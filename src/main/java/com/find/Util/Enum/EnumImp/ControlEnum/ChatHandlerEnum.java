package com.find.Util.Enum.EnumImp.ControlEnum;

public enum ChatHandlerEnum{

    CREATE_CONNECTION(0,"创建连接"),
    SAVE_MESSAGE(1,"保存信息"),
    SIGN_MESSAGE(2,"签收信息"),
    HEART_TEST(3,"心跳测试");

    public final Integer code;
    public final String msg;

    ChatHandlerEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }


    public int code() {
        return this.code;
    }


    public String msg() {
        return this.msg;
    }

    public static ChatHandlerEnum valueOf(int code) {
        for (ChatHandlerEnum chatHandlerEnum : values()) {
            if (chatHandlerEnum.code == code) {
                return chatHandlerEnum;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
