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

    User getUserByNameAndAge(String name, int age);

    @Query("select u from User u where u.name=:name and u.age =:age")
    User getUser(@Param("name") String name, @Param("age") int age);
}
