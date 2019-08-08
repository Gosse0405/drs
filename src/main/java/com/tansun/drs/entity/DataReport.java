package com.tansun.drs.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DataReport implements Serializable {
    private String id;

    private Date reportDate;

    private BigDecimal totalAssets;

    private String ownerEquity;

    public DataReport(String id, Date reportDate, BigDecimal totalAssets, String ownerEquity) {
        this.id = id;
        this.reportDate = reportDate;
        this.totalAssets = totalAssets;
        this.ownerEquity = ownerEquity;
    }

    public DataReport() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getOwnerEquity() {
        return ownerEquity;
    }

    public void setOwnerEquity(String ownerEquity) {
        this.ownerEquity = ownerEquity == null ? null : ownerEquity.trim();
    }
}