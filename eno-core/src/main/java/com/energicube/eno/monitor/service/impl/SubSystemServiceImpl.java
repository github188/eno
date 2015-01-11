package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.asset.model.Asset;
import com.energicube.eno.asset.model.MeasurementAttribute;
import com.energicube.eno.asset.repository.AssetRepository;
import com.energicube.eno.asset.service.MeasurementService;
import com.energicube.eno.common.Const;
import com.energicube.eno.common.Constants;
import com.energicube.eno.message.redis.RedisOpsService;
import com.energicube.eno.monitor.model.DeviceConfig;
import com.energicube.eno.monitor.model.Dict;
import com.energicube.eno.monitor.model.Pagelayout;
import com.energicube.eno.monitor.model.Pagetag;
import com.energicube.eno.monitor.repository.DeviceConfigRepository;
import com.energicube.eno.monitor.repository.PagelayoutRepository;
import com.energicube.eno.monitor.repository.PagetagRepository;
import com.energicube.eno.monitor.repository.jpa.AssetMeasurementRepository;
import com.energicube.eno.monitor.service.SubSystemService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class SubSystemServiceImpl implements SubSystemService {

    private final static Log logger = LogFactory.getLog(SubSystemServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private DeviceConfigRepository deviceConfigRepository;

    @Autowired
    private PagetagRepository pagetagRepository;

    @Autowired
    private PagelayoutRepository pagelayoutRepository;

    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private AssetMeasurementRepository assetMeasurementRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private RedisOpsService redisOpsService;
    
    /**
     * 获取面板内容
     *
     * @param layoutid
     * @param pagetagid
     * @return
     * @zouzhixiang 2014-09-19
     */
    @Override
    public List<Object> findPanelContent(String layoutid, String pagetagid) {
        List<Pagetag> pagetagList = new ArrayList<Pagetag>();
        try {
            pagetagList = pagetagRepository.findByPagetagid(Long.valueOf(pagetagid));
        } catch (DataAccessException e) {
            logger.error(e);
        } catch (NumberFormatException e) {
            logger.error(e);
        }
        return getPanelHtml(layoutid, pagetagList);
    }

    /**
     * 获取设备列表需要的数据
     *
     * @param layoutid
     * @param map
     * @param pagetagList
     * @return
     * @author zouzhixiang 2014-08-29
     */
    public Map<String, List> getDeviceMap(String layoutid, Map<String, List> map, List<Pagetag> pagetagList) {
        if (StringUtils.hasLength(layoutid)) {
        	
            Pagelayout py = pagelayoutRepository.findOne(Long.valueOf(layoutid));
            if (py.getDeviceconfigid() != null) {
                DeviceConfig dConfig = deviceConfigRepository.findById(py.getDeviceconfigid());

                List<Object> cList = new ArrayList<Object>(); // 第一行数据
                List<Object> dList = new ArrayList<Object>(); // tagid列表
                List<Object> vList = new ArrayList<Object>(); // 数据列表
                List<Object> aList = new ArrayList<Object>(); // 属性列表

                if (dConfig != null) {
                	RedisConnection rc = redisTemplate.getConnectionFactory().getConnection();
                    String attribute = dConfig.getAttribute(), desc = dConfig.getDescription(), system = dConfig.getSystem(); // 设备列表中文名称及英文名称
                    String[] ats = attribute.split(","), ds = desc.split(","), syss = system.split(",");

                    // 处理第一行的列数据
                    cList.add("名称");
                    for (int i = 0; i < ds.length; i++) {
                        cList.add(ds[i].substring(0, ds[i].lastIndexOf("(")));
                    }

                    // 处理具体的设备列表数据
                    for (int i = 0; i < pagetagList.size(); i++) {
                        List<Object> tempList = new ArrayList<Object>();
                        List<Object> valueList = new ArrayList<Object>();
                        List<Object> attList = new ArrayList<Object>();
                        Pagetag pt = (Pagetag) pagetagList.get(i);
                        if (Constants.EXTENDPANELTYPE.equals(pt.getPagetagtype() + "") || Constants.STATISTICSPANELTYPE.equals(pt.getPagetagtype() + "")) { // pagetagtype为99和98的时候不需要进行运算
                            continue;
                        }

                        pagetagList.get(i).setTagval("0"); // 默认value为0
                        if (!StringUtils.hasLength(pt.getParentid())) {
                            tempList.add(pt.getLabel());
                            valueList.add(pt.getLabel());
                            attList.add(pt.getLabel());

                            // 根据资产编号查找资产对应的属性
                            List<MeasurementAttribute> attrs = measurementService.findMeasurementsByAssetnum(pt.getTagid(), "", "");
                            for (int j = 0; j < ats.length; j++) {
                                boolean have = false; // 确定设备列表中配置的属性是否找到了对应的tagid，true表示找到了，否则相反
                                for (int k = 0; k < attrs.size(); k++) {
                                    MeasurementAttribute ma = (MeasurementAttribute) attrs.get(k);
                                    // 按照设备列表中的配置来确定展示的顺序
                                    if (ats[j].equals(ma.getAssetattrid())) {

                                        attList.add(ma.getAssetattrid());
                                        tempList.add(ma.getValuetag());
										try {
											String tagval = redisOpsService.getTagInfoByRedisConnectionAndKey(rc, ma.getValuetag());
											tagval = tagval != null ? Const.formatValue(tagval) : "0";
											tagval = Const.dealDeviceString(tagval, syss[j]);
											valueList.add(tagval);
											logger.debug("tagid:[" + ma.getValuetag() + "],tagval:[" + tagval + "]");
										} catch (Exception e) {
											valueList.add("999");
											logger.error("根据tagid获取redis中的值失败了,tagid为[" + ma.getValuetag() + "]");
										}
//                                        String tagval = "0"; // 缺省为0
//                                        try {
//                                            int tagid = Integer.parseInt(ma.getValuetag());
//                                            tagval = taginfo.get(tagid).getV();
//                                            tagval = getValueByKey(dConfig.getClassid(), ats[j], tagval);
//                                            valueList.add(tagval != null ? tagval : "0");
//                                        } catch (Exception e) {
//                                            valueList.add("0");
//                                            logger.error("tagid为【" + ma.getValuetag() + "】的记录获取value失败！");
//                                        }
                                        have = true; // true说明找到了对应属性的tagid
                                        break;
                                    }
                                }
                                if (!have) { // false表示没找到对应的tagid项，则加一个占位项的数据项
                                    attList.add(ats[j]);
                                    tempList.add("");
                                    valueList.add("");
                                }
                            }
                            dList.add(tempList);
                            aList.add(attList);
                            vList.add(valueList);
                        }
                    }
                    rc.close();
                }
                map.put("column", cList); // 列数据
                map.put("data", dList); // tagid列表
                map.put("vdata", vList); // value列表
                map.put("adata", aList); // 属性列表
            }
        }

        return map;
    }

    /**
     * 生成面板对应的html代码
     *
     * @param layoutid
     * @param pagetagList
     * @return
     * @author zouzhixiang 2014-08-31
     */
    public List<Object> getPanelHtml(String layoutid, List pagetagList) {
        List<Object> assetlist = new ArrayList<Object>();
        String html = "<div class='leftdiv'>";

        // 求设备列表对应的信息
        if (StringUtils.hasLength(layoutid)) {

        	RedisConnection rc = redisTemplate.getConnectionFactory().getConnection();
            // 具体的面板数据
            boolean init = true;
            for (int i = 0; i < pagetagList.size(); i++) {
                Pagetag pt = (Pagetag) pagetagList.get(i);
                if (Constants.EXTENDPANELTYPE.equals(pt.getPagetagtype() + "") || Constants.STATISTICSPANELTYPE.equals(pt.getPagetagtype() + "")) { // pagetagtype为99和98的时候不需要进行运算
                    continue;
                }
                if (pt.getParentid() == null || "".equals(pt.getParentid())) {

                    if (init) { // 求第一条记录对应的面板属性有哪些

                        html += "<div class='firstdiv'>" + pt.getTagname() + "</div>";
                        // 根据设备编号查询对应有tagid的属性记录
                        List<MeasurementAttribute> attr = measurementService.findMeasurementsByAssetnum(pt.getTagid(), "", "");
                        // 根据资产编号查询资产记录
                        Asset asset = assetRepository.findByAssetnum(pt.getTagid());

                        if (asset != null) {

                            // 查询所有非设定值属性记录
                            List<Object> notSetList = assetMeasurementRepository.findNotSetAttribute(pt.getTagid(), asset.getClassstructureid(), "not");

                            int column = 3; // 左侧属性页，每行显示属性个数
                            html += "<table class='panellist'>";
                            for (int j = 0; j < notSetList.size(); j++) {
                                html += (j == 0) ? "<tr>" : "";
                                if (j != 0 && j % column == 0) {
                                    html += "</tr><tr>";
                                }
//                                boolean have = false; // 确定设备列表中配置的属性是否找到了对应的tagid，true表示找到了，否则相反
                                String _attr = "", _desc = "", _unitid = "", _classstructureid = ""; // 分别是属性值，中文描述，单位，分类id
                                Object[] objs = (Object[]) notSetList.get(j);
                                if (objs != null && objs.length > 0) {
                                    _attr = objs[0] == null ? "": objs[0].toString();
//                                    _desc = objs[1] == null ? "": objs[1].toString();
//                                    _unitid = dealUnitString(objs[2]);
//                                    _classstructureid = objs[3] == null ? "": objs[3].toString();
                                }

                                for (int k = 0; k < attr.size(); k++) {
                                    MeasurementAttribute ma = (MeasurementAttribute) attr.get(k);
                                    // 按照设备列表中的配置来确定展示的顺序
                                    if (_attr.equals(ma.getAssetattrid())) {
										String value = "0";
										try {
											value = redisOpsService.getTagInfoByRedisConnectionAndKey(rc, ma.getValuetag());
										} catch (Exception e) {
											value = "999";
											logger.error("根据tagid获取redis中的值失败了,tagid为[" + ma.getValuetag() + "]");
										}
//                                        String value = "0"; // 缺省为0
//                                        try {
//                                            int tagid = Integer.parseInt(ma.getValuetag());
//                                            value = taginfo.get(tagid).getV();
//                                            value = getValueByKey(_classstructureid, _attr, value);
//                                        } catch (Exception e) {
//                                            value = "0";
//                                            logger.error("tagid为【" + ma.getValuetag() + "】的记录获取value失败！");
//                                        }
//										logger.debug("面板中的值：【" + value + "】，转换后的【" + Const.dealShowText(ma.getAssetattrid(), Const.formatValue(value)) + "】");
                                        html += "<td class='panelTd_1'>" + ma.getDescrption() + "</td>" + "<td class='p_" + ma.getValuetag() + " color_2 panelTd_2'>" + Const.formatValue(value) + "</td>" + "<td class='panelTd_2'>" + Const.dealUnitString(ma.getMeasureunitid()) + "</td>";
//                                        have = true; // true说明找到了对应属性的tagid
                                        break;
                                    }
                                }
                                // 2014-12-19添加，没有tagid的项不显示
//                                if (!have) { // false表示没找到对应的tagid项，则加一个占位项的数据项
//                                    html += "<td class='panelTd_1'>" + _desc + "</td>" + "<td class='p_" + " color_2 panelTd_2'>" + "" + "</td>" + "<td class='panelTd_2'>" + ("——".equals(_unitid) ? "" : _unitid) + "</td>";
//                                }
                            }
                            html += "</tr></table></div>";

                            // 查询所有设定值属性记录
                            String setButtom = ""; // 设定值开关按钮
                            String setHtml = ""; // 设定html
                            List<Object> setList = assetMeasurementRepository.findNotSetAttribute(pt.getTagid(), asset.getClassstructureid(), "");
                            for (int j = 0; j < setList.size(); j++) {
                                if (j == 0) {
                                    html += "<div class='detail_param'>"
                                            + "	<div class='detail_tit'>"
                                            + "		<span class='decribe_tit'>设定值</span>";
                                }

                                String _attr = "", _desc = "", _unitid = "", _classstructureid = ""; // 分别是属性值，中文描述，单位，分类id
                                Object[] objs = (Object[]) setList.get(j);
                                if (objs != null && objs.length > 0) {
                                	_attr = objs[0] == null ? "": objs[0].toString();
//                                    _desc = objs[1] == null ? "": objs[1].toString();
//                                    _unitid = dealUnitString(objs[2]);
//                                    _classstructureid = objs[3] == null ? "": objs[3].toString();
                                }

                                if ("status_sp".equalsIgnoreCase(_attr)) { // 启停控制，显示开关按钮，规范台帐，status_sp才显示开关
                                	String tagid = objs[4].toString();
                                	setButtom = "		<div class='switch_btn'>"
                                            + "			<div class='switch switch-large panel_switch' tagid='" + tagid + "'>"
                                            + "				<input type='checkbox' ";
//                                    int status_sp = new Random().nextInt(2); // 随机出来1和0，随机显示开关按钮
                                    String status_sp = "0";
									try {
										status_sp = redisOpsService.getTagInfoByRedisConnectionAndKey(rc, tagid);
									} catch (Exception e) {
										status_sp = "999";
										logger.error("status_sp根据tagid获取redis中的值失败了,tagid为[" + tagid + "]");
									}
									setButtom += ("1".equals(status_sp) ? "checked" : "") + " />"
                                            + "			</div>"
                                            + "		</div>";
                                }

                                if (j == 0) {
                                	setHtml += "	</div>"
                                            + "<div class='detail_con'>"
                                            + "	<ul>"
                                            + "<tr>";
                                }

                                if (j != 0 && j % column == 0) {
                                	setHtml += "</tr><tr>";
                                }
//                                boolean have = false; // 确定设备列表中配置的属性是否找到了对应的tagid，true表示找到了，否则相反
                                for (int k = 0; k < attr.size(); k++) {
                                    MeasurementAttribute ma = (MeasurementAttribute) attr.get(k);
                                    // 按照设备列表中的配置来确定展示的顺序
                                    if (_attr.equals(ma.getAssetattrid())) {
                                    	String value = "0";
										try {
											value = Const.formatValue(redisOpsService.getTagInfoByRedisConnectionAndKey(rc, ma.getValuetag()));
										} catch (Exception e) {
											value = "999";
											logger.error("根据tagid获取redis中的值失败了,tagid为[" + ma.getValuetag() + "]");
										}
//                                        String value = "0"; // 缺省为0
//                                        try {
//                                            int tagid = Integer.parseInt(ma.getValuetag());
//                                            value = taginfo.get(tagid).getV();
//                                        } catch (Exception e) {
//                                            value = "0";
//                                            logger.error("tagid为【" + ma.getValuetag() + "】的记录获取value失败！");
//                                        }
                                        if (!"status_sp".equals(_attr)) { // 启停设定，不需要增加
                                        	setHtml += "<li><span class='param_mode'>" + ma.getDescrption() + "</span><div class='input_div'><input type='text' placeholder='18.0' value='" + Const.formatValue(value) + "' class='p_" + ma.getValuetag() + "' /><span class='param_unit'>" + Const.dealUnitString(ma.getMeasureunitid()) + "</span></div></li>";
                                        }
//                                        have = true; // true说明找到了对应属性的tagid
                                        break;
                                    }
                                }
                                  // 2014-12-19添加，没有tagid的项不显示
//                                if (!have) { // false表示没找到对应的tagid项，则加一个占位项的数据项
//                                    html += "<li><span class='param_mode'>" + _desc + "</span><div class='input_div'><input type='text' value='" + "0" + "' class='p_" + "' /><span class='param_unit'>" + ("——".equals(_unitid) ? "" : _unitid) + "</span></div></li>";
//                                }

                                if (setList.size() - 1 == j) {
                                	setHtml += "		</ul>";
                                	setHtml += "	</div>";
                                	setHtml += "</div>";
                                }
                            }

                            html = html + setButtom + setHtml;
                            init = false; // 确定是否是第一次进来此方法
                        }
                    }
                }
            }
            rc.close();
        }
        html += "</div>";
        assetlist.add(html);
        return assetlist;
    }

    /**
     * 根据字典表翻译对应的图标或文字
     *
     * @param classstructureid
     * @param assetattrid
     * @param value
     * @return
     * @author zouzhixiang 2014-10-15
     */
    private String getValueByKey(String classstructureid, String assetattrid, String value) {
        String str = value;
        try {
//            String key = classstructureid + "_" + assetattrid + "_" + value;
//            Dict dict = contextMap.get(key);
//            if (!StringUtils.isEmpty(dict)) {
//                if ("1".equals(dict.getIcon() + "")) { // icon为1的情况下优先使用icon
//                    str = "<span class='" + dict.getIcon_number() + "'></span>";
//                } else if ("1".equals(dict.getTranslate() + "")) { // translate为1，则翻译成display
//                    str = dict.getDisplay();
//                }
//            }
        } catch (Exception e) {
            logger.error(e);
        }
        return str;
    }
    
}
