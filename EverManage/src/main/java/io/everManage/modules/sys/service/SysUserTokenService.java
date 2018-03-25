package io.everManage.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.everManage.common.utils.R;
import io.everManage.modules.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2017-03-23 15:22:07
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
