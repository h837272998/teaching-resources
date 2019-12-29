package cn.onb.tr.entity;

import cn.onb.tr.support.RespBean;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description: (onb)->
 * @Author: 、心
 * @Date: 2019/12/2 13:38
 */

@Data
public class UserDetailsImpl extends SysUser implements UserDetails {
    private static final long serialVersionUID = -5480205288966580473L;

    @JsonIgnore
    private Set<SysRole> sysRoles;

    @JsonIgnore
    private Set<String> permissions;

    /***
     * 权限重写
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            sysRoles.parallelStream().forEach(role -> {
                if (role.getCode().startsWith("ROLE_")) {
                    collection.add(new SimpleGrantedAuthority(role.getCode()));
                } else {
                    collection.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
                }
            });
        }

        if (!CollectionUtils.isEmpty(permissions)) {
            permissions.parallelStream().forEach(per -> {
                collection.add(new SimpleGrantedAuthority(per));
            });
        }
        RespBean.<SysUser>builder().build();

        return collection;
    }


    @JsonIgnore
    public Collection<? extends GrantedAuthority> putAll( Collection<GrantedAuthority> collections) {
        Collection<GrantedAuthority> collection = new HashSet<>();

        collection.addAll(collections) ;

        return collection;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
