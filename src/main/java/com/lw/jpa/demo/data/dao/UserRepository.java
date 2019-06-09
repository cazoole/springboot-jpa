package com.lw.jpa.demo.data.dao;

import com.lw.jpa.demo.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author wei.liuw
 * @date    2019/5/7
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     *  根据用户名称和年龄获取用户信息
     * @param name 用户名称
     * @param age   用户年龄
     * @return    符合条件的用户信息
     */
    User getUserByNameAndAge(String name, int age);

    /**
     * 自定义查询语句的查询，根据用户名称和年龄获取用户信息
     * @param name      用户名称
     * @param age       用户年龄
     * @return          用户信息
     */
    @Query("select u from User u where u.name=:name and u.age =:age")
    User getUser(@Param("name") String name, @Param("age") int age);
}
