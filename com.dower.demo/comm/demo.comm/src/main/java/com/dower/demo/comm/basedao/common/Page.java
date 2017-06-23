package com.dower.demo.comm.basedao.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装. 本类只封装输入输出参数, 具体的分页逻辑全部封装在Paginator类.
 * 
 * @param <T>
 *            Page中记录的类型.
 * 
 * @author liuwei
 */
public class Page<T> implements Iterable<T> {
	// -- 公共变量 --//
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	// -- 分页查询参数 --//
	protected int page = 1;
	protected int pageSize = 0;
	protected boolean autoCount = true;
	protected String orderBy = null;
	protected String order = null;
	protected String jsonPager = null;
	private String error = null;

	// -- 返回结果 --//
	protected List<T> rows = Lists.newArrayList();
	protected long totalItems = -1;
	protected long totalPages = -1;
	protected int total = 0;

	private String counnut = "0";
	private Map<String, Integer> multiplePage = new HashMap<String, Integer>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<String, Integer> getMultiplePage() {
		return multiplePage;
	}

	public void setMultiplePage(Map<String, Integer> multiplePage) {
		this.multiplePage = multiplePage;
	}

	// -- 构造函数 --//
	public Page() {
	}

	public Page(int pageSize) {
		setPageSize(pageSize);
	}

	public Page(int page, int pageSize) {
		setPage(page);
		setPageSize(pageSize);
	}

	// -- 分页参数访问函数 --//
	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPage(final int page) {
		this.page = page;

		if (page < 1) {
			this.page = 1;
		}
	}

	/**
	 * 获得每页的记录数量, 默认为-1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获得排序字段,无默认值. 多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 获得排序方向, 无默认值.
	 */
	public String getOrder() {
		return order;
	}

	public String getCounnut() {
		return counnut;
	}

	public void setCounnut(String counnut) {
		this.counnut = counnut;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order
	 *            可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		if (order != null && order.length() > 0) {
			String lowcaseOrder = StringUtils.lowerCase(order);

			// 检查order字符串的合法值
			String[] orders = StringUtils.split(lowcaseOrder, ',');
			for (String orderStr : orders) {
				if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
					throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
				}
			}

			this.order = lowcaseOrder;
		}
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始. 用于Mysql,Hibernate.
	 */
	public int getOffset() {
		return ((page - 1) * pageSize);
	}

	public void setJsonPager(String jsonPager) {
		this.jsonPager = jsonPager;
	}

	public String getJsonPager() {
		return jsonPager;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始. 用于Oracle.
	 */
	public int getStartRow() {
		return getOffset() + 1;
	}

	/**
	 * 根据pageNo和pageSize计算当前页最后一条记录在总结果集中的位置, 序号从1开始. 用于Oracle.
	 */
	public int getEndRow() {
		return pageSize * page;
	}

	// -- 访问查询结果函数 --//

	/**
	 * 获得页内的记录列表.
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setRows(final List<T> rows) {
		this.rows = rows;
	}

	/**
	 * 实现Iterable接口,可以for(Object item : page)遍历使用
	 */
	@SuppressWarnings("unchecked")
	public Iterator<T> iterator() {
		return rows == null ? IteratorUtils.EMPTY_ITERATOR : rows.iterator();
	}

	/**
	 * 获得总记录数, 默认值为-1.
	 */
	public long getTotalItems() {
		return totalItems;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalItems(final long totalItems) {
		this.totalItems = totalItems;
		this.total = Integer.parseInt(Long.toString(this.totalItems));
		if (totalItems < 0) {
			totalPages = -1;
		}

		if (totalItems <= 0) {
			return;
		}
		long count = totalItems / getPageSize();
		if (totalItems % getPageSize() > 0) {
			count++;
		}
		totalPages = count;
	}

	/**
	 * 是否最后一页.
	 */
	public boolean isLastPage() {
		return page == getTotalPages();
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNextPage() {
		return (page + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNextPage()) {
			return page + 1;
		} else {
			return page;
		}
	}

	/**
	 * 是否第一页.
	 */
	public boolean isFirstPage() {
		return page == 1;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPrePage() {
		return (page - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPrePage()) {
			return page - 1;
		} else {
			return page;
		}
	}

	/**
	 * 根据pageSize与totalItems计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalItems < 0) {
			return -1;
		}

		long count = totalItems / pageSize;
		if (totalItems % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 计算以当前页为中心的页面列表,如"首页,23,24,25,26,27,末页"
	 * 
	 * @param count
	 *            需要计算的列表大小
	 * @return pageNo列表
	 */
	public List<Long> getSlider(int count) {
		int halfSize = count / 2;
		long totalPage = getTotalPages();

		long startPageNumber = Math.max(page - halfSize, 1);
		long endPageNumber = Math.min(startPageNumber + count - 1, totalPage);

		if (endPageNumber - startPageNumber < count) {
			startPageNumber = Math.max(endPageNumber - count, 1);
		}

		List<Long> result = Lists.newArrayList();
		for (long i = startPageNumber; i <= endPageNumber; i++) {
			result.add(new Long(i));
		}
		return result;
	}

}
