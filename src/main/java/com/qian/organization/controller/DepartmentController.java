package com.qian.organization.controller;

import com.qian.organization.dataobject.Department;
import com.qian.organization.service.DepartmentService;
import com.qian.organization.utils.CommonConstants;
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

        if(CommonConstants.NOT_UNIQUE.equals(departmentService.checkDeptNameUnique(dept))){
            return CommonResult.error("新增部门" + dept.getName() + "'失败，部门名称已存在");
        }
        return toResult(departmentService.insertDept(dept));

    }

    /**
     * 修改部门
     */
    public CommonResult edit(@Validated @RequestBody Department dept){
        if(CommonConstants.NOT_UNIQUE.equals(departmentService.checkDeptNameUnique(dept))){

            return CommonResult.error("修改部门'"+dept.getName() + "'失败，部门名称已存在");

        }else if(dept.getParentId().equals(dept.getDeptId())){

            return CommonResult.error("修改部门'" + dept.getName() + "'失败，上级部门不能是自己");

        }
        else if(CommonStringUtils.equals(CommonConstants.DEPT_DISABLE, dept.getId()) &&
                departmentService.selectNormalChildrenDeptById(dept.getDeptId())>0){

            return CommonResult.error("该部门包含未停用的子部门！");

        }

        return toResult(departmentService.departmentToUpdate(dept));

    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    public CommonResult remove(@PathVariable String deptId){
        if (departmentService.hasChildByDeptId(deptId)){
            return CommonResult.error("存在下级部门，不允许删除");
        }

        return toResult(departmentService.deleteDeptById(deptId));
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected CommonResult toResult(int rows)
    {
        return rows > 0 ? CommonResult.success() : CommonResult.error();
    }
}



