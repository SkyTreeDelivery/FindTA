package com.find.pojo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

public class MessageDTO implements Serializable {

    @NotNull(message = "信息发送者id不能为空")
    private Integer sendUserId;

    @NotNull(message = "信息接受者信息不能为空")
    private Integer acceptUserId;

    @NotNull(message = "查询的信息条数不能为空")
    private Integer messageCount;

    @NotNull(message = "最新被查询的时间不能为空")
    @Past(message = "请提供合法的查询时间")
    private Date endTime;
}
