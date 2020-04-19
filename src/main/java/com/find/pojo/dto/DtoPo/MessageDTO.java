package com.find.pojo.dto.DtoPo;

import com.alibaba.fastjson.annotation.JSONField;
import com.find.Util.TypeConverter.GeoCodc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {
    @NotNull(message = "sendUserId不能为空")
    private Integer sendUserId;

    @NotNull(message = "acceptUserId不能为空")
    private Integer acceptUserId;

    @NotNull(message = "message不能为空")
    private String message;

    @JSONField(serializeUsing = GeoCodc.class)
    private Point sendUserLoc;

    @JSONField(serializeUsing = GeoCodc.class)
    private Point acceptUserLoc;
}
