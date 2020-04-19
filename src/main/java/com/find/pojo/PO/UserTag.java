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
@TableName("chat_user_tag")
public class UserTag implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer tagId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;
}
