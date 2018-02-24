package io.everManage.modules.app.dao;

import io.everManage.modules.app.entity.UserEntity;
import io.everManage.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2017-03-23 15:22:06
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    UserEntity queryByMobile(String mobile);
}
