package com.find.TypeHandler;

import com.find.Util.Geometry.Geometry;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgis.PGgeometry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
public abstract class AbstractGeometryTypeHandler<T extends Geometry> extends BaseTypeHandler<T> {

    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        PGgeometry geometry = new PGgeometry();
        geometry.setGeometry(parameter.getGeometry());
        ps.setObject(i, geometry);
    }
}
