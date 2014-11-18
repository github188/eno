package com.energicube.eno.monitor.web;

import com.energicube.eno.common.model.Message;
import com.energicube.eno.common.model.MessageResult;
import com.energicube.eno.monitor.model.DataSql;
import com.energicube.eno.monitor.model.Datasourceconfig;
import com.energicube.eno.monitor.service.DataSqlconfigService;
import com.energicube.eno.monitor.service.DatasourceconfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * SQL配置模块
 */
@Controller
@RequestMapping(value = "/okcsys/datasql")
public class DataSqlController extends BaseController {

//	private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private DataSqlconfigService dataSqlconfigService;

    @Autowired
    private DatasourceconfigService datasourceconfigService;

    /**
     * 数据源列表
     */
    @ModelAttribute("datasourceconfiglist")
    public List<Datasourceconfig> findAllDataSource() {
        return datasourceconfigService.findAll();
    }

    /**
     * 获取配置sql列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<DataSql> getDatasqlList() {
        return dataSqlconfigService.findAll();
    }

    /**
     * 配置sql新增表单
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showDatasqlCreationForm(Model model) {
        model.addAttribute("datasourceconfig", new DataSql());
        return "datasql/datasqledit";
    }

    /**
     * 保存
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public MessageResult<DataSql> processDataSqlCreationForm(@Valid DataSql dataSql,
                                                             BindingResult result, SessionStatus status,
                                                             RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
            return new MessageResult<DataSql>(new Message(false, result), dataSql);
        } else {
            dataSqlconfigService.saveDataSqlconfig(dataSql);
            status.setComplete();
            return new MessageResult<DataSql>(new Message(true, "更新成功"), dataSql);
        }
    }

    /**
     * 删除数据源
     */
    @RequestMapping(value = "/{datasqlid}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Message deleteDataSql(@PathVariable("datasqlid") Integer datasqlid) {
        Message message = new Message();
        if (datasqlid > 0) {
            dataSqlconfigService.deleteDataSqlconfig(datasqlid);
            message.setSuccess(true);
        }
        return message;
    }
}
