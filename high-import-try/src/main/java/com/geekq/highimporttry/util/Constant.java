package com.geekq.highimporttry.util;

/**
 * 邱润泽
 */
public class Constant {
    public static final String CHECK_BILL_FLAG = "isCheckingOrDBBackUpTask";

    public static final String WE_EXPORT_LATEST_DATE = "WE_EXPORT_LATEST_DATE";

    public static final String BILLINFO = "BILLINFO_";

    public static final int limit = 50000;

    public static final int limitNew = 10000;

    public static final int IMPORT_NOT_DEAL = 0;

    public static final int IMPORT_SUCCESS = 1;

    public static final int IMPORT_FAIL = 2;

    public static final int IMPORT_RANGE_NO_DATA = 3;

    /**
     * 账单不需要抽取数据的日期(表示该日期数据已经抽取,或者已通过其它方式导入)
     */
    public static final String BILL_HAS_IMPORT_DATA_DATE = "BILL_HAS_IMPORT_DATA_DATE";

    /**
     *  .......
     */
    public enum IMPORTTYPE{
        point
    }

}
