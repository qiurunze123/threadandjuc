package com.geekq.highimporttry.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiurunze 2019/9/19
 */
public class ListUtils {

    public static List<List<Integer>> splitList(List<Integer> totalList, int size) {
        List<List<Integer>> splitList = new ArrayList<List<Integer>>();
        //获取被拆分的数组个数
        int arrSize = totalList.size() % size == 0 ? totalList.size() / size : totalList.size() / size + 1;
        for (int i = 0; i < arrSize; i++) {
            List<Integer> sub = new ArrayList<Integer>();
            //把指定索引数据放入到list中
            for (int j = i * size; j <= size * (i + 1) - 1; j++) {
                if (j <= totalList.size() - 1) {
                    sub.add(totalList.get(j));
                }
            }
            splitList.add(sub);
        }
        return splitList;
    }

    public static void main(String args[]) {
        List<Integer> totalList = new ArrayList<Integer>();
        for(int i=0;i<8050;i++){
            totalList.add(i);
        }
        List<List<Integer>> listList=ListUtils.splitList(totalList,1000);
        for(List<Integer> list :listList){
            System.out.println("----"+JSON.toJSON( list));

        }

    }
}
