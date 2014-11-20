package com.energicube.eno.asset.web;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.service.AssetService;
import com.energicube.eno.asset.service.ClassService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.common.model.Message;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/asset")
public class AssetController extends BaseAssetController {

    private final static Log logger = LogFactory.getLog(AssetController.class);

    private AssetService assetService;
    private ClassService classService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @Autowired
    public void setClassService(ClassService classService) {
        this.classService = classService;
    }


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String initIndex(Model model) {
        return "redirect:/asset/list";
    }

    /**
     * 资产台账列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAssetIndex(Model model) {
        return "asset/list";
    }

    /**
     * 资台台账对话框
     */
    @RequestMapping(value = "/list/dialog", method = RequestMethod.GET)
    public String showAssetWindow(Model model) {
        return "dialog/dlgAssetList";
    }


    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Asset> queryAssetByCondition(HttpServletRequest request) {

        Map<String, String> customCondition = new HashMap<String, String>();

        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
            customCondition.put(parameterName, request.getParameter(parameterName).toString());
        }

        logger.info(customCondition);

        return assetService.findAssetsByCondtion(customCondition);
    }


    /**
     * 资台台账数据源
     */
    @RequestMapping(value = "/list2", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<Asset> showAssetWindowData(DataTablesRequestParams params, Map<String, String> filter) {

        logger.debug(params);

        //创建DataTable数据格式对象
        DataTablesResponse<Asset> result = new DataTablesResponse<Asset>();
        //排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        //当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

        Page<Asset> page = assetService.findAllAsset(new PageRequest(pageNumber, params.getiDisplayLength(), sort));

        List<Asset> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

    /**
     * 资台台账数据源
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<Asset> findAllForDataTables(HttpServletRequest request) {
        Map<String, String> customCondition = new HashMap<String, String>();
        if (request.getParameter("specclass") != null) {
            customCondition.put("specclass", request.getParameter("specclass").toString());
        }

        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);

        DataSet<Asset> assets = assetService.findAssetsWithDatatablesCriterias(criterias, customCondition);
        return DatatablesResponse.build(assets, criterias);
    }

    /**
     * 新增资产信息
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationAssetForm(Model model) {

        Asset asset = new Asset();
        asset.setStatus(AssetStatusEnum.NOTREADY.toString());
        asset.setIsrunning(true);
        asset.setStatusdate(DateTime.now());
        asset.setChangedate(DateTime.now());
        model.addAttribute("asset", asset);
        model.addAttribute("isNew", true);
        return "asset/editAssetForm";
    }

    /**
     * 验证资产类别ID否存在
     *
     * @param classificationid 类别ID
     */
    @RequestMapping(value = "/new/check", method = RequestMethod.GET)
    @ResponseBody
    public String checkNewAssetNum(@RequestParam("assetnum") String assetnum) {
        if (StringUtils.hasLength(assetnum)) {
            //如果已经存在，则未能通过验证，返回 false,否则为true
            return assetService.existAsset(assetnum) ? "false" : "true";
        } else {
            return "false";
        }
    }

    /**
     * 保存新增资产信息
     *
     * @param asset  资产信息
     * @param result 验证结果
     * @param status 对象状态
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreationAssetForm(@Valid Asset asset,
                                           BindingResult result, SessionStatus status,
                                           RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "asset/editAssetForm";
        } else {
            if (assetService.existAsset(asset.getAssetnum())) {
                result.rejectValue("assetnum", "assetnum", "资产代码已经存在");
                return "asset/editAssetForm";
            } else {
                asset = assetService.saveAsset(asset);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "资产信息新增成功");
                return "redirect:/asset/edit/" + asset.getAssetuid();
            }
        }
    }


    /**
     * 设置资产对象模型
     *
     * @param assetuid 资产ID
     * @param model    {@link model}
     */
    private void setAssetModel(long assetuid, Model model) {
        Asset asset = new Asset();
        if (assetuid > 0) {
            asset = assetService.findAssetById(assetuid);
        }
        model.addAttribute("asset", asset);
        model.addAttribute("isNew", false);
        model.addAttribute("assetuid", Long.toString(assetuid));
    }


    /**
     * 编辑资产信息
     *
     * @param assetuid 资产ID
     */
    @RequestMapping(value = "/edit/{assetuid}", method = RequestMethod.GET)
    public String initUpdateAssetForm(@PathVariable("assetuid") long assetuid,
                                      Model model) {
        setAssetModel(assetuid, model);
        return "asset/editAssetForm";
    }

    /**
     * 保存编辑信息
     *
     * @param assetuid 资产ID
     */
    @RequestMapping(value = "/edit/{assetuid}", method = RequestMethod.PUT)
    public String processUpdateAssetForm(@Valid Asset asset,
                                         BindingResult result, SessionStatus status, Model model,
                                         RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "asset/editAssetForm";
        } else {
            asset = assetService.saveAsset(asset);
            status.setComplete();
            redirectAttrs.addFlashAttribute("message", "资产信息编辑成功");
            return "redirect:/asset/edit/" + asset.getAssetuid();
        }
    }

    /**
     * 显示资产使用的计量器列表
     */
    @RequestMapping(value = "/{assetuid}/meters", method = RequestMethod.GET)
    public String showAssetMeterList(@PathVariable("assetuid") long assetuid,
                                     Model model, HttpServletRequest request) {
        Asset asset = new Asset();
        List<AssetMeterSet> assetMeters = null;
        AssetMeter assetMeter = new AssetMeter();
        if (assetuid > 0) {
            asset = assetService.findAssetById(assetuid);
            assetMeters = assetService.findAssetMeterByAssetnum(asset.getAssetnum(), asset.getSiteid());
            assetMeter.setAssetnum(asset.getAssetnum());
        }
        model.addAttribute("asset", asset);
        model.addAttribute("assetuid", Long.toString(assetuid));
        model.addAttribute("assetMeters", assetMeters);

        model.addAttribute("assetMeter", assetMeter);

        return "asset/meterList";
    }

/*	@RequestMapping(value = "/{assetuid}/meters", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DatatablesResponse<AssetMeterSet> showAssetMeterSetsList(@PathVariable("assetuid") long assetuid,
			Model model,HttpServletRequest request) {
		Asset asset = new Asset();
		DataSet<AssetMeterSet> assetMeters = null;
		AssetMeter assetMeter = new AssetMeter();
		DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
		if (assetuid > 0) {
			asset = assetService.findAssetById(assetuid);
			assetMeters= assetService.findAssetMeterSetsWithDatatablesCriterias(criterias, asset.getAssetnum(), asset.getSiteid());
		}
		
		return DatatablesResponse.build(assetMeters, criterias);
	}
	*/

    /**
     * 显示资产使用的计量器列表
     * */
	/*@RequestMapping(value = "/{assetuid}/meters/{assetmeterid}", method = RequestMethod.GET, produces = "")
	@ResponseBody
	public AssetMeter showAssetMeterInfo(@PathVariable("assetuid") long assetuid,@PathVariable("assetmeterid") int assetmeterid) {
		AssetMeter assetMeter = assetService.findAssetMeterById(assetmeterid);
		return assetMeter;
	}
	*/


    /**
     * 保存资产使用的计量器信息
     *
     * @param assetMeter 计量器信息
     */
    @RequestMapping(value = "/{assetuid}/meters/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processCreationAssetMeter(@Valid AssetMeter assetMeter, BindingResult result, SessionStatus status) {
        Message msg = new Message();
        msg.setSuccess(false);
        if (result.hasErrors()) {
            msg.setMsg(result.toString());
        } else {
            if (assetService.existAssetMeter(assetMeter.getAssetnum(), assetMeter.getMetername(), assetMeter.getSiteid())) {
                msg.setMsg("计量器已经存在");
            } else {
                assetMeter.setChangeby("");
                assetMeter = assetService.saveAssetMeter(assetMeter);
                status.setComplete();
                msg.setSuccess(true);
            }
        }
        return msg;
    }


    /**
     * 显示资产技术规范参数列表
     *
     * @param assetuid 资产唯一主键ID
     * @return 技术规范参数视图
     */
    @RequestMapping(value = "/{assetuid}/specs", method = RequestMethod.GET)
    public String showAssetSpecList(@PathVariable("assetuid") long assetuid,
                                    Model model) {
        Asset asset = new Asset();
        List<AssetSpecSet> assetSpecSets = null;
        AssetSpec assetSpec = new AssetSpec();
        if (assetuid > 0) {
            asset = assetService.findAssetById(assetuid);
            ///assetSpecSets = assetService.findAssetSpecByAssetnum(asset.getAssetnum(), asset.getSiteid());
            assetSpec.setAssetnum(asset.getAssetnum());
        }
        model.addAttribute("asset", asset);
        model.addAttribute("assetuid", Long.toString(assetuid));
        model.addAttribute("assetSpecSets", assetSpecSets);
        model.addAttribute("assetSpec", assetSpec);

        return "asset/specList";
    }

    /**
     * 显示资产规范数据
     *
     * @param assetuid 资产主键
     * @param params   请求参数
     */
    @RequestMapping(value = "/{assetuid}/specs", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<AssetSpecSet> showAssetSpecDatasource(@PathVariable("assetuid") long assetuid,
                                                                    DataTablesRequestParams params) {
        Asset asset = new Asset();
        if (assetuid > 0) {
            asset = assetService.findAssetById(assetuid);
        }
        return assetService.findAssetSpecByAssetnum(asset.getAssetnum(), asset.getSiteid(), params);
    }


    /**
     * 验证资产技术规范参数对象是否存在此记录
     *
     * @param assetnum    资产代码
     * @param assetattrid 属性代码
     * @return false 未能通过验证 true 通过验证
     */
    @RequestMapping(value = "/{assetuid}/specs/check", method = RequestMethod.GET)
    @ResponseBody
    public String checkAssetattrid(
            @PathVariable("assetuid") long assetuid,
            @RequestParam String assetnum,
            @RequestParam String assetattrid) {
        return assetService.existAssetSpec(assetnum, assetattrid, "") ? "false" : "true";
    }

    /**
     * 处理资产技术规范参数对象
     *
     * @param assetSpec 技术规范参数对象
     * @param result    验证结果
     * @param status    会话状态
     * @return 结果消息{@link Message}对象
     */
    @RequestMapping(value = "/{assetuid}/specs/save", method = RequestMethod.POST)
    public
    @ResponseBody
    Message processAssetSpecForm(
            @PathVariable("assetuid") long assetuid,
            @Valid AssetSpec assetSpec, BindingResult result,
            SessionStatus status) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            String msg = result.getFieldError().getField() + "" + result.getFieldError().getDefaultMessage();
            message.setMsg(msg);
        } else {
            boolean isExist = false;
            if (assetSpec.getAssetspecid() == 0) {
                isExist = assetService.existAssetSpec(assetSpec.getAssetnum(), assetSpec.getAssetattrid(), "");
            }
            if (isExist) {
                message.setMsg("属性已经存在");
            } else {
                assetSpec = assetService.saveAssetSpec(assetSpec);
                message.setMsg(Integer.toString(assetSpec.getAssetspecid()));
                message.setSuccess(true);
            }
        }
        return message;
    }

    @RequestMapping(value = "/{assetuid}/specs/saveattribute", method = RequestMethod.POST)
    public
    @ResponseBody
    Message processAssetSpecAttributes(
            @PathVariable("assetuid") long assetuid,
            @Valid AssetSpec assetSpec, BindingResult result,
            SessionStatus status) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            String msg = result.getFieldError().getField() + "" + result.getFieldError().getDefaultMessage();
            message.setMsg(msg);
        } else {

            //classService.findClassStructureById(id);


        }
        return message;
    }


}
