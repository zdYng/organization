package com.qian.organization.service.impl;

import com.qian.organization.VO.TreeSelect;
import com.qian.organization.dataobject.Department;
import com.qian.organization.exception.CustomException;

import com.qian.organization.mapper.DepartmentMapper;
import com.qian.organization.service.DepartmentService;
import com.qian.organization.utils.CommonStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Coco
 * @Date: 2020.05.21 10:30
 * @Version: v0.0.1
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {
    /** 校验返回结果码 */
    public final static String UNIQUE = "0";
    public final static String NOT_UNIQUE = "1";
    /** 部门正常状态 */
    public static final String DEPT_NORMAL = "0";

    /** 部门停用状态 */
    public static final String DEPT_DISABLE = "1";

    private DepartmentMapper departmentMapper;


    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合List
     */
    @Override
    public List<Department> selectDeptList(Department dept) {
        return departmentMapper.selectDeptList(dept);
    }

    /**
     * 构建部门树结构
     *
     * @param depts 部门列表
     * @return 树结构列表List
     */
    @Override
    public List<Department> buildDeptTree(List<Department> depts) {

        List<Department> rtList = new ArrayList<Department>();
        List<String> tempList = new ArrayList<String>();
        for(Department dept:depts){
            tempList.add(dept.getId());
        }
        for(Iterator<Department> iterator = depts.iterator(); iterator.hasNext();){
            Department dept = (Department)iterator.next();
            if(!tempList.contains(dept.getParentId())){
                recursionDeptFn(depts,dept);
                rtList.add(dept);
            }
        }
        if(rtList.isEmpty()){
            rtList = depts;
        }

        return rtList;
    }

    /**
     * 下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<Department> depts) {
        return null;
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public Department selectDeptById(String deptId) {

        return departmentMapper.selectDeptById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(String deptId) {
        return departmentMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(String deptId)
    {
        int result = departmentMapper.hasChildByDeptId(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(Department dept) {
        String deptId = CommonStringUtils.isNull(dept.getParentId())? "" :dept.getId();
        Department info = departmentMapper.checkDeptNameUnique(dept.getName(),dept.getParentId());
        if(CommonStringUtils.isNotNull(info)&&info.getId()!=deptId){
            return NOT_UNIQUE;
        }
        return UNIQUE;
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(Department dept) {

        Department info = departmentMapper.selectDeptById(dept.getParentId());
        if(!DEPT_NORMAL.equals(info.getStatus())){
            throw new CustomException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());

        return departmentMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int departmentToUpdate(Department dept) {

        Department newParentDept = departmentMapper.selectDeptById(dept.getParentId());
        Department oldDept = departmentMapper.selectDeptById(dept.getId());
        if (CommonStringUtils.isNotNull(newParentDept) && CommonStringUtils.isNotNull(oldDept)){
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getParentId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors,oldAncestors);
        }
        int result = departmentMapper.departmentToUpdate(dept);
        if (DEPT_NORMAL.equals(dept.getStatus())){
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(String deptId) {
        return departmentMapper.deleteDeptById(deptId);
    }


    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(String deptId, String newAncestors, String oldAncestors){
        List<Department> children = departmentMapper.selectChildrenDeptById(deptId);
        for(Department child:children){
            child.setAncestors(child.getAncestors().replace(oldAncestors,newAncestors));
        }
        if (children.size()>0){
            departmentMapper.updateDeptChildren(children);
        }
    }

    /**
     * 递归列表
     */
    private void recursionDeptFn(List<Department> list, Department dept){
        // 获取子节点列表
        List<Department> childList = getChildList(list,dept);
        dept.setChildren(childList);
        for(Department tChild:childList){
            if(hasChild(list,tChild)){
                //判断是否有子节点
                Iterator<Department> it = childList.iterator();
                while(it.hasNext()){
                    Department n = (Department) it.next();
                    recursionDeptFn(list,n);
                }
            }
        }

    }

    /**
     * 获取子节点列表
     */
    private List<Department> getChildList(List<Department> list, Department dept){
        List<Department> deptList = new ArrayList<Department>();
        Iterator<Department> deptIt = list.iterator();
        while(deptIt.hasNext()){
            Department n = (Department)deptIt.next();
            if((CommonStringUtils.isNotNull(n.getParentId())) && (n.getParentId() == dept.getId())){
                deptList.add(n);
            }
        }
        return deptList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Department> list, Department t){
        return getChildList(list,t).size() > 0 ? true : false;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(Department dept)
    {
        String updateBy = dept.getUpdateBy();
        dept = departmentMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        departmentMapper.updateDeptStatus(dept);
    }

}
