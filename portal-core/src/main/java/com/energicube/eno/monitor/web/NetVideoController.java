package com.energicube.eno.monitor.web;

import com.energicube.eno.common.ArrayUtil;
import com.energicube.eno.common.FileUtil;
import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 视频控制器
 */
@Controller
@RequestMapping("/okcsys/video")
public class NetVideoController {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private NetvideoCameracfgService cameracfgService;

    @Autowired
    private NetvideoDvrcfgService dvrcfgService;

    @Autowired
    private NetvideoMonitorcfgService monitorcfgService;

    @Autowired
    private NetvideoSysConfigService netvideoSysConfigService;

    @Autowired
    private NetvideoMatrixConfigService netvideoMatrixConfigService;

    @Autowired
    private NetvideoRotationConfigService netvideoRotationConfigService;

    /**
     * 进入系统配置页面
     *
     * @return String (页面路径)
     */
    @RequestMapping(value = "/sysconfig", method = RequestMethod.GET)
    public String showSysconfig(Model model, HttpServletRequest request) {
        try {

            String cfgs = netvideoSysConfigService.readSysconfig(request,
                    "systemconfig");
            if (!cfgs.equals("")) {
                ObjectMapper objectMapper = new ObjectMapper();
                ArrayList list = objectMapper.readValue(cfgs, ArrayList.class);
                NetvideoSystemConfig netvideoSystemConfig = objectMapper.readValue(list.get(0).toString(), NetvideoSystemConfig.class);
                model.addAttribute("sysconfig", netvideoSystemConfig);
            } else {
                NetvideoSystemConfig systemConfig = new NetvideoSystemConfig();
                model.addAttribute("sysconfig", systemConfig);
            }

        } catch (Exception e) {
            logger.error("showSysconfig", e);
        }
        return "netvideo/sysconfig";
    }

    /**
     * 保存系统配置信息 form表单提交
     */
    @RequestMapping(value = "/sysconfig", method = RequestMethod.POST)
    public String saveSysConfigTofile(@Valid NetvideoSystemConfig sysconfig,
                                      HttpServletRequest request, BindingResult result,
                                      SessionStatus status, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            logger.error(result);
            return "netvideo/sysconfig";
        } else {
            netvideoSysConfigService.saveSysconfig(sysconfig, request);
            return "redirect:/okcsys/video/sysconfig";
        }
    }

    /**
     * 进入监视器配置页面
     *
     * @return String (页面路径)
     */
    @RequestMapping(value = "/monitorconfig", method = RequestMethod.GET)
    public String showMonitorconfig(Model model) {
        model.addAttribute("monitorcfgs",
                monitorcfgService.findNetvideoMonitorcfgs());
        return "netvideo/monitorconfig";
    }

    /**
     * 进入监视器配置页面：页面修改处理
     *
     * @return String (页面路径)
     */
    @RequestMapping(value = "change/monitorcfg", method = RequestMethod.POST)
    public String saveMonitorconfig(ModelArray modelArray,
                                    HttpServletRequest request) {
        if (modelArray.getMonitorcfgArray() != null) {
            List<NetvideoMonitorcfg> saveMonitorcfgs = Arrays.asList(modelArray
                    .getMonitorcfgArray());
            List<NetvideoMonitorcfg> oldMonitorcfgs = monitorcfgService
                    .findNetvideoMonitorcfgs();
            List<Integer> savelis = new ArrayList<Integer>();
            List<Integer> oldlis = new ArrayList<Integer>();

            for (NetvideoMonitorcfg saveMonitorcfg : saveMonitorcfgs) {
                savelis.add(saveMonitorcfg.getMonitorid());
            }
            for (NetvideoMonitorcfg oldMonitorcfg : oldMonitorcfgs) {
                oldlis.add(oldMonitorcfg.getMonitorid());
            }
            List<Integer> delelis = ArrayUtil.diffListInteger(oldlis, savelis);
            if (delelis != null && delelis.size() != 0) {
                for (Integer monitorid : delelis) {
                    monitorcfgService.deleteNetvideoMonitorcfg(monitorid);
                }
            }

            if (saveMonitorcfgs.size() != 0) {
                for (Object monitorcfg : saveMonitorcfgs) {
                    monitorcfgService
                            .saveNetvideoMonitorcfg((NetvideoMonitorcfg) monitorcfg);
                }
            }
        }
        return "redirect:/okcsys/video/monitorconfig";
    }

    /**
     * 进入摄像机配置页面
     *
     * @return {@link List} of NetvideoCameracfg
     */
    @RequestMapping(value = "/cameracfg", method = RequestMethod.GET)
    public String showCameracfgs(Model model) {
        model.addAttribute("cameracfgs",
                cameracfgService.findNetvideoCameracfgs());
        Sort sort = new Sort(Direction.ASC, "dvrsequence");
        model.addAttribute("dvrcfgs", dvrcfgService.findNetvideoDvrcfgs(sort));
        return "netvideo/cameracfg";
    }

    /**
     * 进入摄像机配置页面：页面修改处理
     *
     * @return String (页面路径)
     */
    @RequestMapping(value = "change/cameracfg", method = RequestMethod.POST)
    public String saveCameraconfig(ModelArray modelArray,
                                   HttpServletRequest request) {
        if (modelArray.getCameracfgArray() != null) {
            List<NetvideoCameracfg> saveCameracfgs = Arrays.asList(modelArray
                    .getCameracfgArray());
            List<NetvideoCameracfg> oldCameracfgs = cameracfgService
                    .findNetvideoCameracfgs();
            List<Integer> savelis = new ArrayList<Integer>();
            List<Integer> oldlis = new ArrayList<Integer>();

            for (NetvideoCameracfg saveCameracfg : saveCameracfgs) {
                savelis.add(saveCameracfg.getCameracfgid());
            }
            for (NetvideoCameracfg oldCameracfg : oldCameracfgs) {
                oldlis.add(oldCameracfg.getCameracfgid());
            }
            List<Integer> delelis = ArrayUtil.diffListInteger(oldlis, savelis);
            if (delelis != null && delelis.size() != 0) {
                for (Integer cameracfgid : delelis) {
                    cameracfgService.deleteNetvideoCameracfg(cameracfgid);
                }
            }

            if (saveCameracfgs.size() != 0) {
                for (Object cameracfg : saveCameracfgs) {
                    cameracfgService
                            .saveNetvideoCameracfg((NetvideoCameracfg) cameracfg);
                }
            }
        } else {
            cameracfgService.deleteAllCamera();
        }
        return "redirect:/okcsys/video/cameracfg";
    }

    /**
     * @param model
     * @return
     */
    @RequestMapping(value = "/dvrcfg", method = RequestMethod.GET)
    public String getDvrcfgs(Model model) {
        Sort sort = new Sort(Direction.ASC, "dvrsequence");
        model.addAttribute("maxSequence", dvrcfgService.findMaxIndex());// 返回DVR配置信息中最大显示顺序的值
        model.addAttribute("dvrList", dvrcfgService.findNetvideoDvrcfgs(sort)); // 返回所有DVR配置信息
        return "netvideo/dvrconfig";
    }

    /**
     * 保存DVR编辑后数据列表
     *
     * @param ma       对象集合
     * @param delIds   唯一标识集合
     * @param sortList 数据行移动后其唯一标识的顺序集合
     * @return
     */
    @RequestMapping(value = "/savedvr", method = RequestMethod.POST)
    public String saveDvrcfgs(ModelArray ma, int[] delIds, int[] sortList,
                              HttpServletRequest request) {
        Sort sort = new Sort(Direction.ASC, "dvrsequence");
        // 新增和编辑操作结果保存
        if (ma.getDvrcfgArray() != null) {
            List<NetvideoDvrcfg> list = Arrays.asList(ma.getDvrcfgArray());// 获取DVR对象集合，此处将数组转换为List进行保存
            dvrcfgService.saveNetvideoDvrcfg(list);
        }
        // 删除操作结果保存
        if (delIds.length > 0) {
            for (int i : delIds) { // 遍历所有已选删除数据id，执行操作
                dvrcfgService.deleteNetvideoDvrcfg(i);
            }
        }
        // 更新数据显示顺序
        if (sortList.length > 0) {
            List<NetvideoDvrcfg> list = dvrcfgService.findNetvideoDvrcfgs(sort);// 查找所有DVR配置信息
            List<NetvideoDvrcfg> dvrSort = new ArrayList<NetvideoDvrcfg>();// 保存新的显示顺序的DVR配置信息
            for (int i = 0; i < sortList.length; i++) { // 根据新的显示顺序的dvrsequence与原配置信息对比，将原配置信息按新的显示顺序保存到集合dvrSort中
                for (int j = 0; j < list.size(); j++) {
                    if (sortList[i] == list.get(j).getDvrsequence()) {
                        dvrSort.add(list.get(j));
                        break;
                    }
                }
            }
            List<NetvideoDvrcfg> newList = dvrcfgService
                    .findNetvideoDvrcfgs(sort);// 查找所有DVR配置信息
            for (int i = 0; i < dvrSort.size(); i++) {
                dvrSort.get(i).setDvrsequence(newList.get(i).getDvrsequence());// 更新DVR配置信息
            }
            dvrcfgService.saveNetvideoDvrcfg(dvrSort);// 保存重新排序后的DVR配置信息
        }
        return "redirect:/okcsys/video/dvrcfg";
    }

    /**
     * 返回矩阵配置列表
     *
     * @return {@link List} of matrixconfig
     */
    @RequestMapping(value = "/matrixcfg", method = RequestMethod.GET)
    public String getMatrixcfgs(Model model, HttpServletRequest request) {
        try {
            String config = netvideoMatrixConfigService.readMatrixconfig(
                    "matrixconfig", request);
            if (!config.equals("")) {
                ObjectMapper objectMapper = new ObjectMapper();
                ArrayList list = objectMapper.readValue(config, ArrayList.class);
                NetvideoMatrixConfig matrixConfig = objectMapper.readValue(list.get(0).toString(), NetvideoMatrixConfig.class);
                model.addAttribute("matrixconfig", matrixConfig);
            } else {
                NetvideoMatrixConfig matrixConfig = new NetvideoMatrixConfig();
                model.addAttribute("matrixconfig", matrixConfig);
            }
        } catch (Exception e) {
            logger.error("getMatrixcfgs", e);
        }
        return "netvideo/matrixconfig";
    }

    /**
     * 返回矩阵配置列表
     *
     * @return {@link List} of matrixconfig
     */
    @RequestMapping(value = "/matrixcfg", method = RequestMethod.POST)
    public String saveMatrixcfgTofile(@Valid NetvideoMatrixConfig matrixconfig,
                                      HttpServletRequest request, BindingResult result,
                                      SessionStatus status, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            logger.error(result);
            return "netvideo/matrixconfig";
        } else {
            netvideoMatrixConfigService.saveMatrixconfig(matrixconfig, request);
            return "redirect:/okcsys/video/matrixcfg";
        }
    }

    /**
     * 返回轮显组配置列表
     *
     * @return
     */
    @RequestMapping(value = "/rotationcfg", method = RequestMethod.GET)
    public String getRotationcfgs(Model model, HttpServletRequest request) {
        try {

            String config = netvideoRotationConfigService.readconfig(
                    "rotationconfig", request);
            List<NetvideoRotationConfig> list = new ArrayList<NetvideoRotationConfig>();
            if (!config.equals("")) {
                ObjectMapper objectMapper = new ObjectMapper();
                ArrayList list1 = objectMapper.readValue(config, ArrayList.class);
                for (Object obj : list1) {
                    NetvideoRotationConfig netvideoRotationConfig = objectMapper.readValue(obj.toString(), NetvideoRotationConfig.class);
                    list.add(netvideoRotationConfig);
                }
            }
            List<NetvideoCameracfg> cameracfgs = new ArrayList<NetvideoCameracfg>();
            cameracfgs = cameracfgService.findNetvideoCameracfgs();
            model.addAttribute("cameraconfigs", cameracfgs);
            model.addAttribute("rotationconfigs", list);

        } catch (Exception e) {
            logger.error("getRotationcfgs", e);
        }
        return "netvideo/rotationdisplaycfg";
    }

    /**
     * 返回轮显组配置列表
     *
     * @return {@link List} of rotationdisplaycfg
     */
    @RequestMapping(value = "/change/rotationcfg", method = RequestMethod.POST)
    public String saveRotationcfgTofile(ModelArray ma,
                                        HttpServletRequest request) {
        NetvideoRotationConfig[] obj = ma.getRotationConfigs();
        netvideoRotationConfigService.saveRotationconfig(obj, request);
        return "redirect:/okcsys/video/rotationcfg";
    }

    /**
     * 上传驱动到服务器,并记录版本号
     */
    @RequestMapping(value = "/upload/{drivername}", method = RequestMethod.POST)
    @ResponseBody
    public boolean processDriversUpload(@PathVariable String drivername,
                                        HttpServletRequest request) {
        try {
            String path = netvideoSysConfigService.saveDriver(request, "",
                    drivername);// 为“”使用文件原名
            path = path.substring(path.lastIndexOf("/") + 1);// 获取后缀
            logger.debug("driver path:" + path);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取netvideo配置详细版本
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/get/{properties}", method = RequestMethod.GET)
    @ResponseBody
    public Object getNetvideoVersion(@PathVariable String properties, HttpServletRequest request,
                                     HttpServletResponse response) {
        List list = new ArrayList();
        list.add("file was not fond!!");
        if (properties.contains("driver")) {
            try {
                netvideoSysConfigService.downDriver(request, response, properties);
                return null;
            } catch (Exception e) {
                e.printStackTrace();

                return list;
            }
        } else if (properties.equals("versions")) {
            String str0 = netvideoSysConfigService.readVersions(request,
                    "netversion");
            if (str0 == null || str0.length() == 0) {
                return list;
            } else {
                list.clear();
                list.add(str0);
                return list;
            }
        } else if (properties.equals("systemconfig")
                || properties.equals("matrixconfig")
                || properties.equals("rotationconfig")
                || properties.equals("cameragroup")) {
            String configs = "";
            if (properties.equals("matrixconfig")) {
                configs = netvideoMatrixConfigService.readMatrixconfig(
                        "matrixconfig", request);
            } else if (properties.equals("rotationconfig")) {
                configs = netvideoRotationConfigService.readconfig(
                        "rotationconfig", request);
            } else if (properties.equals("systemconfig")) {
                configs = netvideoSysConfigService.readSysconfig(request,
                        "systemconfig");
            } else if (properties.equals("cameragroup")) {
                configs = cameracfgService.readGroupinfo("camGroup", request);
            }
            if (configs == null || configs.length() == 0) {
                return list;
            } else {
                list.clear();
                list.add(configs);
                return list;
            }
        } else if (properties.equals("cameraconfig")
                || properties.equals("dvrconfig")
                || properties.equals("monitorconfig")) {
            String configs = "";
            Object[] cfgs = null;
            if (properties.equals("cameraconfig")) {
                Sort sort = new Sort(Direction.ASC, "displaysequence");
                List<NetvideoCameracfg> cameracfgs = cameracfgService
                        .findSortNetvideoCameracfgs(sort);
                cfgs = cameracfgs.toArray();
            } else if (properties.equals("dvrconfig")) {
                Sort sort = new Sort(Direction.ASC, "dvrsequence");
                List<NetvideoDvrcfg> dvrcfgs = dvrcfgService
                        .findNetvideoDvrcfgs(sort);
                cfgs = dvrcfgs.toArray();
            } else if (properties.equals("monitorconfig")) {
                Sort sort = new Sort(Direction.ASC, "displaysequence");
                List<NetvideoMonitorcfg> monitorcfgs = monitorcfgService
                        .findNetvideoMonitorcfgs(sort);
                cfgs = monitorcfgs.toArray();
            }
            return cfgs;
        } else {
            list.add("file was not fond!!");
            return list;
        }
    }

    //保存组信息到camGroup.ini
    @RequestMapping(value = "/save/g_info", method = RequestMethod.POST)
    @ResponseBody
    public Boolean processSaveGinfo(String group, HttpServletRequest request) {
        Boolean b = true;
        try {
            cameracfgService.saveGroupinfo(group, "camGroup", request);
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

    //
    @RequestMapping(value = "/get/g_info", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String processGetGinfo(String group, HttpServletRequest request) {
        String camGroup = "";
        camGroup = cameracfgService.readGroupinfo("camGroup", request);
        return camGroup;
    }

    @RequestMapping(value = "/get/files", method = RequestMethod.POST)
    @ResponseBody
    public List<String> processgetfileStrings(HttpServletRequest request) {
        List<String> fileStrings = null;
        String dllPath = "resources\\structures\\Netvideo\\Driver";
        fileStrings = FileUtil.getNetvideoFiles(dllPath, request);
        return fileStrings;
    }

    @RequestMapping(value = "/process/{type}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> processDirList(@PathVariable String type, HttpServletRequest request,
                                       String name, String path) {
        FileUtil.processDir(type, name, path);
        List<String> fileStrings = null;
        String dllPath = "resources\\structures\\Netvideo\\Driver";
        fileStrings = FileUtil.getNetvideoFiles(dllPath, request);
        return fileStrings;
    }

}
