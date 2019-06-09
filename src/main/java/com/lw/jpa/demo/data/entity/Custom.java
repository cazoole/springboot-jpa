package com.lw.jpa.demo.data.entity;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author wei.liuw
 * @date 2019/5/24
 */
@Entity
@Table(name = "TB_CUSTOM")
@ApiModel("客户对象")
public class Custom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true, value = "客户ID")
    private Long id;

    @NotEmpty(message = "姓名不可以为空！")
    @ApiModelProperty("客户姓名")
    private String name;

    @NotEmpty(message = "年龄不可以为空！")
    @Range(min = 0, max = 150, message = "年龄不合法！")
    @ApiModelProperty("客户年龄")
    private String age;

    @NotEmpty(message = "邮箱不能为空！")
    @Email(message = "邮箱格式不正确！")
    @ApiModelProperty("客户邮箱")
    private String email;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Custom setId(Long id) {
        this.id = id;
        return this;
    }

    public Custom setName(String name) {
        this.name = name;
        return this;
    }

    public Custom setAge(String age) {
        this.age = age;
        return this;
    }

    public Custom setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("age", age)
                .add("email", email)
                .toString();
    }
}
