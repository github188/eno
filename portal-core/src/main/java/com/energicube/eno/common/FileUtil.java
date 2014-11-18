package com.energicube.eno.common;

import com.energicube.eno.common.excel.ExcelService;
import com.energicube.eno.monitor.model.OkcMenu;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件工具类,包括文件目录创建、上传、下载、删除等常用操作
 *
 * @author CHENPING
 * @version 1.0
 */
public class FileUtil {

    private static final Log logger = LogFactory.getLog(FileUtil.class);

    // 视图的名称和标示编码
    static String LIST_VIEW = "LIST";
    static String STRUCTURE_VIEW = "STRUCTURE";
    static String PLAN_VIEW = "PLAN";
    static String LIST_VIEW_NAME = "设备列表";
    static String STRUCTURE_VIEW_NAME = "系统结构图";
    static String PLAN_VIEW_NAME = "系统平面图";
    static int COUNT = 2;

    /**
     * 上传文件到服务器 <br />
     * 注意： input名称必须为file
     *
     * @param request     HTTP请求对象
     * @param newFileName 重命名文件名
     * @param dirName     目标目录名称
     * @return 文件路径
     */
    public static String uploadFile(HttpServletRequest request,
                                    String newFileName, String dirName) throws Exception {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile mpFile = multipartRequest.getFile("file");// 获取文件
        String fileName = mpFile.getOriginalFilename();// 获取文件名称
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);// 获取后缀
        if (!"".equals(newFileName)) {
            fileName = newFileName + "." + suffix;// 自定义名称
        }
        String path = request.getSession().getServletContext().getRealPath("/")
                + "/" + dirName;// 设置文件保存路径
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String ret = null;
        try {
            SaveFileFromInputStream(mpFile.getInputStream(), path, fileName);

            if (!dirName.endsWith("\\")) {
                dirName += "/";
            }
            // 文件路径
            ret = dirName + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 保存文件
     *
     * @param stream   输入流
     * @param path     保存路径
     * @param filename 文件名称
     * @throws IOException
     */
    public static void SaveFileFromInputStream(InputStream stream, String path,
                                               String filename) throws IOException {
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String realPath = path + "\\" + filename;
        FileOutputStream fs = new FileOutputStream(realPath);
        byte[] buffer = new byte[1024 * 1024];
        int bytesum = 0;
        int byteread = 0;
        while ((byteread = stream.read(buffer)) != -1) {
            bytesum += byteread;
            fs.write(buffer, 0, byteread);
            fs.flush();
        }
        fs.close();
        stream.close();
    }

    /**
     * 删除指定的文件
     *
     * @param filePath 文件地址
     * @return if success is true,else false
     */
    public static boolean deleteFile(String filePath) {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            return false;
        }
        return targetFile.delete();
    }

    public static File[] getFiles(HttpServletRequest request, String path) {
        String realPath = request.getSession().getServletContext()
                .getRealPath("/") + "/"
                + path;
        File targetFile = new File(realPath);
        File[] files = targetFile.listFiles();
        return files;
    }

    /**
     * 保存小牛配置信息
     *
     * @param obj
     * @param filename
     * @param request
     */
    public static void saveconfigToFile(Object obj, String filename,
                                        HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/")
                + "/" + "resources/structures/Netvideo/Config";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fos = null;
        Writer wo = null;
        File f = new File(file + "/" + filename + ".ini");
        if (f.exists()) {
            f.delete();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String netVdio=objectMapper.writeValueAsString(obj);
            fos = new FileOutputStream(file + "/" + filename + ".ini", true);
            wo = new OutputStreamWriter(fos);
            wo.write(netVdio);
            wo.flush();
        } catch (Exception e) {
            logger.error("saveconfigToFile",e);
        } finally {
            try {
                if (wo != null)
                    wo.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                logger.error("saveconfigToFile is IOException ", e);
            }
        }

    }

    /**
     * 读取dat文件
     *
     * @param filename
     * @param request
     * @return
     */
    public static String readConfig(String filename, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/")
                + "/" + "resources/structures/Netvideo/Config/" + filename
                + ".ini";
        File f = new File(path);
        String str = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line = br.readLine()) != null) {
                str = str + line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return str;
    }

    /**
     * 驱动下载
     *
     * @param request
     * @param response
     * @param drivername
     * @throws IOException
     */
    public static void downLoadDriver(HttpServletRequest request,
                                      HttpServletResponse response, String drivername) {
        String filePath = request.getSession().getServletContext()
                .getRealPath("/")
                + "/"
                + "resources/structures/Netvideo/Driver/"
                + drivername;
        File f = new File(filePath);
        OutputStream os = null;
        BufferedInputStream bis = null;
        try {
            if (!f.exists()) {
                response.sendError(404, "file not fond");
            }
            bis = new BufferedInputStream(new FileInputStream(f));
            byte[] buf = new byte[1024];
            int len = 0;

            response.reset();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + f.getName());
            os = response.getOutputStream();
            while ((len = bis.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
                if (bis != null)
                    bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static String getCellValue(Cell cell) {
        if (cell != null) {
            // [ ChengKang 2014-07-26 ]
            // setMethod.invoke()将实际调用之前Java反射机制所获取到的Set方法，其中第一个参数是被Set赋值的对象（变量或元素），第二个参数是Set进去的值
            // 由于之前使用getMethod()方法获取的方法都符合“带有一个String类型参数”的规范，因此这里第二个参数也必须转换为String类型
            switch (cell.getCellType())                                                        // 判断单元格中数据的类型  [ ChengKang 2014-07-26 ]
            {
                case Cell.CELL_TYPE_BLANK:                            // 空
                case Cell.CELL_TYPE_ERROR:                            // 错误 ERROR
                    return "";
                case Cell.CELL_TYPE_BOOLEAN:                        // Boolean
                    return cell.getBooleanCellValue() ? "1" : "0";
                case Cell.CELL_TYPE_FORMULA:                        // 公式
                case Cell.CELL_TYPE_NUMERIC:                        // 数值
                    return String.valueOf((long) (cell.getNumericCellValue()));
                case Cell.CELL_TYPE_STRING:                        // 字符串
                    return cell.getStringCellValue();
            }
        }
        return "";
    }

    /**
     * 解析上传到服务器的菜单配置文件
     *
     * @param request     请求
     * @param dirName     配置文件相对于服务器内的路径
     * @param newFileName 文件名（不含“.后缀”），后缀需要通过对request的解析来获取
     * @throws Exception
     * @author ChengKang
     */
    public static OkcMenu[] analyzeMenuExcel(HttpServletRequest request, String dirName, String newFileName) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile mpFile = multipartRequest.getFile("file");// 获取文件
        String fileName = mpFile.getOriginalFilename();// 获取文件名称
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);// 获取后缀
        if (!"".equals(newFileName)) {
            fileName = newFileName + "." + suffix;// 自定义名称
        }
        String path = request.getSession().getServletContext().getRealPath("/") + "/" + dirName + "/" + fileName;

        File file = new File(path);
        Sheet sheet = ExcelService.getExcelSheetOneFile(file);        // 得Excel里第一个Sheet [ ChengKang 2014-07-26 ]

        int LineNum = sheet.getLastRowNum() - 1;                            // 获得表中最后一条记录的行号，去掉第一、第二行，（由于行号从0计算，因此减1）  [ ChengKang 2014-07-26 ]
        OkcMenu[] MenuItem = new OkcMenu[LineNum];                // 新建菜单项数组  [ ChengKang 2014-07-26 ]

        String[] ParentMenu = {"", "", "", ""};                                        // 用来保存上一级菜单的名称，最多四级  [ ChengKang 2014-08-25 ]
        int[] Position = {0, 0, 0, 0, 0};                                                    // 标记菜单项顺序的序号（10的倍数）

        String CurrentMenuName = "";                                            // 当前处理的菜单项名称
        int CurrentPosition = 0;                                                        // 当前菜单序号
        String CurrentParent = "";                                                    // 当前菜单的父级菜单
        String CurrentViews = "";                                                        // 当前菜单的视图编码
        String CurrentViewNames = "";                                            // 当前菜单的视图名称

        String cellValue = "";                                                            // 存储单元格值的临时变量

        for (int Line = 0; Line < LineNum; Line++)                                // n表示的是实际菜单记录数，对应Excel表中的行号是n+2 [ ChengKang 2014-08-25 ]
        {
            // 对菜单项数组中的元素新建OkcMenu对象，每个对象将用来存储一条菜单配置记录的信息  [ ChengKang 2014-07-26 ]
            MenuItem[Line] = new OkcMenu();

            // 菜单编码
            Cell CurrentCell = sheet.getRow(Line + 2).getCell(5);
            cellValue = getCellValue(CurrentCell);
            if (cellValue != "") {
                MenuItem[Line].setElementvalue(cellValue);
            } else {
                continue;        // 菜单编码是必填项，空缺将直接忽略该记录 [ ChengKang 2014-08-25 ]
            }

            // 处理菜单名以及菜单的层级关系 [ ChengKang 2014-08-25 ]
            CurrentCell = sheet.getRow(Line + 2).getCell(0);
            cellValue = getCellValue(CurrentCell);
            if (cellValue != "")                                                // 表明是1级目录
            {
                ParentMenu[0] = MenuItem[Line].getElementvalue();

                CurrentParent = "";
                CurrentMenuName = cellValue;
                Position[0] += 10;
                CurrentPosition = Position[0];
            } else {
                CurrentCell = sheet.getRow(Line + 2).getCell(1);
                cellValue = getCellValue(CurrentCell);
                if (cellValue != "")                                            // 表明是2级目录
                {
                    ParentMenu[1] = MenuItem[Line].getElementvalue();

                    CurrentParent = ParentMenu[0];
                    CurrentMenuName = cellValue;
                    Position[1] += 10;
                    CurrentPosition = Position[1];
                } else {
                    CurrentCell = sheet.getRow(Line + 2).getCell(2);
                    cellValue = getCellValue(CurrentCell);
                    if (cellValue != "")                                            // 表明是3级目录
                    {
                        ParentMenu[2] = MenuItem[Line].getElementvalue();

                        CurrentParent = ParentMenu[1];
                        CurrentMenuName = cellValue;
                        Position[2] += 10;
                        CurrentPosition = Position[2];
                    } else {
                        CurrentCell = sheet.getRow(Line + 2).getCell(3);
                        cellValue = getCellValue(CurrentCell);
                        if (cellValue != "")                                            // 表明是4级目录
                        {
                            ParentMenu[3] = MenuItem[Line].getElementvalue();

                            CurrentParent = ParentMenu[2];
                            CurrentMenuName = cellValue;
                            Position[3] += 10;
                            CurrentPosition = Position[3];
                        } else {
                            CurrentCell = sheet.getRow(Line + 2).getCell(4);
                            cellValue = getCellValue(CurrentCell);
                            if (cellValue != "")                                            // 表明是5级目录
                            {
                                ParentMenu[4] = MenuItem[Line].getElementvalue();

                                CurrentParent = ParentMenu[3];
                                CurrentMenuName = cellValue;
                                Position[4] += 10;
                                CurrentPosition = Position[4];
                            } else {
                                // 如果5个级别都没有，那就忽略该条记录
                                continue;
                            }
                        }
                    }
                }
            }

            // 对菜单名称、层级关系和顺序做设置
            MenuItem[Line].setHeaderdescription(CurrentMenuName);        // 菜单名称
            MenuItem[Line].setOwnerelement(CurrentParent);                    // 上级菜单
            MenuItem[Line].setPosition(CurrentPosition);                            // 显示序号


            // 图标名称
            CurrentCell = sheet.getRow(Line + 2).getCell(6);
            cellValue = getCellValue(CurrentCell);
            MenuItem[Line].setImage(cellValue);
            // URL
            CurrentCell = sheet.getRow(Line + 2).getCell(7);
            cellValue = getCellValue(CurrentCell);
            MenuItem[Line].setUrl(cellValue);

            // 设备列表视图
            CurrentCell = sheet.getRow(Line + 2).getCell(8);
            cellValue = getCellValue(CurrentCell);
            if (cellValue != "") {
                CurrentViews += LIST_VIEW;
                CurrentViewNames += LIST_VIEW_NAME;
                if (cellValue.equals("0")) {
                    MenuItem[Line].setDefaultView(LIST_VIEW);
                }
            }
            // 系统结构图
            CurrentCell = sheet.getRow(Line + 2).getCell(9);
            cellValue = getCellValue(CurrentCell);
            if (cellValue != "") {
                CurrentViews += (CurrentViews.length() > 0 ? "," : "");
                CurrentViews += STRUCTURE_VIEW;
                CurrentViewNames += (CurrentViewNames.length() > 0 ? "," : "");
                CurrentViewNames += STRUCTURE_VIEW_NAME;
                if (cellValue.equals("0")) {
                    MenuItem[Line].setDefaultView(STRUCTURE_VIEW);
                }
            }
            // 系统平面图
            CurrentCell = sheet.getRow(Line + 2).getCell(10);
            cellValue = getCellValue(CurrentCell);
            if (cellValue != "") {
                CurrentViews += (CurrentViews.length() > 0 ? "," : "");
                CurrentViews += PLAN_VIEW;
                CurrentViewNames += (CurrentViewNames.length() > 0 ? "," : "");
                CurrentViewNames += PLAN_VIEW_NAME;
                if (cellValue.equals("0")) {
                    MenuItem[Line].setDefaultView(PLAN_VIEW);
                }
            }

            MenuItem[Line].setViews(CurrentViews);
            MenuItem[Line].setViewnames(CurrentViewNames);
            CurrentViews = "";
            CurrentViewNames = "";

            // 是否可见
            CurrentCell = sheet.getRow(Line + 2).getCell(11);
            cellValue = getCellValue(CurrentCell);
            if (cellValue != "" && !cellValue.equals("0")) {
                MenuItem[Line].setVisible(true);
            } else {
                MenuItem[Line].setVisible(false);
            }

            // MENUID
            CurrentCell = sheet.getRow(Line + 2).getCell(12);
            cellValue = getCellValue(CurrentCell);
            if (cellValue != "") {
                MenuItem[Line].setMenuid(cellValue);
            }

            // 对所有菜单项都做相同的设置，即实际忽略该菜单项属性
            MenuItem[Line].setMenutype("");
            MenuItem[Line].setSubposition(0);
            MenuItem[Line].setElementtype("");

        }    // End for

        return MenuItem;        // 函数返回解析后获得到的OkcMenu对象数组
    }

    // 解析上传到服务器的菜单配置文件 [ ChengKang 2014-07-26 ]
    public static OkcMenu[] analyzeMenuExcelOld(HttpServletRequest request, String dirName, String newFileName) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile mpFile = multipartRequest.getFile("file");// 获取文件
        String fileName = mpFile.getOriginalFilename();// 获取文件名称
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);// 获取后缀
        if (!"".equals(newFileName)) {
            fileName = newFileName + "." + suffix;// 自定义名称
        }
        String path = request.getSession().getServletContext().getRealPath("/") + "/" + dirName + "/" + fileName;

        File file = new File(path);
        Sheet sheet = ExcelService.getExcelSheetOneFile(file);        // 得Excel里第一个Sheet [ ChengKang 2014-07-26 ]

        Row FirstRow = sheet.getRow(1);                                            // 得到第2行（第2行是列名，对应数据库里的表字段） [ ChengKang 2014-07-26 ]
        int MaxColIndex = FirstRow.getLastCellNum();                        // 得到第2行最大列序号  [ ChengKang 2014-07-26 ]
        String[] TableTitle = new String[MaxColIndex];                        // 用来记录列标题字段的数组  [ ChengKang 2014-07-26 ]
        for (int i = 0; i < MaxColIndex; i++) {
            if (FirstRow.getCell(i) != null) {
                TableTitle[i] = FirstRow.getCell(i).getStringCellValue();    // 取出每一列字段对应于数据库字段的名称  [ ChengKang 2014-07-26 ]
            }
        }

        int LineNum = sheet.getLastRowNum() - 1;                            // 获得表中最后一条记录的行号，去掉第一、第二行，分别表示“标题中文描述”和“数据库对应字段”（由于行号从0计算，因此减1）  [ ChengKang 2014-07-26 ]
        OkcMenu[] MenuItem = new OkcMenu[LineNum];                // 新建菜单项数组  [ ChengKang 2014-07-26 ]
        for (int n = 0; n < LineNum; n++)                                                // 对菜单项数组中的元素新建OkcMenu对象，每个对象将用来存储一条菜单配置记录的信息  [ ChengKang 2014-07-26 ]
        {
            MenuItem[n] = new OkcMenu();
        }

        //  [ ChengKang 2014-07-26 ]
        // 通过Java反射机制，获取配置文件中，每条记录里的菜单配置信息，并保存到MenuItem数组的每个元素里
        // 外层Col循环对配置文件中每一列做处理，每一列对应里数据库中的一个字段，而每个字段在OkcMenu对象中又有相应的成员
        // 根据之前TableTitle获取到的列名，可以通过字符串组合，获得对应于OkcMenu对象中相应成员的set方法，进而实现动态的调用
        // 内层Line循环处理每一行，即程序是按照“先列后行”的方式处理的，这是因为，同一列用的总是OkcMenu对象中同一个成员set方法
        for (int Col = 0; Col < MaxColIndex; Col++) {
            //  [ ChengKang 2014-07-26 ]
            // 根据TableTitle中保存的列名，组合成对应于OkcMenu对象中相应成员的set方法名
            // 方法名总是以“set”开头，之后跟着列名（仅首字母大写），保存在setMethodName中
            String setMethodName = "set" + TableTitle[Col].substring(0, 1).toUpperCase() + TableTitle[Col].substring(1);

            for (int Line = 0; Line < LineNum; Line++) {
                Class<? extends OkcMenu> c = MenuItem[Line].getClass();                // 获取对象的类引用，实际上得到的是OkcMenu.Class  [ ChengKang 2014-07-26 ]
                Method setMethod = c.getMethod(setMethodName, String.class);        // 在类（OkcMenu）中获得setMethodName所表示的方法，并且方法符合“带有一个String类型参数”的规范  [ ChengKang 2014-07-26 ]
                Cell CurrentCell = sheet.getRow(Line + 2).getCell(Col);                            // 当前需要处理的单元格  [ ChengKang 2014-07-26 ]
                if (CurrentCell != null)                                                                            // 判断需要获取的单元格是否为“空”，即单元格不能空白  [ ChengKang 2014-07-26 ]
                {
                    // [ ChengKang 2014-07-26 ]
                    // setMethod.invoke()将实际调用之前Java反射机制所获取到的Set方法，其中第一个参数是被Set赋值的对象（变量或元素），第二个参数是Set进去的值
                    // 由于之前使用getMethod()方法获取的方法都符合“带有一个String类型参数”的规范，因此这里第二个参数也必须转换为String类型
                    switch (CurrentCell.getCellType())                                                        // 判断单元格中数据的类型  [ ChengKang 2014-07-26 ]
                    {
                        case Cell.CELL_TYPE_BLANK:                            // 空
                            setMethod.invoke(MenuItem[Line], "");
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:                        // Boolean
                            setMethod.invoke(MenuItem[Line], CurrentCell.getBooleanCellValue() ? "1" : "0");        // 将Boolean值转换为"1"或"0"，在OkcMenu类中增加了相应的Set方法，处理"1"或"0"的参数
                            break;
                        case Cell.CELL_TYPE_ERROR:                                // 错误 ERROR
                            setMethod.invoke(MenuItem[Line], "");
                            break;
                        case Cell.CELL_TYPE_FORMULA:                        // 公式
                        case Cell.CELL_TYPE_NUMERIC:                        // 数值
                            String strValue = String.valueOf((long) (CurrentCell.getNumericCellValue()));        // 由于OkcMenu类中数值类型的成员都是Integer或Long
                            setMethod.invoke(MenuItem[Line], strValue);                                                    // 因此这里都将结果转换为long类型，目的是去掉小数点后面的部分  [ ChengKang 2014-07-26 ]
                            break;
                        case Cell.CELL_TYPE_STRING:                        // 字符串
                            setMethod.invoke(MenuItem[Line], CurrentCell.getStringCellValue());
                            break;
                    }
                } else {
                    setMethod.invoke(MenuItem[Line], "");                // 对于单元格为空的情况，传递空字符串  [ ChengKang 2014-07-26 ]
                }
            }    // End for Line
        }    // End for Col

        return MenuItem;        // 函数返回解析后获得到的OkcMenu对象数组

    }    // End Function


    /**
     * 解析数据库中的菜单配置数据，并生成Excel文件保存在服务器上，供前台下载
     *
     * @param request     请求
     * @param dirName     配置文件相对于服务器内的路径
     * @param Menus 文件名（不含“.后缀”）
     * @throws Exception
     * @author ChengKang
     */
    // 解析数据库中的菜单配置数据，并生成Excel文件 [ ChengKang 2014-07-27 ]
    public static String exportMenuExcel_old(HttpServletRequest request, String dirName, List<OkcMenu> Menus) throws Exception {
        String newFileName = "MenuConfig.xls";        // 导出的Excel文件名称  [ ChengKang 2014-07-27 ]
        String Dir = request.getSession().getServletContext().getRealPath("/") + "/" + dirName;
        File file = new File(Dir);
        if (!file.exists() && !file.isDirectory())                // 如果文件夹不存在则创建  [ ChengKang 2014-07-29 ]
        {
            file.mkdir();
        }
        String path = Dir + "/" + newFileName;

        ExportExcelUtil<OkcMenu> MenuXls = new ExportExcelUtil<OkcMenu>();

        // 导出Excel的列名数组（最前列增加一个空字段，因为OkcMenu有有一个VersionUID，但该字段没有set方法）  [ ChengKang 2014-07-27 ]
        String[] HeadTitles = {"NO.", "OKCMENUID", "MENUTYPE", "OWNERELEMENT", "POSITION", "SUBPOSITION", "ELEMENTVALUE", "ELEMENTTYPE", "HEADERDESCRIPTION", "IMAGE", "URL", "VISIBLE", "VIEWS", "MENUID", "DEFAULTVIEW"};

        // 输出流指定到path指定的XLS文件  [ ChengKang 2014-07-27 ]
        OutputStream out = new FileOutputStream(path);

        // 调用ExportExcelUtil标准Excel导出工具函数  [ ChengKang 2014-07-27 ]
        MenuXls.exportExcel_old("菜单配置列表", HeadTitles, Menus, out, "yyyy-mm-dd");

        return path;        // 返回导出的XLS文件的路径，供客户端下载

    }    // End Function


    /**
     * 解析数据库中的菜单配置数据，并生成Excel文件保存在服务器上，供前台下载
     *
     * @param request     请求
     * @param dirName     配置文件相对于服务器内的路径
     * @param Menus 文件名（不含“.后缀”）
     * @throws Exception
     * @author ChengKang
     */
    // 解析数据库中的菜单配置数据，并生成Excel文件 [ ChengKang 2014-09-05 ]
    public static String exportMenuExcel(HttpServletRequest request, String dirName, List<Map> Menus) throws Exception {
        String newFileName = "MenuConfig.xls";        // 导出的Excel文件名称 [ ChengKang 2014-09-05 ]
        String Dir = request.getSession().getServletContext().getRealPath("/") + "/" + dirName;
        File file = new File(Dir);
        if (!file.exists() && !file.isDirectory())                // 如果文件夹不存在则创建 [ ChengKang 2014-09-05 ]
        {
            file.mkdir();
        }
        String path = Dir + "/" + newFileName;

        ExportExcelUtil<OkcMenu> MenuXls = new ExportExcelUtil<OkcMenu>();

        // 导出Excel的列名数组（最前列增加一个空字段，因为OkcMenu有有一个VersionUID，但该字段没有set方法） [ ChengKang 2014-09-05 ]
        String[] HeadTitlesCn = {"一级", "二级", "三级", "四级", "五级", "菜单编码", "图标名称", "链接地址", "设备列表", "系统结构图", "系统平面图", "是否显示", "逻辑编号"};
        String[] HeadTitlesEn = {"No.1", "No.2", "No.3", "No.4", "No.5", "Elementvalue", "Image", "Url", "List", "Struct", "Plan", "Visible", "MenuID"};

        // 输出流指定到path指定的XLS文件  [ ChengKang 2014-07-27 ]
        OutputStream out = new FileOutputStream(path);

        // 调用ExportExcelUtil标准Excel导出工具函数  [ ChengKang 2014-07-27 ]
        MenuXls.exportExcel("菜单配置列表", HeadTitlesCn, HeadTitlesEn, Menus, out, "yyyy-mm-dd");

        return path;        // 返回导出的XLS文件的路径，供客户端下载

    }    // End Function

    /**
     * 获取驱动目录下的文件信息
     */
    public static List<String> getNetvideoFiles(String path, HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("/") + path;
        List<String> fileStrings = new ArrayList<String>();
        fileStrings.add(1 + "*" + 0 + "*" + "Driver" + "*" + "dire" + "*" + realPath);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fileStrings", fileStrings);
        map = getfilesString(1, realPath, map);
        COUNT = 2;
        return (List<String>) map.get("fileStrings");
    }

    /**
     * 获取文件列表，递归
     *
     * @param p_index 文件夹的id
     * @param path    文件夹路径
     * @param map     存放文件夹及文件信息
     * @return
     */
    static Map<String, Object> getfilesString(int p_index, String path, Map<String, Object> map) {
        File root = new File(path);
        File[] files = root.listFiles();
        List<String> fileStrings = (List<String>) map.get("fileStrings");
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String filepath = files[i].getAbsolutePath();
                int fileIndex = COUNT;
                String filename = files[i].getName();
                //编号*父编号*+路径
                if (files[i].isDirectory()) {
                    fileStrings.add(fileIndex + "*" + p_index + "*" + filename + "*" + "dire" + "*" + filepath);
                } else {
                    fileStrings.add(fileIndex + "*" + p_index + "*" + filename + "*" + "file" + "*" + filepath);
                }
                COUNT++;
                map.put("fileStrings", fileStrings);
                if (files[i].isDirectory()) {
                    map = getfilesString(fileIndex, files[i].getAbsolutePath(), map);
                }
            }
        }
        map.put("fileStrings", fileStrings);
        return map;
    }

    //文件夹的增删改
    public static void processDir(String type, String name, String path) {
        if (type.equals("add")) {
            String dirPath = path + "\\" + name;
            File file = new File(dirPath);
            file.mkdir();
        } else if (type.equals("modify")) {
            int b_num = path.lastIndexOf('\\');
            String newPathString = path.substring(0, b_num);
            File file = new File(newPathString + "\\" + name);
            file.mkdir();
        } else {
            File file = new File(path);
            deleteDirAndFile(file);
        }
    }

    //删除文件夹及其下的文件
    static void deleteDirAndFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isFile()) {
                    file2.delete();
                } else {
                    deleteDirAndFile(file2);
                }
            }
            file.delete();
        } else {
            file.delete();
        }
    }
}
