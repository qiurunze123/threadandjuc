package com.geekq.highimporttry.mapper;

import com.geekq.highimporttry.entity.ImportPoint;
import com.geekq.highimporttry.entity.ImportPointKey;
import com.geekq.highimporttry.entity.Point;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ImportPointDao {
    int deleteByPrimaryKey(ImportPointKey key);

    int insert(ImportPoint record);

    int insertSelective(ImportPoint record);

    ImportPoint selectByPrimaryKey(ImportPointKey key);

    int updateByPrimaryKeySelective(ImportPoint record);

    int updateByPrimaryKey(ImportPoint record);

    public Integer insertBatch(@Param("list") List<Point> list, @Param("table") String tableName,
                               @Param("day") String day, @Param("createTime") Date date);

    public List<ImportPoint> queryDaily(@Param("table") String tableName, @Param("day") String day, @Param("userId") Integer userId, @Param("startIndex") Integer startIndex, @Param("offset") Integer offset);

    public Integer queryDailyTotal(@Param("table") String tableName, @Param("day") String day, @Param("userId") Integer userId);

    public List<Integer> queryAllUserIdByDay(@Param("table") String tableName, @Param("day") String day);

    public List<Integer> queryAllUserIdByMonth(@Param("table") String tableName, @Param("day") String day);

    public void deleteByDay(@Param("table") String tableName, @Param("day") String day);

    /**
     * 查询该偏移量中最大的用户id
     * @param monthString 月份，确认表
     * @param dateString 确定处理的日期
     * @param startIndex 开始处理的数据index
     * @param offset 偏移量
     * @return
     */
    ImportPoint selectMaxUserIdPoint(@Param("monthString") String monthString,
                                     @Param("dateString") String dateString,
                                     @Param("startIndex") Long startIndex,
                                     @Param("offset") Integer offset);

    /**
     * 获取最大的用户id
     * @param monthString
     * @param dateString
     * @return
     */
    ImportPoint selectMaxPointByDay(@Param("monthString") String monthString,
                                    @Param("dateString") String dateString);

    /**
     * 通过起始和结束获取某一天的范围导入对象
     * @param day
     * @param rangeStart
     * @param rangeEnd
     * @param monthString
     * @return
     */
    List<ImportPoint> selectByDayAndUserIdRange(@Param("monthString") String monthString,
                                                @Param("day") String day,
                                                @Param("rangeStart") Long rangeStart,
                                                @Param("rangeEnd") Long rangeEnd);
}