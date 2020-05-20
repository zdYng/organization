package com.qian.organization.controller;

import com.qian.organization.dataobject.Department;
import com.qian.organization.service.DepartmentService;
import com.qian.organization.utils.CommonResult;
import com.qian.organization.utils.CommonStringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @Author: Coco
 * @Date: 2020.05.20 13:30
 * @Version: v0.0.1
 */

@RestController
@RequestMapping("/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取部门列表
     */
    @GetMapping("/list")
    public CommonResult list(Department dept){
        List<Department> depts = departmentService.selectDeptList(dept);
        return CommonResult.success(depts);

    }

    /**
     * 查询部门列表（排除当前节点）
     * @param deptId
     * @return
     */
    @GetMapping("/list/exclude/{deptId}")
    public CommonResult excludeChild(@PathVariable(value = "deptId", required = false) String deptId){
        List<Department> depts = departmentService.selectDeptList(new Department());
        Iterator<Department> it = depts.iterator();
        while (it.hasNext()){
            Department d = (Department) it.next();
            if (d.getId() == deptId
                    || ArrayUtils.contains(CommonStringUtils.split(d.getAncestors(), ","), deptId + "")){
                it.remove();
            }
        }
        return CommonResult.success(depts);
    }

    /**
     * 根据部门编号-deptId 获取详细信息
     */
    @GetMapping(value = "/{deptId}")
    public CommonResult getInfo(@PathVariable String deptId){
        return CommonResult.success(departmentService.selectDeptById(deptId));
    }

    /**
     * 获取部门树列表
     */
    @GetMapping("/treeselect")
    public CommonResult treeselect(Department dept){
        List<Department> depts = departmentService.selectDeptList(dept);
        return CommonResult.success(departmentService.buildDeptTreeSelect(depts));
    }

    /**
     * 新增部门
     */
    @PostMapping
    public CommonResult add(@Validated @RequestBody Department dept){

        dept.setCr

    }

}



