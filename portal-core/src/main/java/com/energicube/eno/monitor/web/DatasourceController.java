package com.energicube.eno.monitor.web;

import com.energicube.eno.common.model.Message;
import com.energicube.eno.common.model.MessageResult;
import com.energicube.eno.monitor.model.Datacolsrelation;
import com.energicube.eno.monitor.model.Dataieconfig;
import com.energicube.eno.monitor.model.Datasourceconfig;
import com.energicube.eno.monitor.service.DatacolsrelationService;
import com.energicube.eno.monitor.service.DataieconfigService;
import com.energicube.eno.monitor.service.DatasourceconfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Controller
@RequestMapping(value = "/okcsys/datasources")
public class DatasourceController extends BaseController {

    private Connection conn = null;
    List<List<String>> list = null;

    @Autowired
    private DatasourceconfigService datasourceconfigService;

    /**
     * 获取数据源列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Datasourceconfig> getDatasourceList() {
        return datasourceconfigService.findAll();
    }

    /**
     * 显示数据源新增表单
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showDatasourceCreationForm(Model model) {
        model.addAttribute("datasourceconfig", new Datasourceconfig());
        return "datasources/datasourceedit";
    }


    /**
     * 保存数据源
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public MessageResult<Datasourceconfig> processControlCreationForm(@Valid Datasourceconfig datasourceconfig,
                                                                      BindingResult result, SessionStatus status,
                                                                      RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
            return new MessageResult<Datasourceconfig>(new Message(false, result), datasourceconfig);
        } else {
            datasourceconfigService.saveDatasourceconfig(datasourceconfig);
            status.setComplete();
            return new MessageResult<Datasourceconfig>(new Message(true, "更新成功"), datasourceconfig);
        }
    }

    /**
     * 删除数据源
     */
    @RequestMapping(value = "/{datasourceconfiguid}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Message deleteSysControl(@PathVariable("datasourceconfiguid") Integer datasourceconfiguid) {
        Message message = new Message();
        if (datasourceconfiguid > 0) {
            datasourceconfigService.deleteDatasourceconfig(datasourceconfiguid);
            message.setSuccess(true);
        }
        return message;
    }

    /**
     * 连接数据库
     */
    @RequestMapping(value = "/connect/test", method = RequestMethod.POST)
    @ResponseBody
    public String datasourceConnect(String type, String name, String ip, int port, String user, String pwd,
                                    HttpServletResponse response) {
        try {
            conn = datasourceconfigService.connectDataBase(type, name, ip, port, user, pwd);
            return "1";
        } catch (Exception e) {
            return "2";
        }
    }

    /**
     * 读取excel文件
     */
    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
    public String readExcel(MultipartFile file, int dataieuid, Model model) {
        if (conn == null)
            return "redirect:/datasource/showByPage/0";
        //根据id查找到对象，进而找到表名
        Dataieconfig dataieconfig = dataieconfigService.findOne(dataieuid);
        String datatable = dataieconfig.getDatatable();
        Map<String, String> columns = datasourceconfigService.findColumnsType(datatable, conn);
        model.addAttribute("columns", columns);
        list = datasourceconfigService.importDo(file);
        String[] titles = new String[list.get(0).size()];
        int i = 0;
        for (String s : list.get(0)) {
            titles[i] = s;
            i++;
        }
        model.addAttribute("titles", titles);
        model.addAttribute("dataieconfig", dataieconfig);
        return "datasources/showColsrelation";
    }

    @Autowired
    private DataieconfigService dataieconfigService;
    @Autowired
    private DatacolsrelationService datacolsrelationService;

    /**
     * 导入导出配置首页
     */
    @RequestMapping(value = "/dataieconfig/index")
    public String showDataieIndex(Model model) {
        Page<Dataieconfig> page = dataieconfigService.findByPage(0, 5);//返回导入导出配置信息
        model.addAttribute("page", page);
        Dataieconfig dataieconfig = new Dataieconfig();
        model.addAttribute("dataieconfig", dataieconfig);
        List<Datasourceconfig> datasources = datasourceconfigService.findAll();//返回全部数据源信息
        List<Dataieconfig> dataies = page.getContent();
        Map<Dataieconfig, Datasourceconfig> iesources = new HashMap<Dataieconfig, Datasourceconfig>();
        for (Dataieconfig dief : dataies) {
            String sid = dief.getDatasourceconfigid();
            Datasourceconfig sfig = datasourceconfigService.findOneByFk(sid); //查找与导入导入配置相关联的数据源信息
            iesources.put(dief, sfig);
        }
        model.addAttribute("iesources", iesources);//返回全部导入导出配置与其相关联数据源信息
        model.addAttribute("datasources", datasources);//返回全部数据源信息
        return "datasources/dataieconfiglist";
    }

    /**
     * 查询所有表名ajax请求
     */
    @RequestMapping(value = "/alltablenames", method = RequestMethod.GET)
    @ResponseBody
    public List<String> findAllTable() {
        List<String> list = new ArrayList<String>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Name FROM cerp..SysObjects Where XType='U' ORDER BY Name ");
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 导入导出配置分页查询
     */
    @RequestMapping(value = "/dataieconfig/showByPage/{index}")
    public String showDataieByPage(@PathVariable int index, Model model) {
        Page<Dataieconfig> page = dataieconfigService.findByPage(index, 5);
        model.addAttribute("page", page);
        Dataieconfig dataieconfig = new Dataieconfig();
        model.addAttribute("dataieconfig", dataieconfig);
        List<Datasourceconfig> datasources = datasourceconfigService.findAll();
        List<Dataieconfig> dataies = page.getContent();
        Map<Dataieconfig, Datasourceconfig> iesources = new HashMap<Dataieconfig, Datasourceconfig>();
        for (Dataieconfig dief : dataies) {
            String sid = dief.getDatasourceconfigid();
            Datasourceconfig sfig = datasourceconfigService.findOneByFk(sid);
            iesources.put(dief, sfig);
        }
        model.addAttribute("iesources", iesources);
        model.addAttribute("datasources", datasources);
        return "datasources/dataieconfiglist";
    }

    /**
     * 添加修改导入导出配置
     */
    @RequestMapping(value = "/dataieconfig/new", method = RequestMethod.POST)
    public String processAddDataieconfigPage(@Valid Dataieconfig dataieconfig,
                                             BindingResult result) {
        dataieconfigService.saveDataieconfig(dataieconfig);
        return "redirect:/datasources/dataieconfig/index";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/dataieconfig/delete/{id}", method = RequestMethod.GET)
    public String deleteDataieconfig(@PathVariable int id) {
        dataieconfigService.deleteDataieconfig(id);
        return "redirect:/datasources/dataieconfig/index";
    }

    /**
     * 判断导入还是导出
     */
    @RequestMapping(value = "/redirectCtrl/{dataieuid}", method = RequestMethod.GET)
    public String redirectController(@PathVariable int dataieuid, Model model) {
        //配置
        Dataieconfig dataieconfig = dataieconfigService.findOne(dataieuid);
        //数据源id
        String datasourceconfigid = dataieconfig.getDatasourceconfigid();
        //连接数据源
        conn = datasourceconfigService.connectDataBase(datasourceconfigid);
        String configtype = dataieconfig.getConfigtype();
        String datatable = dataieconfig.getDatatable();
        String datacolumnconfigid = dataieconfig.getDatacolumnconfigid();
        if ("EXPORT".equals(configtype)) {
            List<Datacolsrelation> datacolsrelations = datacolsrelationService.findByDatacolId(datacolumnconfigid);
            if (datacolsrelations == null || datacolsrelations.size() == 0) {
                Map<String, String> columns = datasourceconfigService.findColumnsType(datatable, conn);
                for (Entry<String, String> em : columns.entrySet()) {
                    Datacolsrelation datacolsrelation = new Datacolsrelation();
                    datacolsrelation.setDatacolumnconfigid(dataieconfig
                            .getDatacolumnconfigid());
                    datacolsrelation.setIncolumnname(em.getKey());
                    datacolsrelations.add(datacolsrelation);
                }
            }
            model.addAttribute("datacolsrelations", datacolsrelations);
            model.addAttribute("dataieconfig", dataieconfig);
            return "manage/datacolsrelation";
        }
        model.addAttribute("dataieconfig", dataieconfig);
        return "datasources/uploadExcel";
    }

    /**
     * excel导出
     *
     * @param request  http请求
     * @param response http响应
     */
    @RequestMapping(value = "/datacolsrelation/export", method = RequestMethod.POST)
    public void saveAndExport(HttpServletRequest request, HttpServletResponse response) {
        //收集页面数据
        String[] uids = request.getParameterValues("datacolsrelationuid");
        String[] ids = request.getParameterValues("datacolumnconfigid");
        String[] ins = request.getParameterValues("incolumnname");
        String[] outs = request.getParameterValues("outcolumnname");
        //用户不填则默认为列名
        for (int i = 0; i < outs.length; i++) {
            if (outs[i] == "") outs[i] = ins[i];
        }
        //保存用户输入的列名
        for (int i = 0; i < uids.length; i++) {
            Datacolsrelation datacolsrelation = new Datacolsrelation();
            datacolsrelation.setDatacolsrelationuid(Integer.parseInt(uids[i]));
            datacolsrelation.setDatacolumnconfigid(ids[i]);
            datacolsrelation.setIncolumnname(ins[i]);
            datacolsrelation.setOutcolumnname(outs[i]);
            datacolsrelationService.saveDatacolsrelation(datacolsrelation);
        }
        Dataieconfig dataieconfig = dataieconfigService.findByDatacolumnconfigid(ids[0]);
        String datatable = dataieconfig.getDatatable();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select * from " + datatable);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            datasourceconfigService.createExcel(rs, outs, response, dataieconfig.getConfigname());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * excel导入
     *
     * @param datatable
     * @param request
     */
    @RequestMapping(value = "/datacolsrelation/import", method = RequestMethod.POST)
    public String savaAndImport(String datatable, HttpServletRequest request) {
        String[] tablenames = request.getParameterValues("tablenames");
        String[] excelnames = request.getParameterValues("excelnames");
        String[] colstype = request.getParameterValues("colstype");
        datasourceconfigService.importFromExcel(tablenames, colstype, excelnames, datatable, list, conn);
        return "redirect:/datasources/dataieconfig/index";
    }

}
