package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.Datasourceconfig;
import com.energicube.eno.monitor.repository.DatasourceconfigRepository;
import com.energicube.eno.monitor.service.DatasourceconfigService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Service
public class DatasourceconfigServiceImpl implements DatasourceconfigService {
    @Autowired
    private DatasourceconfigRepository datasourceconfigRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Datasourceconfig> findByPage(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<Datasourceconfig> datasources = datasourceconfigRepository
                .findAll(pageable);
        return datasources;
    }

    @Override
    @Transactional
    public void saveDatasourceconfig(Datasourceconfig datasourceconfig) {
        datasourceconfigRepository.save(datasourceconfig);
    }

    @Override
    @Transactional
    public void deleteDatasourceconfig(int datasourceconfiguid) {
        datasourceconfigRepository.delete(datasourceconfiguid);
    }

    @Override
    @Transactional(readOnly = true)
    public Datasourceconfig findOne(int id) {
        Datasourceconfig one = datasourceconfigRepository.findOne(id);
        return one;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Datasourceconfig> findAll() {
        List<Datasourceconfig> list = datasourceconfigRepository.findAll();
        if (list.size() == 0)
            return null;
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Datasourceconfig findOneByFk(String fk) {
        List<Datasourceconfig> list = datasourceconfigRepository
                .findByDatasourceconfigid(fk);
        if (list.size() == 0)
            return null;
        return list.get(0);
    }

    @Override
    public Connection connectDataBase(String id) {
        Connection conn = null;
        Datasourceconfig datasourceconfig = findOneByFk(id);
        String dbtype = datasourceconfig.getDbtype();
        String url = null;
        String driver = null;
        if ("oracle".equals(dbtype)) {
            driver = "oracle.jdbc.OracleDriver";
            url = "jdbc:oracle:thin:@" + datasourceconfig.getIpaddress() + ":"
                    + datasourceconfig.getPort() + ":"
                    + datasourceconfig.getDbname();
        }
        if ("mysql".equals(dbtype)) {
            driver = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://" + datasourceconfig.getIpaddress() + ":"
                    + datasourceconfig.getPort() + "/"
                    + datasourceconfig.getDbname();
        }
        if ("sqlserver".equals(dbtype)) {
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            url = "jdbc:sqlserver://" + datasourceconfig.getIpaddress();
            if (datasourceconfig.getPort() > 0) {
                url += ":" + datasourceconfig.getPort();
            }
            url += ";databaseName=" + datasourceconfig.getDbname();
        }
        if ("access".equals(dbtype)) {
            driver = "sun.jdbc.odbc.JdbcOdbcDriver";
            url = "jdbc:odbc:" + datasourceconfig.getDbname();
        }
        if (driver == null || url == null)
            return null;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url,
                    datasourceconfig.getUserid(),
                    datasourceconfig.getPassword());
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<List<String>> importDo(MultipartFile file) {
        List<List<String>> list = new ArrayList<List<String>>();

        try {
            HSSFWorkbook wookbook = new HSSFWorkbook(file.getInputStream());
            HSSFSheet sheet = wookbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                HSSFRow rowInt = sheet.getRow(0);
                HSSFRow row = sheet.getRow(i);
                List<String> rlist = new ArrayList<String>();
                int cells = rowInt.getLastCellNum();
                if (row != null) {
                    for (int j = 0; j < cells; j++) {
                        HSSFCell cell = row.getCell(j);
                        if (cell == null || cell.equals("")
                                || cell.equals("null")) {
                            rlist.add(null);
                        } else {
                            switch (cell.getCellType()) {
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    rlist.add(cell.getNumericCellValue() + "");
                                    break;
                                case HSSFCell.CELL_TYPE_STRING:
                                    if (cell.getStringCellValue().equals("")) {
                                        rlist.add(null);
                                    } else {
                                        rlist.add(cell.getStringCellValue());
                                    }
                                    break;
                                default:
                                    rlist.add(null);
                                    break;
                            }
                        }
                    }
                    list.add(rlist);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return list;
    }

    public void createExcel(ResultSet rs, String[] outs,
                            HttpServletResponse response, String name) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String newdate = sdf.format(new Date());
        // 创建一个EXCEL
        Workbook wb = new HSSFWorkbook();
        // 创建一个SHEET
        Sheet sheet1 = wb.createSheet(newdate);
        int i = 0;
        Font font = wb.createFont();
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        CellStyle Contentstyle = wb.createCellStyle();
        Contentstyle.setAlignment(CellStyle.ALIGN_RIGHT);
        Contentstyle.setFont(font);
        // 创建一行
        Row row = sheet1.createRow((short) 0);
        // 填充标题
        for (String s : outs) {
            Cell cell = row.createCell(i);
            cell.setCellValue(s);
            cell.setCellStyle(cellStyle);
            i++;
        }
        while (rs.next()) {
            Row row1 = sheet1.createRow((short) rs.getRow());
            for (int k = 0; k < i; k++) {
                Cell cell = row1.createCell(k);
                cell.setCellStyle(Contentstyle);
                cell.setCellValue(rs.getString(k + 1));
            }

            for (int w = 0; w < 2; w++) {
                sheet1.setColumnWidth(w, 3500);
            }
        }
        String format = sdf.format(new Date());
        OutputStream os = response.getOutputStream();
        response.setHeader("content-disposition", "attachment;filename=" + name
                + "_" + format + ".xls");
        wb.write(os);
        os.close();
    }

    public Map<String, String> findColumnsType(String datatable, Connection conn) {
        String sql = "select name,type_name(xtype) from syscolumns where id=object_id(?)";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<String, String> columns = new HashMap<String, String>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, datatable.toUpperCase());
            rs = pstmt.executeQuery();
            rs.next();
            while (rs.next()) {
                columns.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }

    public void importFromExcel(String[] tablenames, String[] colstype,
                                String[] excelnames, String datatable, List<List<String>> list,
                                Connection conn) {
        // 存储excel标题与列名关系
        HashMap<String, String> map = new HashMap<String, String>();
        // 存储excel标题与数据类型关系
        HashMap<String, String> map2 = new HashMap<String, String>();
        for (int i = 0; i < tablenames.length; i++) {
            if (excelnames[i] != "") {
                map2.put(excelnames[i], colstype[i]);
                map.put(excelnames[i], tablenames[i]);
            }
        }
        String[] titles = new String[list.get(0).size()];
        for (int i = 0; i < titles.length; i++) {
            titles[i] = list.get(0).get(i);
        }
        String s1 = "values(";
        String s2 = datatable + " (";
        for (int i = 0; i < titles.length; i++) {
            s2 += map.get(titles[i]);
            s1 += "?";
            if (i != titles.length - 1) {
                s1 += " ,";
                s2 += " ,";
            } else {
                s1 += " )";
                s2 += " )";
            }
        }
        PreparedStatement pstmt = null;
        String sql = "insert into " + s2 + s1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            pstmt = conn.prepareStatement(sql);
            for (int i = 1; i < list.size(); i++) {
                String[] val = new String[list.get(0).size()];
                for (int k = 0; k < val.length; k++) {
                    val[k] = list.get(i).get(k);
                }
                // 判断数据库中需要的类型，并转换
                for (int j = 0; j < val.length; j++) {
                    if ("varchar".equals(map2.get(titles[j])))
                        pstmt.setString(j + 1, val[j]);
                    if ("int".equals(map2.get(titles[j])))
                        pstmt.setInt(j + 1, Integer.parseInt(val[j]));
                    if ("float".equals(map2.get(titles[j])))
                        pstmt.setDouble(j + 1, Double.parseDouble(val[j]));
                    if ("datetime".equals(map2.get(titles[j]))) {
                        Date date = null;
                        try {
                            date = sdf.parse(val[j]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        pstmt.setDate(j + 1, new java.sql.Date(date.getTime()));
                    }
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection connectDataBase(String type, String name, String ip,
                                      int port, String user, String pwd) throws Exception {
        Connection conn = null;
        String dbtype = type;
        String url = null;
        String driver = null;
        if ("oracle".equals(dbtype)) {
            driver = "oracle.jdbc.OracleDriver";
            url = "jdbc:oracle:thin:@" + ip + ":"
                    + port + ":"
                    + name;
        }
        if ("mysql".equals(dbtype)) {
            driver = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://" + ip + ":"
                    + port + "/"
                    + name;
        }
        if ("sqlserver".equals(dbtype)) {
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            url = "jdbc:sqlserver://" + ip;
            if (port > 0) {
                url += ":" + port;
            }
            url += ";databaseName=" + name;
        }
        if ("access".equals(dbtype)) {
            driver = "sun.jdbc.odbc.JdbcOdbcDriver";
            url = "jdbc:odbc:" + name;
        }
        if (driver == null || url == null)
            return null;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(url,
                user,
                pwd);
        return conn;
    }
}
