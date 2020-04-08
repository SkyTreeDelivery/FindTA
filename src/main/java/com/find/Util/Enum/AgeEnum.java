package com.find.Util.Enum;

public enum AgeEnum {
    MAN(0,"男性"),
    WOMAN(1,"女性");

    public final Integer code;
    public final String msg;

    AgeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
