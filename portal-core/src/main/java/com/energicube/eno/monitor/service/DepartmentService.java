package com.energicube.eno.monitor.service;

import com.energicube.eno.common.model.Tree;

import java.util.List;

/**
 * 文章操作接口
 */
public interface DepartmentService {

    public List<Tree> departmentListByTreeComboTree();

    public void updateDepartment(int cudeptid, String parent, String description, String deptnum);
}
