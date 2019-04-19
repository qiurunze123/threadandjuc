package com.geekq.highimporttry.service;

import com.geekq.highimporttry.entity.ImportDataStep;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 邱润泽 bullock
 */
@Service
public interface HighImportDataService {


    public void recordHandle(String day);

    public void recordHandleImport(String day);

    public void insertPointTaskStep(List<ImportDataStep> steps);

}
