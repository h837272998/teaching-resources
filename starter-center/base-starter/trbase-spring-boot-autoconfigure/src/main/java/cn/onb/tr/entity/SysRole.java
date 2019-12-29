package cn.onb.tr.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/1 23:57
 */
@Data
@TableName("sys_role")
public class SysRole extends Model<SysRole> implements Serializable {
    private static final long serialVersionUID = -4840598196001746325L;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private String code;
    private String name;
    private String describe;
    private Instant createTime;
    private Instant updateTime;
}
