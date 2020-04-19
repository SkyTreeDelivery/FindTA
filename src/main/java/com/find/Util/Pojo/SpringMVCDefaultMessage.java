package com.find.Util.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpringMVCDefaultMessage {
//    fastjson 对data的默认的序列化方式为时间戳
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
