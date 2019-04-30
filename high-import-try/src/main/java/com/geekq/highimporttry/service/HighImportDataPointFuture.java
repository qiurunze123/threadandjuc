package com.geekq.highimporttry.service;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.ImportDataTask;
import com.geekq.highimporttry.entity.Point;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.mapper.PointDao;
import com.geekq.highimporttry.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author 邱润泽 bullock
 */
public class HighImportDataPointFuture implements Callable<List<Point>> {

    private static final Logger logger = LoggerFactory.getLogger(HighImportDataPointFuture.class);

    private ImportDataStep step;
    private PointDao pointDao;

    public HighImportDataPointFuture(ImportDataStep step,
                                     PointDao pointDao) {
        this.step = step;
        this.pointDao = pointDao;
    }
    @Override
    public List<Point> call() throws Exception {
        logger.info("start :{} ==  end :{}",step.getRangeStart(),step.getRangeEnd());
        List<Point> lists = pointDao.queryAllByPointId(step.getRangeStart(),step.getRangeEnd());
        return lists ;
    }



}
