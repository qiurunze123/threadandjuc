package com.geekq.highimporttry.logic.impl;

import com.geekq.highimporttry.entity.Point;
import com.geekq.highimporttry.logic.PointLogic;
import com.geekq.highimporttry.mapper.PointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class PointLogicImpl implements PointLogic {

    @Autowired
    private PointDao pointDao;
    @Override
    public Point getPointByUserId(Integer userId) {
        return null;
    }

    @Override
    public int updateByid(Point point) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer pointid) {
        return 0;
    }

    @Override
    public int insert(Point record) {
        return 0;
    }

    @Override
    public Integer insertBatch(List<Point> list, Date date) {
        return null;
    }

    @Override
    public int insertSelective(Point record) {
        return 0;
    }

    @Override
    public Point selectByPrimaryKey(Integer pointid) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Point record) {
        return 0;
    }

    @Override
    public List<Point> queryAllByOffset(Integer offset, Integer limit) {
        return null;
    }

    @Override
    public Integer getCount() {
        return null;
    }

    @Override
    public long getMaxPointId() {
        return pointDao.getMaxPointId();
    }

    @Override
    public List<Point> queryAllByPointId(Long start, Long end) {
        return pointDao.queryAllByPointId(start, end);
    }
}
