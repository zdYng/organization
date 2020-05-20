package com.qian.organization.repository;

import com.qian.organization.dataobject.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @Author: Coco
 * @Date: 2020.05.20 13:00
 * @Version: v0.0.1
 */


@Repository
public interface DepartmentRepository extends JpaRepository<Department,String> {

    Collection<Department> findDepartmentsByLevel(Integer level);


}
