package com.find.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO  implements Serializable {
    @JSONField(name = "messageId",ordinal = 0)
    private Integer id;
    private Integer sendUserId;
    private Integer msgIndex;
    private String message;
    private LocalDateTime gmtCreated;
}
