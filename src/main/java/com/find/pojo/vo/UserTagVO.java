package com.find.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTagVO {
    private Integer userTagId;
    private Integer groupId;
    private String tagName;
}
