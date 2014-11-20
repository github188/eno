package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.Expression;
import com.energicube.eno.monitor.service.ExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/okcsys")
public class ExpConfigController {

    @Autowired
    private ExpressionService expressionService;

    /**
     * 表达式配置页面
     *
     * @return
     */
    @RequestMapping(value = "/expression/index", method = RequestMethod.GET)
    public String showIndex(Model model) {
        Pageable pageable = new PageRequest(0, 5, Direction.ASC, "expname");
        Page<Expression> expressions = expressionService.findByPage(pageable);
        model.addAttribute("expressions", expressions);
        return "expression/config";
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/expression/get/{page}/{size}", method = RequestMethod.GET)
    public String showNext(Model model, @PathVariable int page, @PathVariable int size) {
        Pageable pageable = new PageRequest(page, size, Direction.ASC, "expname");
        Page<Expression> expressions = expressionService.findByPage(pageable);
        model.addAttribute("expressions", expressions);
        return "expression/config";
    }

    @RequestMapping(value = "/expression/get/all", method = RequestMethod.POST)
    @ResponseBody
    public List<Expression> getAll() {
        return expressionService.findAllExp();
    }

    /**
     * 新增或修改--保存
     *
     * @param expcode
     * @param expname
     * @param expcontent
     * @return
     */
    @RequestMapping(value = "/expression/save", method = RequestMethod.POST)
    @ResponseBody
    public boolean processSave(String expcode, String expname, String expcontent, String expindex) {
        Expression expression = new Expression();
        if (expindex != null && expindex != "") {
            expression.setExpindex(Long.valueOf(expindex));
        }
        expression.setExpcode(expcode);
        expression.setExpname(expname);
        expression.setExpcontent(expcontent);
        try {
            expressionService.saveOne(expression);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/expression/delete/{expindex}", method = RequestMethod.GET)
    public String processDelete(@PathVariable long expindex) {
        expressionService.deleteOne(expindex);
        return "redirect:/okcsys/expression/index";
    }
}
