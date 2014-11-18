package com.energicube.eno.monitor.web;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 联动信息模块
 */
@Controller
@RequestMapping("/linkage")
public class LinkageController extends BaseController {

    private static final Log logger = LogFactory.getLog(LinkageController.class);

    @Autowired
    private UclcLinkcellService uclcLinkcellService; // 规划中预案关联Service
    @Autowired
    private UclcActionService uclcActionService; // 动作Service
    @Autowired
    private UclcLinkageService uclcLinkageService; // 规划Service
    @Autowired
    private UclcConditionService uclcConditionService; // 事件库Service
    @Autowired
    private UclcCellService uclcCellService; // 预案Service

    // ============首页跳转=======================================================

    /**
     * 设备动作_事件库_首页
     */
    @RequestMapping(value = "/view_condition", method = RequestMethod.GET)
    public String viewCondition(Model model) {
        List<UclcCondition> list = uclcConditionService.findAll(); // 查找事件库中所有事件
        model.addAttribute("list", list);
        return "linkage/view_condition";
    }

    /**
     * 设备动作-联动预案_首页
     */
    @RequestMapping(value = "/view_cell", method = RequestMethod.GET)
    public String viewCell(Model model) {
        List<UclcCell> list = uclcCellService.findAll(); // 查找全部预案信息
        model.addAttribute("list", list);
        return "linkage/view_cell";
    }

    /**
     * 设备动作-联动规划_首页
     */
    @RequestMapping(value = "/view_linkall", method = RequestMethod.GET)
    public String viewLinkage(Model model) {
        List<UclcLinkage> list = uclcLinkageService.findAll();// 查找全部规划信息
        model.addAttribute("list", list);
        return "linkage/view_linkall";
    }

    // ============编辑页面跳转=======================================================

    /**
     * 编辑事件库页面跳转
     *
     * @param m      操作类型标识，用以区分添加和编辑操作
     * @param target 编辑对象类型，用以区分是对哪个子类进行编辑
     * @param id     被操作对象的唯一标识
     * @param model
     * @return
     */
    @RequestMapping(value = "/forward/{m}/{target}/{id}", method = RequestMethod.GET)
    public String forwardEdit(@PathVariable String m,
                              @PathVariable String target, @PathVariable int id, Model model) {
        /* 新建map，key为数据库中保存的动作类型的值，value为用以前台显示的相应名称 */
        HashMap<Integer, String> maplist = new HashMap<Integer, String>();
        maplist.put(1, "设备控制");
        maplist.put(2, "视频控制");
        maplist.put(3, "脚本命令");
		/*
		 * maplist.put(4, "消防广播"); maplist.put(5, "引导屏幕显示"); maplist.put(6,
		 * "电话通知"); maplist.put(7, "VB脚本"); maplist.put(8, "扩展命令");
		 */
        if (m.equals("add")) { // 添加操作获取相应数据方法
            if (target.equals("condition")) { // 添加事件库
                // 添加事件库事件页面中无需其它数据，故无其它操作
            } else if (target.equals("cell")) { // 添加预案
                model.addAttribute("actionNames", maplist); // 传入动作分类信息
            } else if (target.equals("linkall")) { // 添加规划
                model.addAttribute("conditionList",
                        uclcConditionService.findAll()); // 查找全部事件
                model.addAttribute("cellList", uclcCellService.findAll()); // 查找全部预案信息
                model.addAttribute("linkcellList", null); // 查找指定规划中的全部预案信息
            }
        } else { // 修改操作获取相应数据方法
            Object obj = null;
            if (target.equals("condition")) { // 编辑事件库
                obj = (UclcCondition) uclcConditionService.findOne(id);// 编辑事件库页面中需显示原有信息，故在此查询出需编辑事件的详细信息
            } else if (target.equals("cell")) { // 编辑预案
                obj = (UclcCell) uclcCellService.findOne(id);// 查找指定预案信息
                model.addAttribute("actionNames", maplist); // 传入动作分类信息
                model.addAttribute("actionList",
                        uclcActionService.findByCellid(id)); // 查找预案中的动作
            } else if (target.equals("linkall")) { // 编辑规划
                obj = (UclcLinkage) uclcLinkageService.findOne((long) id); // 根据id查找规划
                model.addAttribute("conditionList",
                        uclcConditionService.findAll()); // 查找全部动作信息
                model.addAttribute("cellList", uclcCellService.findAll()); // 查找全部预案信息
                model.addAttribute("linkcellList",
                        uclcLinkcellService.findByLinkageid((long) id)); // 查找指定规划中的全部预案信息
            }
            model.addAttribute("obj", obj); // 放入编辑时所需显示的数据
        }
        model.addAttribute("m", m); // 放入页面标识，用以拼接转跳页面名称
        return "linkage/edit_" + target; // 转跳到相应编辑页面
    }

    // ============添加页面=======================================================

    /**
     * 保存事件对象，并查询所有事件，转跳到事件库首页显示
     *
     * @param uclcCondition 前台提交过来的事件对象
     * @param model
     * @return
     */
    @RequestMapping(value = "/add_condition")
    public String addCondition(UclcCondition uclcCondition, Model model) {
        uclcCondition.setUpdatet(new Date()); // 设置更新时间
        uclcConditionService.save(uclcCondition); // 保存事件对象
        return "redirect:/linkage/view_condition";
    }

    @RequestMapping(value = "/importExcel")
    public String importExcel(HttpServletRequest request, Model model) {
        String source = request.getParameter("source");
        model.addAttribute("source", source);
        return "linkage/importExcel"; // 转跳到相应导入页面
    }

    @RequestMapping(value = "/importExcelData")
    public String importExcelData(HttpServletRequest request, Model model) {
        String source = request.getParameter("source");

        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        MultipartFile mf = multipartRequest.getFile("file");
        String msg = importExcel(mf, source);
        model.addAttribute("uploadStatus", msg);

        return "linkage/view_condition";
    }

    /**
     * 添加预案
     *
     * @param uclcCell 预案对象
     * @param ma       预案动作对象集合
     * @param model
     * @return
     */
    @RequestMapping(value = "/add_cell", method = RequestMethod.POST)
    public String addCell(UclcCell uclcCell, ModelArray ma, Model model) {
        uclcCell.setUpdatet(new Date());// 保存新建修改时间
        uclcCellService.save(uclcCell);
		/*
		 * 当集合ma中的动作集合不为空时，对该集合进行遍历，并将其所属预案id保存
		 */
        if (ma.getUclcActionArray() != null) {
            List<UclcAction> actionList = Arrays
                    .asList(ma.getUclcActionArray());// 获取ma中的动作对象集合
            for (UclcAction action : actionList) {
                action.setCellid(uclcCell.getCellid());
                action.setUpdatet(new Date());// 保存新建修改时间
                if (action.getActiontype() != null) {
                    uclcActionService.save(action);
                }
            }
        }
        return "redirect:/linkage/view_cell";
    }

    /**
     * 添加规划信息
     *
     * @param uclcLinkage 规划信息对象
     * @param ma          规划中的预案关联集合
     * @param model
     * @return
     */
    @RequestMapping(value = "/add_linkall", method = RequestMethod.POST)
    public String addLinkage(UclcLinkage uclcLinkage, Model model, UclcLinkcell uclcLinkcell, HttpServletRequest request) {
        try {
            uclcLinkage.setUpdatet(new Date()); // 更新规划修改时间
            uclcLinkageService.save(uclcLinkage); // 保存规划信息

            String offsetts = request.getParameter("offsetts");
            String cellnames = request.getParameter("cellnames");
            String shiftsnames = request.getParameter("shiftsnames");
            if (StringUtils.hasLength(cellnames)) {
                saveLinkCell(uclcLinkage.getLinkageid(), cellnames, offsetts, shiftsnames);
            }
        } catch (Exception e) {
            logger.error("保存规划中的预案关联集合出错了！");
        }

        return "redirect:/linkage/view_linkall";
    }

    // 保存关联的预案信息
    private void saveLinkCell(long linkageid, String cellnames,
                              String offsetts, String shiftsnames) {
        String[] cells = cellnames.split(",");
        String[] offs = offsetts.split(",");
        String[] shifts = shiftsnames.split(",");
        for (int i = 0; i < cells.length; i++) {
            UclcLinkcell uclclinkcell = new UclcLinkcell();
            uclclinkcell.setCellname(cells[i]);
            uclclinkcell.setLinkageid(linkageid);
            uclclinkcell.setShiftname(shifts[i]);
            uclclinkcell.setOffsett(Integer.parseInt(offs[i]));
            uclclinkcell.setUpdatet(new Date());
            uclcLinkcellService.save(uclclinkcell);// 保存
        }
    }

    // ============编辑页面=======================================================

    /**
     * 编辑事件库
     */
    @RequestMapping(value = "/edit_condition")
    public String editCondition(int condid, String condname,
                                String condcomment, String condexp, Model model) {
        UclcCondition obj = new UclcCondition();
        obj.setCondid(condid);
        obj.setCondcomment(condcomment);
        obj.setCondexp(condexp);
        obj.setCondname(condname);
        obj.setUpdatet(new Date());
        uclcConditionService.edit(obj);
        return "redirect:/linkage/view_condition";
    }

    /**
     * 编辑预案
     *
     * @param uclcCell 预案对象
     * @param ma       预案相关联动作对象集合
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit_cell", method = RequestMethod.POST)
    public String editCell(UclcCell uclcCell, ModelArray ma, Model model) {
        uclcCell.setUpdatet(new Date()); // 设置预案更新时间
        uclcCellService.edit(uclcCell); // 保存修改

        if (ma.getUclcActionArray() != null) { // 当预案相关联动作集合不为null时，才执行
            List<UclcAction> actionList = Arrays
                    .asList(ma.getUclcActionArray());// 获取对象集合
            for (UclcAction action : actionList) { // 遍历动作集合
                action.setCellid(uclcCell.getCellid()); // 设置动作所属预案id
                action.setUpdatet(new Date()); // 设置动作更新时间
                if (action.getActiontype() != null) { // 判断动作是否有效
                    uclcActionService.save(action); // 保存
                }
            }
        }
        return "redirect:/linkage/view_cell";
    }

    /**
     * 编辑规划
     *
     * @param uclcLinkage
     * @param ma
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit_linkall", method = RequestMethod.POST)
    public String editLinkage(UclcLinkage uclcLinkage, ModelArray ma, Model model, HttpServletRequest request) {
        uclcLinkage.setUpdatet(new Date()); // 更新规划修改时间
        uclcLinkageService.update(uclcLinkage); // 保存并更新规划信息
        List<UclcLinkcell> cells = uclcLinkcellService
                .findByLinkageid(uclcLinkage.getLinkageid()); // 规划中原预案信息
        uclcLinkcellService.del(cells);// 删除已移除的预案关联信息

        try {
            String cellnames = request.getParameter("cellnames");
            String offsetts = request.getParameter("offsetts");
            String shiftsnames = request.getParameter("shiftsnames");
            if (StringUtils.hasLength(cellnames)) {
                saveLinkCell(uclcLinkage.getLinkageid(), cellnames, offsetts, shiftsnames);
            }
        } catch (Exception e) {
            logger.error("修改的时候，保存规划中的预案关联集合出错了！");
        }

        return "redirect:/linkage/view_linkall";
    }

    // ============删除=======================================================

    /**
     * 删除设备动作各子部分指定数据，如删除事件库、删除联动预案等
     *
     * @param targe 用来区分删除数据所属类别，及删除后转跳的页面标记
     * @param id    指定数据在数据库中的唯一标记
     * @param model
     * @return
     */
    @RequestMapping(value = "/del/{target}/{id}")
    public String del(@PathVariable String target, @PathVariable int id,
                      Model model) {
        List<?> list = null;
        if (target.equals("condition")) {
            uclcConditionService.del(id); // 删除指定事件
            list = uclcConditionService.findAll();
        } else if (target.equals("cell")) {
            uclcCellService.del(id);// 删除指定预案
            List<UclcAction> actions = uclcActionService.findByCellid(id);// 获取该预案中的动作信息
            uclcActionService.del(actions);// 删除预案中的所有动作
            list = uclcCellService.findAll();
        } else if (target.equals("linkall")) {
            uclcLinkageService.del((long) id);// 删除规划
            List<UclcLinkcell> cellLinks = uclcLinkcellService
                    .findByLinkageid((long) id); // 查找预案关联信息
            uclcLinkcellService.del(cellLinks);// 批量删除预案关联信息
            list = uclcLinkageService.findAll();
        }
        model.addAttribute("list", list);
        return "linkage/view_" + target;
    }

    /**
     * 删除子类数据
     *
     * @param m        操作类型标识
     * @param target   父类id
     * @param targetId 子类id
     * @param id       被删除记录id
     * @param model
     * @return
     */
    @RequestMapping(value = "/del/{m}/{target}/{targetId}/{id}")
    public String del(@PathVariable String m, @PathVariable String target,
                      @PathVariable int targetId, @PathVariable int[] id, Model model) {
        if (target.equals("cell")) {
			/* 新建map，key为数据库中保存的动作类型的值，value为用以前台显示的相应名称 */
            HashMap<Integer, String> maplist = new HashMap<Integer, String>();
            maplist.put(1, "设备控制");
            maplist.put(2, "视频控制");
            maplist.put(3, "脚本命令");
			/*
			 * maplist.put(4, "消防广播"); maplist.put(5, "引导屏幕显示"); maplist.put(6,
			 * "电话通知"); maplist.put(7, "VB脚本"); maplist.put(8, "扩展命令");
			 */
            for (int i = 0; i < id.length; i++) {
                uclcActionService.del(id[i]); // 删除指定动作
            }
        }
        return "linkage/edit_" + target;
    }

    /**
     * 获取事件库数据
     */
    @RequestMapping(value = "/eventlist", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    DataTablesResponse<UclcCondition> showConditionDataSource(DataTablesRequestParams params) {
        return uclcConditionService.findAllCondiotionToDataTables(params);
    }

    /**
     * 获取联动预案数据
     */
    @RequestMapping(value = "/celllist", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    DataTablesResponse<UclcCell> showCellDataSource(DataTablesRequestParams params) {
        return uclcCellService.findAllCellToDataTables(params);
    }

    /**
     * 获取联动预案数据
     */
    @RequestMapping(value = "/linkalllist", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    DataTablesResponse<UclcLinkage> showLinkAllDataSource(DataTablesRequestParams params) {
        return uclcLinkageService.findAllLinkAllToDataTables(params);
    }

    /**
     * 获取事件库快捷输入的变量数据
     */
    @RequestMapping(value = "/getInputList", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<UcTag> getInputList() {
        return uclcConditionService.findAllUcTagNames();
    }

    @RequestMapping(value = "/exportCondition", method = RequestMethod.GET)
    public void exportCondition(HttpServletRequest request, HttpServletResponse response) {

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            String storeName = System.currentTimeMillis() + ".xls";
            String contentType = "application/octet-stream";
            String patternExcelPath = "\\uploadfiles\\";
            String ctxPath = request.getSession().getServletContext().getRealPath("/") + patternExcelPath;

            File file = new File(ctxPath);
            if (!file.isDirectory()) {
                file.mkdir();
            }
            String downLoadPath = ctxPath + storeName;
            logger.info("--downLoadPath--" + downLoadPath);
            String source = request.getParameter("source");
            writeToExcel(source, downLoadPath);

            //向客流端返回文件流
            request.setCharacterEncoding("UTF-8");
            long fileLength = new File(downLoadPath).length();

            response.setContentType(contentType);
            response.setHeader("Content-disposition", "attachment; filename=" + new String(storeName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (IOException io) {
            logger.error("---file export fail---", io);
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (Exception io) {
                logger.error("--file export fail-2--", io);
            }
        }
    }

    /**
     * 将联动事件写入EXCEL
     *
     * @param downLoadPath
     */
    private void writeToExcel(String source, String downLoadPath) {

        List<UclcCondition> condList = new ArrayList<>();
        List<UclcCell> cellList = new ArrayList<>();
        int loop = 0;
        String[] headers = {"事件名称", "事件描述", "触发条件"};
        if (StringUtils.hasLength(source)) {
            if ("condition".equalsIgnoreCase(source)) { // 导出联动事件
                condList = uclcConditionService.findAll();
                loop = condList.size();
            } else if ("cell".equalsIgnoreCase(source)) { // 导出联动预案
                cellList = uclcCellService.findAll();
                loop = cellList.size();
                headers = new String[]{"预案名称", "预案描述"};
            }
        }

        Workbook wb = new HSSFWorkbook();// 创建一个EXCEL
        Sheet sheet1 = wb.createSheet("联动事件");// 创建一个SHEET
        Row row = sheet1.createRow((short) 0);// 创建第一行标题
        // 内容样式
        CellStyle contentstyle = wb.createCellStyle();
        contentstyle.setAlignment(CellStyle.ALIGN_RIGHT);

        // 创建列
        for (int column = 0; column < headers.length; column++) {
            Cell cell = row.createCell(column);
            cell.setCellStyle(contentstyle);
            cell.setCellValue(headers[column]);
            sheet1.setColumnWidth(column, headers[column].getBytes().length * 1 * 256); // 设置列宽自适应
        }

        int index = 0;
        for (int i = 0; i < loop; i++) {
            index++;
            row = sheet1.createRow(index);
            String[] values = new String[3];

            if (StringUtils.hasLength(source)) {
                if ("condition".equalsIgnoreCase(source)) { // 导出联动事件
                    UclcCondition uclcCondition = (UclcCondition) condList.get(i);
                    values = new String[]{uclcCondition.getCondname(), uclcCondition.getCondcomment(), uclcCondition.getCondexp()};
                } else if ("cell".equalsIgnoreCase(source)) { // 导出联动预案
                    UclcCell uclcCell = (UclcCell) cellList.get(i);
                    values = new String[]{uclcCell.getCellname(), uclcCell.getCellcomment()};
                }
            }

            for (int k = 0; k < headers.length; k++) {
                Cell cell = row.createCell(k);
                cell.setCellStyle(contentstyle);
                String val = (values[k] == null ? "" : values[k]);
                cell.setCellValue(val);
                logger.info(k + "--" + val + "--" + (val.getBytes().length * 1 * 256 + 500));
                sheet1.setColumnWidth(k, val.getBytes().length * 1 * 256 + 500); // 设置列宽自适应
            }
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(downLoadPath);
            wb.write(fileOut);
        } catch (IOException e) {
            logger.error("写入excel文件的时候报错了！");
        } finally {
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 导入Excel
     *
     * @param mf
     * @param type
     * @return
     */
    public String importExcel(MultipartFile mf, String source) {
        String returnStr = "success";

        try {
            String[][] result = getData(mf, 1);

            if ("condition".equalsIgnoreCase(source)) { // 导入事件
                importConditionExcel(result);
            } else if ("cell".equalsIgnoreCase(source)) { // 导入预案
                importCellExcel(result);
            }

            returnStr = "success";
        } catch (Exception e) {
            returnStr = "failure";
            e.printStackTrace();
        }

        return returnStr;
    }

    /**
     * 导入联动事件
     *
     * @param result
     */
    public void importConditionExcel(String[][] result) {
        int rowLength = result.length;

        logger.info("--导入的excel行数--" + rowLength);
        for (int i = 0; i < rowLength; i++) {

            UclcCondition ucon = new UclcCondition();

            for (int j = 0; j < result[i].length; j++) {

                if (j == 0 && !"".equals(result[i][j])) { // 第1列的数据不为空

                    ucon.setCondname(result[i][j]);

                }

                if (j == 1 && !"".equals(result[i][j])) { // 第2列的数据不为空

                    ucon.setCondcomment(result[i][j]);

                }

                if (j == 2 && !"".equals(result[i][j])) { // 第3列的数据不为空

                    ucon.setCondexp(result[i][j]);

                }

            }

            ucon.setUpdatet(new Date());
            uclcConditionService.save(ucon);

        }
    }

    /**
     * 导入联动预案
     *
     * @param result
     */
    public void importCellExcel(String[][] result) {
        int rowLength = result.length;

        logger.info("--导入的excel行数--" + rowLength);
        for (int i = 0; i < rowLength; i++) {

            UclcCell ucell = new UclcCell();
            for (int j = 0; j < result[i].length; j++) {

                if (j == 0 && !"".equals(result[i][j])) { // 第1列的数据不为空

                    ucell.setCellname(result[i][j]);

                }

                if (j == 1 && !"".equals(result[i][j])) { // 第2列的数据不为空

                    ucell.setCellcomment(result[i][j]);

                }

            }

            ucell.setUpdatet(new Date());
            uclcCellService.save(ucell);

        }
    }

    /**
     * 获取excel数据
     *
     * @param file
     * @param ignoreRows
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String[][] getData(MultipartFile file, int ignoreRows) throws FileNotFoundException, IOException {

        List<String[]> result = new ArrayList<String[]>();

        int rowSize = 0;

        BufferedInputStream in = new BufferedInputStream(file.getInputStream());

        // 打开HSSFWorkbook

        POIFSFileSystem fs = new POIFSFileSystem(in);

        HSSFWorkbook wb = new HSSFWorkbook(fs);

        HSSFCell cell = null;

        HSSFSheet st = wb.getSheetAt(0);

        // 第一行为标题，不取

        for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {

            HSSFRow row = st.getRow(rowIndex);

            if (row == null) {

                logger.info(rowIndex + "row为null");

                continue;

            }

            int tempRowSize = row.getLastCellNum() + 1;

            if (tempRowSize > rowSize) {

                rowSize = tempRowSize;

            }

            String[] values = new String[rowSize];

            Arrays.fill(values, "");

            boolean hasValue = false;

            for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {

                String value = "";

                cell = row.getCell(columnIndex);

                if (cell != null) {

                    // 注意：一定要设成这个，否则可能会出现乱码
                    // cell.setEncoding(HSSFCell.ENCODING_UTF_16);

                    switch (cell.getCellType()) {

                        case HSSFCell.CELL_TYPE_STRING:

                            value = cell.getStringCellValue();

                            break;

                        case HSSFCell.CELL_TYPE_NUMERIC:

                            if (HSSFDateUtil.isCellDateFormatted(cell)) {

                                Date date = cell.getDateCellValue();

                                if (date != null) {

                                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);

                                } else {

                                    value = "";

                                }

                            } else {

                                value = new DecimalFormat("0").format(cell.getNumericCellValue());

                            }

                            break;

                        case HSSFCell.CELL_TYPE_FORMULA:

                            // 导入时如果为公式生成的数据则无值

//						if (!cell.getStringCellValue().equals("")) {
//
//							value = cell.getStringCellValue();
//
//						} else {

                            value = cell.getNumericCellValue() + "";

//						}

                            break;

                        case HSSFCell.CELL_TYPE_BLANK:

                            break;

                        case HSSFCell.CELL_TYPE_ERROR:

                            value = "";

                            break;

                        case HSSFCell.CELL_TYPE_BOOLEAN:

                            value = (cell.getBooleanCellValue() == true ? "Y" : "N");

                            break;

                        default:

                            value = "";

                    }

                }

                if (columnIndex == 0 && columnIndex == 1
                        && columnIndex == 2 && columnIndex == 3
                        && columnIndex == 4 && value.trim().equals(""))
                    break;

                values[columnIndex] = rightTrim(value);

                hasValue = true;

            }

            if (hasValue) {

                result.add(values);

            }

        }

        in.close();

        String[][] returnArray = new String[result.size()][rowSize];

        for (int i = 0; i < returnArray.length; i++) {

            returnArray[i] = (String[]) result.get(i);

        }

        return returnArray;

    }

    /**
     * 去掉字符串右边的空格
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
    public static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20)
                break;
            length--;
        }
        return str.substring(0, length);
    }
}
