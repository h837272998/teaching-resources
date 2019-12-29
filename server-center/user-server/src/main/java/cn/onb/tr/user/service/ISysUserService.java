package cn.onb.tr.user.service;

import cn.onb.tr.entity.SysUser;
import cn.onb.tr.entity.UserDetailsImpl;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/6 19:49
 */
public interface ISysUserService {

    public UserDetailsImpl getUserByUserName(String username);

    public UserDetailsImpl getUserByPhone(String phone);

    void addUser(SysUser user);
}
