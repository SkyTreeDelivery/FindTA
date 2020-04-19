package com.find.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {

    @NotNull(message = "信息发送者id不能为空")
    private Integer sendUserId;

    @NotNull(message = "信息接受者信息不能为空")
    private Integer acceptUserId;

    @NotNull(message = "查询的信息条数不能为空")
    @Max(value = 50,message = "最大不能超过50条数据")
    private Integer messageCount;

    @NotNull(message = "最新被查询的时间不能为空")
    private LocalDateTime endTime;
}
