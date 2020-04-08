package com.find.Util;

import com.alibaba.fastjson.JSONObject;
import com.find.Util.Enum.CustomErrorCodeEnum;
import com.find.Util.Enum.HttpResponseEnum;

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

    public static <T> HttpResult<T> exception(CustomErrorCodeEnum status){
        return new HttpResult<T>(status.code, status.msg, null);
    }


    public static <T> HttpResult<T> exception(CustomErrorCodeEnum status, String msg){
        //msg是在调用时额外添加的信息
        return new HttpResult<T>(status.code, status.msg + " : " + msg,null);
    }


    /*=============================== HttpResult定义 ===================================*/

    /**
     * http返回的json格式的结果，为响应的主体结构
     * @param <T>
     */
    public static class HttpResult<T> {
        Integer code;
        String msg;
        T data;

        /**
         * httpresult构造器，只能通过HttpResultHelper的静态方法调用
         * @param code code码
         * @param msg 默认使用status的getReasonPhraseCN方法获取的信息
         * @param data 传输的数据
         */

        private HttpResult(Integer code, String msg, T data){
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }

        public Integer getStatus() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
        public T getData() {
            return data;
        }
    }
}
