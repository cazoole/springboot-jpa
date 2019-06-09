package com.lw.jpa.demo.global;

/**
 * @author wei.liuw
 * @date 2019/5/7
 */
public enum ResponseCodeEnum {
    /** 成功响应*/
    SUCCESS("0000", "处理完成"),
    /** 失败的响应*/
    FAIL("1111", "处理异常");

    /** 响应码  */
    private String code;
    /** 响应描述 */
    private String desc;

    ResponseCodeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public ResponseCodeEnum valueByCode(String code) {
        ResponseCodeEnum codeEnum = null;
        for (ResponseCodeEnum responseCodeEnum : ResponseCodeEnum.values()) {
            if(responseCodeEnum.getCode().equals(code)) {
                codeEnum = responseCodeEnum;
                break;
            }
        }
        return codeEnum;
    }

    public static boolean isSuccess(String code) {
        return SUCCESS.getCode().equals(code);
    }
}
