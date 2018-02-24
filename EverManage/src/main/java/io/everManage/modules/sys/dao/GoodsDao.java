package io.everManage.modules.sys.dao;

import io.everManage.modules.sys.entity.GoodsEntity;
import io.everManage.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品管理
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2018-02-24 22:28:58
 */
@Mapper
public interface GoodsDao extends BaseDao<GoodsEntity> {
	
}
