package com.gqrz.sphereg.dto;

import com.gqrz.sphereg.entity.Order;
import com.gqrz.sphereg.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class OrderInfoDto {

    private List<Order> order;

    private List<OrderItem> item;

}
