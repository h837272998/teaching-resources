package cn.onb.tr.support;

import cn.onb.tr.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/12 21:12
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public abstract class AbstractRespBean<T> implements Serializable {

    private static final long serialVersionUID = 728527049766047324L;
    @JsonView(JSONViewInterface.Base.class)
    private Integer code;
    @JsonView(JSONViewInterface.Base.class)
    private String msg;
    @JsonView(JSONViewInterface.Base.class)
    private T data;
}
