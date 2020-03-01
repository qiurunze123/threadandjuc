package com.geekq.highimporttry.logic.impl;

import com.geekq.highimporttry.entity.ImportPoint;
import com.geekq.highimporttry.entity.ImportPointKey;
import com.geekq.highimporttry.entity.Point;
import com.geekq.highimporttry.logic.ImportPointLogic;
import com.geekq.highimporttry.mapper.ImportPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ImportPointLogicImpl implements ImportPointLogic {

    @Autowired
    private ImportPointDao pointDao;

    @Override
    public int deleteByPrimaryKey(ImportPointKey key) {
        return 0;
    }

    @Override
    public int insert(ImportPoint record) {
        return 0;
    }

    @Override
    public int insertSelective(ImportPoint record) {
        return 0;
    }

    @Override
    public ImportPoint selectByPrimaryKey(ImportPointKey key) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(ImportPoint record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ImportPoint record) {
        return 0;
    }

    @Override
    public Integer insertBatch(List<Point> list, String tableName, String day, Date date) {
        return pointDao.insertBatch(list, tableName, day, date);
    }


    @Override
    public List<ImportPoint> queryDaily(String tableName, String day, Integer userId, Integer startIndex, Integer offset) {
        return null;
    }

    @Override
    public Integer queryDailyTotal(String tableName, String day, Integer userId) {
        return null;
    }

    @Override
    public List<Integer> queryAllUserIdByDay(String tableName, String day) {
        return null;
    }

    @Override
    public List<Integer> queryAllUserIdByMonth(String tableName, String day) {
        return null;
    }

    @Override
    public void deleteByDay(String tableName, String day) {

    }

    @Override
    public ImportPoint selectMaxUserIdPoint(String monthString, String dateString, Long startIndex, Integer offset) {
        return null;
    }

    @Override
    public ImportPoint selectMaxPointByDay(String monthString, String dateString) {
        return null;
    }

    @Override
    public List<ImportPoint> selectByDayAndUserIdRange(String monthString, String day, Long rangeStart, Long rangeEnd) {
        return null;
    }
}
