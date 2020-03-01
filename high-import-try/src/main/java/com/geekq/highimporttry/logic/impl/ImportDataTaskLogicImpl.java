package com.geekq.highimporttry.logic.impl;

import com.geekq.highimporttry.logic.ImportDataTaskLogic;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.mapper.PointDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImportDataTaskLogicImpl implements ImportDataTaskLogic {

    @Autowired
    private PointDao pointDao ;

    @Override
    public long maxId(String day) {

        //强制路由 分片规则
        HintManager hintManager = HintManager.getInstance();
        hintManager.setDatabaseShardingValue(2);
        long maxId = pointDao.getMaxPointId();
        hintManager.close();
        return maxId;
    }
}
