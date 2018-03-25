package io.everManage.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.everManage.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2017-03-23 15:22:07
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);
	
}
