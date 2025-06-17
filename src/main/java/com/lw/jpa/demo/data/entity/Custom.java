package com.lw.jpa.demo.data.entity;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

import jakarta.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author wei.liuw
 * @date 2019/5/24
 */
@Entity
@Table(name = "TB_CUSTOM")
@ApiModel("Customer")
@Builder
public class Custom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true, value = "Customer ID")
    private Long id;

    @NotEmpty(message = "Name should not be empty!")
    @ApiModelProperty("Customer name")
    private String name;

    @NotEmpty(message = "Age should not be empty!")
    @Range(min = 0, max = 150, message = "Age is illegal")
    @ApiModelProperty("Customer age")
    private String age;

    @NotEmpty(message = "Email address should not be null!")
    @Email(message = "Email address is illegal")
    @ApiModelProperty("Customer email address")
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
