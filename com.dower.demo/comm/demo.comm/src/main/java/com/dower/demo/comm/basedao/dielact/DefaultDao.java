package com.dower.demo.comm.basedao.dielact;

import java.util.List;
import java.util.Map;

import com.dower.demo.comm.basedao.common.Page;
import org.springframework.stereotype.Repository;


/**
 * 默认DAO层 统一处理默认业务
 * @author Wticher
 *
 */
@Repository
public class DefaultDao extends MyBatisDao {

	/**
	 * 保存
	 * @param entity   继承BaseEntity实体类
	 * @param statement mybatis配置里的查询id
	 * @return 继承BaseEntity实体类,如果在mybatis里设置了返回id则自动带出
	 */
	public <T> T save(T entity, String statement) {
		getSqlSession().insert(statement, entity);
		return entity;
	}

	/**
	 * 更新
	 * @param entity   继承BaseEntity实体类
	 * @param statement mybatis配置里的查询id
	 * @return 继承BaseEntity实体类,如果在mybatis里设置了返回id则自动带出
	 */
	public <T> T update(T entity, String statement) {
		getSqlSession().update(statement, entity);
		return entity;
	}

	/**
	 * 删除
	 * @param params map集合删除参数攻mybatis使用
	 * @param statement mybatis配置里的查询id
	 */
	public void delete(Map<String, Object> params, String statement) {
		getSqlSession().update(statement, params);
	}

	/**
	 * 查询单个对象
	 * @param params map集合删除参数攻mybatis使用
	 * @param statement mybatis配置里的查询id
	 * @return
	 */
	public <T> T get(Map<String, Object> params, String statement) {
		return getSqlSession().selectOne(statement, params);
	}

	/**
	 * 查询多个对象 
	 * @param params map集合删除参数攻mybatis使用
	 * @param statement mybatis配置里的查询id
	 * @return
	 */
	public <T> List<T> find(Object object, String statement) {
		return getSqlSession().selectList(statement, object);
	}

	/**
	 * 分页查询
	 * @param page 分页类
	 * @param params map集合删除参数攻mybatis使用
	 * @param statement mybatis配置里的查询id
	 * @return
	 */
	public <T> Page<T> search(Page<T> page, Map<String, Object> params, String statement) {
		return selectPage(page, statement, params);
	}
}
