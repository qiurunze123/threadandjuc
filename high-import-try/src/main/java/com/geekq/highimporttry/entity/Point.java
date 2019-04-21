package com.geekq.highimporttry.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 主账户
 */
public class Point {

    private Integer pointId;

    private BigDecimal availablePoints;

    private BigDecimal frozenPoints;

    private Integer version;

    private Integer user;

    private Date lastUpdateTime;

    private Integer delayUpdateMode;

    private Integer latestPointLogId;

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    public BigDecimal getAvailablePoints() {
        return availablePoints;
    }

    public void setAvailablePoints(BigDecimal availablePoints) {
        this.availablePoints = availablePoints;
    }

    public BigDecimal getFrozenPoints() {
        return frozenPoints;
    }

    public void setFrozenPoints(BigDecimal frozenPoints) {
        this.frozenPoints = frozenPoints;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getDelayUpdateMode() {
        return delayUpdateMode;
    }

    public void setDelayUpdateMode(Integer delayUpdateMode) {
        this.delayUpdateMode = delayUpdateMode;
    }

    public Integer getLatestPointLogId() {
        return latestPointLogId;
    }

    public void setLatestPointLogId(Integer latestPointLogId) {
        this.latestPointLogId = latestPointLogId;
    }

    public boolean isUpdateDelay() {
//        if (0 != this.getDelayUpdateMode() && null != this.getLatestPointLogId()) {
//            return true;
//        }
//        return false;
        return (this.delayUpdateMode == 1 && null != this.latestPointLogId);
    }
}