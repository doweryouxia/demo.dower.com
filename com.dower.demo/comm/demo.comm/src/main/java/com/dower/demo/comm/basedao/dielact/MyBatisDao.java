package com.dower.demo.comm.basedao.dielact;

import java.util.List;
import java.util.Map;

import com.dower.demo.comm.basedao.common.Page;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ReflectionUtils;


/**
 * Mybatis分页查询工具类,为分页查询增加传递: startRow,endRow : 用于oracle分页使用,从1开始 offset,limit :
 * 用于mysql 分页使用,从0开始
 * 
 */
@SuppressWarnings("unchecked")
public class MyBatisDao extends SqlSessionDaoSupport {

	public <T> Page<T> selectPage(Page<T> page, String statementName, Object parameter) {
		String countStatementName = statementName + "_count";
		return selectPage(page, statementName, countStatementName, parameter);
	}

	private <T> Page<T> selectPage(Page<T> page, String statementName, String countStatementName, Object parameter) {

		Number totalItems = (Number) getSqlSession().selectOne(countStatementName, parameter);
		if (parameter instanceof Map)
			((Map) parameter).put("_limi_", page.getOffset() + page.getPageSize());
		//if (totalItems != null && totalItems.longValue() > 0) {
		List<T> list = getSqlSession().selectList(statementName, toParameterMap(parameter, page),
				new RowBounds(page.getOffset(), page.getPageSize()));
		page.setRows(list);
		page.setTotalItems(totalItems.longValue());
		//}
		return page;
	}

	protected static Map toParameterMap(Object parameter, Page page) {
		Map map = toParameterMap(parameter);
		map.put("startRow", page.getStartRow());
		map.put("endRow", page.getEndRow());
		map.put("offset", page.getOffset());
		map.put("limit", page.getPageSize());

		map.put("orderBy", page.getOrderBy());
		map.put("order", page.getOrder());
		return map;
	}

	protected static Map toParameterMap(Object parameter) {
		if (parameter instanceof Map) {
			return (Map) parameter;
		} else {
			try {
				return PropertyUtils.describe(parameter);
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
				return null;
			}
		}
	}

}
