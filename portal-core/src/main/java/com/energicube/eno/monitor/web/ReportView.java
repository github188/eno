package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.Article;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReportView extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        Map<String, List<Article>> reportData = (Map<String, List<Article>>) model.get("reportData");
        HSSFSheet sheet1 = workbook.createSheet("安全知识");
        List<Article> results = new LinkedList<Article>();
        if (reportData.entrySet().iterator().hasNext()) {
            results = reportData.entrySet().iterator().next().getValue();
        }
        if (results == null)
            return;
        String[] title = {"文章ID", "标题", "链接标题", "摘要", "版本", "类型",};
        int i = 0;
        Font font = workbook.createFont();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        CellStyle Contentstyle = workbook.createCellStyle();
        Contentstyle.setAlignment(CellStyle.ALIGN_RIGHT);
        Contentstyle.setFont(font);
        // 创建一行
        Row row = sheet1.createRow((short) 0);
        // 填充标题
        for (String s : title) {
            Cell cell = row.createCell(i);
            cell.setCellValue(s);
            cell.setCellStyle(cellStyle);
            i++;
        }
        for (int j = 0; j < results.size(); j++) {
            Article article = results.get(j);
            Row row1 = sheet1.createRow((short) j + 1);
            Cell cell = row1.createCell(0);
            cell.setCellStyle(Contentstyle);
            cell.setCellValue(article.getId());
            cell = row1.createCell(1);
            cell.setCellStyle(Contentstyle);
            cell.setCellValue(article.getTitle());
            cell = row1.createCell(2);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(article.getUrlTitle());
            cell = row1.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(article.getDescription());
            cell = row1.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(article.getVersion() + "");
            cell = row1.createCell(5);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(article.getType());
        }
    }

}
