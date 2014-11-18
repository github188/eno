package com.energicube.eno.common;

import com.energicube.eno.alarm.model.UcAlarmlog;
import com.energicube.eno.asset.model.Classification;
import com.energicube.eno.common.excel.ExcelService;
import com.energicube.eno.pattern.model.UcDevicePattern;
import com.energicube.eno.pattern.model.UcPattern;
import com.energicube.eno.pattern.model.UcPatternAction;
import com.energicube.eno.pattern.model.UcRunParam;
import com.energicube.eno.pattern.service.PatternService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-10-18
 * Time: 下午3:44
 * To change this template use File | Settings | File Templates.
 */
public class PatternExcel {
    private static Log logger = LogFactory.getLog(PatternExcel.class);

    private static PatternService patternService;

    public static void setPatternService(PatternService patternService) {
        PatternExcel.patternService = patternService;
    }

    /**
     * 获取模式的集合
     *
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static Collection<UcPatternAction> getUcPatternAction(String filePath) throws Exception {
        File file = new File(filePath);
        return getUcPatternAction(file);
    }

    /**
     * 获取模式的集合
     *
     * @param file 文件
     * @return
     * @throws Exception
     */
    public static Collection<UcPatternAction> getUcPatternAction(File file) throws Exception {
        //得到第一个页面
        Config config = new Config();
        Sheet sheet = ExcelService.getExcelSheetOneFile(file);

        //得到时间列表
        Collection<UcPatternAction> ucPatternCollection = new ArrayList<UcPatternAction>();


        // 得到第一面的所有行
        Iterator<Row> allRows = sheet.rowIterator();

        // 得到第一行，也就是标题行
        Row title = allRows.next();
        // 得到第一行的所有列
        Iterator<Cell> cellTitle = title.cellIterator();
        // 将标题的文字内容放入到一个map中。
        Map titleMap = new HashMap();
        int i = 0;
        while (cellTitle.hasNext()) {
            Cell cell = cellTitle.next();
            String value = "";
            if (null != cell) {//当前单元格不为空
                if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                    DecimalFormat formatter = new DecimalFormat("#");
                    value = formatter.format(cell.getNumericCellValue());
                } else if (cell.getCellType() == cell.CELL_TYPE_STRING) {
                    value = String.valueOf(cell.getStringCellValue());
                } else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {
                    value = String.valueOf(cell.getDateCellValue());
                } else if (cell.getCellType() == cell.CELL_TYPE_BLANK) {
                    value = String.valueOf(cell.getStringCellValue());
                } else if (cell.getCellType() == cell.CELL_TYPE_ERROR) {
                    value = "";
                }
            }
            System.out.println("---title---" + value);
            titleMap.put(i, value);
            i = i + 1;
        }


        int j = 0;//行数
        int k = 0;//列数
        try {
//                System.out.println("---title---"+allRows.hasNext());
            while (allRows.hasNext()) {
                // 标题下的第一行
                Row nextRow = allRows.next();
                // 得到传入类的实例
//                    System.out.println("---row---:"+j);
                UcPatternAction ucPatternAction = new UcPatternAction();
                ucPatternCollection.add(ucPatternAction);
                UcDevicePattern ucDevicePattern = new UcDevicePattern();
                ucDevicePattern.setUcPatternAction(ucPatternAction);
                ucDevicePattern.setSelectType("A");
                ucDevicePattern.setName("全部");
//                    ucPattern.getUcPatternActions().add(ucPatternAction);
                String deviceIds = null;
                // 遍历一行的列,每行开始初始化列数
                for (k = 0; k < titleMap.size(); k++) {
//                        System.out.println("--1-cell---"+k);
                    // 列取值
                    Cell cell = null;
                    try {
                        cell = nextRow.getCell(k);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    // 这里得到此列对应的标题
                    String titleString = titleMap.get(k).toString();
                    // 如果这一列的标题和类中的某一列的Annotation相同，那么就调用此类的set方法进行赋值
                    String typeString = null;
                    try {
                        if (null != cell) {//当前单元格不为空
                            if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                                DecimalFormat formatter = new DecimalFormat("#");

                                typeString = String.valueOf(cell.getNumericCellValue());
                                System.out.println("---CELL_TYPE_NUMERIC-1--]" + typeString);
                                int a = typeString.indexOf(".");
                                String x = typeString.substring(a + 1);
                                int xi = Integer.parseInt(x);
                                if (xi > 0) {
                                    //是小数
                                } else {
                                    typeString = formatter.format(cell.getNumericCellValue());
                                }
                            } else if (cell.getCellType() == cell.CELL_TYPE_STRING) {
                                typeString = String.valueOf(cell.getStringCellValue());
                            } else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {
                                typeString = String.valueOf(cell.getDateCellValue());
                            } else if (cell.getCellType() == cell.CELL_TYPE_BLANK) {
                                typeString = String.valueOf(cell.getStringCellValue());
                            } else if (cell.getCellType() == cell.CELL_TYPE_ERROR) {
                                typeString = "";
                            }
                            System.out.println("---typeString---" + typeString + "----" + k);
                            switch (k) {
                                case 0:
                                    if (typeString != null && !"".equals(typeString)) { //基础时间
                                        int base = Integer.parseInt(typeString);
                                        ucPatternAction.setBaseTime(base);
                                        break;
                                    }
                                case 1:
                                    if (typeString != null && !"".equals(typeString)) {//偏移时间
                                        int offset = Integer.parseInt(typeString);
                                        ucPatternAction.setOffsetTime(offset);
                                        break;
                                    }
                                case 2:
                                    if (typeString != null && !"".equals(typeString)) {//设备组编码
//                                            ucDevicePattern.setName(typeString);
                                        deviceIds = typeString;
                                        break;
                                    }
                                case 3:
                                    if (typeString != null && !"".equals(typeString)) {//设备选择类型
                                        ucDevicePattern.setSelectType(typeString);
                                        break;
                                    }
                                case 4:
                                    if (typeString != null && !"".equals(typeString)) { //设备的活动类型
                                        ucPatternAction.setActionType(typeString);
                                        break;
                                    }
                                case 5:
                                    if (typeString != null && !"".equals(typeString)) { //策略ID
                                        ucDevicePattern.setStrategyId(typeString);
                                        break;
                                    }
                                default:
                                    if (typeString != null && !"".equals(typeString)) {//参数
                                        UcRunParam ucRunParam = new UcRunParam();
                                        ucRunParam.setParamValue(typeString);
                                        ucRunParam.setParamName(titleString);
                                        ucDevicePattern.getUcRunParams().add(ucRunParam);

                                    }
                            }
                        }
                        System.out.println((k + 1) + "---cell---:" + titleString + ":----" + typeString);
                    } catch (Exception e) {
                        System.out.println("第" + (j + 1) + "行" + (k + 1) + "列出错！请检查！");
                    }
                }
                //处理设备的名字的拼串
                if (deviceIds != null && !"".equals(deviceIds)) {

                    if (deviceIds.contains(",")) {
                        StringTokenizer stringTokenizer = new StringTokenizer(deviceIds, ",");
                        while (stringTokenizer.hasMoreTokens()) {
                            String dId = stringTokenizer.nextToken();
                            UcDevicePattern devicePattern = new UcDevicePattern();
                            BeanUtils.copyProperties(ucDevicePattern, devicePattern);
                            if (ucDevicePattern.getSelectType().equals(PatternConst.PATTERN_SELECT_CLASS)) {
                                Classification classification = patternService.findClassificationByClassificationid(dId);
                                if (classification != null) {
                                    ucDevicePattern.setName(classification.getDescription());
                                }
                            }
                            devicePattern.setDevice(dId);
                            ucPatternAction.getUcDevicePatterns().add(ucDevicePattern);
                        }
                    } else {

                        ucDevicePattern.setDevice(deviceIds);
                        if (ucDevicePattern.getSelectType().equals(PatternConst.PATTERN_SELECT_CLASS)) {
                            Classification classification = patternService.findClassificationByClassificationid(deviceIds);
                            if (classification != null) {
                                ucDevicePattern.setName(classification.getDescription());
                            }
                        }
                        ucPatternAction.getUcDevicePatterns().add(ucDevicePattern);
                    }
                } else {
                    ucPatternAction.getUcDevicePatterns().add(ucDevicePattern);
                }
            }
        } catch (Exception e) {
//                logger.error("第" + (j + 1) + "行" + (k + 1) + "列出错！请检查！");
            System.out.println("第" + (j + 1) + "行" + (k + 1) + "列出错！请检查！");
            System.out.println("第" + (j + 1) + "行" + (k + 1) + "列出错！请检查！");
            throw new Exception("第" + (j + 1) + "行" + (k + 1) + "列出错！请检查！");
        }

        return ucPatternCollection;
    }

    /**
     * 获取模式的集合
     *
     * @param file 文件
     * @return
     * @throws Exception
     */
    public static Collection<UcPattern> getUcPattern(File file) throws Exception {
        Collection<Sheet> sheets = ExcelService.getExcelSheetFile(file);
        Collection<UcPattern> ucPatternCollection = new ArrayList<UcPattern>();

        for (Sheet sheet : sheets) {

            //一个sheet一个模式
            UcPattern ucPattern = new UcPattern();
            ucPattern.setDefaultPattern("N"); //是否是默认的模式
            ucPattern.setPatternType("C");   //自宝义
            ucPattern.setCreateDate(new Date());
            ucPattern.setIsNew("Y"); //最新的
            ucPattern.setOrderType("T"); //时序
            ucPatternCollection.add(ucPattern);
            // 得到第一面的所有行
            Iterator<Row> allRows = sheet.rowIterator();
            /**
             * 标题解析
             */
            // 得到第一行，也就是标题行
            Row titleName = allRows.next();
            // 得到第一行的所有列
            Iterator<Cell> cellTitleName = titleName.cellIterator();
            // 将标题的文字内容放入到一个中。
            String patternName = null;

            int i = 0;
            while (cellTitleName.hasNext()) {
                Cell cell = cellTitleName.next();
                if (i == 0) {
                    //第一个是模式名
                    patternName = cell.getStringCellValue();
                    ucPattern.setName(patternName);
                }
                if (i == 1) {
                    //第2个是模式的描述
                    patternName = cell.getStringCellValue();
                    ucPattern.setDescription(patternName);
                }
                if (i == 2) {
                    //第3个是模式所属系统
                    patternName = cell.getStringCellValue();
                    ucPattern.setSystemId(patternName);
                }

                i++;
            }

            // 得到第一行，也就是标题行
            Row title = allRows.next();
            // 得到第一行的所有列
            Iterator<Cell> cellTitle = title.cellIterator();
            // 将标题的文字内容放入到一个map中。
            Map titleMap = new HashMap();
            i = 0;
            while (cellTitle.hasNext()) {
                Cell cell = cellTitle.next();
                String value = cell.getStringCellValue();
                titleMap.put(i, value);
                i = i + 1;
            }


            int j = 0;//行数
            int k = 0;//列数
            try {
                while (allRows.hasNext()) {
                    // 标题下的第一行
                    Row nextRow = allRows.next();
                    // 得到传入类的实例

                    UcPatternAction ucPatternAction = new UcPatternAction();

                    UcDevicePattern ucDevicePattern = new UcDevicePattern();
                    ucDevicePattern.setUcPatternAction(ucPatternAction);
                    ucDevicePattern.setSelectType("A");
                    ucPattern.getUcPatternActions().add(ucPatternAction);
                    ucPatternAction.getUcDevicePatterns().add(ucDevicePattern);
//                    ucPatternAction.set
                    // 遍历一行的列,每行开始初始化列数
                    for (k = 0; k < titleMap.size(); k++) {
                        // 列取值
                        Cell cell = nextRow.getCell(k);
                        // 这里得到此列对应的标题
                        String titleString = titleMap.get(k).toString();
                        // 如果这一列的标题和类中的某一列的Annotation相同，那么就调用此类的set方法进行赋值
                        String typeString = null;

                        if (null != cell) {//当前单元格不为空
                            if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                                DecimalFormat formatter = new DecimalFormat("#");
                                typeString = formatter.format(cell.getNumericCellValue());
                            } else if (cell.getCellType() == cell.CELL_TYPE_STRING) {
                                typeString = String.valueOf(cell.getStringCellValue());
                            } else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {
                                typeString = String.valueOf(cell.getDateCellValue());
                            } else if (cell.getCellType() == cell.CELL_TYPE_BLANK) {
                                typeString = String.valueOf(cell.getStringCellValue());
                            } else if (cell.getCellType() == cell.CELL_TYPE_ERROR) {
                                typeString = "";
                            }

                            switch (k) {
                                case 0:
                                    if (typeString != null && !"".equals(typeString)) {
                                        int base = Integer.parseInt(typeString);
                                        ucPatternAction.setBaseTime(base);
                                        break;
                                    }
                                case 2:
                                    if (typeString != null && !"".equals(typeString)) {
                                        int offset = Integer.parseInt(typeString);
                                        ucPatternAction.setOffsetTime(offset);
                                        break;
                                    }
                                case 3:
                                    if (typeString != null && !"".equals(typeString)) {
                                        ucPatternAction.setDescription(typeString);
                                        break;
                                    }
                                case 4:
                                    if (typeString != null && !"".equals(typeString)) {
                                        ucPatternAction.setActionType(typeString);
                                        break;
                                    }
                                case 5:
                                    if (typeString != null && !"".equals(typeString)) {
                                        ucDevicePattern.setStrategyId(typeString);
                                        break;
                                    }
                                default:
                                    if (typeString != null && !"".equals(typeString)) {
                                        UcRunParam ucRunParam = new UcRunParam();
                                        ucRunParam.setParamValue(typeString);
                                        ucRunParam.setParamName(titleString);
                                        ucDevicePattern.getUcRunParams().add(ucRunParam);

                                    }
                            }
                        }
                    }
                }
            } catch (Exception e) {
//                logger.error("第" + (j + 1) + "行" + (k + 1) + "列出错！请检查！");
                throw new Exception("第" + (j + 1) + "行" + (k + 1) + "列出错！请检查！");
            }
        }
        return ucPatternCollection;
    }

    /**
     * 获取模式的时间列表的文件
     *
     * @param ucPattern 文件
     * @return
     * @throws Exception
     */
    public static void getUcPatternToFile(UcPattern ucPattern, String fileName) throws Exception {


        HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象

        HSSFSheet sheet = wb.createSheet("pattern");//建立新的sheet对象

        HSSFRow tileRow = sheet.createRow(0);//建立新行 增加title
        HSSFCell tileCell = tileRow.createCell(0);
        tileCell.setCellValue("baseTime");
        tileCell = tileRow.createCell(1);
        tileCell.setCellValue("offsetTime");
        tileCell = tileRow.createCell(2);
        tileCell.setCellValue("name");
        tileCell = tileRow.createCell(3);
        tileCell.setCellValue("actionType");
        tileCell = tileRow.createCell(4);
        tileCell.setCellValue("strategyId");
        int title = 5;
// Create a row and put some cells in it. Rows are 0 based.
        Set<UcPatternAction> ucPatternActionSet = ucPattern.getUcPatternActions();
        int i = 1;
        int k = 0;
        for (UcPatternAction ucPatternAction : ucPatternActionSet) {


            Set<UcDevicePattern> ucDevicePatterns = ucPatternAction.getUcDevicePatterns();//todo 不处理设备
            UcDevicePattern ucDevicePattern = ucDevicePatterns.iterator().next();

            HSSFRow row = sheet.createRow(i);//建立新行

            HSSFCell cell = row.createCell(k);//建立新cell
            cell.setCellValue(ucPatternAction.getBaseTime());

            k++;
            cell = row.createCell(k);//建立新cell
            cell.setCellValue(ucPatternAction.getOffsetTime());

            k++;

            cell = row.createCell(k);//建立新cell
            cell.setCellValue(ucPatternAction.getDescription());

            k++;

            cell = row.createCell(k);//建立新cell
            cell.setCellValue(ucPatternAction.getActionType());

            k++;

            cell = row.createCell(k);//建立新cell
            cell.setCellValue(ucDevicePattern.getStrategyId());


            Set<UcRunParam> ucRunParams = ucDevicePattern.getUcRunParams();
            for (UcRunParam ucRunParam : ucRunParams) {
                if (i == 1) {
                    tileCell = tileRow.createCell(title);
                    tileCell.setCellValue(ucRunParam.getParamName());
                    title++;
                }
                k++;
                cell = row.createCell(k);//建立新cell
                cell.setCellValue(ucRunParam.getParamValue());
            }
            k = 0;
            i++;
        }

        FileOutputStream fileOut = new FileOutputStream(fileName);

        wb.write(fileOut);

        fileOut.close();

    }

    /**
     * 报警列表导出成文件
     *
     * @param alarmlogList 报警列表
     * @param fileName     文件名
     * @return
     * @throws Exception
     */
    public static void getAlarmToFile(List<UcAlarmlog> alarmlogList, String fileName) {
        FileOutputStream fileOut = null;

        try {
            HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
            HSSFSheet sheet = wb.createSheet("alarm");//建立新的sheet对象
            HSSFRow tileRow = sheet.createRow(0);//建立新行 增加title
            HSSFCell tileCell = tileRow.createCell(0);
            tileCell.setCellValue("报警日期");
            tileCell = tileRow.createCell(1);
            tileCell.setCellValue("报警描述");
            tileCell = tileRow.createCell(2);
            tileCell.setCellValue("子系统");
            tileCell = tileRow.createCell(3);
            tileCell.setCellValue("设备");
            tileCell = tileRow.createCell(4);
            tileCell.setCellValue("位置");
            tileCell = tileRow.createCell(5);
            tileCell.setCellValue("报警级别");
            tileCell = tileRow.createCell(6);
            tileCell.setCellValue("报警处理");
// Create a row and put some cells in it. Rows are 0 based.
            int i = 1;
            int k = 0;
            DateTime dateTime = DateTime.now();
            for (UcAlarmlog ucAlarmlog : alarmlogList) {

                HSSFRow row = sheet.createRow(i);//建立新行

                HSSFCell cell = row.createCell(k);//建立新cell
                dateTime = new DateTime(ucAlarmlog.getAlmt());
                cell.setCellValue(dateTime.toString("yyyy年MM月dd日 HH时mm分ss秒"));

                k++;
                cell = row.createCell(k);//建立新cell
                cell.setCellValue(ucAlarmlog.getAlmcomment());

                k++;

                cell = row.createCell(k);//建立新cell
                cell.setCellValue(ucAlarmlog.getGroupname());

                k++;

                cell = row.createCell(k);//建立新cell
                cell.setCellValue(ucAlarmlog.getDevicename());

                k++;

                cell = row.createCell(k);//建立新cell
                cell.setCellValue(ucAlarmlog.getTagcomment());

                k++;
                cell = row.createCell(k);//建立新cell
                cell.setCellValue(getAlarmGrade(ucAlarmlog.getAlmpriority()));

                k++;
                cell = row.createCell(k);//建立新cell
                cell.setCellValue(getAlarmReviewer(ucAlarmlog.getReviewer()));

                k = 0;
                i++;
            }

            fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.close();
            } catch (Exception io) {
                io.printStackTrace();
            }
        }
    }

    /**
     * 将数字级别转为中文
     *
     * @param grade 级别
     * @return 中文级别
     */
    private static String getAlarmGrade(int grade) {
        String result = "";
        switch (grade) {
            case 1:
                result = "极高";
                break;
            case 2:
                result = "高";
                break;
            case 3:
                result = "中";
                break;
            case 4:
                result = "低";
                break;
        }
        return result;
    }

    /**
     * 将处理状态转为中文
     *
     * @param status 级别
     * @return 中文级别
     */
    private static String getAlarmReviewer(String status) {

        if (StringUtils.isNotEmpty(status) && "1".equals(status)) {
            return "已应答";
        } else {
            return "未应答";
        }
    }
}
