package com.find.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO {
    @JSONField(name = "messageId")
    private Integer id;
    private Integer sendUserId;
    private String message;
    private LocalDateTime gmtCreated;
}
