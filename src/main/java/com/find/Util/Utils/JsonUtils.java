package com.find.Util.Utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.Date;

public class JsonUtils {

    public static String wktToGeoJson(String wkt){
        try {
            if(wkt != null){
                Geometry geom = wktToGeom(wkt);
                if(geom != null){
                    return geomToGeoJson(geom);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Geometry wktToGeom(String wkt){
        if(wkt != null){
            try {
                WKTReader reader = new WKTReader();
                return reader.read(wkt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            return null;
        }
    }

    public static String geomToGeoJson(Geometry geom){
        if(geom != null){
            StringWriter writer = new StringWriter();
            GeometryJSON g = new GeometryJSON();
            try {
                g.write(geom, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return writer.toString();
        }
        return null;
    }

    public static String geoJsonToWkt(String geoJson){
        if(geoJson != null){
            Geometry geometry = geoJsonToGeom(geoJson);
            if(geometry != null){
                return geomToWkt(geometry);
            }
        }
        return null;
    }


    public static Geometry geoJsonToGeom(String geoJson){
        if(geoJson != null){
            try {
                GeometryJSON gjson = new GeometryJSON();
                Reader reader = new StringReader(geoJson);
                return gjson.read(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    public static String geomToWkt(Geometry geometry){
        if(geometry != null){
            return geometry.toText();
        }
        return null;
    }


    public static org.postgis.Geometry geomToPostGisGeom(org.locationtech.jts.geom.Geometry geometry){
        if(geometry != null){
            String geometryType = geometry.getGeometryType();
            switch (geometryType){
                case "Point":
                    org.locationtech.jts.geom.Point oriPoint = (org.locationtech.jts.geom.Point)geometry;
                    org.postgis.Point point = new org.postgis.Point(oriPoint.getX(),oriPoint.getY());
                    return point;
            }
            return null;
        }else{
            return null;
        }
    }

    public static Geometry postGisGeomToGeom(org.postgis.Geometry postGisGeom){
        if(postGisGeom != null){
            StringBuffer wktBuffer = new StringBuffer();
            postGisGeom.outerWKT(wktBuffer);
            return wktToGeom(wktBuffer.toString());
        }else {
            return null;
        }
    }

    public static String allValueToStirng(Object object){
        SerializeWriter out = new SerializeWriter();
        try {
            //此处必须new一个SerializeConfig,防止修改默认的配置
            JSONSerializer serializer = new JSONSerializer(out, new SerializeConfig());
            serializer.getMapping().put(Date.class, new ObjectSerializer() {
                @Override
                public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
                    if(object == null){
                        serializer.writeNull();
                    }
                    serializer.write(object.toString());
                }
            });
            serializer.write(object);
            return out.toString();
        } finally {
            out.close();
        }
    }
}
