package com.energicube.eno.monitor.web;

import com.energicube.eno.common.model.Tree;
import com.energicube.eno.monitor.model.Department;
import com.energicube.eno.monitor.repository.DepartmentRepository;
import com.energicube.eno.monitor.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户登陆处理控制
 *
 * @author CHENPING
 */
@Controller
public class DepartmentController extends BaseController {

    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping(value = "/department/departmentList", method = RequestMethod.GET)
    public String initDepartmentView(Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "department/departmentList";
    }

    @RequestMapping(value = "/department/departmentListById", method = RequestMethod.GET)
    public String departmentListById(HttpServletRequest request,
                                     HttpServletResponse response, Model model) {
        String cudeptid = request.getParameter("cudeptid");
        List<Department> departments = new ArrayList<Department>();
        Department department = departmentRepository.findOne(Integer
                .valueOf(cudeptid));
        departments.add(department);
        model.addAttribute("departments", departments);
        return "department/departmentList";
    }

    @RequestMapping(value = "/department/departmentListByTree", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Tree> getEnergyitemdicts(HttpServletResponse response) {
        List<Tree> list = departmentService.departmentListByTreeComboTree();
        return list;
    }

    @RequestMapping(value = "/department/doSelect", method = RequestMethod.POST)
    public String doSelect(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        String deptnum = request.getParameter("deptnum");
        List<Department> departments = departmentRepository
                .findByDeptnumContaining(deptnum);
        model.addAttribute("departments", departments);
        return "department/departmentList";
    }

    /**
     * 新增用户信息
     */
    @RequestMapping(value = "/department/departmentAdd", method = RequestMethod.GET)
    public String initArticleForm(Model model) {
        Department department = new Department();
        model.addAttribute("department", department);
        return "department/departmentAdd";
    }

    @RequestMapping(value = "/department/doInsert", method = RequestMethod.POST)
    public String doInsert(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        try {
            Department department = new Department();
            department.setDeptnum(request.getParameter("deptnum"));
            department.setDeptorder(Integer.valueOf(request
                    .getParameter("deptorder")));
            department.setParent(request.getParameter("parent"));
            department.setDescription(request.getParameter("description"));
            department.setGm(request.getParameter("gm"));
            department.setType(request.getParameter("type"));
            departmentRepository.save(department);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "redirect:departmentList";
    }

    @RequestMapping(value = "/department/departmentUpdate")
    public String initDepartmentUpdateForm(HttpServletRequest request,
                                           HttpServletResponse response, Model model) {
        String cudeptid = request.getParameter("cudeptid");
        Department department = departmentRepository.findOne(Integer
                .valueOf(cudeptid));
        model.addAttribute("department", department);
        return "department/departmentUpdate";
    }

    @RequestMapping(value = "/department/doUpdate", method = RequestMethod.POST)
    public String doUpdate(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        String cudeptid = request.getParameter("cudeptid");
        String deptnum = request.getParameter("deptnum");
        String deptorder = request.getParameter("deptorder");
        String description = request.getParameter("description");
        String gm = request.getParameter("gm");
        String type = request.getParameter("type");
        String parent = request.getParameter("parent");

        Department department = new Department();
        department.setCudeptid(Integer.valueOf(cudeptid));
        department.setDeptnum(deptnum);
        department.setDeptorder(Integer.valueOf(deptorder));
        department.setDescription(description);
        department.setGm(gm);
        department.setType(type);
        String retruns = "";
        try {
            if (!parent.equals("")) {
                String pa = departmentRepository.findOne(
                        Integer.valueOf(cudeptid)).getParent();
                if (pa.equals("")
                        || departmentRepository
                        .findOne(Integer.valueOf(parent)).getParent()
                        .equals(cudeptid) || cudeptid.equals(parent)) {
                    model.addAttribute("error", "1");
                    retruns = "department/false";
                } else {
                    department.setParent(parent);
                    departmentRepository.save(department);
                    List<Department> departments = departmentRepository
                            .findAll();
                    model.addAttribute("departments", departments);
                    retruns = "redirect:departmentList";
                }
            } else {
                department.setParent(parent);
                departmentRepository.save(department);
                List<Department> departments = departmentRepository.findAll();
                model.addAttribute("departments", departments);
                retruns = "redirect:departmentList";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retruns;
    }

    @RequestMapping(value = "/department/deleteDepartment")
    public String deleteDepartment(HttpServletRequest request,
                                   HttpServletResponse response, Model model) {
        String cudeptid = request.getParameter("cudeptid");
        List<Department> departments = departmentRepository.findByParent(String
                .valueOf(cudeptid));
        if (departments.size() > 0) {
            model.addAttribute("error", "2");
            return "department/false";
        } else {
            departmentRepository.delete(Integer.valueOf(cudeptid));
            return "redirect:departmentList";
        }
    }
}
