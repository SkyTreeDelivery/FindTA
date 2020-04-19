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
@TableName("chat_friend")
public class Friend implements Serializable {
    private Integer id;

    private Integer myId;

    private Integer friendId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

}
