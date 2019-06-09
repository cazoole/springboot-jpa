package com.lw.jpa.demo.controller;

import com.lw.jpa.demo.data.dao.CustomRepository;
import com.lw.jpa.demo.data.entity.Custom;
import com.lw.jpa.demo.exception.ConstraintViolationExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

/**
 * @author wei.liuw
 * @date 2019/5/31
 */
@RestController
@RequestMapping("custom")
@Slf4j
@Api(value = "顾客信息对应操作的Controller")
public class CustomerController {

    @Autowired
    CustomRepository customRepository;

    @ApiOperation(value = "增加顾客信息", notes = "根据用户输入增加对应的顾客信息")
    @ApiImplicitParam(name = "custom", required = true, paramType = "body")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Object addCustom(@ModelAttribute Custom custom) {
        try {
            return customRepository.save(custom).toString();
        } catch (ConstraintViolationException e) {
            log.error("校验异常", e);
            return e.getMessage();
        } catch (TransactionSystemException e){
            log.error("事务类异常", e);
            Throwable t = e.getCause();
            if(null != t && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
                if(t instanceof ConstraintViolationException) {
                    return ConstraintViolationExceptionHandler.getCauseMessage((ConstraintViolationException) t);
                }
            }
            return null != e.getMessage() ? "未知异常":e.getMessage();
        } catch (Exception e) {
            log.error("未知异常：", e);
            return "未知异常！";
        }
    }
}
