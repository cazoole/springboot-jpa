package com.lw.jpa.demo.controller;

import com.lw.jpa.demo.data.dao.CustomRepository;
import com.lw.jpa.demo.data.entity.Custom;
import com.lw.jpa.demo.global.ResponseCodeEnum;
import com.lw.jpa.demo.global.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wei.liuw
 * @date 2019/6/5
 */
@RestController
@RequestMapping("custom")
@Slf4j
public class RestfulCustomController {

    @Autowired
    CustomRepository customRepository;

    /**
     *  paramType means the parameter's type：
     *      header--> get info from ：@RequestHeader
     *      query--> get info from：@RequestParam
     *      path（for restful api）--> get param from request path：@PathVariable
     *      body（no need）
     *      form（no need）
     * @param id Customer ID
     * @return string
     */
    @ApiOperation(value = "delete customer with ID")
    @ApiImplicitParam(paramType = "path", name = "id", value = "Customer ID", required = true)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object deleteCustom(@PathVariable("id") String id) {
        log.info("Begin to delete customer, ID:" + id);
        customRepository.deleteById(Long.valueOf(id));
        log.info("Done the delete of customer, ID：" + id);
        return ResponseUtil.buildResponse(ResponseCodeEnum.SUCCESS);
    }

    @ApiOperation(value = "add customer")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object addCustom(@ModelAttribute Custom custom) {
        return customRepository.save(custom);
    }

}
