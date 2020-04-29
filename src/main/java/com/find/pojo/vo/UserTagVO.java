package com.find.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTagVO implements Serializable {
    @JSONField(name = "userTagId")
    private Integer id;
    private Integer tagId;

    private String tagContext;
    @JSONField(name = "tagGroupId")
    private Integer tgId;
}
