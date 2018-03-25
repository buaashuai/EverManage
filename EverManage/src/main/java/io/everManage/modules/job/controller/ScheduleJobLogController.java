package io.everManage.modules.job.controller;

import io.everManage.common.utils.PageUtils;
import io.everManage.common.utils.R;
import io.everManage.modules.job.entity.ScheduleJobLogEntity;
import io.everManage.modules.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 定时任务日志
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2016年12月1日 下午10:39:52
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
    @GetMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public R list(@RequestParam Map<String, Object> params){
        PageUtils page = scheduleJobLogService.queryPage(params);

        return R.ok().put("page", page);
	}
	
	/**
	 * 定时任务日志信息
	 */
    @GetMapping("/info/{logId}")
	public R info(@PathVariable("logId") Long logId){
        ScheduleJobLogEntity log = scheduleJobLogService.selectById(logId);
		
		return R.ok().put("log", log);
	}
}
