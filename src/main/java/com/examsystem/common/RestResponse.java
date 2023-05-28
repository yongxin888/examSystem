package com.examsystem.common;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 通用响应类
 * @DATE: 2023/5/8 17:00
 */
public class RestResponse<T> {
    //状态码
    private int code;
    //响应信息
    private String message;
    //响应数据
    private T response;

    /**
     * 无响应数据
     * @param code 状态码
     * @param message 响应信息
     */
    public RestResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 生成响应类数据
     * @param code 状态码
     * @param message 响应信息
     * @param response 响应数据
     */
    public RestResponse(int code, String message, T response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    /**
     * 失败时响应
     * @param code 状态码
     * @param msg 响应信息
     * @return
     */
    public static RestResponse fail(Integer code, String msg) {
        return new RestResponse<>(code, msg);
    }

    /**
     * 成功时响应
     * @return
     */
    public static RestResponse ok() {
        SystemCode systemCode = SystemCode.OK;
        return new RestResponse<>(systemCode.getCode(), systemCode.getMessage());
    }

    /**
     * 成功时带响应数据
     * @param response 响应数据
     * @return
     * @param <F> 对象类型
     */
    public static <F> RestResponse<F> ok(F response) {
        SystemCode systemCode = SystemCode.OK;
        return new RestResponse<>(systemCode.getCode(), systemCode.getMessage(), response);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
