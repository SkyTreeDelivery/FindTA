package com.find.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.find.Util.TypeConverter.GeoCodc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchVO {
    private Integer id;
    private Integer age;
    private boolean gender;
    private String face_image;
    private String pickname;
    @JSONField(serializeUsing = GeoCodc.class)
    private Point location;
}
