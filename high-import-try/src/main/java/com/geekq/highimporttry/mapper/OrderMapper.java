package com.geekq.highimporttry.mapper;

import com.geekq.highimporttry.entity.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long order_id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long order_id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> getOrders();
}