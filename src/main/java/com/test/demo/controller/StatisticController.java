package com.test.demo.controller;

import com.alibaba.fastjson2.JSON;
import com.test.demo.mapper.DataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("statistic")
public class StatisticController {

    @Resource
    private DataMapper dataMapper;

    /**
     * 获取数据
     *
     * @return ROAS与TOP5 JSON
     */
    @GetMapping("get/data")
    public Map<String, Object> getData() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("ROAS", JSON.toJSONString(dataMapper.getROAS()));
        map.put("TOP5", JSON.toJSONString(dataMapper.getTop5()));
        return map;
    }


}
