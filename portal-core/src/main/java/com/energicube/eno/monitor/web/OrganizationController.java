package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.Organization;
import com.energicube.eno.monitor.repository.OrganizationRepository;
import com.energicube.eno.monitor.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 用户登陆处理控制
 *
 * @author CHENPING
 */
@Controller
@RequestMapping("/okcsys/okcorghier")
public class OrganizationController extends BaseController {

    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initOrganizationView(Model model) {
        List<Organization> organizations = organizationRepository.findAll();
        model.addAttribute("organizations", organizations);
        return "organization/organizationList";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String initOrganizationForm(Model model) {
        Organization organization = new Organization();
        model.addAttribute("organization", organization);
        return "organization/organizationAdd";
    }

    @RequestMapping(value = "/update")
    public String initOrganizationUpdateForm(HttpServletRequest request,
                                             HttpServletResponse response, Model model) {
        String organizationid = request.getParameter("organizationid");
        Organization organization = organizationRepository.findOne(Long
                .valueOf(organizationid));
        model.addAttribute("organization", organization);
        return "organization/organizationUpdate";
    }

    @RequestMapping(value = "/doSelect", method = RequestMethod.GET)
    public String doSelect(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        String orgid = request.getParameter("orgid");
        List<Organization> organizations = organizationRepository
                .findByOrgidContaining(orgid);
        model.addAttribute("organizations", organizations);
        return "organization/organizationList";
    }

    @RequestMapping(value = "/doInsert", method = RequestMethod.POST)
    public String doInsert(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        try {
            Organization organization = new Organization();
            Date dateTime = new Date();
            organization.setLangcode(request.getParameter("langcode"));
            organization.setEnterdate(dateTime);
            organization.setOrgid(request.getParameter("orgid"));
            organization.setDescription(request.getParameter("description"));
            organization.setClearingacct(request.getParameter("clearingacct"));
            organization
                    .setBasecurrency1(request.getParameter("basecurrency1"));
            organization
                    .setBasecurrency2(request.getParameter("basecurrency2"));
            organizationRepository.save(organization);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Organization> organizations = organizationRepository.findAll();
        model.addAttribute("organizations", organizations);
        return "redirect:";
    }

    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public String doUpdate(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        try {
            String organizationid = request.getParameter("organizationid");
            String langcode = request.getParameter("langcode");
            String orgid = request.getParameter("orgid");
            String description = request.getParameter("description");
            String clearingacct = request.getParameter("clearingacct");
            String basecurrency1 = request.getParameter("basecurrency1");
            String basecurrency2 = request.getParameter("basecurrency2");
            Date dateTime = new Date();
            Organization organization = new Organization();
            organization.setOrganizationid(Long.parseLong(organizationid));
            organization.setOrgid(orgid);
            organization.setLangcode(langcode);
            organization.setDescription(description);
            organization.setClearingacct(clearingacct);
            organization.setBasecurrency1(basecurrency1);
            organization.setBasecurrency2(basecurrency2);
            organization.setEnterdate(dateTime);
            organizationRepository.save(organization);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Organization> organizations = organizationRepository.findAll();
        model.addAttribute("organizations", organizations);
        return "redirect:";
//		return "organization/organizationList";
    }

    @RequestMapping(value = "/delete")
    public String deleteOrganization(HttpServletRequest request,
                                     HttpServletResponse response, Model model) {
        String organizationid = request.getParameter("organizationid");
        try {
            organizationRepository.delete(Long.valueOf(organizationid));
            List<Organization> organizations = organizationRepository.findAll();
            model.addAttribute("organizations", organizations);
            return "redirect:";
//			return "organization/organizationList";
        } catch (Exception e) {
            return "organization/false";
        }
    }
}
