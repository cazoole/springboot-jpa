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
     *  paramType 表明了参数的来源：
     * \\ @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
     *      header-->请求参数的获取：@RequestHeader
     *      query-->请求参数的获取：@RequestParam
     *      path（用于restful接口）-->请求参数的获取：@PathVariable
     *      body（不常用）
     *      form（不常用）
     * @param id 客户ID
     * @return string
     */
    @ApiOperation(value = "根据客户ID删除客户信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "客户ID", required = true)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object deleteCustom(@PathVariable("id") String id) {
        log.info("删除客户开始，客户ID:" + id);
        customRepository.deleteById(Long.valueOf(id));
        log.info("删除客户结束，客户ID：" + id);
        return ResponseUtil.buildResponse(ResponseCodeEnum.SUCCESS);
    }

    @ApiOperation(value = "根据用户")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object addCustom(@ModelAttribute Custom custom) {
        return customRepository.save(custom);
    }

}
