package io.everManage.modules.job.dao;

import io.everManage.modules.job.entity.ScheduleJobLogEntity;
import io.everManage.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2016年12月1日 下午10:30:02
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {
	
}
