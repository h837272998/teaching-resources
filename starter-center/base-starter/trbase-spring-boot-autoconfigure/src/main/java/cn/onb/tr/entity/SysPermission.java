package cn.onb.tr.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/1 23:57
 */
@Data
@TableName("sys_permission")
public class SysPermission extends Model<SysPermission> implements Serializable {
    private static final long serialVersionUID = -3984060940622308062L;

    private Long id;

    private String permission;

    private String name;

    private String describe;

    private Date createTime;

    private Date updateTime;

}
