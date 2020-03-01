package com.gqrz.sphereg.controller;

import com.gqrz.sphereg.dto.OrderInfoDto;
import com.gqrz.sphereg.service.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @date ：Created in 2019/11/17 17:24
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description: 
 **/
@Slf4j
@RestController
public class ShardingController {

    @Autowired
    OrderServiceImpl orderService;

    @GetMapping("/confirm_order")
    public String confirmOrder(int sequenceId){
        long id = orderService.confirmOrder(sequenceId);
        return "创建订单成功:订单ID = " + id;
    }

    @GetMapping("/order_histroy_list")
    public OrderInfoDto orderHistoryList(){
        return orderService.selectAll();
    }

    /**
     * 删除历史订单
     * @param orderId
     * @return
     */
    @GetMapping("/delete_histroy_order")
    public String deleteHistroyOrder(long orderId){
        return orderService.deleteData(orderId);
    }

    /**
     * 更改历史订单状态
     * @param orderId
     * @param status
     * @return
     */
    @GetMapping("/update_histroy_order")
    public int updateHistoryOrderStatus(long orderId,String status){
        return orderService.updateOrder(orderId,status);
    }

    /**
     * range orderid {200000000000000000 - 400000000000000000}
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/order_range_list")
    public OrderInfoDto orderRangeList(long start,long end){
        return orderService.selectOrderRange(start,end);
    }

    /**
     * range userid {1-20}
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/item_range_list")
    public OrderInfoDto orderItemRangeList(int start,int end){
        return orderService.selectOrderItemRange(start,end);
    }

    /**
     * 笛卡尔积测试
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/item_range_in_list")
    public OrderInfoDto orderItemRangeInList(long start,long end){
        return orderService.selectOrderItemWithIn(start,end);
    }

    @GetMapping("/item_page_list")
    public OrderInfoDto orderPageList(long offset,long size){
        return orderService.selectOrderPageList(offset,size);
    }
}
