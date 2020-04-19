package com.find.Util.Enum.EnumImp.POJOEnum;

public enum GenderEnum{
    MAN(0,"男性"),
    WOMAN(1,"女性");

    public final Integer code;
    public final String msg;

    GenderEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public static GenderEnum valueOf(int code) {
        for (GenderEnum genderEnum : values()) {
            if (genderEnum.code == code) {
                return genderEnum;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
