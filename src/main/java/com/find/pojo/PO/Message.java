package com.find.pojo.PO;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.find.Util.TypeConverter.GeoCodc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("chat_message")
public class Message implements Serializable {
    private Integer id;

    private Integer sendUserId;

    private Integer acceptUserId;

    private String message;

    private Integer signFlag;

    @JSONField(serializeUsing = GeoCodc.class)
    private Point sendUserLoc;

    @JSONField(serializeUsing = GeoCodc.class)
    private Point acceptUserLoc;

    Integer partId;

    Integer msgIndex;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;
}
