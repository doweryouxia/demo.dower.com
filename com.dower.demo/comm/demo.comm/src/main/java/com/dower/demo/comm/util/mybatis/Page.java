package com.dower.demo.comm.util.mybatis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	// easyUI
	private int pageNo = 1;
	private int pageSize = -1;
	private List<T> rows = new ArrayList<T>();
	
	// 分页查询开始记录位置
	private int begin;
	// 分页查看下结束位置
	private int end;
	// 每页显示记录数
	private int length;
	// 查询结果总记录数
	private int count;
	// 当前页码
	private int current;
	// 总共页数
	private int total;

	public Page() {
	}

	/**
	 * 构造函数
	 * 
	 * @param begin
	 * @param length
	 */
	public Page(int begin, int length) {
		this.begin = begin;
		this.length = length;
		this.end = this.begin + this.length;
		this.current = (int) Math.floor((this.begin * 1.0d) / this.length) + 1;
	}

	/**
	 * @param begin
	 * @param length
	 * @param count
	 */
	public Page(int begin, int length, int count) {
		this(begin, length);
		this.count = count;
	}

	/**
	 * @return the begin
	 */
	public int getBegin() {
		return begin;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * @param begin
	 *            the begin to set
	 */
	public void setBegin(int begin) {
		this.begin = begin;
		if (this.length != 0) {
			this.current = (int) Math.floor((this.begin * 1.0d) / this.length) + 1;
		}
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(int length) {
		this.length = length;
		if (this.begin != 0) {
			this.current = (int) Math.floor((this.begin * 1.0d) / this.length) + 1;
		}
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
		this.total = (int) Math.floor((this.count * 1.0d) / this.length);
		if (this.count % this.length != 0) {
			this.total++;
		}
	}

	/**
	 * @return the current
	 */
	public int getCurrent() {
		return current;
	}

	/**
	 * @param current
	 *            the current to set
	 */
	public void setCurrent(int current) {
		this.current = current;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		if (total == 0) {
			return 1;
		}
		return total;
	}

	/**
	 * @param total
	 *  the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	// easyUI
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		if(pageSize > 0){
			this.pageSize = pageSize;
		} else {
			pageSize = 1;
		}
		this.setLength(pageSize);
		this.begin = ((pageNo-1) * pageSize);
		this.end = this.begin + this.pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		if(pageNo > 0){
			this.pageNo = pageNo;
		} else {
			this.pageNo = 1;
		}
		this.setCurrent(pageNo);
		if(pageSize>0){
			this.begin = ((pageNo-1) * pageSize);
			this.end = this.begin + this.pageSize;
		}
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}