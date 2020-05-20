package com.qian.organization.dataobject;

import com.qian.organization.dataobject.base.AbstractAuditModel;
import lombok.*;


import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 部门实体类
 *
 *
 * @Author: Coco
 * @Date: 2020.05.20 10:20
 * @Version: v0.0.1
 */


@Data
@Entity
@Table(name = "department")
public class Department  extends AbstractAuditModel {

    /**
     * 部门名称
     */
    @Column(name = "name", columnDefinition = "varchar(128) not null")
    private String name;

    /**
     * 上级部门id
     */

//    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true)
//    @JoinColumn(name = "superiorId",referencedColumnName = "id")
    private String parentId;

    /**
     * 上级部门名称
     */
    private String parentName;

    /**
     * 所属层级
     */
    @Column(name = "levels", columnDefinition = "int not null default 0")
    private Integer levels;

    /** 祖级列表 */
    private String ancestors;

    /** 部门状态: 0正常，1停用*/
    private String status;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 备注 */
    private String remarks;

    /**
     * 子部门
     */
    private List<Department> children = new ArrayList<Department>();

    public List<Department> getChildren(){
        return children;
    }

    public void setChildren(List<Department> children){
        this.children = children;
    }


}

