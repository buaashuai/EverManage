package io.everManage.modules.oss.dao;

import io.everManage.modules.oss.entity.SysOssEntity;
import io.everManage.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2017-03-25 12:13:26
 */
@Mapper
public interface SysOssDao extends BaseDao<SysOssEntity> {
	
}
