package com.lw.jpa.demo.global;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * @author wei.liuw
 * @date 2019/5/7
 */
public class ResultResponse implements Serializable {

    private static final long serialVersionUID = -3045163256061385887L;

    private String respCode;
    private String respMsg;
    private boolean success;
    private Object respData;

    public String getRespCode() {
        return respCode;
    }

    public ResultResponse setRespCode(String respCode) {
        this.respCode = respCode;
        return this;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public ResultResponse setRespMsg(String respMsg) {
        this.respMsg = respMsg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResultResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Object getRespData() {
        return respData;
    }

    public ResultResponse setRespData(Object respData) {
        this.respData = respData;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("respCode", respCode)
                .add("respMsg", respMsg)
                .add("success", success)
                .add("respData", respData)
                .toString();
    }
}
