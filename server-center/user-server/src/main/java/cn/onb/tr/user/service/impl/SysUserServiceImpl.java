package cn.onb.tr.user.service.impl;

import cn.onb.tr.entity.*;
import cn.onb.tr.user.mapper.ISysPremissionMapper;
import cn.onb.tr.user.mapper.ISysRoleMapper;
import cn.onb.tr.user.mapper.ISysUserMapper;
import cn.onb.tr.user.mapper.ISysUserRoleMapper;
import cn.onb.tr.user.service.ISysUserService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 19/12/6 19:50
 */
@Slf4j
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private ISysUserMapper sysUserMapper;

    @Autowired
    private ISysPremissionMapper sysPremissionMapper;

    @Autowired
    private ISysRoleMapper sysRoleMapper;


    @Override
    @Cacheable(cacheNames = "user",key = "#username",unless="#result == null")
    public UserDetailsImpl getUserByUserName(String username) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
        return sysUserTransformUserDetails(user);
    }




    @Override
    @Cacheable(cacheNames = "user",key = "#phone",unless="#result == null")
    public UserDetailsImpl getUserByPhone(String phone) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("mobile", phone));
        return sysUserTransformUserDetails(user);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ISysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(Instant.now());
        user.setUpdateTime(Instant.now());
        int insert = sysUserMapper.insert(user);
        int insert1 =
                sysUserRoleMapper.insert(SysUserRole.builder().userId(String.valueOf(user.getId())).roleId("2").build());
        if (insert1 == 0 || insert == 0) {
            throw new RuntimeException();
        }

    }

    /**
     * 如果账号存在则转化成UserDetails 就是添加权限和角色
     *
     * @param sysUser 用户封装
     * @return
     */
    private UserDetailsImpl sysUserTransformUserDetails(SysUser sysUser) {
        if (sysUser != null) {
            UserDetailsImpl user = new UserDetailsImpl();
            BeanUtils.copyProperties(sysUser, user);

            List<SysRole> sysRoles = sysRoleMapper.getByUser(sysUser.getId());
            user.setSysRoles(new HashSet<>(sysRoles));

            if (CollectionUtils.isNotEmpty(sysRoles)) {
                Set<Long> roleIds = sysRoles.parallelStream().map(r -> r.getId()).collect(Collectors.toSet());
                Set<SysPermission> sysPermissions = sysPremissionMapper.getByRoles(roleIds);
                if (CollectionUtils.isNotEmpty(sysPermissions)) {
                    Set<String> permissions = sysPermissions.parallelStream().map(p -> p.getPermission())
                            .collect(Collectors.toSet());
                    user.setPermissions(permissions);
                }
            }
            return user;
        }
        return null;
    }
}
