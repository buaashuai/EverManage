package io.everManage.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import io.everManage.common.exception.RRException;
import io.everManage.common.utils.PageUtils;
import io.everManage.common.utils.Query;
import io.everManage.modules.sys.dao.SysConfigDao;
import io.everManage.modules.sys.entity.SysConfigEntity;
import io.everManage.modules.sys.redis.SysConfigRedis;
import io.everManage.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {
	@Autowired
	private SysConfigRedis sysConfigRedis;

	@Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");

        Page<SysConfigEntity> page = this.selectPage(
                new Query<SysConfigEntity>(params).getPage(),
                new EntityWrapper<SysConfigEntity>()
                        .like(StringUtils.isNotBlank(key), "key", key)
                        .eq("status", 1)
        );

        return new PageUtils(page);
    }

    @Override
	public void save(SysConfigEntity config) {
        this.insert(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public void update(SysConfigEntity config) {
        this.updateById(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
		sysConfigRedis.delete(key);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
            SysConfigEntity config = this.selectById(id);
			sysConfigRedis.delete(config.getKey());
		}

        this.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = sysConfigRedis.get(key);
		if(config == null){
            config = baseMapper.queryByKey(key);
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getValue();
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}
}
