package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.FileUtil;
import com.energicube.eno.monitor.model.KeyValue;
import com.energicube.eno.monitor.model.OkcMenu;
import com.energicube.eno.monitor.model.Pagelayout;
import com.energicube.eno.monitor.model.Pagetag;
import com.energicube.eno.monitor.repository.PagelayoutRepository;
import com.energicube.eno.monitor.repository.PagetagRepository;
import com.energicube.eno.monitor.service.PagelayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
public class PagelayoutServiceImpl implements PagelayoutService {

    private PagelayoutRepository pagelayoutRepository;
    private PagetagRepository pagetagRepository;

    @Autowired
    public PagelayoutServiceImpl(PagelayoutRepository pagelayoutRepository, PagetagRepository pagetagRepository) {
        this.pagelayoutRepository = pagelayoutRepository;
        this.pagetagRepository = pagetagRepository;
    }


    @Transactional(readOnly = true)
    public Pagelayout findOne(long pagelayoutuid) {
        return pagelayoutRepository.findOne(pagelayoutuid);
    }

    ///@Cacheable(value="pagelayouts")
    @Transactional(readOnly = true)
    public List<Pagelayout> findAllPagelayout() {
        return pagelayoutRepository.findAll(new Sort(Direction.ASC, "layoutname"));
    }

    @Transactional(readOnly = true)
    public Pagelayout findDefaultLayoutByMenuid(String menuid) {

        List<Pagelayout> results = new ArrayList<Pagelayout>();
        List<Pagelayout> pagelayouts = findLayoutsByMenuid(menuid);
        //如果存在多个页面只取第一页
        for (Pagelayout pagelayout : pagelayouts) {
            if (pagelayout.getPageindex() == 0) {
                results.add(pagelayout);
            }
        }
        //判断重复(当两条数据都设置页面索引为0时)，只取第一条数据
        return results.get(0);

    }

    @Transactional(readOnly = true)
    public Pagelayout findLayoutsByMenuid(String menuid, int pageindex) {
        if (!StringUtils.hasLength(menuid)) {
            throw new IllegalArgumentException("菜单ID不能为空");
        }
        List<Pagelayout> result = pagelayoutRepository.findByMenuidAndPageindex(menuid, pageindex);
        return result.isEmpty() ? new Pagelayout() : result.get(0);
    }


    @Transactional(readOnly = true)
    public List<Pagelayout> findLayoutsByMenuid(String menuid) {

        List<Pagelayout> result = pagelayoutRepository.findByMenuid(menuid);

        Collections.sort(result, new Comparator<Pagelayout>() {
            public int compare(Pagelayout a, Pagelayout b) {
                int orderA = a.getPageindex();
                int orderB = b.getPageindex();
                return orderA - orderB;
            }
        });

        return result;
    }


    @Transactional(readOnly = true)
    public Page<Pagelayout> findPagelayoutList(Pageable pageable) {

        return pagelayoutRepository.findAll(pageable);
    }

    @CacheEvict(value = "syscontrols", allEntries = true)
    @Transactional
    public Pagelayout savePagelayout(Pagelayout pagelayout) {
        boolean isnew = pagelayout.getPagelayoutuid() == 0;
        if (isnew) {
            pagelayout = pagelayoutRepository.save(pagelayout);
            pagelayout.setLayoutid(pagelayout.getPagelayoutuid()+"");
            pagelayout = pagelayoutRepository.saveAndFlush(pagelayout);
//            if (StringUtils.hasLength(pagelayout.getLayoutid())) {
//                pagelayout.setLayoutid("" + pagelayout.getPagelayoutuid());
//            }
        } else {
            Pagelayout oldPagelayout = pagelayoutRepository.findOne(pagelayout.getPagelayoutuid());
            pagelayout.setLayoutid(oldPagelayout.getLayoutid());
            pagelayout = pagelayoutRepository.save(pagelayout);
        }

        return pagelayout;
    }

    @CacheEvict(value = "syscontrols", allEntries = true)
    @Transactional
    public void deletePagelayout(long pagelayoutuid) {

        Pagelayout pagelayout = pagelayoutRepository.findOne(pagelayoutuid);
        pagelayoutRepository.delete(pagelayout);

        //删除布局包括的设备点
        List<Pagetag> entities = pagetagRepository.findByLayoutid(pagelayout.getLayoutid());
        pagetagRepository.deleteInBatch(entities);
    }

    @CacheEvict(value = "pagelayoutfiles", allEntries = true)
    @Transactional
    public String saveBackgroundFile(HttpServletRequest request, String syscode, String filename) throws Exception {
        //设置上传目录
        String dirName = "resources/structures/" + syscode;
        return FileUtil.uploadFile(request, filename, dirName);
    }

    // 下载（导出）菜单配置文件的响应 [ ChengKang 2014-07-27 ]
    @CacheEvict(value = "pagelayoutfiles", allEntries = true)
    @Transactional
    public String exportMenuFile_old(HttpServletRequest request, String syscode, List<OkcMenu> Menus) {
        // [ ChengKang 2014-07-27 ]
        // 设置下载目录，实际下载的文件保存在服务器：【Tomcat目录】\webapps\【项目名】\resources\structures\【syscode】
        String dirName = "resources/structures/" + syscode;
        try {
            // 调用函数对菜单数据做分析后导出成Excel  [ ChengKang 2014-07-27 ]
            // 函数返回生成的Excel文件路径
            // 这里作为Service的实现类，不进行处理，而是直接返回，供上层处理
            return FileUtil.exportMenuExcel_old(request, dirName, Menus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";        // 前端Web将根据返回不是“Error”就认为成功  [ ChengKang 2014-07-27 ]
    }

    // 下载（导出）菜单配置文件的响应 [ ChengKang 2014-09-05 ]
    @CacheEvict(value = "pagelayoutfiles", allEntries = true)
    @Transactional
    public String exportMenuFile(HttpServletRequest request, String syscode, List<Map> Menus) {
        //  [ ChengKang 2014-09-05 ]
        // 设置下载目录，实际下载的文件保存在服务器：【Tomcat目录】\webapps\【项目名】\resources\structures\【syscode】
        String dirName = "resources/structures/" + syscode;
        try {
            // 调用函数对菜单数据做分析后导出成Excel   [ ChengKang 2014-09-05 ]
            // 函数返回生成的Excel文件路径
            // 这里作为Service的实现类，不进行处理，而是直接返回，供上层处理
            return FileUtil.exportMenuExcel(request, dirName, Menus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";        // 前端Web将根据返回不是“Error”就认为成功  [ ChengKang 2014-07-27 ]
    }

    // 上传菜单配置文件的响应 [ ChengKang 2014-07-25 ]
    @CacheEvict(value = "pagelayoutfiles", allEntries = true)
    @Transactional
    public OkcMenu[] saveMenuFile(HttpServletRequest request, String syscode, String filename) throws Exception {
        // [ ChengKang 2014-07-25 ]
        // 设置上传目录，实际上传的文件保存在服务器：【Tomcat目录】\webapps\【项目名】\resources\structures\【syscode】
        String dirName = "resources/structures/" + syscode;
        String path = FileUtil.uploadFile(request, filename, dirName);
        try {
            // 调用函数对Excel配置文件进行解析  [ ChengKang 2014-07-26 ]
            // 函数返回配置文件解析后，获得的OkcMenu对象数组
            // 这里作为Service的实现类，不对OkcMenu对象数组进行处理，而是直接返回，供上层处理
            OkcMenu[] Menus = FileUtil.analyzeMenuExcel(request, dirName, filename);
            return Menus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //	@Cacheable(value="pagelayoutfiles")
    @Transactional(readOnly = true)
    public List<KeyValue> getFileNames(HttpServletRequest request, String syscode) {
        List<KeyValue> list = new ArrayList<KeyValue>();
        String path = "resources/structures/" + syscode;

        File[] files = FileUtil.getFiles(request, path);
        if (files.length == 0)
            return list;
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            list.add(new KeyValue(file.getName(), path + "/" + file.getName()));
        }
        return list;
    }

    @Transactional
    public List<Pagelayout> findByLayoutid(String layoutid) {
        return pagelayoutRepository.findByLayoutid(layoutid);
    }

    @Override
    public List<Pagelayout> findByDeviceconfigid(int deviceconfigid) {
        return pagelayoutRepository.findByDeviceconfigid(deviceconfigid);
    }

}
