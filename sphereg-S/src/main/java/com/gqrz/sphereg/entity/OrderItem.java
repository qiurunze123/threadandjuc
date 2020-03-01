package com.gqrz.sphereg.entity;

import java.io.Serializable;

/**
 *                  ,;,,;
 *                ,;;'(    社
 *      __      ,;;' ' \   会
 *   /'  '\'~~'~' \ /'\.)  主
 * ,;(      )    /  |.     义
 *,;' \    /-.,,(   ) \    码
 *     ) /       ) / )|    农
 *     ||        ||  \)     
 *     (_\       (_\
 * @author ：杨过
 * @date ：Created in 2019/11/17 17:25
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description: 订单从表
 **/
public class OrderItem implements Serializable {
    
    private static final long serialVersionUID = 263434701950670170L;
    
    private long orderItemId;
    
    private long orderId;
    
    private int userId;
    
    private String status;
    
    public long getOrderItemId() {
        return orderItemId;
    }
    
    public void setOrderItemId(final long orderItemId) {
        this.orderItemId = orderItemId;
    }
    
    public long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(final long orderId) {
        this.orderId = orderId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return String.format("order_item_id:%s, order_id: %s, user_id: %s, status: %s", orderItemId, orderId, userId, status);
    }
}
