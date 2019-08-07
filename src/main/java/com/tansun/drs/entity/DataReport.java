package com.tansun.drs.entity;

import java.io.Serializable;
import java.util.Date;

public class DataReport implements Serializable {
    private String id;
    private Date reportDate;
    private double totalAssete;
    private String ownerEquity;

    public DataReport() {

    }

    public DataReport(String id, Date reportDate, double totalAssete, String ownerEquity) {
        this.id = id;
        this.reportDate = reportDate;
        this.totalAssete = totalAssete;
        this.ownerEquity = ownerEquity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public double getTotalAssete() {
        return totalAssete;
    }

    public void setTotalAssete(double totalAssete) {
        this.totalAssete = totalAssete;
    }

    public String getOwnerEquity() {
        return ownerEquity;
    }

    public void setOwnerEquity(String ownerEquity) {
        this.ownerEquity = ownerEquity;
    }
}
