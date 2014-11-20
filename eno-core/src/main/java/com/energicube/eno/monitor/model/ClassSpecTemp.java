package com.energicube.eno.monitor.model;

import com.energicube.eno.asset.model.ClassSpec;
import com.energicube.eno.asset.model.ClassStructure;

import java.util.List;

/**
 * 设备属性，用于前台页面展示
 * <p/>
 * <p>
 * 主要用于显示设备属性下拉列表数据
 * </p>
 *
 * @author shangepibao
 * @version 1.0
 * @date 2014-09-18
 */
public class ClassSpecTemp {

    private ClassStructure classStructure;
    private List<ClassSpec> classSpecs;

    public ClassStructure getClassStructure() {
        return classStructure;
    }

    public void setClassStructure(ClassStructure classStructure) {
        this.classStructure = classStructure;
    }

    public List<ClassSpec> getClassSpecs() {
        return classSpecs;
    }

    public void setClassSpecs(List<ClassSpec> classSpecs) {
        this.classSpecs = classSpecs;
    }
}
