package com.lw.jpa.demo.global;

/**
 * @author wei.liuw
 * @date 2019/5/7
 */
public enum ResponseCodeEnum {
    /** success*/
    SUCCESS("0000", "PROCEED"),
    /** failed*/
    FAIL("1111", "EXCEPTION");

    /** code  */
    private String code;
    /** code desc */
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
