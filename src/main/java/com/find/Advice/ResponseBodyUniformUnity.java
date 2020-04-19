package com.find.Advice;

import com.alibaba.fastjson.JSON;
import com.find.Util.Enum.EnumImp.HttpResponseEnum;
import com.find.Util.HttpResultBuiler;
import com.find.Util.Pojo.SpringMVCDefaultMessage;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;

/**
 * 此类的作用是同一处理controller接口的返回值，只要直接返回data，就会自动封装为HttpResult的json格式
 * 同时处理异常，对于异常，由于已经在全局异常类（CustomExceptionHandler）里处理过了，所以不需要再次封装
 */
@ControllerAdvice
public class ResponseBodyUniformUnity implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o != null) {
            //如果已经是HttpResult类型，就直接返回
            if (o instanceof HttpResultBuiler.HttpResult) {
                return o;
            } else if (o instanceof Boolean) {
                Boolean isOk = (Boolean) o;
                return isOk ? HttpResultBuiler.ok(o) : HttpResultBuiler.error500();
            } else if (o instanceof LinkedHashMap) {
                try {
//                    springmvc使用LinkedHashMap封装错误信息，将其转换为HttpResult对象返回
                    String s = JSON.toJSONString(o);
                    SpringMVCDefaultMessage err = JSON.parseObject(s, SpringMVCDefaultMessage.class);
                    Integer code = err.getStatus();
                    HttpResponseEnum httpResponseEnum = HttpResponseEnum.valueOf(code);
                    return HttpResultBuiler.error(httpResponseEnum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return HttpResultBuiler.ok(o);
        } else {
            return HttpResultBuiler.error500();
        }
    }
}
