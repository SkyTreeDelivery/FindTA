package com.find.Util.Enum.EnumImp.POJOEnum;

public enum SignStatusEnum {


    UNSIGN(0,"未签收"),
    SIGNED(1,"已签收");

    public final Integer code;
    public final String msg;

    SignStatusEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public static SignStatusEnum valueOf(int code) {
        for (SignStatusEnum signEnum : values()) {
            if (signEnum.code == code) {
                return signEnum;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
