package io.renren.modules.ga.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean success = true;

    private Integer code;

    private T data;

    private String msg;



    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(T data){
        this.data = data;
    };

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
