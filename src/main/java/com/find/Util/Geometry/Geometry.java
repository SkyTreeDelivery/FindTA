package com.find.Util.Geometry;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.io.WKTReader;

import java.io.StringWriter;

/**
 *  对postgis的geometry做了一层封装,使用了代理设计模式
 *  1. 为了提供wkt转换为geojson的函数
 *  2. 为了更好地扩展，postgis的geometry对象有的方法就有其完成，扩展方法在自定义类中实现
 */
public  abstract class Geometry <T extends org.postgis.Geometry>{
    protected org.postgis.Geometry geometry;

    public String toGeoJson() {
        String json = null;
        WKTReader reader = new WKTReader();
        org.locationtech.jts.geom.Geometry geometry = null;
        try {
            geometry = reader.read(this.geometry.toString());
            StringWriter writer = new StringWriter();
            GeometryJSON g = new GeometryJSON();
            g.write(geometry, writer);
            json = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @JsonBackReference
    public T getGeometry(){
        return (T)this.geometry;
    }

    @JsonBackReference
    public void setGeometry(T geometry) {
        this.geometry = geometry;
    }

    /**
     * 主要是为了向前端传递时使用
     * @return
     */
    @Override
    public String toString() {
        return toGeoJson();
    }
}
