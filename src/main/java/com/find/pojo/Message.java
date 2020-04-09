package com.find.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.find.Util.Annonation.GeoField;
import com.find.Util.Geometry.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_message")
public class Message implements Serializable {
    private Integer id;

    @NotNull(message = "sendUserId不能为空")
    private Integer sendUserId;

    @NotNull(message = "acceptUserId不能为空")
    private Integer acceptUserId;

    @NotNull(message = "message不能为空")
    private String message;

    private Integer signFlag;

    @GeoField
    private Point sendUserLoc;

    private Point acceptUserLoc;

    private Date gmtCreated;

    private Date gmtModified;
}
