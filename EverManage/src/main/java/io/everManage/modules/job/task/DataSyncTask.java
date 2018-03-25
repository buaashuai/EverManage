package io.everManage.modules.job.task;

import io.everManage.modules.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 数据同步定时任务
 *
 * @author WangShuai
 * @date 2018/3/14 21:18
 */
@Component("dataSyncTask")
public class DataSyncTask {
    private final String WEBSITE = "https://www.getabstract.com/";
    private Logger logger = LoggerFactory.getLogger(getClass());
    private HashMap<String, String> urlMap = new HashMap<String, String>() {
        {
            put("token", "api/oauth/token");
            put("featured", "api/library/v1/abstract/featured?language=zh");
        }
    };

    @Autowired
    private SysUserService sysUserService;

    //定时任务只能接受一个参数；如果有多个参数，使用json数据即可
    public void dataSync(String params) {
        logger.info("我是带参数的 dataSyncTest 方法，正在被执行，参数为：" + params);

    }
}
