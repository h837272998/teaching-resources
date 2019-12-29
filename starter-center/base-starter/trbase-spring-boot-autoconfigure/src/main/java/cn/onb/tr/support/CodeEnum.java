package cn.onb.tr.support;

import org.springframework.http.HttpStatus;

/**
 * @Description: hbanana--响应状态码
 * @Author: 、心
 * @Date: 2019/10/31 14:13
 */
public enum CodeEnum {
    /**
     * 成功标志
     */
    SUCCESS(200, HttpStatus.OK),
    /**
     * 失败标志
     */
    ERROR(500,HttpStatus.INTERNAL_SERVER_ERROR),

    UNAUTHORIZED(403, HttpStatus.UNAUTHORIZED);

    private Integer code;

    private HttpStatus status;

    CodeEnum(Integer code,HttpStatus status){
        this.code = code;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
