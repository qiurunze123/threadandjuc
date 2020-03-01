package com.gqrz.sphereg.service;

import com.alibaba.fastjson.JSON;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class IdSharingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {

        System.out.println("collection: " + JSON.toJSONString(collection) + " ,preciseShardingValue: "
                + JSON.toJSONString(preciseShardingValue));

        Integer id = preciseShardingValue.getValue();
        //范围超如200+ 插入0库 小于2000 插入1库
        for (String name : collection) {
            if(name.endsWith("0")&& id>200){
                return name;
            }
            if(name.endsWith("1")&& id<200){
                return name;
            }
        }
        throw new IllegalArgumentException();
    }
}
