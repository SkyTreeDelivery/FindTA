package com.find.Util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.find.Util.Enum.EnumImp.HttpResponseEnum;
import com.find.Util.Exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Description : 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理
 *
 */
public class HttpResultBuiler {

    /*=============================== http官方定义的响应处理 ===================================*/
    /*-------------------------------- ok ----------------------------------------------*/
    public static <T>  HttpResult<T> ok(){
        return new HttpResult<T>(HttpResponseEnum.OK.code,
                HttpResponseEnum.OK.reasonPhraseCN, null);
    }

    public static <T>  HttpResult<T> ok(T data){
        return new HttpResult<T>(HttpResponseEnum.OK.code,
                HttpResponseEnum.OK.reasonPhraseCN, data);
    }

    public static <T> HttpResult<T> ok(String msg, T data){
        return new HttpResult<T>(HttpResponseEnum.OK.code, msg, data);
    }

    /*-------------------------------- error404 ----------------------------------------------*/
    public static <T> HttpResult<T> error404(){
        return new HttpResult<T>(HttpResponseEnum.NOT_FOUND.code, HttpResponseEnum.NOT_FOUND.msg, null);
    }

    /*-------------------------------- error500 ----------------------------------------------*/

    public static <T> HttpResult<T> error500(){
        return new HttpResult<T>(HttpResponseEnum.INTERNAL_SERVER_ERROR.code,
                HttpResponseEnum.INTERNAL_SERVER_ERROR.reasonPhraseCN, null);
    }

    public static <T> HttpResult<T> error500(String msg){
        return new HttpResult<T>(HttpResponseEnum.INTERNAL_SERVER_ERROR.code,
                msg, null);
    }

    public static <T> HttpResult<T> error500(String msg, T data){
        return new HttpResult<T>(HttpResponseEnum.INTERNAL_SERVER_ERROR.code, msg, data);
    }

    /*-------------------------------- error ----------------------------------------------*/

    public static <T> HttpResult<T> error(HttpResponseEnum status){
        return new HttpResult<T>(status.code, status.reasonPhraseCN, null);
    }

    public static <T> HttpResult<T> error(HttpResponseEnum status, String msg){
        return new HttpResult<T>(status.code, msg, null);
    }

    /*=============================== 自定义异常处理 ===================================*/
    /*-------------------------------- exception ----------------------------------------------*/

    /**
     *  处理自定义异常，转换为HttpResult，默认响应代码500
     * @param ex
     * @param <T>
     * @return
     */
    public static <T> HttpResult<T> exception(CustomException ex){
        return new HttpResult<T>(500,ex.getMessage(), null);
    }


    /*=============================== HttpResult定义 ===================================*/

    /**
     * http返回的json格式的结果，为响应的主体结构
     * @param <T>
     */
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    public static class HttpResult<T> implements Serializable {
        @JSONField(ordinal = 0)
        Integer status;
        @JSONField(ordinal = 1)
        String msg;
        @JSONField(ordinal = 2)
        T data;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }
}
