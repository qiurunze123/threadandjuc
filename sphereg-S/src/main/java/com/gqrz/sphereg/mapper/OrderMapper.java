package com.gqrz.sphereg.mapper;

import com.gqrz.sphereg.entity.Order;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ：图灵-杨过
 * @date：2019/11/11
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description :
 */
@Mapper
public interface OrderMapper {

    @Options(useGeneratedKeys = true,keyProperty = "orderId",keyColumn = "order_id")
    @Insert("INSERT INTO t_order (user_id, address_id, status) VALUES (#{userId,jdbcType=INTEGER}," +
            " #{addressId,jdbcType=BIGINT}, #{status,jdbcType=VARCHAR})")
    long insert(Order order) throws SQLException;

    @Delete("DELETE FROM t_order WHERE order_id = #{orderId}")
    void delete(long orderId) throws SQLException;

    @Select("SELECT * FROM t_order")
    List<Order> selectAll() throws SQLException;

    @Select("SELECT * FROM t_order WHERE order_id BETWEEN #{start} AND #{end}")
    List<Order> selectRange(@Param("start") long start, @Param("end") long end) throws SQLException;

    @Select("SELECT * FROM t_order order by order_id limit #{offset},#{size}")
    List<Order> selectPageList(@Param("offset") long offset, @Param("size") long size) throws SQLException;

    @Update("update t_order set status = #{status} where order_id = #{orderId}")
    int update(@Param("orderId") long orderId, @Param("status") String status) throws SQLException;
}
