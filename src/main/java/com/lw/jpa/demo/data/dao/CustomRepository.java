package com.lw.jpa.demo.data.dao;

import com.lw.jpa.demo.data.entity.Custom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wei.liuw
 * @date 2019/5/31
 */
public interface CustomRepository extends JpaRepository<Custom, Long> {
}
