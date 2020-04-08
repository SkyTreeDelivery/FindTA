package com.find.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO {
    private Integer id;
    private String text;
    private Date msgGreatedTime;
}
