package com.find.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_friend")
public class Friend implements Serializable {
    private Integer id;

    @NotNull(message = "myId不能为空")
    private Integer myId;

    @NotNull(message = "friendId不能为空")
    private Integer friendId;

    private Date gmtCreated;

    private Date gmtModified;

}
