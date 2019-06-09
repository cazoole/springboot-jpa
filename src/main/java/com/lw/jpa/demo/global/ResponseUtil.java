package com.lw.jpa.demo.global;

import com.lw.jpa.demo.exception.MyException;

/**
 * @author wei.liuw
 * @date 2019/6/5
 */
public class ResponseUtil {

    public static ResultResponse builResponse(MyException e) {
        return new ResultResponse()
                .setRespCode(e.getCode())
                .setRespMsg(e.getMessage())
                .setSuccess(ResponseCodeEnum.isSuccess(e.getCode()));
    }

    public static ResultResponse buildResponse(ResponseCodeEnum responseCodeEnum) {
        return buildResponse(responseCodeEnum, null, null);
    }

    public static ResultResponse buildResponse(ResponseCodeEnum responseCodeEnum, String message) {
        return buildResponse(responseCodeEnum, message, null);
    }

    public static ResultResponse buildResponse(ResponseCodeEnum responseCodeEnum, String message, Object data) {
        return new ResultResponse()
                .setRespCode(responseCodeEnum.getCode())
                .setRespMsg(responseCodeEnum.getDesc().concat(null == message ? "":"，异常原因：".concat(message)))
                .setSuccess(ResponseCodeEnum.isSuccess(responseCodeEnum.getCode()))
                .setRespData(data);
    }
}
