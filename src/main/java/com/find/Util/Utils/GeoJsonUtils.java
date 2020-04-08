package com.find.Util.Utils;

import com.alibaba.fastjson.JSONObject;
import com.find.Util.Exception.CustomException;

public class GeoJsonUtils {

    public static Boolean isPoint(String jsonStr) throws CustomException {
        if(StringUtils.isBlank(jsonStr)){
            return false;
        }
        try {
            JSONObject feature = JSONObject.parseObject(jsonStr);
            JSONObject geometry = feature.getJSONObject("geometry");
            String type = geometry.getObject("type", String.class);
            return type.equals("Point");
        }catch (Exception e){
            //发生异常说明json无法解析或解析的json对象不包含所需的属性
           return false;
        }
    }

}
