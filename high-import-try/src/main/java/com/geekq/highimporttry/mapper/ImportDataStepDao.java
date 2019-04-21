package com.geekq.highimporttry.mapper;



import com.geekq.highimporttry.entity.ImportDataStep;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ImportDataStepDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ImportDataStep record);

    Integer insertBatch(@Param("list") List<ImportDataStep> list);

    int insertSelective(ImportDataStep record);

    ImportDataStep selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImportDataStep record);

    int updateByPrimaryKey(ImportDataStep record);

    List<ImportDataStep> queryAllStepNotDealAndFail(@Param("taskId") Integer taskId, @Param("day") String day, @Param("type") String type);


    ImportDataStep queryStepByTypeAndDay(@Param("type") String type, @Param("day") String day);

    void updateByStepId(@Param("id") Integer id, @Param("status") Integer status, @Param("updateTime") Date updateTime);

    public List<ImportDataStep> queryNotDealStep(Integer status);

    public Integer queryCountNotDealStep(@Param("day") String day, @Param("status") Integer status);

    public Integer queryTodayStepIsImportSuccess(String day);


}