package io.everManage.modules.job.service;

import com.baomidou.mybatisplus.service.IService;
import io.everManage.common.utils.PageUtils;
import io.everManage.modules.job.entity.ScheduleJobEntity;

import java.util.Map;

/**
 * 定时任务
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2016年11月28日 上午9:55:32
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

    PageUtils queryPage(Map<String, Object> params);

	/**
	 * 保存定时任务
	 */
	void save(ScheduleJobEntity scheduleJob);
	
	/**
	 * 更新定时任务
	 */
	void update(ScheduleJobEntity scheduleJob);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Long[] jobIds);
	
	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Long[] jobIds, int status);
	
	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pause(Long[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(Long[] jobIds);
}
