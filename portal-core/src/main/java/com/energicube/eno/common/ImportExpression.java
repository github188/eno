package com.energicube.eno.common;

import com.energicube.eno.monitor.model.Expression;
import com.energicube.eno.monitor.model.NetvideoCameracfg;
import com.energicube.eno.monitor.model.NetvideoDvrcfg;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImportExpression {

    public static List<Expression> readExcel(HttpServletRequest request, String filename) throws Exception {
        List<Expression> expressions = new ArrayList<Expression>();
        String path = request.getSession().getServletContext().getRealPath("/")
                + "/" + filename;
        File file = new File(path);
        boolean isE2007 = false;    //判断是否是excel2007格式
        if (file.getName().endsWith("xlsx"))
            isE2007 = true;
        InputStream input = new FileInputStream(file);  //建立输入流  
        Workbook wb = null;
        //根据文件格式(2003或者2007)来初始化  
        if (isE2007)
            wb = new XSSFWorkbook(input);
        else
            wb = new HSSFWorkbook(input);
        Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
        Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
        while (rows.hasNext()) {
            Row row = rows.next();  //获得行数据  
            if (row.getRowNum() > 0) {
                Expression expression = new Expression();
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        if (cell.getColumnIndex() == 0) {
                            expression.setExpcode(cell.getStringCellValue());
                        } else if (cell.getColumnIndex() == 1) {
                            expression.setExpname(cell.getStringCellValue());
                        } else if (cell.getColumnIndex() == 2) {
                            expression.setExpcontent(cell.getStringCellValue());
                        }
                    }
                }
                expressions.add(expression);
            }
        }
        file.deleteOnExit();//删除文件
        return expressions;
    }

    public static List<NetvideoDvrcfg> readDvrExcel(File file) throws Exception {
        List<NetvideoDvrcfg> dvrcfgs = new ArrayList<NetvideoDvrcfg>();
        boolean isE2007 = false;    //判断是否是excel2007格式
        if (file.getName().endsWith("xlsx"))
            isE2007 = true;
        try {
            InputStream input = new FileInputStream(file);  //建立输入流  
            Workbook wb = null;
            //根据文件格式(2003或者2007)来初始化  
            if (isE2007)
                wb = new XSSFWorkbook(input);
            else
                wb = new HSSFWorkbook(input);
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
            while (rows.hasNext()) {
                Row row = rows.next();  //获得行数据  
                if (row.getRowNum() > 1) {
                    NetvideoDvrcfg dvrcfg = new NetvideoDvrcfg();
                    dvrcfg.setDvrsequence(row.getRowNum());
                    Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                    while (cells.hasNext()) {
                        Cell cell = cells.next();
                        switch (cell.getColumnIndex()) {
                            case 0:
                                dvrcfg.setDvrname(cell.getStringCellValue());
                                break;
                            case 1:
                                dvrcfg.setDvrtype(cell.getStringCellValue());
                                break;
                            case 2:
                                dvrcfg.setIpaddress(cell.getStringCellValue());
                                break;
                            case 3:
                                dvrcfg.setPort((int) cell.getNumericCellValue());
                                break;
                            case 4:
                                dvrcfg.setUsername(cell.getStringCellValue());
                                break;
                            case 5:
                                dvrcfg.setPassword(cell.getStringCellValue());
                                break;
                            case 6:
                                if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                                    dvrcfg.setResourceid((int) cell.getNumericCellValue() + "");
                                } else {
                                    dvrcfg.setResourceid(cell.getStringCellValue());
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    dvrcfgs.add(dvrcfg);
                }
            }
            return dvrcfgs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<NetvideoCameracfg> readCamExcel(File file) throws Exception {
        List<NetvideoCameracfg> cameracfgs = new ArrayList<NetvideoCameracfg>();
        boolean isE2007 = false;    //判断是否是excel2007格式
        if (file.getName().endsWith("xlsx"))
            isE2007 = true;
        try {
            InputStream input = new FileInputStream(file);  //建立输入流  
            Workbook wb = null;
            //根据文件格式(2003或者2007)来初始化  
            if (isE2007)
                wb = new XSSFWorkbook(input);
            else
                wb = new HSSFWorkbook(input);
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
            while (rows.hasNext()) {
                Row row = rows.next();  //获得行数据  
                if (row.getRowNum() > 1) {
                    NetvideoCameracfg cameracfg = new NetvideoCameracfg();
                    cameracfg.setDisplaysequence(row.getRowNum());
                    Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                    while (cells.hasNext()) {
                        Cell cell = cells.next();
                        switch (cell.getColumnIndex()) {
                            case 0:
                                if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                                    cameracfg.setCameraid((int) cell.getNumericCellValue() + "");
                                } else {
                                    cameracfg.setCameraid(cell.getStringCellValue());
                                }
                                break;
                            case 1:
                                if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                                    cameracfg.setCameraname((int) cell.getNumericCellValue() + "");
                                } else {
                                    cameracfg.setCameraname(cell.getStringCellValue());
                                }
                                break;
                            case 2:
                                cameracfg.setDvrname(cell.getStringCellValue());
                                break;
                            case 3:
                                cameracfg.setDvrchannel((int) cell.getNumericCellValue());
                                break;
                            case 4:
                                cameracfg.setMatrixindex((int) cell.getNumericCellValue());
                                break;
                            case 5:
                                cameracfg.setMatrixchannel((int) cell.getNumericCellValue());
                                break;
                            case 6:
                                cameracfg.setPtzindex((int) cell.getNumericCellValue() + 1);
                                break;
                            case 7:
                                if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                                    cameracfg.setResourceid((int) cell.getNumericCellValue() + "");
                                } else {
                                    cameracfg.setResourceid(cell.getStringCellValue());
                                }
                                break;
                            case 12:
                                cameracfg.setGroupid((int) cell.getNumericCellValue());
                                break;
                            default:
                                break;
                        }
                        switch (cameracfg.getMatrixindex()) {
                            case 0:
                                cameracfg.setMatrix("不连接矩阵");
                                break;
                            case 1:
                                cameracfg.setMatrix("1#矩阵");
                                break;
                            case 2:
                                cameracfg.setMatrix("2#矩阵");
                                break;
                            case 3:
                                cameracfg.setMatrix("3#矩阵");
                                break;
                            case 4:
                                cameracfg.setMatrix("4#矩阵");
                                break;
                            default:
                                break;
                        }
                        switch (cameracfg.getPtzindex()) {
                            case 0:
                                cameracfg.setPtzcontrol("没有云镜控制");
                                break;
                            case 1:
                                cameracfg.setPtzcontrol("云镜由矩阵控制");
                                break;
                            case 2:
                                cameracfg.setPtzcontrol("云镜由DVR控制");
                                break;
                            default:
                                break;
                        }
                        cameracfg.setPtzparam1(0);
                        cameracfg.setPtzparam2(0);
                        cameracfg.setPtzparam3(0);
                    }
                    cameracfgs.add(cameracfg);
                }
            }
            return cameracfgs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
