package com.energicube.eno.monitor.service;

import com.energicube.eno.common.model.Tree;
import com.energicube.eno.common.util.TreeUtil;
import com.energicube.eno.monitor.model.Department;
import com.energicube.eno.monitor.repository.DepartmentRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final static Log logger = LogFactory.getLog(DepartmentServiceImpl.class);

    public DepartmentServiceImpl() {
    }

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<Tree> departmentListByTreeComboTree() {
        List<Tree> treelist = new ArrayList<Tree>();
        List<Department> departmentList = departmentRepository.findAll();
        for (int i = 0; i < departmentList.size(); i++) {
            Department department = departmentList.get(i);
            Tree tree = transform(department);
            treelist.add(tree);
        }
        return TreeUtil.build(treelist);
    }

    private Tree transform(Department department) {
        Tree tree = new Tree();
        tree.setId(department.getCudeptid() + "");
        tree.setText(department.getDeptnum());
        tree.setParentid(department.getParent() == null ? "" : department.getParent());
        return tree;
    }

    public void updateDepartment(int cudeptid, String parent, String description, String deptnum) {
        departmentRepository.updateDepartment(cudeptid, parent, description, deptnum);
    }
}
