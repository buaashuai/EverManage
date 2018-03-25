package io.everManage.datasources;

import io.everManage.datasources.annotation.DataSource;
import io.everManage.modules.app.entity.UserEntity;
import io.everManage.modules.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2017/9/16 23:10
 */
@Service
public class DataSourceTestService {
    @Autowired
    private UserService userService;

    public UserEntity queryObject(Long userId){
        return userService.selectById(userId);
    }

    @DataSource(name = DataSourceNames.SECOND)
    public UserEntity queryObject2(Long userId){
        return userService.selectById(userId);
    }
}
