package com.gqrz.sphereg.service;

import com.gqrz.sphereg.mapper.OrderItemMapper;
import com.gqrz.sphereg.mapper.OrderMapper;
import com.gqrz.sphereg.dto.OrderInfoDto;
import com.gqrz.sphereg.entity.Order;
import com.gqrz.sphereg.entity.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Slf4j
@Service
public class OrderServiceImpl {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Transactional
    public long confirmOrder(int sequenceId){
        //创建订单
        Order order = new Order();
        order.setAddressId(sequenceId);
        order.setUserId(sequenceId);
        order.setStatus("创建订单");
        try {
            orderMapper.insert(order);
            //订单对应产品
            OrderItem item = new OrderItem();
            item.setOrderId(order.getOrderId());
            item.setUserId(sequenceId);
            item.setOrderItemId(sequenceId);
            item.setStatus("创建订单");
            orderItemMapper.insert(item);
        } catch (SQLException e) {
//            log.info(e.getMessage(),e.getCause());
            throw new RuntimeException("SQLException",e.getCause());
        }
        return order.getOrderId();
    }

    public OrderInfoDto selectAll(){
        try {
            return new OrderInfoDto(orderMapper.selectAll(),orderItemMapper.selectAll());
        } catch (SQLException e) {
//            log.info(e.getMessage(),e.getCause());
        }
        return null;
    }

    @Transactional
    public String deleteData(long orderId){
        try {
            orderMapper.delete(orderId);
            orderItemMapper.delete(orderId);
            return "delete data success";
        } catch (SQLException e) {
            log.info(e.getMessage(),e.getCause());
        }
        return "failure";
    }

    @Transactional
    public int updateOrder(long orderId,String status) {
        try {
            return orderMapper.update(orderId,status);
        } catch (SQLException e) {
            log.info(e.getMessage(),e.getCause());
            throw new RuntimeException("exception is happenning, tx will be rollback",e.getCause());
        }
    }

    public OrderInfoDto selectOrderRange(long start,long end){
        try {
            return new OrderInfoDto(orderMapper.selectRange(start,end),null);
        } catch (SQLException e) {
            log.info(e.getMessage(),e.getCause());
        }
        return null;
    }

    public OrderInfoDto selectOrderItemRange(int start,int end){
        try {
            return new OrderInfoDto(null,orderItemMapper.selectRange(start,end));
        } catch (SQLException e) {
            log.info(e.getMessage(),e.getCause());
        }
        return null;
    }

    public OrderInfoDto selectOrderItemWithIn(long start,long end){
        try {
            return new OrderInfoDto(null,orderItemMapper.selectWithInCondition(start,end));
        } catch (SQLException e) {
            log.info(e.getMessage(),e.getCause());
        }
        return null;
    }

    public OrderInfoDto selectOrderPageList(long offset,long size){
        try {
            return new OrderInfoDto(orderMapper.selectRange(offset,size),null);
        } catch (SQLException e) {
//            log.info(e.getMessage(),e.getCause());
        }
        return null;
    }

}
