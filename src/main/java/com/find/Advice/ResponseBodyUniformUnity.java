package com.find.Advice;

import com.alibaba.fastjson.JSON;
import com.find.Util.JsonFilter.GeoFieldSerilizeFilter;
import com.find.Util.HttpResultBuiler;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *  此类的作用是同一处理controller接口的返回值，只要直接返回data，就会自动封装为HttpResult的json格式
 *  同时处理异常，对于异常，由于已经在全局异常类（CustomExceptionHandler）里处理过了，所以不需要再次封装
 */
@ControllerAdvice
public class ResponseBodyUniformUnity implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //如果已经是HttpResult类型，说明已经封装过了，也就是是一个异常的封装
        if(o instanceof HttpResultBuiler.HttpResult){
            return o;
        }else if(o != null){
            return HttpResultBuiler.ok(JSON.toJSONString(o, new GeoFieldSerilizeFilter()));
        }else{
            return HttpResultBuiler.error500();
        }
    }
}
