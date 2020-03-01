package com.geekq.highimporttry.logic.impl;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.logic.ImportDataStepLogic;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ImportDataStepLogicImpl implements ImportDataStepLogic {

    @Autowired
    private ImportDataStepDao  stepDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(ImportDataStep record) {
        return 0;
    }

    @Override
    public Integer insertBatch(List<ImportDataStep> list) {
        return stepDao.insertBatch(list);
    }

    @Override
    public int insertSelective(ImportDataStep record) {
        return 0;
    }

    @Override
    public ImportDataStep selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(ImportDataStep record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ImportDataStep record) {
        return 0;
    }

    @Override
    public List<ImportDataStep> queryAllStepNotDealAndFail(Integer taskId, String day, String type) {
        return stepDao.queryAllStepNotDealAndFail(taskId, day, type);
    }

    @Override
    public ImportDataStep queryStepByTypeAndDay(String type, String day) {
        return null;
    }

    @Override
    public void updateByStepId(Integer id, Integer status, Date updateTime) {
        stepDao.updateByStepId(id, status, updateTime);
    }

    @Override
    public List<ImportDataStep> queryNotDealStep(Integer status) {
        return null;
    }

    @Override
    public Integer queryCountNotDealStep(String day, Integer status) {
        return null;
    }

    @Override
    public Integer queryTodayStepIsImportSuccess(String day) {
        return stepDao.queryTodayStepIsImportSuccess(day);
    }

    @Override
    public Integer isExistTodayTable(String database, String tableName) {
        return stepDao.isExistTodayTable(database, tableName);
    }
}
