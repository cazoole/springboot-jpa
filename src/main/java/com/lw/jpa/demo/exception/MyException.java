package com.lw.jpa.demo.exception;

import org.springframework.util.Assert;

/**
 * @author wei.liuw
 * @date 2019/5/7
 */

public class MyException extends Exception {
    private String code;
    private String desc;

    public MyException(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public MyException setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public MyException setDesc(String desc) {
        this.desc = desc;
        return this;
    }
}
