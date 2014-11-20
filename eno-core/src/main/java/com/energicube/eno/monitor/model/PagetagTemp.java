package com.energicube.eno.monitor.model;

import java.util.List;

/**
 * 页面标签定义，用于前台页面展示
 * <p/>
 * <p>
 * 主要用于定义各个页面包含的设备点信息，包括设备点ID，名称，以前在页面中的绝对位置
 * </p>
 *
 * @author zouzhixiang
 * @version 1.0
 * @date 2014-09-18
 */
public class PagetagTemp implements java.io.Serializable {


    private Pagetag pagetage;
    private List<Pagetag> pagetags;

    public Pagetag getPagetage() {
        return pagetage;
    }

    public void setPagetage(Pagetag pagetage) {
        this.pagetage = pagetage;
    }

    public List<Pagetag> getPagetags() {
        return pagetags;
    }

    public void setPagetags(List<Pagetag> pagetags) {
        this.pagetags = pagetags;
    }


}
