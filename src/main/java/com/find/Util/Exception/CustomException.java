package com.find.Util.Exception;

import com.find.Util.Enum.EnumImp.CustomErrorCodeEnum;

public class CustomException extends Exception {

    private CustomErrorCodeEnum costomErrorCodeEnum;

    private String msg = null;

    public CustomException(CustomErrorCodeEnum iCostomErrorCodeEnum){
        costomErrorCodeEnum = iCostomErrorCodeEnum;
    }

    public CustomException(CustomErrorCodeEnum iCostomErrorCodeEnum,String msg){
        costomErrorCodeEnum = iCostomErrorCodeEnum;
        this.msg = msg;
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

    public String getMsg() {
        return msg;
    }
}
