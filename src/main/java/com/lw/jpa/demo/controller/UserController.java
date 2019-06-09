package com.lw.jpa.demo.controller;

import com.lw.jpa.demo.aspect.LogIntercept;
import com.lw.jpa.demo.data.dao.UserRepository;
import com.lw.jpa.demo.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author  wei.liuw
 * @date    2019/5/7
 */
@RestController
@LogIntercept
@ApiIgnore
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("user/add/{name}/{age}/{gender}")
    public Object addUser(@PathVariable("name") String name, @PathVariable("age") int age,
                          @PathVariable("gender") String gender) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setGender(gender);

        User savedUser = userRepository.save(user);
        return "already add. UserId = ".concat(String.valueOf(savedUser.getUserId()));
    }

    @GetMapping("user/get/{name}/{age}")
    public Object getUserByNameAndAge(@PathVariable("name") String name, @PathVariable("age") int age) {
        return userRepository.getUserByNameAndAge(name, age).toString();
    }

    @GetMapping("user/get/query/{name}/{age}")
    public Object getByQuery(@PathVariable("name") String name, @PathVariable("age") int age) {
        return userRepository.getUser(name, age).toString();
    }
}
