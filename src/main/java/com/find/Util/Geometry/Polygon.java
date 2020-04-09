package com.find.Util.Geometry;

import org.postgis.LinearRing;

import java.sql.SQLException;

public class Polygon  extends Geometry <org.postgis.Polygon> {
    public Polygon() {
         super.geometry = new org.postgis.Polygon();
    }

    public Polygon(LinearRing[] rings) {

        super.geometry = new org.postgis.Polygon(rings);
    }

    public Polygon(String value) throws SQLException {
        super.geometry = new org.postgis.Polygon(value);
    }

    public Polygon(String value, boolean haveM){
        try {
            super.geometry = new org.postgis.Polygon(value,haveM);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
