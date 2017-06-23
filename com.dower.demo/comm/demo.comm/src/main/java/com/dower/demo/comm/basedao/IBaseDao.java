package com.dower.demo.comm.basedao;

import java.util.List;
import java.util.Map;

import com.dower.demo.comm.basedao.common.CommonBean;
import com.dower.demo.comm.basedao.common.PageInfo;

public interface IBaseDao {
	
	/**
	 * 方法说明: 查询
	 * @param cb
	 * @return
	 * @author：陈思凡
	 * @date：2015年7月30日 下午1:28:14
	 * @QQ：995998760
	 */
	public PageInfo selectList(CommonBean cb, PageInfo pi);
	
	/**
	 * 方法说明: 插入,返回id,失败状态为null
	 * @param cb
	 * @return
	 * @throws Exception 
	 * @author：陈思凡
	 * @date：2015年7月31日 下午1:31:14
	 * @QQ：995998760
	 */
	public List insertList(CommonBean cb);
	
	/**
	 * 方法说明: 删除，返回状态
	 * @param cb
	 * @author：陈思凡
	 * @date：2015年8月1日 下午1:35:26
	 * @QQ：995998760
	 */
	public Boolean deleteList(CommonBean cb);
	
	/**
	 * 方法说明: 更新，返回状态
	 * @param cb
	 * @return
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月1日 下午2:58:53
	 */
	public Boolean updateList(CommonBean cb);
	
	/**
	 * 方法说明: 得到序列号值
	 * @param seq 序列号名字
	 * @param number 数量
	 * @return
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月4日 上午11:54:18
	 */
	public List<String> getSeq(String seq ,String number);
	
	/**
	 * 方法说明: 得到总数
	 * @param mapIn
	 * @return
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月4日 下午9:54:59
	 */
	public Integer getTotalCount(Map mapIn);

}
