package com.find.Util.Enum.EnumImp.ControlEnum;

public enum FriendRequestHandleEnum {
    REQUEST_FRIEND(0,"接收朋友"),
    REJECT_FRIEND(1,"拒绝朋友");

    public final Integer code;
    public final String msg;

    FriendRequestHandleEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public static FriendRequestHandleEnum valueOf(int code) {
        for (FriendRequestHandleEnum requestHandleEnum : values()) {
            if (requestHandleEnum.code == code) {
                return requestHandleEnum;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
