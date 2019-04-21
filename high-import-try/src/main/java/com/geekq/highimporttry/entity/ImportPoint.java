package com.geekq.highimporttry.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ImportPoint extends ImportPointKey {
    private BigDecimal availablepoints = BigDecimal.ZERO;

    private BigDecimal frozenpoints = BigDecimal.ZERO;

    private Date createTime;

    public BigDecimal getAvailablepoints() {
        return availablepoints;
    }

    public void setAvailablepoints(BigDecimal availablepoints) {
        this.availablepoints = availablepoints;
    }

    public BigDecimal getFrozenpoints() {
        return frozenpoints;
    }

    public void setFrozenpoints(BigDecimal frozenpoints) {
        this.frozenpoints = frozenpoints;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}