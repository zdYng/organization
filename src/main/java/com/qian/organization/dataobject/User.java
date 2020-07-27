package com.qian.organization.dataobject;

import com.qian.organization.dataobject.base.AbstractAuditModel;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 *
 * 用户实体类
 *
 * @Author: Coco
 * @Date: 2020.05.20 10:54
 * @Version: v0.0.1
 */

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user")
@ToString(callSuper = true)
public class User extends AbstractAuditModel {

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
     * 用户名
     */
    private String name;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 密码(加密后)
     */
    private String password;

    /**
     *  密码盐值
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 状态，-1：逻辑删除，0：禁用，1：启用
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "sex")
    private Integer sex;

    /**
     *
     * 关联部门表
     * 表名：user_department
     * @JoinTable注解的name属性指定关联表的名字，joinColumns指定外键的名字，关联到关系维护端(User)
     * inverseJoinColumns指定外键的名字，要关联的关系被维护端(Department)
     *
     *
     *
     */
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "orm_usersetdept",joinColumns = @JoinColumn(name = "userid",referencedColumnName="id"),
//            inverseJoinColumns = @JoinColumn(name = "departmenid",referencedColumnName="id"))
//    private Collection<Department> departmentList;

}
