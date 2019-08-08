package com.tansun.drs.mapper;

import com.tansun.drs.entity.DataReport;

public interface DataReportMapper {
    int deleteByPrimaryKey(String id);

    int insert(DataReport record);

    int insertSelective(DataReport record);

    DataReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataReport record);

    int updateByPrimaryKey(DataReport record);
}