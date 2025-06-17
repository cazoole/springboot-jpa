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
        log.error("Unresolved exception：", e);
        return ResponseUtil.buildResponse(ResponseCodeEnum.FAIL, "Unresolved exception while processing record!");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseBody
    public ResultResponse dataNoExistsResponse(EmptyResultDataAccessException e) {
        log.error("Data not exists exception：", e);
        return ResponseUtil.buildResponse(ResponseCodeEnum.FAIL, "Data not exists while try to operate.");
    }

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Object exceptionHandler(MyException e) {
        log.error("Common exception：", e);
        return ResponseUtil.buildResponse(ResponseCodeEnum.FAIL, e.getMessage());
    }

}
