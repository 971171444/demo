package com.test.demo.entity;

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
public class DataEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String dayPart;
    private Date localAirDate;
    private Time localAirTime;
    private int localAirDay;
    private String station;
    private String modProgram;
    private BigDecimal cpc;
    private BigDecimal cpo;
    private BigDecimal cost;
    private BigDecimal webRevenue;
    private BigDecimal webOrder;
}
