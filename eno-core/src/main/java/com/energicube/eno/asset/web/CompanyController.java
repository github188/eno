package com.energicube.eno.asset.web;

import com.energicube.eno.asset.model.Companies;
import com.energicube.eno.asset.service.CompaniesService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.common.model.Message;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/companies")
public class CompanyController extends BaseAssetController {

    private CompaniesService companiesService;

    @Autowired
    public CompanyController(CompaniesService companiesService) {
        this.companiesService = companiesService;
    }

    /**
     * 显示公司列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showCompaniesList(Model model) {
        return "companies/listCompanies";
    }


    /**
     * 显示公司列表数据源
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<Companies> findLocationsWithDatatablesCriterias(HttpServletRequest request) {
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        DataSet<Companies> companies = companiesService.findCompaniesWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(companies, criterias);
    }


    /**
     * 显示公司列表
     */
    @RequestMapping(value = "/list/dialog", method = RequestMethod.GET)
    public String showCompaniesListDialog(@RequestParam(value = "type", required = false, defaultValue = "") String type, Model model) {
        model.addAttribute("type", type);
        return "dialog/dlgCompanies";
    }

    /**
     * 删除公司
     */
    @RequestMapping(value = "/delete/{companiesid}", method = RequestMethod.GET)
    public String processDeleteCompanies(@PathVariable("companiesid") long companiesid) {
        try {
            companiesService.deleteCompanies(companiesid);
            return "redirect:/companies/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "companies/editCompanyForm";
        }

    }


    /**
     * 新增公司信息
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationCompanyForm(Model model) {
        Companies companies = new Companies();
        model.addAttribute("companies", companies);
        return "companies/editCompanyForm";
    }

    /**
     * 校验公司代码是否已经存在
     *
     * @param company 公司代码
     * @return
     */
    @RequestMapping(value = "/new/check", method = RequestMethod.GET)
    @ResponseBody
    public String processCheckNewCompany(@RequestParam("company") String company) {
        if (StringUtils.hasLength(company)) {
            return companiesService.existCompany(company.toUpperCase()) ? "true" : "false";
        } else {
            return "false";
        }

    }

    /**
     * 保存新增公司信息
     *
     * @param companies {@link Companies} object
     * @param result    {@link BindingResult} object
     * @param status    {@link SessionStatus} object
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreationCompanyForm(@Valid Companies companies,
                                             BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "companies/editCompanyForm";
        } else {
            //判断是否存在相同的公司
            if (companiesService.existCompany(companies.getCompany())) {
                result.addError(new ObjectError("company", "公司标识已经存在!"));
                return "companies/editCompanyForm";
            } else {
                //保存公司信息
                companies = companiesService.saveCompanies(companies);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "公司新增成功");
                return "redirect:/companies/edit/" + companies.getCompaniesid();
            }
        }


    }

    /**
     * 编辑公司信息
     */
    @RequestMapping(value = "/edit/{companiesid}", method = RequestMethod.GET)
    public String initEditCompanyForm(@PathVariable("companiesid") long companiesid, Model model) {
        Companies companies = new Companies();
        if (companiesid > 0) {
            companies = companiesService.findOne(companiesid);
        }
        model.addAttribute("companies", companies);
        return "companies/editCompanyForm";
    }


    /**
     * 保存编辑信息
     */
    @RequestMapping(value = "/edit/{companiesid}", method = RequestMethod.PUT)
    public String processEditCompanyForm(@PathVariable("companiesid") long companiesid,
                                         @Valid Companies companies, BindingResult result, SessionStatus status,
                                         RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "companies/editCompanyForm";
        } else {
            try {
                //保存公司信息
                companies = companiesService.saveCompanies(companies);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "公司更新成功");
                return "redirect:/companies/edit/" + companies.getCompaniesid();
            } catch (Exception e) {
                result.addError(new ObjectError("companies", e.toString()));
                return "companies/editCompanyForm";
            }
        }
    }


    /**
     * 编辑公司地址
     */
    @RequestMapping(value = "/{companiesid}/address", method = RequestMethod.GET)
    public String initEditCompanyAddressFrom(@PathVariable("companiesid") long companiesid,
                                             Model model) {
        Companies companies = new Companies();
        if (companiesid > 0) {
            companies = companiesService.findOne(companiesid);
        }
        model.addAttribute("companies", companies);
        return "companies/editAddressForm";
    }


    /**
     * 保存编辑公司地址
     */
    @RequestMapping(value = "/{companiesid}/address", method = RequestMethod.POST)
    public String processEditCompanyAddressFrom(@PathVariable("companiesid") long companiesid,
                                                @Valid Companies companies, BindingResult result, SessionStatus status,
                                                RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "companies/editAddressForm";
        } else {
            try {
                //保存公司信息
                companies = companiesService.saveCompanies(companies);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "公司地址成功");
                return "redirect:/companies/" + companies.getCompaniesid() + "/address";
            } catch (Exception e) {
                result.addError(new ObjectError("companies", e.toString()));
                return "companies/editAddressForm";
            }
        }
    }

    /**
     * 显示公司的所有分支机构
     */
    @RequestMapping(value = "/{companiesid}/branch", method = RequestMethod.GET)
    public String showCompanyBranchList(@PathVariable("companiesid") long companiesid, Model model) {
        Companies companies = new Companies();
        Companies childCompany = new Companies();
        if (companiesid > 0) {
            companies = companiesService.findOne(companiesid);
            childCompany.setParentcompany(companies.getCompany());
        }
        model.addAttribute("companies", companies);
        model.addAttribute("childCompany", childCompany);
        return "companies/listBranch";
    }

    /**
     * 显示公司的所有分支机构数据
     */
    @RequestMapping(value = "/{companiesid}/branch", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<Companies> showCompanyBrachToDataTables(
            @PathVariable("companiesid") long companiesid, DataTablesRequestParams params) {
        Companies companies = new Companies();
        if (companiesid > 0) {
            companies = companiesService.findOne(companiesid);
            return companiesService.findBranchs(companies.getCompany(), params);
        } else {
            return null;
        }
    }

    /**
     * 保存公司分支机构信息
     */
    @RequestMapping(value = "/{companiesid}/branch/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processCompanyBranchForm(@PathVariable("companiesid") long companiesid, @Valid Companies childCompany,
                                            BindingResult result, SessionStatus status) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            String msg = result.getFieldError().getField() + "" + result.getFieldError().getDefaultMessage();
            message.setMsg(msg);
        } else {
            if (childCompany.getCompaniesid() == companiesid) {
                message.setMsg("不能添加自己为分支");
                return message;
            }
            if (companiesService.existBranch(childCompany.getParentcompany(), childCompany.getCompany())) {
                result.addError(new ObjectError("company", "分支中公司标识已经存在!"));
                message.setMsg(result.toString());
            } else {

                childCompany = companiesService.saveCompanies(childCompany);
                status.setComplete();
                message.setSuccess(true);
            }
        }
        return message;
    }

    @RequestMapping(value = "/{companiesid}/branch/delete", method = RequestMethod.POST)
    @ResponseBody
    public Message processDeleteBranch(@PathVariable("companiesid") long companiesid,
                                       @RequestParam("id") long id) {
        Message message = new Message();
        message.setSuccess(false);
        if (id > 0) {
            try {
                companiesService.deleteBranch(id);
                message.setSuccess(true);
            } catch (Exception e) {
                message.setMsg(e.toString());
                e.printStackTrace();
            }
        }
        return message;
    }

}
