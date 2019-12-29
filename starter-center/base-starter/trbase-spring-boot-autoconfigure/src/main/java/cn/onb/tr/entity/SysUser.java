package cn.onb.tr.entity;

import cn.onb.tr.support.JSONViewInterface;
import cn.onb.tr.support.RespBean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonBackReference;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;
/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/1 23:34
 */
@Data
@TableName("sys_user")
public class SysUser extends Model<SysUser> implements Serializable {

    private static final long serialVersionUID = -9059533247448572412L;

    /**
     * 雪花算法生成ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonView(JSONViewInterface.User.Simple.class)
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @JsonView(JSONViewInterface.User.Simple.class)
    private String username;

    @NotBlank(message = "密码不能为空")
    @JsonView(JSONViewInterface.User.All.class)
    private String password;

    @TableField(value = "real_name")
    @JsonView(JSONViewInterface.User.Simple.class)
    private String realName;

    @JsonView(JSONViewInterface.User.Simple.class)
    private String avatar;

    @JsonView(JSONViewInterface.User.Simple.class)
    private String mobile;

    @TableField(value = "is_male")
    @JsonView(JSONViewInterface.User.All.class)
    private int male;

    @TableField(value = "is_enabled")
    @JsonView(JSONViewInterface.User.Simple.class)
    private boolean enabled;

    @TableField(value="create_time")
    @JsonView(JSONViewInterface.User.All.class)
    private Instant createTime;

    @TableField(value="update_time")
    @JsonView(JSONViewInterface.User.All.class)
    private Instant updateTime;

//    @TableField(exist=false)
//    private List<SysRole> roles;
//
//    @TableField(exist=false)
//    private String roleId;
//
//    @TableField(exist=false)
//    private String oldPassword;
//
//    @TableField(exist=false)
//    private String newPassword;

}
