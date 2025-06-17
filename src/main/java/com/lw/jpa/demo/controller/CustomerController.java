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

import jakarta.validation.ConstraintViolationException;

/**
 * @author wei.liuw
 * @date 2019/5/31
 */
@RestController
@RequestMapping("custom")
@Slf4j
@Api(value = "Customer information operate ")
public class CustomerController {

    @Autowired
    CustomRepository customRepository;

    @ApiOperation(value = "add customer", notes = "add customer with data support by user")
    @ApiImplicitParam(name = "custom", required = true, paramType = "body")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Object addCustom(@ModelAttribute Custom custom) {
        try {
            return customRepository.save(custom).toString();
        } catch (ConstraintViolationException e) {
            log.error("Constraint check failed:", e);
            return e.getMessage();
        } catch (TransactionSystemException e){
            log.error("Transaction exception:", e);
            Throwable t = e.getCause();
            if(null != t && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
                if(t instanceof ConstraintViolationException) {
                    return ConstraintViolationExceptionHandler.getCauseMessage((ConstraintViolationException) t);
                }
            }
            return null != e.getMessage() ? "Unknown Exception":e.getMessage();
        } catch (Exception e) {
            log.error("Unknown Exception：", e);
            return "Unknown Exception!";
        }
    }
}
