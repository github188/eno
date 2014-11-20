package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.ChartModel;
import com.energicube.eno.common.jdbc.ChartModelMapper;
import com.energicube.eno.common.util.UCServUtil;
import com.energicube.eno.monitor.model.ReportConfig;
import com.energicube.eno.monitor.repository.ReportConfigRepository;
import com.energicube.eno.monitor.service.ChartService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ChartServiceImpl implements ChartService {

    private final static Log logger = LogFactory.getLog(ChartServiceImpl.class);

    @Autowired
    private JdbcTemplate reportTemplate;

    @Autowired
    ReportConfigRepository reportConfigRepository;

    /**
     * 根据以下参数，返回图表需要的数据
     */
    public Map<String, Object> getChartData(String name, String id,
                                            String ispd, String type, String tfrom, String tto,
                                            String attribute, String beforeFormat, String afterFormat,
                                            String ipaddress, String port, String isNotStatic) {

        Map<String, Object> map = new HashMap<String, Object>();

        String[] names = name.split(","); // 分割成为数组
        String[] ids = id.split(","); // 分割成为数组
        String[] ispds = ispd.split(","); // 分割成为数组

        List<Object> dataList = new ArrayList<Object>();// 存储数据
        List<Object> cataList = new ArrayList<Object>();// 存储时间数据
        DecimalFormat df = new DecimalFormat("percents".equalsIgnoreCase(attribute) ? "0.000" : "0.0"); // 保留小数位数，0.0表示保留一位小数
        SimpleDateFormat bFormat = new SimpleDateFormat(beforeFormat); // 格式化字符串
        SimpleDateFormat aFormat = new SimpleDateFormat(afterFormat); // 格式化字符串

        UCServUtil<ChartModel> servUtil = new UCServUtil<ChartModel>();

        for (int i = 0; i < names.length; i++) {
            List<ChartModel> list;
            if (!"true".equals(isNotStatic)) {
                String wsurl = "http://" + ipaddress + ":" + port + "/ChinaArtsPalace/history?name=" + names[i] + "&id=" + ids[i] + "&type=" + type + "&tfrom=" + tfrom + "&tto=" + tto + "&attribute=" + attribute + "&ispd=";
                logger.info("wsurl----" + wsurl);
                list = servUtil.getUCServResult(ChartModel.class, wsurl);
            } else {
                list = getChartDataList(names[i], ids[i], type, tfrom, tto, attribute);
            }

            List<Double> tempDataList = new ArrayList<Double>(); // 存储数据，注意存储的格式一定要是doule类型，或者其它数字类型

            for (int k = 0; k < list.size(); k++) {
                ChartModel cm = (ChartModel) list.get(k);
                try {
                    String datavalue = df.format(Double.parseDouble(cm.getValue()));
                    tempDataList.add(Double.parseDouble(datavalue));
                } catch (Exception e) {
                    cataList.add(cm.getTime());
                    tempDataList.add(Double.parseDouble("0"));
                }

                if (i == (names.length - 1)) { // 最后一个X轴信息
                    try {
                        if (afterFormat.length() > 0) {
                            // logger.info("names--" + names[i] + "#" + ids[i] + "afterFormat--【" + afterFormat + "】" + "beforeFormat--【" + beforeFormat + "】");
                            cataList.add(aFormat.format(bFormat.parse(cm.getTime())));
                        }
                    } catch (Exception e) {
                        cataList.add(cm.getTime());
                    }
                }
            }
            dataList.add(tempDataList);
        }

        map.put("datalist", dataList);
        map.put("catalist", cataList);

        return map;
    }

    // 报表数据  组织SQL 并  查询数据
    @SuppressWarnings("unchecked")
    private List<ChartModel> getChartDataList(String name, String id,
                                              String type, String tfrom, String tto, String attribute) {
        ChartModelMapper chartModelMapper = new ChartModelMapper();
        String sql = "select '" + name + "#" + id + "#" + type + "' as name, s.time, ";
        if ("month".equals(type)) {
            sql += " s.value from " + name + "#" + id + "#span s where 1=1 ";
            Calendar calendar = Calendar.getInstance();
            if (!"".equals(tfrom) && tfrom.length() > 7) {
                tfrom = tfrom.substring(0, 7);
            }
            if (!"".equals(tfrom) && tfrom.length() == 7) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                Date date = null;
                try {
                    date = format.parse(tfrom);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            sql += " and convert(varchar(10), '" + tfrom + "-01', 120) <= s.time";
            sql += " and convert(varchar(10), '" + tfrom + "-" + lastDay + "', 120) >= s.time";

        } else {
            sql += " s.value from " + name + "#" + id + "#span s where 1 = 1 ";
            if (!"".equals(tfrom)) {
                sql += " and convert(varchar(10), '" + tfrom + "', 120) <= s.time";
            }
            if (!"".equals(tto)) {
                sql += " and convert(varchar(10), '" + tto + "', 120) >= s.time";
            }
        }

        List<ChartModel> list = null;
        try {
            list = reportTemplate.query(sql, chartModelMapper);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }

    /**
     * 根据以下参数，返回单个url获取的值
     */
    public Map<String, Object> getSingleValueData(String name, String id,
                                                  String ispd, String type, String tfrom, String tto,
                                                  String ipaddress, String port) {
        Map<String, Object> map = new HashMap<String, Object>();

        String[] names = name.split(","); // 分割成为数组
        String[] ids = id.split(","); // 分割成为数组
        String[] ispds = ispd.split(","); // 分割成为数组

        List<Object> dataList = new ArrayList<Object>();// 存储数据
        DecimalFormat df = new DecimalFormat("0.0"); // 保留小数位数，0.0表示保留一位小数

        UCServUtil<ChartModel> servUtil = new UCServUtil<ChartModel>();

        for (int i = 0; i < names.length; i++) {
            String wsurl = "http://" + ipaddress + ":" + port + "/ChinaArtsPalace/history?name=" + names[i] + "&id=" + ids[i] + "&type=" + type + "&tfrom=" + tfrom + "&tto=" + tto + "&ispd=" + (ispds.length > i ? ispds[i] : "");
            //logger.info("wsurl----" + wsurl);
            List<ChartModel> list = servUtil.getUCServResult(ChartModel.class, wsurl);

            List<Double> tempDataList = new ArrayList<Double>(); // 存储数据，注意存储的格式一定要是doule类型，或者其它数字类型
            for (int k = 0; k < list.size(); k++) {
                ChartModel cm = (ChartModel) list.get(k);
                try {
                    String datavalue = df.format(Double.parseDouble(cm.getValue()));
                    tempDataList.add(Double.parseDouble(datavalue));
                } catch (Exception e) {
                    tempDataList.add(Double.parseDouble("0"));
                }
            }
            dataList.add(tempDataList);
        }

        map.put("datalist", dataList);

        return map;
    }

    /**
     * 查询门户管理-报表需要的数据
     */
    public Map<String, Object> getReportDataList(String name, String id, String type, String tfrom, String tto,
                                                 String decimals, String ipaddress, String port, String isNotStatic) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String[] names = name.split(",");
        String[] ids = id.split(",");
        List<Object> dataList = new ArrayList<Object>();// 存储数据
        List<Object> cataList = new ArrayList<Object>();// X轴存储数据
        List<Object> showCataList = new ArrayList<Object>();// 预览报表时显示的日期
        List<Object> showDataList = new ArrayList<Object>();// 预览报表时显示的日期
        // 分别表示，格式化前的格式和格式化后的格式
        String beforeFormat = "yyyy-MM-dd HH:mm:ss", afterFormat = "HH:mm", preFormat = "HH:mm";
        if ("day".equals(type)) {
            beforeFormat = "yyyy-MM-dd HH:mm:ss";
            afterFormat = "HH:mm";
            preFormat = "HH:mm";
        } else if ("week".equals(type)) {
            beforeFormat = "yyyy/MM/dd HH:mm";
            afterFormat = "MM/dd";
            preFormat = "yyyy/MM/dd HH:mm";
        } else if ("month".equals(type)) {
            beforeFormat = "yyyy/MM/dd HH:mm";
            afterFormat = "MM/dd";
            preFormat = "yyyy/MM/dd HH:mm";
        } else if ("year".equals(type)) {
            beforeFormat = "yyyy-MM";
            afterFormat = "yyyy/MM";
            preFormat = "yyyy/MM";
        }

        SimpleDateFormat sf = new SimpleDateFormat(beforeFormat);
        SimpleDateFormat sformat = new SimpleDateFormat(afterFormat);
        SimpleDateFormat pre_format = new SimpleDateFormat(preFormat); // 预览格式化时间戳

        String def = (decimals != null && decimals.length() > 0) ? decimals : "0.00";
        DecimalFormat df = new DecimalFormat(def);

        UCServUtil<ChartModel> servUtil = new UCServUtil<ChartModel>();
        // 循环处理name和id为多参数的情况
        for (int w = 0; w < names.length; w++) {
            try {
                List<Double> tempShowDataList = new ArrayList<Double>();// 存储预览的数据列表
                List<Double> tempDataList = new ArrayList<Double>();// 存储数据
                List<String> tempCataList = new ArrayList<String>();// 存储X轴的显示列表
                List<String> tempShowCataList = new ArrayList<String>();// 存储预览视图页面的的日期
                List<ChartModel> list = null;
                if (!"true".equals(isNotStatic)) {
                    String wsurl = "http://" + ipaddress + ":" + port + "/ChinaArtsPalace/history?name=" + names[w] + "&id=" + ids[w] + "&type=" + type + "&tfrom=" + tfrom + "&tto=" + tto + "&ispd=";
                    list = servUtil.getUCServResult(ChartModel.class, wsurl);
                    logger.info("printReportInfo----" + wsurl);
                } else {
                    list = getChartDataList(names[w], ids[w], type, tfrom, tto, null);
                }

                double total = 0;
                for (int k = 0; k < list.size(); k++) {
                    ChartModel cm = (ChartModel) list.get(k);

                    // 求和
                    try {
                        double datavalue = Double.parseDouble(df.format(Double.parseDouble(cm.getValue())));

                        tempDataList.add(datavalue);
                        tempShowDataList.add(datavalue);
                        total += datavalue;
                    } catch (Exception e) {
                        total += 0;
                        tempDataList.add(Double.parseDouble("0"));
                        tempShowDataList.add(Double.parseDouble("0"));
                        logger.error(e);
                    }

                    try {
                        tempCataList.add(sformat.format(sf.parse(cm
                                .getTime())));
                    } catch (Exception e) {
                        tempCataList.add(cm.getTime());
                    }

                    // 处理预览情况
                    try {
                        tempShowCataList.add(pre_format.format(sf
                                .parse(cm.getTime())));
                    } catch (Exception e) {
                        tempShowCataList.add(cm.getTime());
                    }
                }
                showDataList.add(tempShowDataList); // 显示在预览页面的数据列表
                showCataList.add(tempShowCataList); // 显示在预览页面的日期列表
                tempCataList.add("合计");
                cataList.add(tempCataList);
                tempDataList.add(Double.parseDouble(df.format(total)));
                dataList.add(tempDataList);
            } catch (Exception e) {
                logger.error(e);
            }
        }

        resultMap.put("showDataList", showDataList);
        resultMap.put("showCataList", showCataList);
        resultMap.put("cataList", cataList);
        resultMap.put("dataList", dataList);

        return resultMap;
    }

    /**
     * 查询门户管理2-报表需要的数据
     */
    public Map<String, Object> getReportDataList(List<String> names, List<String> ids, String type, String tfrom, String tto,
                                                 String decimals, String ipaddress, String port, String isNotStatic) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Object> dataList = new ArrayList<Object>();// 存储数据
        List<Object> cataList = new ArrayList<Object>();// X轴存储数据
        List<Object> showCataList = new ArrayList<Object>();// 预览报表时显示的日期
        List<Object> showDataList = new ArrayList<Object>();// 预览报表时显示的日期
        // 分别表示，格式化前的格式和格式化后的格式
        String beforeFormat = "yyyy-MM-dd HH:mm:ss", afterFormat = "HH:mm", preFormat = "HH:mm";
        if ("day".equals(type)) {
            beforeFormat = "yyyy-MM-dd HH:mm:ss";
            afterFormat = "HH:mm";
            preFormat = "HH:mm";
        } else if ("week".equals(type)) {
            beforeFormat = "yyyy/MM/dd HH:mm";
            afterFormat = "MM/dd";
            preFormat = "yyyy/MM/dd HH:mm";
        } else if ("month".equals(type)) {
            beforeFormat = "yyyy/MM/dd HH:mm";
            afterFormat = "MM/dd";
            preFormat = "yyyy/MM/dd HH:mm";
        } else if ("year".equals(type)) {
            beforeFormat = "yyyy-MM";
            afterFormat = "yyyy/MM";
            preFormat = "yyyy/MM";
        }

        SimpleDateFormat sf = new SimpleDateFormat(beforeFormat);
        SimpleDateFormat sformat = new SimpleDateFormat(afterFormat);
        SimpleDateFormat pre_format = new SimpleDateFormat(preFormat); // 预览格式化时间戳

        String def = (decimals != null && decimals.length() > 0) ? decimals : "0.00";
        DecimalFormat df = new DecimalFormat(def);

        UCServUtil<ChartModel> servUtil = new UCServUtil<ChartModel>();
        // 循环处理name和id为多参数的情况
        for (int w = 0; w < names.size(); w++) {
            try {
                List<Double> tempShowDataList = new ArrayList<Double>();// 存储预览的数据列表
                List<Double> tempDataList = new ArrayList<Double>();// 存储数据
                List<String> tempCataList = new ArrayList<String>();// 存储X轴的显示列表
                List<String> tempShowCataList = new ArrayList<String>();// 存储预览视图页面的的日期
                List<ChartModel> list = null;
                if (!"true".equals(isNotStatic)) {
                    String wsurl = "http://" + ipaddress + ":" + port + "/ChinaArtsPalace/history?name=" + names.get(w) + "&id=" + ids.get(w) + "&type=" + type + "&tfrom=" + tfrom + "&tto=" + tto + "&ispd=";
                    list = servUtil.getUCServResult(ChartModel.class, wsurl);
                    logger.info("printReportInfo----" + wsurl);
                } else {
                    list = getChartDataList(names.get(w), ids.get(w), type, tfrom, tto, null);
                }

                double total = 0;
                for (int k = 0; k < list.size(); k++) {
                    ChartModel cm = (ChartModel) list.get(k);

                    // 求和
                    try {
                        double datavalue = Double.parseDouble(df.format(Double.parseDouble(cm.getValue())));

                        tempDataList.add(datavalue);
                        tempShowDataList.add(datavalue);
                        total += datavalue;
                    } catch (Exception e) {
                        total += 0;
                        tempDataList.add(Double.parseDouble("0"));
                        tempShowDataList.add(Double.parseDouble("0"));
                        logger.error(e);
                    }

                    try {
                        tempCataList.add(sformat.format(sf.parse(cm
                                .getTime())));
                    } catch (Exception e) {
                        tempCataList.add(cm.getTime());
                    }

                    // 处理预览情况
                    try {
                        tempShowCataList.add(pre_format.format(sf
                                .parse(cm.getTime())));
                    } catch (Exception e) {
                        tempShowCataList.add(cm.getTime());
                    }
                }
                showDataList.add(tempShowDataList); // 显示在预览页面的数据列表
                showCataList.add(tempShowCataList); // 显示在预览页面的日期列表
                tempCataList.add("合计");
                cataList.add(tempCataList);
                tempDataList.add(Double.parseDouble(df.format(total)));
                dataList.add(tempDataList);
            } catch (Exception e) {
                logger.error(e);
            }
        }

        resultMap.put("showDataList", showDataList);
        resultMap.put("showCataList", showCataList);
        resultMap.put("cataList", cataList);
        resultMap.put("dataList", dataList);

        return resultMap;
    }

    /**
     * 输出报表数据到excel文件
     */
    public void outputToExcel(String name, String id,
                              String ispd, String type, String tfrom, String tto,
                              String decimals, String ipaddress, String port, String sheetname,
                              String commonColumn, String isNotStatic, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = getReportDataList(name, id, type, tfrom, tto, decimals, ipaddress, port, isNotStatic);

        // 创建一个EXCEL
        Workbook wb = new HSSFWorkbook();
        // 创建一个SHEET
        try {
            sheetname = new String(sheetname.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            logger.error(e2);
        }
        Sheet sheet1 = wb.createSheet(sheetname);

        // 获取配置项中的共有的列头数据，如果类型为day的话，将day特有的列加入到列头数据中
        String str = "日期" + "," + (("day".equals(type)) ? "时间," : "");

        // 将配置项中的列头信息分割成java中的数组，以便循环处理相关数据
        String str1 = "";
        try {
            str1 = (str + new String(commonColumn.getBytes("ISO-8859-1"), "UTF-8")).replace("[", "").replace("]", "").replace(" ", "").replace("'", "");
        } catch (UnsupportedEncodingException e1) {
            logger.error(e1);
        }
        // 内容样式
        CellStyle contentstyle = wb.createCellStyle();
        contentstyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
        contentstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中

        // 创建标题一行
        Row row = sheet1.createRow((short) 0);
        // 创建列
        String[] title = str1.split(",");
        for (int column = 0; column < title.length; column++) {
            Cell cell = row.createCell(column);
            cell.setCellValue(title[column]);
            cell.setCellStyle(contentstyle);// 设置单元格样式
            sheet1.setColumnWidth(column, 6000);
        }

        List<Object> resultList = new ArrayList<Object>();
        List<Object> dataList = (List<Object>) map.get("showDataList");

        // 创建列
        if ("day".equals(type)) {
            List<Object> tempList = new ArrayList<Object>();
            int hang = ((List<Object>) dataList.get(0)).size();
            for (int t = 0; t < hang; t++) {
                tempList.add(tfrom);
            }
            resultList.add(tempList);

        }

        resultList.add((List<Object>) ((List<Object>) map.get("showCataList"))
                .get(0));
        resultList.addAll(dataList);

        List<Object> a = (List<Object>) resultList.get(0);
        int b = resultList.size();

        int rows = a.size();
        int cols = b;

        String result[][] = new String[rows][cols];

        // 设置全局的行数和列数
        int r = 0;
        int c = 0;

        // 循环将数据添加到二维数组中
        for (int j = 0; j < resultList.size(); j++) {
            List<Object> list = (List<Object>) resultList.get(j);
            for (int k = 0; k < list.size(); k++) {
                String valString = list.get(k) + "";
                result[r][c] = valString;
                r++;
                r = r % rows;
            }
            c++;
        }

        // 循环从二维数组中取值存放到对应的excel中
        for (int q = 0; q < result.length; q++) {
            Row row1 = sheet1.createRow((short) q + 1);

            for (int d = 0; d < result[q].length; d++) {
                Cell cell = row1.createCell(d);
                cell.setCellStyle(contentstyle);// 设置单元格样式
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                try {
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(Double.parseDouble(result[q][d]));
                } catch (Exception e) {
                    cell.setCellValue(result[q][d]);
                }
            }
        }

        String storeName = System.currentTimeMillis() + ".xls";
        String rootDir = request.getSession().getServletContext().getRealPath("/") + "WEB-INF\\log\\";
        String realPath = rootDir + storeName;

        File deleteFile = new File(rootDir);//里面输入特定目录
        File temp = null;
        File[] filelist = deleteFile.listFiles();
        for (int i = 0; i < filelist.length; i++) {
            temp = filelist[i];
            if (temp.getName().endsWith("xls"))//获得文件名，如果后缀为“”，这个你自己写，就删除文件
                temp.delete(); //删除文件
        }

        FileOutputStream fileOut = null;
        File file = new File(realPath);
        if (file.exists())
            file.delete();
        try {
            fileOut = new FileOutputStream(realPath);
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            request.setCharacterEncoding("UTF-8");
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            long fileLength = new File(realPath).length();
            String contentType = "application/octet-stream";
            response.setContentType(contentType);
            response.setHeader("Content-disposition", "attachment; filename=" + new String(storeName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(realPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportToExcel(String tfrom, String tto, String type, String system, String deviceType, String deviceStr,
                              String decimals, String ipaddress, String port, String isNotStatic, HttpServletRequest request, HttpServletResponse response) {
        try {
            deviceType = new String(request.getParameter("deviceType").getBytes("ISO-8859-1"), "utf-8");
            deviceStr = new String(request.getParameter("devices").getBytes("ISO-8859-1"), "utf-8");
            system = new String(request.getParameter("system").getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] devices = deviceStr.split(",");
        List<String> commonColumn;
        List<String> name;
        List<String> id;
        List<ReportConfig> list = null;
        // 创建一个EXCEL
        Workbook wb = new HSSFWorkbook();
        Sheet sheet;
        Map<String, Object> map;
        List<Object> listObj;
        try {

            for (String device : devices) {
                if (StringUtils.isNotEmpty(system) && StringUtils.isNotEmpty(deviceType) && StringUtils.isNotEmpty(device)) {
                    list = reportConfigRepository.findReportConfigsList(system, deviceType, device);
                } else {
                    list = reportConfigRepository.findParamsList();
                }
                commonColumn = new ArrayList<String>();
                name = new ArrayList<String>();
                id = new ArrayList<String>();
                for (ReportConfig reportConfig : list) {
                    commonColumn.add(reportConfig.getParams() + "(" + reportConfig.getUnit() + ")");
                    name.add(reportConfig.getName());
                    id.add(reportConfig.getId());
                }
                map = getReportDataList(name, id, type, tfrom, tto, decimals, ipaddress, port, isNotStatic);
                // 创建一个SHEET
                sheet = wb.createSheet(device);
                // 获取配置项中的共有的列头数据，如果类型为day的话，将day特有的列加入到列头数据中
                String str = "日期" + "," + (("day".equals(type)) ? "时间," : "");

                // 将配置项中的列头信息分割成java中的数组，以便循环处理相关数据
                String str1 = str + commonColumn.toString().replace("[", "").replace("]", "").replace(" ", "").replace("'", "");
                // 内容样式
                CellStyle contentstyle = wb.createCellStyle();
                contentstyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
                contentstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中

                // 创建标题一行
                Row row = sheet.createRow((short) 0);
                // 创建列
                String[] title = str1.split(",");
                for (int column = 0; column < title.length; column++) {
                    Cell cell = row.createCell(column);
                    cell.setCellValue(title[column]);
                    cell.setCellStyle(contentstyle);// 设置单元格样式
                    sheet.setColumnWidth(column, 6000);
                }

                List<Object> resultList = new ArrayList<Object>();
                List<Object> dataList = (List<Object>) map.get("showDataList");
                if (dataList != null && dataList.size() > 0) {
                    // 创建列
                    if ("day".equals(type)) {
                        List<Object> tempList = new ArrayList<Object>();
                        int hang = ((List<Object>) dataList.get(0)).size();
                        for (int t = 0; t < hang; t++) {
                            tempList.add(tfrom);
                        }
                        resultList.add(tempList);
                    }
                }

                List<Object> showList = (List<Object>) map.get("showCataList");
                if (showList != null && showList.size() > 0) {
                    resultList.add(showList.get(0));
                }
                resultList.addAll(dataList);
                List<Object> a = new ArrayList<Object>();
                if (resultList != null && resultList.size() > 0) {
                    a = (List<Object>) resultList.get(0);
                }
                int b = resultList.size();

                int rows = a.size();
                int cols = b;

                String result[][] = new String[rows][cols];

                // 设置全局的行数和列数
                int r = 0;
                int c = 0;

                // 循环将数据添加到二维数组中
                for (int j = 0; j < resultList.size(); j++) {
                    listObj = (List<Object>) resultList.get(j);
                    for (int k = 0; k < listObj.size(); k++) {
                        String valString = listObj.get(k) + "";
                        result[r][c] = valString;
                        r++;
                        r = r % rows;
                    }
                    c++;
                }
                // 循环从二维数组中取值存放到对应的excel中
                for (int q = 0; q < result.length; q++) {
                    Row row1 = sheet.createRow((short) q + 1);

                    for (int d = 0; d < result[q].length; d++) {
                        Cell cell = row1.createCell(d);
                        cell.setCellStyle(contentstyle);// 设置单元格样式
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        try {
                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            cell.setCellValue(Double.parseDouble(result[q][d]));
                        } catch (Exception e) {
                            cell.setCellValue(result[q][d]);
                        }
                    }
                }
            }
            String storeName = System.currentTimeMillis() + ".xls";
            String rootDir = request.getSession().getServletContext().getRealPath("/") + "WEB-INF\\log\\";
            String realPath = rootDir + storeName;

            File deleteFile = new File(rootDir);//里面输入特定目录
            File temp = null;
            File[] filelist = deleteFile.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                temp = filelist[i];
                if (temp.getName().endsWith("xls"))//获得文件名，如果后缀为“”，这个你自己写，就删除文件
                    temp.delete(); //删除文件
            }

            FileOutputStream fileOut = null;
            File file = new File(realPath);
            if (file.exists())
                file.delete();
            try {
                fileOut = new FileOutputStream(realPath);
                wb.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                request.setCharacterEncoding("UTF-8");
                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;

                long fileLength = new File(realPath).length();
                String contentType = "application/octet-stream";
                response.setContentType(contentType);
                response.setHeader("Content-disposition", "attachment; filename=" + new String(storeName.getBytes("utf-8"), "ISO8859-1"));
                response.setHeader("Content-Length", String.valueOf(fileLength));

                bis = new BufferedInputStream(new FileInputStream(realPath));
                bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                bis.close();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

