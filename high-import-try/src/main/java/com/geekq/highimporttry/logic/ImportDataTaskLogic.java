package com.geekq.highimporttry.logic;

import com.geekq.highimporttry.entity.ImportDataTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface ImportDataTaskLogic  {

    int deleteByPrimaryKey(Integer id);

    int insert(ImportDataTask record);

    int insertSelective(ImportDataTask record);

    ImportDataTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImportDataTask record);

    int updateByPrimaryKey(ImportDataTask record);

    List<ImportDataTask> queryTaskDatas(@Param("type") String type, @Param("day") String day);

    void updateByTaskId(@Param("taskId") Integer taskId, @Param("status") Integer status, @Param("updateTime") Date updateTime);

    Integer queryNotDealTask();

    Integer queryTodayTaskIsImport(String day);

    Integer getTest(Integer id);

}
