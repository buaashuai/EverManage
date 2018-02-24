package io.everManage.modules.sys.service;

import io.everManage.modules.sys.entity.SysUserEntity;
import io.everManage.modules.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * shiro相关接口
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2017-06-06 8:49
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserEntity queryUser(Long userId);
}
