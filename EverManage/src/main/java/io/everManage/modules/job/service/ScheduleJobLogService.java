package io.everManage.modules.job.service;

import com.baomidou.mybatisplus.service.IService;
import io.everManage.common.utils.PageUtils;
import io.everManage.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2016年12月1日 下午10:34:48
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
	
}
