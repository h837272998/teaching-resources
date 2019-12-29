package cn.onb.tr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/2 0:00
 */
@Data
public class SysLog implements Serializable {
    private static final long serialVersionUID = 2545693910705496235L;
    private Long id;
    //	用户名
    private String username;
    //	归属模块
    private String module;
    //	执行方法的参数值
    private String params;
    private String remark;
    //	是否执行成功
    private Boolean flag;

    private Date createTime;
}
