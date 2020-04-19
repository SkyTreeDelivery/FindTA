package com.find.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.find.Util.TypeConverter.GeoCodc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocDTO implements Serializable {

    @NotNull(message = "查询点坐标不能为空")
    @JSONField(serializeUsing = GeoCodc.class)
    private Point centerPoint;

    @NotNull(message = "查询的用户数量不能为空")
    @Min(value = 0,message = "查询的用户数量不能小于0")
    private Integer userCount;

    @NotNull(message = "搜索半径不能为空")
    @Min(value = 0,message = "搜索半径不能小于0")
    private Double redius;
}
