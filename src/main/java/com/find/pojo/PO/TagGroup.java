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
@TableName("chat_tag_group")
public class TagGroup implements Serializable {

    private Integer id;

    private String tagGroupName;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;
}
