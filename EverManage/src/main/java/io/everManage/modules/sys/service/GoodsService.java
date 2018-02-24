package io.everManage.modules.sys.service;

import io.everManage.modules.sys.entity.GoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品管理
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2018-02-24 22:28:58
 */
public interface GoodsService {
	
	GoodsEntity queryObject(Long goodsId);
	
	List<GoodsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(GoodsEntity goods);
	
	void update(GoodsEntity goods);
	
	void delete(Long goodsId);
	
	void deleteBatch(Long[] goodsIds);
}
