package com.energicube.eno.monitor.web;

import com.energicube.eno.asset.model.Asset;
import com.energicube.eno.asset.model.AssetAttribute;
import com.energicube.eno.asset.model.ClassStructure;
import com.energicube.eno.asset.model.Locations;
import com.energicube.eno.asset.repository.AssetAttributeRepository;
import com.energicube.eno.asset.repository.AssetRepository;
import com.energicube.eno.asset.repository.ClassStructureRepository;
import com.energicube.eno.asset.service.AssetService;
import com.energicube.eno.asset.service.FailureCodeService;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.common.model.MessageResult;
import com.energicube.eno.monitor.model.Pagetag;
import com.energicube.eno.monitor.repository.OkcDMAlphaNumRepository;
import com.energicube.eno.monitor.repository.PagelayoutRepository;
import com.energicube.eno.alarm.repository.UcAlarmactiveRepository;
import com.energicube.eno.alarm.repository.UcAlarmlogRepository;
import com.energicube.eno.monitor.repository.jpa.AssetMeasurementRepository;
import com.energicube.eno.monitor.service.DictionaryService;
import com.energicube.eno.monitor.service.PagetagService;
import com.energicube.eno.alarm.service.UcAlarmgroupService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备管理处理类
 *
 * @author ZouZhiXiang
 */
@Controller
@RequestMapping("/asset")
public class AssetInfoController extends BaseController {

    private static final Log logger = LogFactory
            .getLog(AssetInfoController.class);

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetAttributeRepository assetAttributeRepository;

    @Autowired
    private AssetMeasurementRepository assetMeasurementRepository;

    @Autowired
    private UcAlarmlogRepository ucAlarmlogRepository;

    @Autowired
    private UcAlarmgroupService ucAlarmgroupService;

    @Autowired
    private UcAlarmactiveRepository ucAlarmactiveRepository;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private OkcDMAlphaNumRepository okcDMAlphaNumRepository;

    @Autowired
    private AssetService assetService;

    @Autowired
    private FailureCodeService failureCodeService;

    @Autowired
    private PagelayoutRepository pagelayoutRepository;

    @Autowired
    private ClassStructureRepository classStructureRepository;

    @Autowired
    private PagetagService pagetagService;

    /**
     * 获取设备属性列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/attribute/list", method = RequestMethod.GET)
    public
    @ResponseBody
    List<AssetAttribute> attrList(HttpServletRequest request) {
        List<AssetAttribute> list = new ArrayList<AssetAttribute>();
        String classstructureid = request.getParameter("classstructureid");
        try {
            if (StringUtils.hasLength(classstructureid)) {
                list = assetAttributeRepository
                        .findByClassStructureId(classstructureid);
            } else {
                list = assetAttributeRepository.findAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取设备分类列表
     *
     * @return
     */
    @RequestMapping(value = "/specclassinfo/list", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Asset> getAssetSpecClassList() {
        List<Asset> list = new ArrayList<Asset>();
        try {
            list = assetMeasurementRepository.findSpecClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取设备分类列表
     *
     * @return
     */
    @RequestMapping(value = "/locationinfo/list", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Locations> getAssetLocationList() {
        List<Locations> list = new ArrayList<Locations>();
        try {
            list = assetMeasurementRepository.findLocations();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取设备分类列表
     *
     * @return
     */
    @RequestMapping(value = "/assetlist/{layoutuid}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Asset> getAssetList(@PathVariable("layoutuid") long layoutuid) {
        List<Asset> list = new ArrayList<Asset>();
        try {
            // 根据layoutid设置对应的setting内容，用于存储资产的设置信息
//			Pagelayout pl = pagelayoutRepository.findOne(layoutuid);
//			if (StringUtils.hasLength(pl.getSetting())) {
//				JSONObject jsonObject = JSONObject.fromObject(pl.getSetting());
//				String specid = jsonObject.get("specid").toString();
//				String locationid = jsonObject.get("locationid").toString();
//				String classstructureid = jsonObject.get("classstructureid")
//						.toString();
//				list = assetRepository
//						.findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(
//								locationid, specid, classstructureid, "");
//			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据选择的资产分类，查询出对应的资产信息并保存页面TAG信息
     *
     * @param pagetag 页面TAG对象
     */
    @RequestMapping(value = "/buildpagetag/{pagelayoutuid}", method = RequestMethod.POST)
    @ResponseBody
    public MessageResult<Pagetag> processPagetagForm(@Valid Pagetag pagetag,
                                                     BindingResult result, SessionStatus status,
                                                     RedirectAttributes redirectAttrs, HttpServletRequest request) {

        if (result.hasErrors()) {

            return new MessageResult<Pagetag>(new Message(false, result),
                    pagetag);

        } else {

            List<Asset> assetList = new ArrayList<Asset>();

            String b_specid = pagetag.getParentid(); // 选择生成的资产分类id
            String b_locationid = pagetag.getMeasureunitid(); // 选择生成的资产位置
            String b_classid = pagetag.getControlid(); // 选择生成的资产类别id
            try {

                // 根据layoutid设置对应的setting内容，用于存储资产的设置信息
//				Pagelayout pl = pagelayoutRepository.findOne(Long
//						.valueOf(pagetag.getLayoutid()));
//				pl.setSetting(pagetag.getSetting());
//				pagelayoutRepository.save(pl);

                // 根据资产位置、分类、类别等信息查找对应的设备信息
                assetList = assetRepository
                        .findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(
                                b_locationid, b_specid, b_classid, "");
                for (int i = 0; i < assetList.size(); i++) {
                    Asset asset = (Asset) assetList.get(i);
                    pagetag.setTagid(asset.getAssetnum());
                    pagetag.setTagname(asset.getDescription());
                    pagetag.setLabel(asset.getDescription());
                    pagetag.setComments(asset.getDescription());
                    pagetag.setPagetagtype(3);
                    pagetag.setParentid("");
                    pagetag.setMeasureunitid("");
                    pagetag.setControlid("");
                    pagetag.setSetting("");
                    pagetag.setPagetagid((long) 0); // 初始化为0
                    pagetag = pagetagService.savePagetag(pagetag);
                }
            } catch (DataAccessException e) {
                e.printStackTrace();
            }

            status.setComplete();
            return new MessageResult<Pagetag>(new Message(true, "设备点更新成功"),
                    pagetag);

        }

    }

    /**
     * 获取设备分类列表
     *
     * @return
     */
    @RequestMapping(value = "/class/structurelist", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ClassStructure> getStructureList(HttpServletRequest request) {
        List<ClassStructure> list = new ArrayList<ClassStructure>();
        try {
            String parent = request.getParameter("parent");
            if (!StringUtils.hasLength(parent)) {
                list = classStructureRepository.findByRootParent(parent);
            } else {
                list = classStructureRepository.findByParent(parent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取设备分类列表
     *
     * @return
     */
    @RequestMapping(value = "/class/attribute/list", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Object> getListByClassId(HttpServletRequest request) {
        List<Object> list = new ArrayList<Object>();
        try {
            String classId = request.getParameter("classId");
            list = assetMeasurementRepository.findAttributeByClassId(classId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取设备分类列表
     *
     * @return
     */
    @RequestMapping(value = "/class/classspec/listAll", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Object> getAllClassspecs(HttpServletRequest request) {
        List<Object> list = new ArrayList<Object>();
        try {
            list = assetMeasurementRepository.findAssetattridByClassstructureid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
