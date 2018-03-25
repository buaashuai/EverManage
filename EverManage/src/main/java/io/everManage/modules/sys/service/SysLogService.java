package io.everManage.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import io.everManage.common.utils.PageUtils;
import io.everManage.modules.sys.entity.SysLogEntity;

import java.util.Map;

/**
 * 系统日志
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2017-03-08 10:40:56
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
