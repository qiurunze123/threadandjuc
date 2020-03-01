package com.geekq.highimporttry.logic.impl;

import com.geekq.highimporttry.entity.ImportDataTask;
import com.geekq.highimporttry.logic.ImportDataTaskLogic;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.mapper.PointDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ImportDataTaskLogicImpl implements ImportDataTaskLogic {

    @Autowired
    private ImportDataTaskDao importDataTaskDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(ImportDataTask record) {
        return importDataTaskDao.insert(record);
    }

    @Override
    public int insertSelective(ImportDataTask record) {
        return 0;
    }

    @Override
    public ImportDataTask selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(ImportDataTask record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ImportDataTask record) {
        return 0;
    }

    @Override
    public List<ImportDataTask> queryTaskDatas(String type, String day) {
        return importDataTaskDao.queryTaskDatas(type,day);
    }

    @Override
    public void updateByTaskId(Integer taskId, Integer status, Date updateTime) {
        importDataTaskDao.updateByTaskId(taskId, status, updateTime);
    }

    @Override
    public Integer queryNotDealTask() {
        return null;
    }

    @Override
    public Integer queryTodayTaskIsImport(String day) {
        return importDataTaskDao.queryTodayTaskIsImport(day);
    }

    @Override
    public Integer getTest(Integer id) {
        System.out.println("======================="+id);
        return id;
    }

//    @Override
//    public long maxId(String day) {
//
//        //强制路由 分片规则
//        HintManager hintManager = HintManager.getInstance();
//        hintManager.setDatabaseShardingValue(2);
//        long maxId = pointDao.getMaxPointId();
//        hintManager.close();
//        return maxId;
//    }
}
