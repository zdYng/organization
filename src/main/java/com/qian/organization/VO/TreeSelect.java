package com.qian.organization.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qian.organization.dataobject.Department;
import com.sun.source.tree.Tree;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * TreeSelect 树结构实体类
 *
 * @Author: Coco
 * @Date: 2020.05.20 13:40
 * @Version: v0.0.1
 */

public class TreeSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     * */
    private String id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect(){

    }

    public TreeSelect(Department dept){
        this.id = dept.getId();
        this.label = dept.getName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public String getId(){
        return id;
    }

    public String getLabel(){
        return label;
    }

    public List<TreeSelect> getChildren(){
        return children;
    }

    public void setChildren(List<TreeSelect> children){
        this.children=children;
    }



}
