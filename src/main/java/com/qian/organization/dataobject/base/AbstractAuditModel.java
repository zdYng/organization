package com.qian.organization.dataobject.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Coco
 * @Date: 2020.05.20 10:00
 * @Version: v0.0.1
 */


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AbstractAuditModel implements Serializable {

    /**
     * 主键
     */
//    @Id
//    private Long id;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name="jpa-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, updatable = false)
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", nullable = false)
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date UpdateTime;


    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }
}
