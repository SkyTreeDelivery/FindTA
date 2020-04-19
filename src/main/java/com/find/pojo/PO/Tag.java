package com.find.pojo.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_tag")
public class Tag implements Serializable {
    private Integer id;

    private Integer tgId;

    private String tagContext;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;
}
