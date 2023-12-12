package com.test.demo.controller;

import com.test.demo.mapper.DataMapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/charts")
public class ChartController {

    @Resource
    private DataMapper dataMapper;

    /**
     * 生成折线图
     */
    @GetMapping("/generateLineChart")
    public void generateLineChart(HttpServletResponse response) throws IOException {
        // 生成折线图
        JFreeChart lineChart = createLineChart();

        // 将图表以PNG格式写入输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(outputStream, lineChart, 800, 400);

        // 将二进制数据转换为Base64编码的字符串
        String base64Chart = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        // 将Base64编码的图表字符串写入响应
        response.setContentType("text/plain");
        response.getWriter().write(base64Chart);
    }

    /**
     * 生成柱状图
     */
    @GetMapping("/generateBarChart")
    public void generateBarChart(HttpServletResponse response) throws IOException {
        // 生成柱状图
        JFreeChart barChart = createBarChart();

        // 将图表以PNG格式写入输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(outputStream, barChart, 800, 400);

        // 将二进制数据转换为Base64编码的字符串
        String base64Chart = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        // 将Base64编码的图表字符串写入响应
        response.setContentType("text/plain");
        response.getWriter().write(base64Chart);
    }

    private JFreeChart createLineChart() {
        // 创建数据集
        CategoryDataset lineDataset = createStringDateDataset();

        // 创建折线图
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Line Chart",
                "month",
                "Amount",
                lineDataset
        );

        // 配置折线图样式
        CategoryPlot linePlot = (CategoryPlot) lineChart.getPlot();
        linePlot.setBackgroundPaint(Color.lightGray);
        linePlot.setDomainGridlinePaint(Color.white);
        linePlot.setRangeGridlinePaint(Color.white);

        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        linePlot.setRenderer(lineRenderer);

        // 配置横坐标轴
        CategoryAxis domainAxis = linePlot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        return lineChart;
    }

    private JFreeChart createBarChart() {
        // 创建柱状图数据集
        DefaultCategoryDataset dataset = createBarDataset();

        // 创建柱状图
        JFreeChart barChart = ChartFactory.createBarChart(
                "Bar Chart",
                "Station",
                "Amount",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // 配置柱状图样式
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        BarRenderer renderer = new BarRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        plot.setRenderer(renderer);
        // 设置为0.0，表示柱子之间没有间隔
        renderer.setItemMargin(0.02);
        // 配置轴标签
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 8));
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));

        return barChart;
    }

    private CategoryDataset createStringDateDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Map<String, Object>> list = dataMapper.getLineChart();
        list.forEach(item -> {
            /*快速开发不创建过多mapping类*/
            dataset.addValue((BigDecimal) item.get("revenue"), "Web revenue", (String) item.get("month"));
            dataset.addValue((BigDecimal) item.get("cost"), "Cost", (String) item.get("month"));
        });

        return dataset;
    }

    private DefaultCategoryDataset createBarDataset() {
        // 创建柱状图数据集
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // 添加示例数据点
        List<Map<String, Object>> list = dataMapper.getBarChart();
        list.forEach(item -> {
            dataset.addValue((BigDecimal) item.get("revenue"), "Web revenue", (String) item.get("station"));
            dataset.addValue((BigDecimal) item.get("cost"), "Cost", (String) item.get("station"));
        });
        return dataset;
    }
}
