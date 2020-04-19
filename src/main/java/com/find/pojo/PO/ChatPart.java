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
@TableName("chat_chat_part")
public class ChatPart implements Serializable {
    private Integer id;

    private Integer msgSum;

    private String userPart;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;
}
