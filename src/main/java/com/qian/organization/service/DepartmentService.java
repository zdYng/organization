package com.qian.organization.service;

import com.qian.organization.VO.TreeSelect;
import com.qian.organization.dataobject.Department;

import java.util.List;

/**
 * 部门管理
 * 服务层
 *
 * @Author: Coco
 * @Date: 2020.05.20 13:30
 * @Version: v0.0.1
 */

public interface DepartmentService {

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合List
     */
    List<Department> selectDeptList(Department dept);

    /**
     * 构建部门树结构
     * @param depts 部门列表
     * @return 树结构列表List
     */
    List<Department> buildDeptTree(List<Department> depts);



    /**
     * 下拉树结构
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildDeptTreeSelect(List<Department> depts);


    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    Department selectDeptById(String deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(String deptId);

    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public boolean hasChildByDeptId(String deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(Department dept);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(Department dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int departmentToUpdate(Department dept);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(String deptId);

}
