package com.find.Util.Geometry;

import org.postgis.Point;

import java.sql.SQLException;

public class LineString extends Geometry<org.postgis.LineString>  {
    public LineString() {
        super.geometry = new org.postgis.LineString();
    }

    public LineString(Point[] points) {
        super.geometry = new org.postgis.LineString(points);
    }

    public LineString(String value) throws SQLException {
        super.geometry = new org.postgis.LineString(value);
    }

    public LineString(String value, boolean haveM){
        try {
            super.geometry = new org.postgis.LineString(value,haveM);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
