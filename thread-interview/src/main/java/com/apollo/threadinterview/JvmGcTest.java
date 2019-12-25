package com.apollo.threadinterview;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/** 5次ygc，然后3次fgc，然后3次ygc，然后1次fgc，请给出代码以及启动参数
 *
 * -Xms41m -Xmx41m -Xmn10m -XX:+UseParallelGC -XX:+PrintGCDetails
 *
 * -Xms41m -Xmx41m 堆设置内存 41  -Xmn 新生代 10m
 *
 * 新生代 分配 10m  那么堆分配了 40多 20多
 * 想让他先发生5次ygc
 *
 * @author 邱润泽 bullock
 */
public class JvmGcTest {

    /**
     * 最小的单位
     */
    private static final int ALL_LOCATION = 1024 * 1024;

    public static void main(String[] args) {
        List jvmCatchT = new ArrayList();
        //Eden 区 不够触发
        for (int i = 1; i <= 12; i++){
            jvmCatchT.add(new byte[3 * ALL_LOCATION]);
        }
        jvmCatchT.remove(0);
        jvmCatchT.add(new byte[3 * ALL_LOCATION]);
        for (int i = 0; i < 8; i++){
            jvmCatchT.remove(0);
        }
        jvmCatchT.add(new byte[3 * ALL_LOCATION]);
        for (int i = 0; i < 6; i++){
            jvmCatchT.add(new byte[3 * ALL_LOCATION]);
        }
    }

}
