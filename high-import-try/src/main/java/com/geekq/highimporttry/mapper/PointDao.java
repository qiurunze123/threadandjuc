package com.geekq.highimporttry.mapper;

import com.geekq.highimporttry.entity.Point;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PointDao {

    /**
     * 根据用户ID查询该用户的point
     *
     * @param userId
     * @return
     */
    public Point getPointByUserId(Integer userId);

    /**
     * 根据主键更新主账户信息
     * @param point
     * @return 影响的行数
     */
    public int updateByid(Point point);

    int deleteByPrimaryKey(Integer pointid);

    int insert(Point record);

    int insertSelective(Point record);

    Point selectByPrimaryKey(Integer pointid);

    int updateByPrimaryKeySelective(Point record);

    public List<Point> queryAllByOffset(@Param("offset") Integer offset, @Param("limit") Integer limit);

    public Integer getCount();

    public long getMaxPointId();

    public List<Point> queryAllByPointId(@Param("start") Long start, @Param("end") Long end);


}