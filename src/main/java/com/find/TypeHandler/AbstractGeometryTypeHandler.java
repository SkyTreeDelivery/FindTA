package com.find.TypeHandler;

import com.find.Util.Utils.JsonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.locationtech.jts.geom.Geometry;
import org.postgis.PGgeometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public abstract class AbstractGeometryTypeHandler<T extends Geometry> extends BaseTypeHandler<T> {

    private static final Logger logger = LoggerFactory.getLogger(LineStringTypeHandler.class);

    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        PGgeometry geometry = new PGgeometry();
        geometry.setGeometry(JsonUtils.geomToPostGisGeom(parameter));
        ps.setObject(i, geometry);
    }

    @Override
    public T getNullableResult(ResultSet resultSet, String columnName)  {
        try {
            PGgeometry pGgeometry = (PGgeometry)resultSet.getObject(columnName);
            return pGgeometry == null? null : (T) JsonUtils.postGisGeomToGeom(pGgeometry.getGeometry());
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int i) {
        try {
            PGgeometry pGgeometry = (PGgeometry)resultSet.getObject(i);
            return pGgeometry == null? null : (T) JsonUtils.postGisGeomToGeom(pGgeometry.getGeometry());
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int i) {
        try {
            PGgeometry pGgeometry = (PGgeometry)callableStatement.getObject(i);
            return pGgeometry == null? null : (T) JsonUtils.postGisGeomToGeom(pGgeometry.getGeometry());
        }catch (Exception e){
            return null;
        }
    }

}
