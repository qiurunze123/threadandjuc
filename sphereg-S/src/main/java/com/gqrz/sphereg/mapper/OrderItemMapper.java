package com.gqrz.sphereg.mapper;

import com.gqrz.sphereg.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface OrderItemMapper {

    @Options(useGeneratedKeys = true,keyProperty = "orderItemId",keyColumn = "order_item_id")
    @Insert("INSERT INTO t_order_item (order_id, user_id, status) VALUES (#{orderId,jdbcType=INTEGER}," +
            " #{userId,jdbcType=INTEGER}, #{status,jdbcType=VARCHAR})")
    long insert(OrderItem order) throws SQLException;

    @Delete("DELETE FROM t_order_item WHERE order_id = #{orderId}")
    void delete(long orderId) throws SQLException;

    /**
     * 查询所有数据
     * @return
     * @throws SQLException
     */
    @Select("SELECT i.* FROM t_order o, t_order_item i WHERE o.order_id = i.order_id")
    List<OrderItem> selectAll() throws SQLException;

    /**
     * 绑定表,避免无笛卡尔积查询现象，
     * @param start
     * @param end
     * @return
     * @throws SQLException
     */
    @Select("SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.order_id = 0 or o.order_id = 1")
    List<OrderItem> selectWithInCondition(@Param("start") long start, @Param("end") long end) throws SQLException;

    /**
     * 不支持该路由规则
     */
    @Select("SELECT i.* FROM t_order o, t_order_item i WHERE o.order_id = i.order_id" +
            " AND o.user_id BETWEEN #{start} AND #{end}")
    List<OrderItem> selectRange(@Param("start") int start, @Param("end") int end) throws SQLException;

    @Update("update t_order_item set status = #{status} where order_id = #{orderId}")
    int update(@Param("orderId") long orderId, @Param("status") String status) throws SQLException;
}
