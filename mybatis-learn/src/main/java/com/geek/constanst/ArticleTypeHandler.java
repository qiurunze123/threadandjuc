package com.geek.constanst;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 邱润泽 bullock
 */

public class ArticleTypeHandler extends BaseTypeHandler<ArticleTypeEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    ArticleTypeEnum parameter, JdbcType jdbcType) throws SQLException {
// 获取枚举code值 并设置在 PreparedStatement 中
        ps.setInt(i, parameter.code());
    }

    @Override
    public ArticleTypeEnum getNullableResult(
            ResultSet rs, String columnName) throws SQLException {
// ResultSet 获取 code
        int code = rs.getInt(columnName);
// 解析 code 对应的枚举并返回
        return ArticleTypeEnum.find(code);
    }

    @Override
    public ArticleTypeEnum getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        int code = rs.getInt(columnIndex);
        return ArticleTypeEnum.find(code);
    }
    @Override
    public ArticleTypeEnum getNullableResult(CallableStatement cs,
                                             int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return ArticleTypeEnum.find(code);
    }
}
