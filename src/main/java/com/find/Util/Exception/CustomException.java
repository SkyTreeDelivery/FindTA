package com.find.Util.Exception;

import com.find.Util.Enum.EnumImp.CustomErrorCodeEnum;

public class CustomException extends Exception {

//    默认的错误信息
    private CustomErrorCodeEnum costomErrorCodeEnum;

//    携带的额外信息
    private String extraMessage = null;

    public CustomException(CustomErrorCodeEnum iCostomErrorCodeEnum){
//        将getMessage()方法的返回值与自定义异常信息同步
        super("errorCode :" + iCostomErrorCodeEnum.code + ";\n" +
                "errorMessage" + ":" + iCostomErrorCodeEnum.msg);
        costomErrorCodeEnum = iCostomErrorCodeEnum;
    }

    public CustomException(CustomErrorCodeEnum costomErrorCodeEnum,String extraMessage){
//        将getMessage()方法的返回值与自定义异常信息同步
        super("errorCode : " + costomErrorCodeEnum.code + ";\n" +
                "errorMessage" + " : " + costomErrorCodeEnum.msg + " --- " + extraMessage);
        this.costomErrorCodeEnum = costomErrorCodeEnum;
        this.extraMessage = extraMessage;
    }

    public CustomErrorCodeEnum getCostomErrorCodeEnum() {
        return costomErrorCodeEnum;
    }

    public int getExceptionCode(){
        return costomErrorCodeEnum.code;
    }

    public String getExceptionMessage(){
        return costomErrorCodeEnum.msg;
    }

    public String getExtraMessage() {
        return extraMessage;
    }
}
