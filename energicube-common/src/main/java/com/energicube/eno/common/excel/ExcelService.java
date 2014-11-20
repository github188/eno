package com.energicube.eno.common.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:    Copyright(C) 2013-2014
 * Company   ZCLF Energy Technologies Inc.
 *
 * @author 刘广路
 * @version 1.0
 * @date 2014/10/19 22:45
 * @Description 用于Excel的导入、导出操作
 */
public class ExcelService {
    /**
     * 得EXCEL里所有sheet
     * @param filePath 文件路径
     * @return sheet集合
     * @throws Exception
     */
    public static Collection<Sheet> getExcelSheetFilePath(String filePath) throws Exception {
        File file1 = new File(filePath);
        return getExcelSheetFile(file1);
    }

    /**
     * 得EXCEL里所有sheet
     * @param file 文件
     * @return sheet集合
     * @throws Exception
     */
    public static Collection<Sheet> getExcelSheetFile(File file) throws Exception {

        FileInputStream in = new FileInputStream(file);
        // 得到工作表
        Workbook book = null;
        book = new HSSFWorkbook(in);
        Collection<Sheet> sheets = new ArrayList<Sheet>();

        int x=book.getNumberOfSheets();
        for (int i=0;i<x;i++){
            Sheet sheet = book.getSheetAt(i);
            sheets.add(sheet);
        }
        return sheets;
    }

    /**
     * 得EXCEL里所有sheet
     * @param file 文件
     * @return sheet集合
     * @throws Exception
     */
    public static Sheet getExcelSheetOneFile(File file) throws Exception {

        FileInputStream in = new FileInputStream(file);
        // 得到工作表
        Workbook book = null;
        book = new HSSFWorkbook(in);
        return  book.getSheetAt(0);
    }
}
