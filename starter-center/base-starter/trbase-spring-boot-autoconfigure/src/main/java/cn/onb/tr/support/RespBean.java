package cn.onb.tr.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/1 18:44
 */
public class RespBean<T> extends AbstractRespBean<T> {
    private static final long serialVersionUID = 4521497191482750246L;

    @Builder
    public RespBean(Integer code, String msg, T data) {
        super(code, msg, data);
    }

    public RespBean() {
    }
}
