package com.dower.demo.comm.basedao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dower.demo.comm.basedao.common.CommonBean;
import com.dower.demo.comm.basedao.common.PageInfo;
import com.dower.demo.comm.basedao.common.superDao;
import org.springframework.stereotype.Repository;

import com.dower.demo.comm.basedao.IBaseDao;
import com.dower.demo.comm.basedao.exception.MybatisException;

@Repository
public class BaseDao  extends superDao implements IBaseDao {


	@Override
	public PageInfo selectList(CommonBean cb, PageInfo pi) {
		if(pi==null){
			pi=new PageInfo();
		}
		Map map=null;
		try {
			map = CommonBeanTransformMap(cb);//bean转map
			takeOutEmpty(map);//去掉空值
		} catch (Exception e) {
			new MybatisException().errorException("CommonBean转Map失败！");
			e.printStackTrace();
			return null;
		}
		pi.setTotalCount(this.getTotalCount(map));
		pi.setData(getSqlSession().selectList("selectList", map));
		return pi;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List insertList(CommonBean cb) {
		
		List retunList=new ArrayList();
		if(cb.getSeqMap().size()!=0){
			if (!cb.checkSeqMap() || cb.getTableList().size() <= 0 || cb.getValuesList().size()<=0) {
				new MybatisException()
				.errorException("请检查valuesList或者tableList是不是没值或者序列号是不是没填！");
				return null;
			}
			List seqNamelist=(List) cb.getSeqMap().get("list");//得到序列号名字
			for (int i = 0; i < seqNamelist.size(); i++) {
				List<String> seqValueList=this.getSeq(seqNamelist.get(i).toString().toUpperCase(), cb.getSeqMap().get("number").toString());
				if(seqValueList==null)
					return null;
				super.addPK(cb.getValuesList(), seqValueList, seqNamelist.get(i).toString());
				retunList.add(seqValueList);
			}
		}
		Map map=null;
		try {
			map = CommonBeanTransformMap(cb);//bean转map
			takeOutEmpty(map);//去掉空值
		} catch (Exception e) {
			new MybatisException().errorException("CommonBean转Map失败！");
			e.printStackTrace();
			return null;
		}
		if(getSqlSession().insert("insertList", map)>=1){
			return retunList;
		}
		new MybatisException().errorException("插入失败！请检查sql语句是否正确！");
		return null;
	}

	@Override
	public Boolean deleteList(CommonBean cb) {
		if(cb.getTableList().size()<=0 || cb.getWhereList().size()<=0){
			new MybatisException().errorException("请检查tableList或者whereList是不是空的！");
			return false;
		}
		Map map=null;
		try {
			map = CommonBeanTransformMap(cb);//bean转map
			takeOutEmpty(map);//去掉空值
		} catch (Exception e) {
			new MybatisException().errorException("CommonBean转Map失败！");
			e.printStackTrace();
			return false;
		}
		return getSqlSession().delete("deleteList", map)>=1;
	}

	@Override
	public Boolean updateList(CommonBean cb) {
		if(cb.getTableList().size()<=0 || cb.getSetList().size()<=0 || cb.getWhereList().size()<=0){
			new MybatisException().errorException("请检查tableList或者setList或者whereList是不是空的！");
			return false;
		}
		Map map=null;
		try {
			map = CommonBeanTransformMap(cb);//bean转map
			takeOutEmpty(map);//去掉空值
		} catch (Exception e) {
			new MybatisException().errorException("CommonBean转Map失败！");
			e.printStackTrace();
			return false;
		}
		return getSqlSession().update("updateList", map)>=1;
	}

	@Override
	public List<String> getSeq(String seq ,String number) {
		Map<String,Object> seqMap=new HashMap<String,Object>();
		List<String> columnList=new ArrayList<String>();
		columnList.add(seq+".NEXTVAL AS ID");
		seqMap.put("columnList", columnList);
		seqMap.put("number", number);
		List<Object> seqList = getSqlSession().selectList("selectList", seqMap);//得到id
		if(seqList==null || seqList.size()<=0 || ((Map)seqList.get(0)).get("ID")==null){
			new MybatisException().errorException("序列号为空！请检查序列是否正确！");
			return null;
		}
		List<String> returnList=new ArrayList<String>();
		for (int i = 0; i < seqList.size(); i++) {
			returnList.add(((Map)seqList.get(i)).get("ID").toString());
		}
		return returnList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Integer getTotalCount(Map mapIn) {
		Map map=new HashMap();
		map.put("tableList", mapIn.get("tableList"));
		map.put("whereList", mapIn.get("whereList"));
		List<String> columnList=new ArrayList<String>();
		columnList.add("COUNT(*) AS NUM");
		map.put("columnList", columnList);
		List<Object> selectList = getSqlSession().selectList("selectList", map);
		return Integer.parseInt(((Map)selectList.get(0)).get("NUM").toString());
	}
}
