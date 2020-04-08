package com.find.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_friendrequest")
public class FriendRequest implements Serializable {
    private Integer id;

    @NotNull(message = "发送者Id不能为空")
    private Integer sendUserId;

    @NotNull(message = "接受者Id不能为空")
    private Integer acceptUserId;

    @NotNull(message = "请求信息不能为空")
    @Size(max = 50, message = "好友请求说明不超过50个字符")
    private String requestMessage;

    private Date gmtCreated;

    private Date gmt_modified;

}
