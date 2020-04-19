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
@TableName("chat_friendrequest")
public class FriendRequest implements Serializable {
    private Integer id;

    private Integer sendUserId;

    private Integer acceptUserId;

    private String requestMessage;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

}
