package com.geekq.highimporttry.service.algorithm;

import com.alibaba.fastjson.JSON;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class IdSharingAlgorithm implements HintShardingAlgorithm<Integer> {


    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         HintShardingValue<Integer> shardingValue) {

        List<String> shardingList = new LinkedList<>();
        for (String each : availableTargetNames){
            for (Integer val : shardingValue.getValues()){
                if(each.endsWith(val + "")){
                    shardingList.add(each);
                }
            }
        }
        return shardingList;
    }
}
