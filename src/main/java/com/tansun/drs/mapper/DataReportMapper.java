package com.tansun.drs.mapper;

import com.tansun.drs.entity.DataReport;

public interface DataReportMapper {

    /**
     * 插入数据
     * @param dataReport
     * @return
     */
    int insert(DataReport dataReport);

    int deleteByPrimaryKey(String id);

    int insertSelective(DataReport record);

    DataReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataReport record);

    int updateByPrimaryKey(DataReport record);
}