package io.everManage.modules.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.everManage.modules.sys.entity.GoodsEntity;
import io.everManage.modules.sys.service.GoodsService;
import io.everManage.common.utils.PageUtils;
import io.everManage.common.utils.Query;
import io.everManage.common.utils.R;




/**
 * 商品管理
 * 
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2018-02-24 22:28:58
 */
@RestController
@RequestMapping("/sys/goods")
public class GoodsController {
	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:goods:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<GoodsEntity> goodsList = goodsService.queryList(query);
		int total = goodsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(goodsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{goodsId}")
	@RequiresPermissions("sys:goods:info")
	public R info(@PathVariable("goodsId") Long goodsId){
		GoodsEntity goods = goodsService.queryObject(goodsId);
		
		return R.ok().put("goods", goods);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:goods:save")
	public R save(@RequestBody GoodsEntity goods){
		goodsService.save(goods);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:goods:update")
	public R update(@RequestBody GoodsEntity goods){
		goodsService.update(goods);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:goods:delete")
	public R delete(@RequestBody Long[] goodsIds){
		goodsService.deleteBatch(goodsIds);
		
		return R.ok();
	}
	
}
