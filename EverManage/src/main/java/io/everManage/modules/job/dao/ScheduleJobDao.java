package io.everManage.modules.job.dao;

import io.everManage.modules.job.entity.ScheduleJobEntity;
import io.everManage.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2016年12月1日 下午10:29:57
 */
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
