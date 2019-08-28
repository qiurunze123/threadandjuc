package com.geek.constanst;

/**
 * @author 邱润泽 bullock
 */
public enum ArticleTypeEnum {

    JAVA(1),
    DUBBO(2),
    SPRING(4),
    MYBATIS(8);
    private int code;
    ArticleTypeEnum(int code) {
        this.code = code;
    }
    public int code() {
        return code;
    }
    public static ArticleTypeEnum find(int code) {
        for (ArticleTypeEnum at : ArticleTypeEnum.values()) {
            if (at.code == code) {
                return at;
            }
        }
        return null;
    }
}
