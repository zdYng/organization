package com.qian.organization.repository;

import com.qian.organization.dataobject.Department;
import com.qian.organization.repository.base.BaseRepository;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Coco
 * @Date: 2020.05.20 13:00
 * @Version: v0.0.1
 */


@Repository


public interface DepartmentRepository extends BaseRepository {

    Collection<Department> findDepartmentsByLevel(Integer level);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合List
     */
    List<Department> selectDeptList(Department dept);
//    List<Department> findByParentId

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    Department selectDeptById(String deptId);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    public List<Department> selectChildrenDeptById(String deptId);

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
    public int hasChildByDeptId(String deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public Department checkDeptNameUnique(@Param("departName") String deptName, @Param("parentId") String parentId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(Department dept);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int departmentToUpdate(Department dept);

    /**
     * 修改所在部门的父级部门状态
     *
     * @param dept 部门
     */
    public void updateDeptStatus(Department dept);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<Department> depts);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(String deptId);


}
