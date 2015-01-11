package com.energicube.eno.monitor.service.impl;
import com.energicube.eno.asset.model.Measurement;
import com.energicube.eno.asset.model.MeasurementAttribute;
import com.energicube.eno.asset.repository.AssetAttributeRepository;
import com.energicube.eno.asset.repository.AssetRepository;
import com.energicube.eno.asset.service.MeasurementService;
import com.energicube.eno.common.Config;
import com.energicube.eno.common.Const;
import com.energicube.eno.message.redis.RedisOpsService;
import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.repository.DeviceConfigRepository;
import com.energicube.eno.monitor.repository.PagelayoutRepository;
import com.energicube.eno.monitor.repository.PagetagRepository;
import com.energicube.eno.monitor.repository.SyscontrolRepository;
import com.energicube.eno.monitor.repository.jpa.AssetMeasurementRepository;
import com.energicube.eno.monitor.service.PagelayoutService;
import com.energicube.eno.monitor.service.PagetagService;
import com.energicube.eno.monitor.service.PassengerFlowService;
import com.energicube.eno.monitor.service.SubSystemService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PagetagServiceImpl implements PagetagService {

    private final static Log logger = LogFactory.getLog(PagetagServiceImpl.class);

    private PagelayoutRepository pagelayoutRepository;

    private PagetagRepository pagetagRepository;

    @Autowired
    private PagelayoutService pagelayoutService;

    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private SyscontrolRepository syscontrolRepository;

    @Autowired
    private DeviceConfigRepository deviceConfigRepository;

    @Autowired
    private AssetAttributeRepository assetAttributeRepository;

    @Autowired
    private AssetMeasurementRepository assetMeasurementRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private RedisOpsService redisOpsService;

    @Autowired
    private SubSystemService subSystemService;
    
    @Autowired
    private PassengerFlowService passengerFlowService;
    
    private Config config = new Config();
	
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    public PagetagServiceImpl(PagelayoutRepository pagelayoutRepository,
                              PagetagRepository pagetagRepository) {
        this.pagelayoutRepository = pagelayoutRepository;
        this.pagetagRepository = pagetagRepository;
    }

    public List<Pagetag> findByPagelayoutuid(long pagelayoutuid) {
        Pagelayout pagelayout = pagelayoutRepository.findOne(pagelayoutuid);
//        List<Pagetag> returnList = new ArrayList<Pagetag>();
        List<Pagetag> list = pagetagRepository.findByLayoutid(pagelayout.getPagelayoutuid()+"");
//        for (int i = 0; i < list.size(); i++) {
//            Pagetag pt = (Pagetag) list.get(i);
//            pt.setCreatedate(null);
//            returnList.add(pt);
//        }
        return list;
    }

    /**
     * 根据classstructureid查询指定页面的对应的数据
     */
    public List<Pagetag> findByLayoutidAndClassId(long pagelayoutuid, String classid) {
    	Pagelayout pagelayout = pagelayoutRepository.findOne(pagelayoutuid);
    	List<Pagetag> list = new ArrayList<Pagetag>();
    	if (!org.apache.commons.lang3.StringUtils.isEmpty(classid)) {
    		list = pagetagRepository.findByLayoutidAndClassId(pagelayout.getPagelayoutuid() + "", classid);
    	} else {
			list = pagetagRepository.findByLayoutid(pagelayout.getPagelayoutuid() + "");
    	}
    	return list;
    }
    

    @Transactional(readOnly = true)
    public Pagetag findOne(long id) {
        return pagetagRepository.findOne(id);
    }

    @Transactional
    public void deletePagetag(long id) {
        if (pagetagRepository.exists(id)) {
            pagetagRepository.delete(id);
        }
    }

    @Transactional
    public Pagetag savePagetag(Pagetag pagetag) {
        // 新增时
        if (pagetag.getPagetagid() == null || pagetag.getPagetagid() == 0) {
            if (pagetag.getShowrange() == null || pagetag.getShowrange().equals("")) {
                pagetag.setShowrange("list"); // 默认作用于设备列表.[2014-08-26 zzx]
            }
            pagetag = pagetagRepository.save(pagetag);

            String classstructureid = "";
            // 如果选择的设备为资产数据，则同时保存资产属性到列表
            if (pagetag.getPagetagtype() == 3) {
                List<MeasurementAttribute> attrs = measurementService.findMeasurementsByAssetnum(pagetag.getTagid(), "", "");
                for (int i = 0; i < attrs.size(); i++) {
                	MeasurementAttribute attr = attrs.get(i);
                    if (!StringUtils.isEmpty(attr.getValuetag()) && !"null".equalsIgnoreCase(attr.getValuetag())) {
                        Pagetag tag = new Pagetag();
                        tag.setTagid(attr.getValuetag());
                        tag.setTagname(attr.getAssetattrid());
                        tag.setLabel(attr.getDescrption());
                        tag.setMeasureunitid("".equals(attr.getMeasureunitid()) ? "" : attr.getMeasureunitid());
                        tag.setControlid(pagetag.getControlid());
//                        tag.setCreatedate(DateTime.now());
                        tag.setParentid(pagetag.getPagetagid() + "");
                        tag.setGroupname(pagetag.getGroupname());
                        tag.setLayoutid(pagetag.getLayoutid());
                        tag.setPagetagtype(pagetag.getPagetagtype());
                        tag.setUsesetting(pagetag.getUsesetting());
                        tag.setTagval(pagetag.getTagval());
                        tag.setShowrange(pagetag.getShowrange());
                        tag.setClassstructureid(attr.getClassstructureid()); // 为适应WEB版万达设备列表和面板数据字典对应而加
                        tag.setOrderindex(pagetag.getOrderindex()*10 + i);
                        tag = pagetagRepository.save(tag);
                        classstructureid = tag.getClassstructureid();
                    }
                }
//				logger.debug("classstructureid--" + classstructureid);
				if (!attrs.isEmpty() && !StringUtils.isEmpty(classstructureid)) {
					pagetagRepository.updateParentClassId(classstructureid, pagetag.getTagid()); // 更新父级的分类id
				}
            }
        } else {
            // 修改时，为了防止坐标丢失，先加载在历史坐标
            String coodrs = pagetagRepository.findOne(pagetag.getPagetagid()).getCoords();
            pagetag.setCoords(coodrs);
            pagetag = pagetagRepository.save(pagetag);
            // 更新资产子级对应的作用范围
            pagetagRepository.updateValue(pagetag.getShowrange(), pagetag.getControlid(), pagetag.getControlid2(), pagetag.getControlid3(), pagetag.getTagtype(), pagetag.getPagetagtype(), pagetag.getTagval(), pagetag.getTagid());
        }
        return pagetag;
    }

    @Transactional
	public Pagetag savePassengerPagetag(Pagetag pagetag) {
    	// 新增时
        if (pagetag.getPagetagid() == null || pagetag.getPagetagid() == 0) {
            if (pagetag.getShowrange() == null || pagetag.getShowrange().equals("")) {
                pagetag.setShowrange("list"); // 默认作用于设备列表.[2014-08-26 zzx]
            }
            Measurement measurement = measurementService.findMeasurementByAssetnum(pagetag.getTagid());
            if(measurement != null) {
            	String valueTag = measurement.getValuetag();
            	pagetag.setTagid(valueTag.substring(0, valueTag.indexOf("_")));
            }
            pagetag = pagetagRepository.save(pagetag);

        } else {
            // 修改时，为了防止坐标丢失，先加载在历史坐标
            String coodrs = pagetagRepository.findOne(pagetag.getPagetagid()).getCoords();
            pagetag.setCoords(coodrs);
            pagetag = pagetagRepository.save(pagetag);
            // 更新资产子级对应的作用范围
            pagetagRepository.updateValue(pagetag.getShowrange(), pagetag.getControlid(), pagetag.getControlid2(), pagetag.getControlid3(), pagetag.getTagtype(), pagetag.getPagetagtype(), pagetag.getTagval(), pagetag.getTagid());
        }
        return pagetag;
	}
	
    @Transactional
    public Pagetag updatePagetagCoordinate(long pagetagid, String left,
                                           String top) {

        Pagetag pagetag = pagetagRepository.findOne(pagetagid);
        pagetag.setLeft(left);
        pagetag.setTop(top);

        return pagetagRepository.save(pagetag);
    }

    /**
     * 更新坐标位置 <br />
     * 注意区分pagetab,
     */
    @Transactional
    public int updatePagetagCoords(Collection<Pagetag> pagetags) {
        List<String> pagetabs = new ArrayList<String>(); // PAGETAB集合
        List<Long> ids = new ArrayList<Long>(); // PAGETAGID集合
        for (Pagetag pagetag : pagetags) {
            pagetabs.add(pagetag.getPagetab());
            ids.add(pagetag.getPagetagid());
        }

        Set<Long> uniquePagetagid = new HashSet<Long>(ids);

        for (Long id : uniquePagetagid) {
            Pagetag pagetag = pagetagRepository.findOne(id);

            List<Pagetag> tags = getPagetagsById(pagetags, id);
            String coords = getPagetabCoords(tags);
            logger.info(coords);
            pagetag.setCoords("{" + coords + "}");
            pagetagRepository.save(pagetag);
        }

        return 0;
    }

    /**
     * 获取相同ID的设备点集合
     */
    private List<Pagetag> getPagetagsById(Collection<Pagetag> pagetags,
                                          long pagetagid) {
        List<Pagetag> result = new ArrayList<Pagetag>();
        for (Pagetag tag : pagetags) {
            if (tag.getPagetagid() == pagetagid) {
                result.add(tag);
            }
        }
        return result;
    }

    /**
     * 获取设置点在各个子页上的坐标点
     */
    private String getPagetabCoords(List<Pagetag> pagetags) {
        StringBuilder sb = new StringBuilder();
        for (Pagetag tag : pagetags) {
            if (sb.length() > 0)
                sb.append(",");
            sb.append(String.format("\"%s\":{\"top\":\"%s\",\"left\":\"%s\"}",
                    tag.getPagetab(), tag.getTop(), tag.getLeft()));
        }

        return sb.toString();
    }

    @Transactional(readOnly = true)
    public List<Pagetag> findByLayoutid(String layoutid) {
        return pagetagRepository.findByLayoutid(layoutid);
    }

    @Transactional(readOnly = true)
    public Map<String, List> findPagetagAndControlByMenuid(String menuid, String layoutid, String elementvalue) {

        Map<String, List> map = new HashMap<String, List>();
        //获取控件列表
        List<Syscontrol> syscontrolList = syscontrolRepository.findAll();
        if (!StringUtils.hasLength(layoutid)) {
            layoutid = pagelayoutService.findDefaultLayoutByMenuid(menuid).getLayoutid();
        }
        logger.debug("findPagetagAndControlByMenuid-----layoutid--"+layoutid);
        //获取指定布局ID所属的所有设备点
        List<Pagetag> pagetagList = pagetagRepository.findByLayoutid(layoutid);

        // 返回前台设备列表需要的数据
        List<PagetagTemp> pagetagTemps = new ArrayList<>();
        if(elementvalue != null && !"".equals(elementvalue) && "PFE".equals(elementvalue)) {
        	List<Pagetag> pagetags = new ArrayList<Pagetag>();
        	List<String> pagetagids = new ArrayList<String>();
        	String allShopType = config.getProps().getProperty("pfe.allShopType500");
            String type1 = config.getProps().getProperty("pfe.allShopType600");
            DateTime dt = DateTime.parse(getTodayStartTime(), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            List<UcPassengerFlowDay> ucPassengerFlowDetailList = passengerFlowService.findAllShopPassenger(allShopType, type1, dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
            Pagetag pt = null;
            PagetagTemp pagetagTemp = null;
            for (UcPassengerFlowDay ucPassengerFlowDay : ucPassengerFlowDetailList) {
            	for(int i=0; i<pagetagList.size(); i++) {
            		pt = pagetagList.get(i);
            		if(pt.getTagid().equals(ucPassengerFlowDay.getShopCode())) {
            			pt.setPassenger(ucPassengerFlowDay);
            			pagetagTemp = new PagetagTemp();
            			pagetagTemp.setPagetage(pt);
            			pagetagTemps.add(pagetagTemp);
            			
            		}
    			}

			}
        }else {
        	for (Pagetag pagetag : pagetagList) {
                if (pagetag.getParentid() == null || "".equals(pagetag.getParentid())) {
                    pagetagTemps.add(dealPagetages(pagetag, pagetagList));
                }
            }
		}
        logger.debug("findPagetagAndControlByMenuid---"+pagetagTemps.size());
        map.put("pagetag", pagetagTemps);
        map = subSystemService.getDeviceMap(layoutid, map, pagetagList); // 设备列表

        if ("HVAC".equals(elementvalue) || "WSD".equals(elementvalue)) { // 针对暖通空调、给排水的面板
            List<Object> panel = subSystemService.getPanelHtml(layoutid, pagetagList);
            map.put("panel", panel);
//		} else {
//			map.put("pagetag", pagetagList);
        }

        // add result list to a map
        map.put("syscontrol", syscontrolList);

        return map;
    }

    private PagetagTemp dealPagetages(Pagetag pagetag, List<Pagetag> pagetags) {
        PagetagTemp pagetagTemp = new PagetagTemp();
        pagetagTemp.setPagetage(pagetag);
        List<Pagetag> pts = new ArrayList<>();
        String value = "0.00";
        BigDecimal bigDecimal;
        RedisConnection rc = redisTemplate.getConnectionFactory().getConnection();
        for (Pagetag pt : pagetags) {
            if ((pagetag.getPagetagid() + "").equals(pt.getParentid())) {
            	value = redisOpsService.getTagInfoByRedisConnectionAndKey(rc, pt.getTagid());
            	logger.debug("----dealPagetages----value----" + value);
            	try {
					bigDecimal = new BigDecimal(value);
					value = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
				} catch (Exception e) {
					logger.error("---dealPagetages---error---" + e);
				}
				pt.setTagvalue(value);
//				pt.setTagvalue("");
                pts.add(pt);
            }
        }
        rc.close();
        pagetagTemp.setPagetags(pts);
        return pagetagTemp;
    }
    /**
     * 获取有表达式的pagetag
     */
    @Override
    public List<Pagetag> findPagetagByExpressionsNotNull() {
        return pagetagRepository.findByExpressionsNotNull();
    }

    @Override
    public List<Pagetag> findPagetagByLayoutidAndExpressionsNotNull(
            String layoutid) {
        List<Pagetag> pagetags = pagetagRepository.findByLayoutidAndExpressionsNotNull(layoutid);
        for (int i = 0; i < pagetags.size(); i++) {
            Integer tagid = Integer.parseInt(pagetags.get(i).getTagid());
            tagid += 100000;
            pagetags.get(i).setTagid(tagid + "");
        }
        return pagetags;
    }

    @Transactional(readOnly = true)
    public List<Pagetag> getTagLastValues(String menuid) {

        List<Pagelayout> list = pagelayoutRepository.findByMenuid(menuid);
        List<Pagetag> pagetagList = new ArrayList<Pagetag>();
        // 获取菜单所属的所有设备点
        for (Pagelayout layout : list) {
            pagetagList.addAll(pagetagRepository.findByLayoutid(layout.getLayoutid()));
        }

        try {
        	RedisConnection rc = redisTemplate.getConnectionFactory().getConnection();
            // 从redis中获取tagid最后的测量值
            for (int i = 0; i < pagetagList.size(); i++) {
                Pagetag tag = pagetagList.get(i);
                String tagval = "0"; // 缺省为0
                try {
                	tagval = redisOpsService.getTagInfoByRedisConnectionAndKey(rc, tag.getTagid());
                } catch (Exception e) {
                    tagval = "0";
                    logger.error("tagid为【" + tag.getTagid() + "】的记录获取value失败！");
                }
                pagetagList.get(i).setTagval(Const.formatValue(tagval));
            }
            rc.close();
        } catch (Exception ex) {
            logger.error("getTagLastValues---Redis获取TAG值失败," + ex.getMessage());
        }
        return pagetagList;
    }

    @Transactional(readOnly = true)
    public Map<String, List> findPagetagAndControlByLayoutid(String layoutid) {
        Map<String, List> map = new HashMap<String, List>();
        List<Syscontrol> syscontrolList = syscontrolRepository.findAll();
        List<Pagetag> pagetagList = pagetagRepository.findByLayoutid(layoutid);

        map.put("pagetag", pagetagList);
        map.put("syscontrol", syscontrolList);
        return map;
    }

    /**
     * 更新指定pagetagid的属性
     * <p/>
     * <pre>
     * pagetagid字符串临时保存在setting中，操作过程中不更新setting值
     * </pre>
     */
    @Transactional
    public void batchUpdatePagetagProps(String layoutid, Pagetag pagetag) {
        String[] settings = pagetag.getSetting().split(";");
        if (settings != null && settings.length > 1) {
            pagetag.setSetting(settings[1]);
        } else {
            pagetag.setSetting(null);
        }
        if (settings.length > 0) {
            String ids = settings[0];
            if (ids.indexOf(',') > -1) {
                for (String id : ids.split(",")) {
                    batchUpdatePagetagProp(Long.parseLong(id), pagetag);
                }
            } else {
                batchUpdatePagetagProp(Long.parseLong(ids), pagetag);
            }
        }
    }

    /**
     * 批量更新设备属性值
     *
     * @param id      设置数据ID
     * @param pagetag 设备信息
     */
    private Pagetag batchUpdatePagetagProp(long id, Pagetag pagetag) {
        Pagetag tag = pagetagRepository.findOne(id);
        //批量设置时，如果重新设置面板的模版，则清空其表达式
        if (tag.getPagetagtype() == 99) {
            tag.setExpressions("");
        }
        if (StringUtils.hasLength(pagetag.getControlid())) {
            tag.setControlid(pagetag.getControlid());
        }
        if (StringUtils.hasLength(pagetag.getControlid2())) {
            tag.setControlid2(pagetag.getControlid2());
        }
        if (StringUtils.hasLength(pagetag.getControlid3())) {
            tag.setControlid3(pagetag.getControlid3());
        }
        if (StringUtils.hasLength(pagetag.getMeasureunitid())) {
            tag.setMeasureunitid(pagetag.getMeasureunitid());
        }
        if (pagetag.getTagtype() > 0) {
            tag.setTagtype(pagetag.getTagtype());
        }
        if (StringUtils.hasLength(pagetag.getShowrange())) {
            tag.setShowrange(pagetag.getShowrange());
        }
        if (pagetag.getPagetagtype() > -1) {
            tag.setPagetagtype(pagetag.getPagetagtype());
        }
        if (StringUtils.hasLength(pagetag.getSetting())) {
            Syscontrol sc = syscontrolRepository.findOne(Integer.parseInt(pagetag.getAttrctrlid()));
            tag.setSetting(sc.getSettting());
        }
        if (StringUtils.hasLength(pagetag.getAttrctrlid())) {
            tag.setAttrctrlid(pagetag.getAttrctrlid());
        }
        // 更新资产子级对应的作用范围[2014-09-18,zouzhixiang]
        pagetagRepository.updateValue(pagetag.getShowrange(), pagetag.getControlid(), pagetag.getControlid2(), pagetag.getControlid3(), pagetag.getTagtype(), pagetag.getPagetagtype(), pagetag.getTagval(), tag.getTagid());
        return pagetagRepository.save(tag);
    }

    /**
     * 批量添加设备点
     */
    @Override
    @Transactional(rollbackForClassName = "Exception")
    public void addPagetags(Collection<Pagetag> pagetags) {
        pagetagRepository.save(pagetags);
    }

	@Override
	public Map<String, Object> findETDPanelDatas(String pagelayoutuid) throws Exception {
		
		Map<String, Object> etdMap = new HashMap<String, Object>();
		if(pagelayoutuid != null && !"".equals(pagelayoutuid)) {
			try {
				long id = Long.valueOf(pagelayoutuid);
				Pagelayout currentPagelayout = pagelayoutService.findOne(id);
				List<Pagetag> pagetags = this.findByLayoutid(currentPagelayout.getLayoutid());
				List<PagetagTemp> pagetagTemps = new ArrayList<PagetagTemp>();
				for (Pagetag pagetag : pagetags) {
					if (pagetag.getParentid() == null || "".equals(pagetag.getParentid())) {
		                pagetagTemps.add(dealPagetages(pagetag, pagetags));
		            }
				}
				etdMap.put("edtPanel", pagetagTemps);
			} catch (NumberFormatException e) {
				logger.debug(e);
			}
		}
		
		return etdMap;
	}

	@Override
	public void sortTags(long pagetagid, boolean type) throws Exception {
		List<Pagetag> pagetags = pagetagRepository.findByPagetagid(pagetagid);
		if(pagetags != null && pagetags.size()>0) {
			Pagetag pagetag = pagetags.get(0);
			List<Pagetag> listPagetags = pagetagRepository.findParentTagByLayoutid(pagetag.getLayoutid());
			for(int i=0; i<listPagetags.size(); i++) {
				if(listPagetags.get(i).getPagetagid() == pagetagid) {
					Pagetag pt = new Pagetag();
					if(type && i>0){
						pt = listPagetags.get(i-1);
						int index = pt.getOrderindex();
						pt.setOrderindex(pagetag.getOrderindex());
						pagetag.setOrderindex(index);
						listPagetags.set(i-1, pt);
						listPagetags.set(i, pagetag);
						break;
					}else if(i<listPagetags.size()){
						pt = listPagetags.get(i+1);
						int index = pt.getOrderindex();
						pt.setOrderindex(pagetag.getOrderindex());
						pagetag.setOrderindex(index);
						listPagetags.set(i, pagetag);
						break;
					}
				}
			}
			this.addPagetags(listPagetags);
		}
	}
    /**
     * 得到本日的统计的开始时间;
     * 统计时间从早晨6点开始
     *
     * @return 时间 “yyyy-MM-dd HH:mm:ss”
     */
    private String getTodayStartTime() {
        DateTime dateTime = DateTime.now();
        int hour = dateTime.getHourOfDay();
        String startHour = config.getProps().getProperty("pfe.startHour");
        if (hour < Integer.parseInt(startHour)) {
            DateTime temp = DateTime.now().plusDays(-1);
            return temp.toString("yyyy-MM-dd ") + startHour + ":00:00";
        } else {
            return dateTime.toString("yyyy-MM-dd ") + startHour + ":00:00";
        }
    }

	@Override
	public void deletePagetags(String[] pagetagids) throws Exception {
		if (pagetagids != null && pagetagids.length>0) {
			long id;
			for (String string : pagetagids) {
				try {
					id = Long.parseLong(string);
					if (pagetagRepository.exists(id)) {
			            pagetagRepository.delete(id);
			        }
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
	}

}
