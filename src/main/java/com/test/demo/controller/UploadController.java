package com.test.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.test.demo.entity.DataEntity;
import com.test.demo.mapper.DataMapper;
import com.test.demo.model.ExcelModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


@Controller
@Slf4j
public class UploadController {

    @Resource
    private DataMapper dataMapper;
    private static final Map<String, Integer> WEEKDAY_MAP = new HashMap<String, Integer>() {{
        put("MO", 1);
        put("TU", 2);
        put("WE", 3);
        put("TH", 4);
        put("FR", 5);
        put("SA", 6);
        put("SU", 7);
    }};

    /**
     * 上传数据文件
     *
     * @param file 文件
     * @return 上传结果
     * 失败会异常，暂无校验处理
     */
    @ResponseBody
    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        List<ExcelModel> dataList = EasyExcel.read(file.getInputStream()).head(ExcelModel.class).sheet().doReadSync();
        List<DataEntity> saveList = new ArrayList<>();
        /*数据转化,暂不写service层，简化嵌套层级*/
        dataList.forEach(item -> {
            DataEntity data = new DataEntity();
            BeanUtils.copyProperties(item, data);
            data.setLocalAirDate(stringToDate(item.getLocalAirDate(), "yyyy/MM/dd"));
            Time time = time12To24(item.getLocalAirTime(), "hh:mm:ss a");
            data.setLocalAirTime(time == null ? time12To24(item.getLocalAirTime(), "h:mm:ss a") : time);
            //周缩写转化为int 1-7保存
            data.setLocalAirDay(WEEKDAY_MAP.get(item.getLocalAirDay()));
            data.setCpc(new BigDecimal(item.getCpc()));
            data.setCpo(new BigDecimal(item.getCpo()));
            data.setCost(new BigDecimal(item.getCost()));
            data.setWebRevenue(new BigDecimal(item.getWebRevenue()));
            data.setWebOrder(new BigDecimal(item.getWebOrder()));
            saveList.add(data);

        });
        int batchSize = 600;
        for (int i = 0; i < saveList.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, saveList.size());
            List<DataEntity> batch = saveList.subList(i, endIndex);
            dataMapper.saveInBatch(batch);
        }
        return "数据导入成功";
    }

    /**
     * 时间转化工具
     *
     * @param dateString str格式date
     * @param format     格式如yyyy-MM-dd
     * @return Date
     */
    public static Date stringToDate(String dateString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(dateString, pos);
    }

    /**
     * 非标准格式Time转化工具
     *
     * @param timeString str格式time
     * @param format     格式
     * @return Time
     */
    public static Time time12To24(String timeString, String format) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(format);
        Time time;
        try {
            LocalTime localTime = LocalTime.parse(timeString, inputFormatter);
            time = Time.valueOf(localTime);
        } catch (DateTimeParseException e) {
            return null;
        } catch (NullPointerException ne) {
            log.error(timeString);
            return null;
        }

        return time;

    }

}
