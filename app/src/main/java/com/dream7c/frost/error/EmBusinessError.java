package com.dream7c.frost.error;

public enum EmBusinessError implements CommonError {
    //Data process error
    DATA_VALIDATION_ERROR(10001, "数据为空或不合法"),
    PARAMETER_VALIDATION_ERROR(10002, "参数为空或不合法"),
    SORT_ERROR(10003, "数据无法排序")
    ;

    private int errCode;
    private String errMsg;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
