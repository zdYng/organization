package com.qian.organization.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Coco
 * @Date: 2020.05.22 11:20
 * @Version: v0.0.1
 */

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    /**
     * 查询多个属性
     * 返回List<Object[]>数组形式的List，数组中内容按照查询字段先后
     *
     * @param sql 原生SQL语句
     * @return
     */
    List<Object[]> sqlArrayList(String sql);

    /**
     * 查询多个属性
     * 返回List<Object>对象形式的List，Object为Class格式对象
     *
     * @param sql 原生SQL语句
     * @param obj Class格式对象
     * @return
     */
    List sqlObjectList(String sql, Object obj);

    /**
     * 查询单个属性
     * 返回List<Object>对象形式的List，Object为对象数据类型
     *
     * @param sql 原生SQL语句
     * @return
     */
    List sqlSingleList(String sql);
}
