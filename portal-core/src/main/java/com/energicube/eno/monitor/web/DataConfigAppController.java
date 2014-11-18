package com.energicube.eno.monitor.web;

import com.energicube.eno.common.model.Message;
import com.energicube.eno.common.model.MessageResult;
import com.energicube.eno.monitor.model.DataConfigApp;
import com.energicube.eno.monitor.model.DataSql;
import com.energicube.eno.monitor.service.DataConfigAppService;
import com.energicube.eno.monitor.service.DataSqlconfigService;
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
 * 应用配置模块
 */
@Controller
@RequestMapping(value = "/okcsys/dataapp")
public class DataConfigAppController extends BaseController {

//	private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private DataConfigAppService dataConfigAppService;

    @Autowired
    private DataSqlconfigService dataSqlconfigService;

    /**
     * 配置SQL列表
     */
    @ModelAttribute("datasqllist")
    public List<DataSql> findAllDataSql() {
        return dataSqlconfigService.findAll();
    }

    /**
     * 获取配置app列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<DataConfigApp> getDataAppList() {
        return dataConfigAppService.findAll();
    }

    /**
     * 配置app新增表单
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showDataAppCreationForm(Model model) {
        model.addAttribute("dataConfigApp", new DataConfigApp());
        return "dataapp/dataappedit";
    }

    /**
     * 保存APP
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public MessageResult<DataConfigApp> processDataAppCreationForm(@Valid DataConfigApp dataConfigApp,
                                                                   BindingResult result, SessionStatus status,
                                                                   RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
            return new MessageResult<DataConfigApp>(new Message(false, result), dataConfigApp);
        } else {
            dataConfigAppService.saveDataConfigApp(dataConfigApp);
            status.setComplete();
            return new MessageResult<DataConfigApp>(new Message(true, "更新成功"), dataConfigApp);
        }
    }

    /**
     * 删除APP
     */
    @RequestMapping(value = "/{dataconfigappid}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Message deleteDataApp(@PathVariable("dataconfigappid") Integer dataconfigappid) {
        Message message = new Message();
        if (dataconfigappid > 0) {
            dataConfigAppService.deleteDataConfigApp(dataconfigappid);
            message.setSuccess(true);
        }
        return message;
    }
}
