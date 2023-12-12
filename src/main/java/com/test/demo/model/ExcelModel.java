package com.test.demo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Data
@Builder(builderMethodName = "newInstance")
@AllArgsConstructor
@NoArgsConstructor
public class ExcelModel {

    @ExcelProperty("DayPart")
    private String dayPart;
    @ExcelProperty("LocalAirDate")
    private String localAirDate;
    @ExcelProperty("LocalAirTime")
    private String localAirTime;
    @ExcelProperty("LocalAirDay")
    private String localAirDay;
    @ExcelProperty("Station")
    private String station;
    @ExcelProperty("MODProgram")
    private String modProgram;
    @ExcelProperty("CPC")
    private String cpc;
    @ExcelProperty("CPO")
    private String cpo;
    @ExcelProperty("Cost")
    private String cost;
    @ExcelProperty("Web Revenue")
    private String webRevenue;
    @ExcelProperty("WebOrder")
    private String webOrder;
}
