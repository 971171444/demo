package com.test.demo.mapper;

import com.test.demo.entity.DataEntity;

import java.util.List;
import java.util.Map;

public interface DataMapper {

    void saveInBatch(List<DataEntity> entityList);

    List<Map<String, String>> getROAS();

    List<Map<String, String>> getTop5();

    List<Map<String, Object>> getBarChart();

    List<Map<String, Object>> getLineChart();
}
