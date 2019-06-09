package com.lw.jpa.demo.data.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author wei.liuw
 * @date  2019-5-7
 */
@Entity
@Data
@Table(name = "TB_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    private String name;
    private int age;
    private String gender;

}
