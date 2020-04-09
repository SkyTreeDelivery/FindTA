package com.find.Util.JsonFilter;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.find.Util.Annonation.GeoField;

import java.lang.reflect.Field;

public class GeoFieldSerilizeFilter implements ValueFilter {
    @Override
    public Object process(Object obj, String name, Object value) {
        Boolean flag = false;
        try {
            Field field = obj.getClass().getDeclaredField(name);
            // 获取注解
            flag = field.getAnnotation(GeoField.class).value().equals("true");
            if (flag == true && value != null) {
                // 这里 其他类型转换成String类型
                value = value.toString();
            }
        } catch (NoSuchFieldException e) {
            return value;
        } catch (Exception e) {
            return value;
        }
        return value;
    }
}
