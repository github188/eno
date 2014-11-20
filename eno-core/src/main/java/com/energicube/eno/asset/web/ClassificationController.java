package com.energicube.eno.asset.web;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.service.ClassService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.common.model.Tree;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import java.util.List;

/**
 * 资产类别
 */
@Controller
@RequestMapping("/class")
public class ClassificationController extends BaseAssetController {

    private ClassService classService;

    @Autowired
    public ClassificationController(ClassService classService) {
        this.classService = classService;
    }

    /**
     * 显示资产类别结构列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAssetClassStructureList(Model model) {
        return "class/classStructureList";
    }

    /**
     * 获取资产类别结构数据
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<ClassStructure> showAssetClassStructureDataSource(DataTablesRequestParams params) {
        return classService.findAllClassStructureToDataTables(params);
    }

    /**
     * 资产类别台账对话框
     */
    @RequestMapping(value = "/list/dialog", method = RequestMethod.GET)
    public String showAssetWindow(
            @RequestParam(value = "domainid", required = false, defaultValue = "") String domainid,
            @RequestParam(value = "value", required = false, defaultValue = "") String value,
            Model model) {
        model.addAttribute("domainid", domainid);
        model.addAttribute("value", value);
        return "dialog/dlgClassStructureList";
    }


    /**
     * 获取资产类别结构数据
     */
    @RequestMapping(value = "/list/dialog", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Tree> showAssetClassStructureDialogDataSource() {
        return classService.findClassStructureTree();
    }


    /**
     * 显示资产类别定义窗口
     *
     * @param model {@link Model}
     */
    @RequestMapping(value = "/classification", method = RequestMethod.GET)
    public String showClassificationEditWindow(Model model) {
        Classification classification = new Classification();
        model.addAttribute("classification", classification);
        return "dialog/dlgClassification";
    }

    /**
     * 显示资产类别定义窗口
     *
     * @param model {@link Model}
     */
    @RequestMapping(value = "/classification/dialog", method = RequestMethod.GET)
    public String showClassificationSelectWindow(
            @RequestParam(value = "section", required = false, defaultValue = "classStructure") String section, Model model) {
        Classification classification = new Classification();
        model.addAttribute("classification", classification);
        model.addAttribute("dlgtype", "select");
        model.addAttribute("section", section);
        return "dialog/dlgClassification";
    }

	
/*
	*/

    /**
     * 获取资产类别结构数据
     *//*
	@RequestMapping(value = "/classification", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTablesResponse<Classification> showClassificationDataSource(DataTablesRequestParams params) {
		return classService.findClassificationsToDataTables(params);
	}*/
    @RequestMapping(value = "/classificationlist", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Classification> findAllClassifications() {
        return classService.findAllClassification(new PageRequest(0, Integer.MAX_VALUE)).getContent();
    }


    /**
     * 获取资产类别数据
     */
    @RequestMapping(value = "/classification", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<Classification> findClassificationsWithDatatablesCriterias(HttpServletRequest request) {
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        DataSet<Classification> classifications = classService.findClassificationsWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(classifications, criterias);
    }


    /**
     * 验证资产类别ID否存在
     *
     * @param classificationid 类别ID
     */
    @RequestMapping(value = "/classification/check", method = RequestMethod.GET)
    @ResponseBody
    public String checkClassificationId(@RequestParam String classificationid) {
        //如果已经存在，则未能通过验证，返回 false,否则为true
        return classService.existClassificationid(classificationid, "") ? "false" : "true";
    }

    /**
     * 保存新增资产类别定义数据
     *
     * @param classification 资产类别
     * @param result         验证结果
     * @param status         会话状态
     * @return {@link Message}
     */
    @RequestMapping(value = "/classification/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processCreationClassification(
            @Valid Classification classification, BindingResult result,
            SessionStatus status, Model model) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            String msg = result.getFieldError().getField() + "" + result.getFieldError().getDefaultMessage();
            message.setMsg(msg);
        } else {
            boolean isContinue = true;
            if (classification.getClassificationuid() == 0) {
                boolean ischeck = classService.existClassificationid(classification.getClassificationid(), "");
                if (ischeck) {
                    message.setMsg("在相同位置已经存在类别ID");
                    isContinue = false;
                }
            }
            if (isContinue) {
                classification = classService.saveClassification(classification);
                message.setSuccess(true);
                message.setMsg(Long.toString(classification.getClassificationuid()));
                status.setComplete();
            }

        }
        return message;
    }


    /**
     * 显示资产属性定义窗口
     *
     * @param model {@link Model}
     */
    @RequestMapping(value = "/assetattribute", method = RequestMethod.GET)
    public String showAssetAttributeWindow(Model model) {
        AssetAttribute assetAttribute = new AssetAttribute();
        model.addAttribute("assetAttribute", assetAttribute);
        return "dialog/dlgAssetAttribute";
    }

    /**
     * 获取资产类别结构数据
     */
    @RequestMapping(value = "/assetattribute", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<AssetAttribute> findAssetAttributesForDataTables(HttpServletRequest request) {
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        DataSet<AssetAttribute> assetAttributes = classService.findAssetAttributesWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(assetAttributes, criterias);
    }

    /**
     * 显示资产类别定义窗口
     *
     * @param model {@link Model}
     */
    @RequestMapping(value = "/assetattribute/dialog", method = RequestMethod.GET)
    public String showAssetAttributeSelectWindow(Model model) {
        AssetAttribute assetAttribute = new AssetAttribute();
        model.addAttribute("assetAttribute", assetAttribute);
        model.addAttribute("dlgtype", "select");
        return "dialog/dlgAssetAttribute";
    }

    /**
     * 验证资产类别ID否存在
     *
     * @param assetattrid 类别ID
     */
    @RequestMapping(value = "/assetattribute/check", method = RequestMethod.GET)
    @ResponseBody
    public String checkAssetattrid(@RequestParam String assetattrid) {
        //如果已经存在，则未能通过验证，返回 false,否则为true
        return classService.existAssetAttrId(assetattrid) ? "false" : "true";
    }

    /**
     * 保存新增资产属性定义数据
     *
     * @param assetAttribute 资产属性
     * @param result         验证结果
     * @param status         会话状态
     * @return {@link Message}
     */
    @RequestMapping(value = "/assetattribute/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processCreationAssetAttribute(
            @Valid AssetAttribute assetAttribute, BindingResult result,
            SessionStatus status, Model model) {
        //
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            String msg = result.getFieldError().getField() + "" + result.getFieldError().getDefaultMessage();
            message.setMsg(msg);

        } else {
            boolean isContinue = true;
            if (assetAttribute.getAssetattributeid() == 0) {
                boolean ischeck = classService.existAssetAttrId(assetAttribute.getAssetattrid());
                if (ischeck) {
                    message.setMsg("属性ID" + assetAttribute.getAssetattrid() + "已经存在");
                    isContinue = false;
                }
            }
            if (isContinue) {
                assetAttribute = classService.saveAssetAttribute(assetAttribute);
                message.setSuccess(true);
                message.setMsg(Long.toString(assetAttribute.getAssetattributeid()));
                status.setComplete();
            }

        }
        return message;
    }

    /**
     * 新增资产类别结构
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationClassStructureForm(Model model) {
        // 定义类别的层次结构
        ClassStructure classStructure = new ClassStructure();
        classStructure.setUseclassindesc(true);
        classStructure.setGenassetdesc(true);

        ClassStructure classStructureChildren = new ClassStructure();
        classStructureChildren.setUseclassindesc(true);
        classStructureChildren.setGenassetdesc(true);
        // 分类结构使用的对象
        ClassUseWith classUseWith = new ClassUseWith();
        // 分类结构对应的资产属性模板
        ClassSpec classSpec = new ClassSpec();

        model.addAttribute("classStructure", classStructure);
        model.addAttribute("classStructureChildren", classStructureChildren);
        model.addAttribute("classification", new Classification());
        model.addAttribute("classstructureuid", "");
        model.addAttribute("classUseWith", classUseWith);
        model.addAttribute("classSpec", classSpec);
        return "class/editStructureForm";
    }

    /**
     * 保存新增资产类别结构
     *
     * @param classStructure 资产类别结构对象{@link ClassStructure}
     * @param result
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreationClassStructureForm(
            @Valid ClassStructure classStructure,
            BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, Model model) {
        model.addAttribute("classstructureuid", "");
        model.addAttribute("classUseWith", new ClassUseWith());
        model.addAttribute("classSpec", new ClassSpec());
        if (result.hasErrors()) {
            return "class/editStructureForm";
        } else {
            //判断是否有重复的类别定义ID
            boolean isexist = classService.existClassStructureByClassificationid(classStructure.getClassificationid());
            if (isexist) {
                result.addError(new ObjectError("classstructureid", "分类ID已经存在！"));
                return "class/editStructureForm";
            } else {
                classStructure = classService.saveClassStructure(0l, classStructure);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "类别层次定义新增成功");
                return "redirect:/class/edit/" + classStructure.getClassstructureuid();
            }
        }
    }


    /**
     * 编辑资产类别结构
     */
    @RequestMapping(value = "/edit/{classstructureuid}", method = RequestMethod.GET)
    public String initEditClassStructureForm(@PathVariable String classstructureuid, Model model) {

        // 定义类别的层次结构
        ClassStructure classStructure = null;
        Classification classification = null;
        ClassStructure classStructureChildren = new ClassStructure();
        if (StringUtils.hasLength(classstructureuid)) {
            classStructure = classService.findClassStructureById(Long.parseLong(classstructureuid));
            classification = classService.findClassificationByClassificationid(classStructure.getClassificationid());

            //子类
            classStructureChildren.setUseclassindesc(true);
            classStructureChildren.setGenassetdesc(true);
            classStructureChildren.setParent(classStructure.getClassstructureid());
            classStructureChildren.setClassstructureuid(0);
        }

        // 分类结构使用的对象
        ClassUseWith classUseWith = new ClassUseWith();
        // 分类结构对应的资产属性模板
        ClassSpec classSpec = new ClassSpec();


        model.addAttribute("classStructure", classStructure);
        model.addAttribute("classStructureChildren", classStructureChildren);
        model.addAttribute("classification", classification);
        model.addAttribute("classstructureuid", classstructureuid);
        model.addAttribute("classUseWith", classUseWith);
        model.addAttribute("classSpec", classSpec);
        return "class/editStructureForm";
    }

    /**
     * 保存编辑资产类别结构
     */
    @RequestMapping(value = "/edit/{classstructureuid}", method = RequestMethod.PUT)
    public String processEditClassStructureForm(@PathVariable String classstructureuid, @Valid @ModelAttribute("classStructure") ClassStructure classStructure,
                                                BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, Model model) {

        if (result.hasErrors()) {
            return "class/editStructureForm";
        } else {
            classStructure = classService.saveClassStructure(0l, classStructure);
            status.setComplete();
            redirectAttrs.addFlashAttribute("message", "类别层次定义新增成功");
            return "redirect:/class/edit/" + classStructure.getClassstructureuid();

        }

    }


    /**
     * 获取资产类别子级数据
     */
    @RequestMapping(value = "/edit/{classstructureuid}/children", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<ClassStructure> showClassStructureChildrenDataSource(@PathVariable String classstructureuid, DataTablesRequestParams params) {
        return classService.findClassStructureChildrenToDataTables(classstructureuid, params);
    }


    /**
     * 处理类别结构列表子级数据
     */
    @RequestMapping(value = "/edit/{classstructureuid}/classstructure/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processClassstructureChildrenForm(@PathVariable long classstructureuid,
                                                     @Valid @ModelAttribute("classStructureChildren") ClassStructure classStructureChildren,
                                                     BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, Model model) {

        Message message = new Message();
        message.setSuccess(true);
        if (result.hasErrors()) {
            message.setSuccess(false);
            message.setMsg(result.toString());
        } else {
            //判断是否有重复的类别定义ID
            boolean isexist = classService.existClassStructureByClassificationid(classStructureChildren.getClassificationid());
            if (isexist) {
                result.addError(new ObjectError("classstructureid", "分类ID已经存在！"));
                message.setSuccess(false);
                message.setMsg(result.toString());
            } else {
                classStructureChildren.setClassstructureuid(0);
                classStructureChildren = classService.saveClassStructure(classstructureuid, classStructureChildren);
                status.setComplete();
                message.setMsg("子类别定义新增成功");
            }
        }

        return message;
    }

    /**
     * 获取资产类别规范数据
     */
    @RequestMapping(value = "/edit/{classstructureid}/classspces", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<ClassSpec> showClassSpecDataSource(@PathVariable String classstructureid, DataTablesRequestParams params) {
        return classService.findClassSpecsToDataTables(classstructureid, params);
    }


    /**
     * 验证资产属性是否已经存在于同一个类别结构中
     *
     * @param classstructureid 类别结构ID
     * @param assetattrid      资产属性ID
     * @return 如果已经存在，则未能通过验证，返回 false,否则为true
     */
    @RequestMapping(value = "/edit/{classstructureid}/classspec/check", method = RequestMethod.POST)
    @ResponseBody
    public String processCheckClassSpecForm(@PathVariable String classstructureid, String assetattrid) {
        return classService.existClassSpecByClassificationid(classstructureid, assetattrid) ? "false" : "true";
    }

    /**
     * 处理类别规范数据
     *
     * @param classstructureuid 类别结构ID
     */
    @RequestMapping(value = "/edit/{classstructureuid}/classspec/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processClassSpecChildrenForm(@PathVariable String classstructureuid,
                                                @Valid @ModelAttribute("classSpec") ClassSpec classSpec,
                                                BindingResult result, SessionStatus status, Model model) {
        Message message = new Message();
        message.setSuccess(true);
        if (result.hasErrors()) {
            message.setSuccess(false);
            message.setMsg(result.toString());
        } else {
            boolean isexist = classService.existClassSpecByClassificationid(classSpec.getClassstructureid(), classSpec.getAssetattrid());
            if (isexist) {
                result.addError(new ObjectError("assetattrid", "资产属性已经存在当前类别中！"));
                message.setSuccess(false);
                message.setMsg(result.toString());
            } else {
                classSpec.setClassspecid(0);
                classSpec = classService.saveClassSpec(classSpec);
                status.setComplete();
                message.setMsg("资产类别规范新增成功");
            }
        }

        return message;

    }

    /**
     * 获取资产类别数据
     *
     * @creator zouzhixiang
     * @date 2014-08-13
     */
    @RequestMapping(value = "/classstructure/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ClassStructure> getClassStructureList() {
        return classService.findAllClassStructure();
    }
}
