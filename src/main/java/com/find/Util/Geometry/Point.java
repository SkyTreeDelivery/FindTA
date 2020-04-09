package com.find.Util.Geometry;

import java.sql.SQLException;

public class Point extends Geometry<org.postgis.Point> {
    public Point() {
        super.geometry = new org.postgis.Point();
    }

    public Point(double x, double y, double z) {
        super.geometry = new org.postgis.Point(x, y, z);
    }

    public Point(double x, double y) {
        super.geometry = new org.postgis.Point(x, y);
    }

    public Point(String value){
        try {
            super.geometry = new org.postgis.Point(value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
