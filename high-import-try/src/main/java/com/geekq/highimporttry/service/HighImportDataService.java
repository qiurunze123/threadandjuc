package com.geekq.highimporttry.service;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.Point;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 邱润泽 bullock
 */
@Service
public interface HighImportDataService {


    /**
     * 获取分片数据范围
     */
    public void recordHandle(String day);

    public void recordHandleImport(String day);

    public void insertPointTaskStep(List<ImportDataStep> steps);
    /**
     * 线程池 查询
     */

    public List<Point> recordHandlePoints(List<ImportDataStep> steps);

    public void  importData(String data);
}
