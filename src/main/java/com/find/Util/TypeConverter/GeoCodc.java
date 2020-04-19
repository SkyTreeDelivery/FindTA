package com.find.Util.TypeConverter;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.find.Util.Utils.JsonUtils;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;
import java.lang.reflect.Type;

public class GeoCodc implements ObjectSerializer, ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String geoJson = parser.lexer.stringVal();
        return (T) JsonUtils.geoJsonToGeom(geoJson);
    }

    @Override
    public int getFastMatchToken() {

        return 0;
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        System.out.println(object);
        if(object == null){
            serializer.writeNull();
        }else{
            if(object instanceof Geometry){
                Geometry geometry = (Geometry)object;
                serializer.write(JsonUtils.geomToGeoJson(geometry));
                return;
            }else{
                serializer.write(object);
            }
        }
    }
}
