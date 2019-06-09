package com.lw.jpa.demo.global;

import com.lw.jpa.demo.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wei.liuw
 * @date 2019/5/7
 */
@ControllerAdvice
@Slf4j
public class ControllerAdviceHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultResponse unCatchExceptionResponse(Exception e){
        log.error("处理时出现未捕获异常：", e);
        return ResponseUtil.buildResponse(ResponseCodeEnum.FAIL, "处理时出现未知异常！");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseBody
    public ResultResponse dataNoExistsResponse(EmptyResultDataAccessException e) {
        log.error("处理时出现操作数据不存在的异常：", e);
        return ResponseUtil.buildResponse(ResponseCodeEnum.FAIL, "操作数据不存在！");
    }

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Object exceptionHandler(MyException e) {
        log.error("出现通用型业务调用异常：", e);
        return ResponseUtil.buildResponse(ResponseCodeEnum.FAIL, e.getMessage());
    }

}
