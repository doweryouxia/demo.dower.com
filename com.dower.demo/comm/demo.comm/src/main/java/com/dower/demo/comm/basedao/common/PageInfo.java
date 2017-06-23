package com.dower.demo.comm.basedao.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 文件说明: 指定总数和当前页便可以了
 * @author：陈思凡
 * @QQ：995998760
 * @date：2015年7月30日 下午7:58:52
 */
@Repository
public class PageInfo<E> implements Serializable {

	private static final long serialVersionUID = 3L;

	public static int DEFAULT_PAGE_SIZE = 10;

	//页面大小
	private Integer pageSize = DEFAULT_PAGE_SIZE;

	//当前页
	private Integer currentPage = 1;
	
	//开始行数
	private Integer startCount;
	
	//结束行数
	private Integer endCount;

	//总行数,总数
	private Integer totalCount;

	//总页数
	private Integer totalPage;
	
	//预留
	private List<E> data =new ArrayList<E>();
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		if(totalCount == null) return null;
		if (totalCount % pageSize == 0) {
			this.totalPage = totalCount / pageSize;
		} else {
			this.totalPage = totalCount / pageSize + 1;
		}
		return this.totalPage;
	}
	
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}
	
	public void setData(E data) {
		this.data.add(data);
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		if(currentPage<=1){
			currentPage=1;	
		}
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getStartCount() {
		if(this.currentPage.intValue()==1){
			this.startCount=1;
		}else{
			this.startCount = (this.getCurrentPage()-1)*this.getPageSize()+1;
		}
		return startCount;
	}

	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	public Integer getEndCount() {
		this.endCount = this.currentPage*this.pageSize;
		if(this.totalCount!=null&&this.endCount>this.totalCount){
			this.endCount=this.totalCount;
		}
		return endCount;
	}

	public void setEndCount(Integer endCount) {
		this.endCount = endCount;
	}

}
