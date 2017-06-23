package com.dower.demo.comm.basedao.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文件说明: 所有Dao的祖类，预留，方便以后加入功能
 * @author：陈思凡
 * @QQ：995998760
 * @date：2015年7月30日 下午1:47:05
 */
public abstract class superDao<T> extends SqlSessionDaoSupport {
	
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
    
    /**
     * 从1.2.*开始，需要自己手动加入注解
     */
    @Autowired(required = false)
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
    protected <S> S getMapper(Class<S> clazz) {
        return getSqlSession().getMapper(clazz);
    }
    
    /**
     * 方法说明: 简单验证列名
     * @param obj
     * @return
     * @author：陈思凡
     * @QQ：995998760
     * @date：2015年7月30日 下午2:42:51
     */
    public boolean columnNameIsTrue(Object obj){
    	if(obj==null)
    		return false;
    	if(!(obj instanceof List))
    		return false;
    	return true;
    }
    
    /**
     * 方法说明: CommonBean转换成Map
     * @param cb
     * @return map
     * @throws Exception
     * @author：陈思凡
     * @QQ：995998760
     * @date：2015年7月30日 下午6:21:51
     */
    public Map CommonBeanTransformMap(CommonBean cb) throws Exception{
    	Map map=new HashMap();
    	Field[] declaredFields = cb.getClass().getDeclaredFields();
    	for (int i = 0; i < declaredFields.length; i++) {
    		if(declaredFields[i].getModifiers()==9){
    			continue;
    		}
    		String fieldName=declaredFields[i].getName();
    		String firstLetter = fieldName.substring(0, 1).toUpperCase();    
            String getter = "get" + firstLetter + fieldName.substring(1); 
    		Method method = cb.getClass().getMethod(getter);
			map.put(fieldName, method.invoke(cb));
		}
    	return map;
    }
    
    /**
     * 方法说明: 去掉Map空的值
     * @author：陈思凡
     * @QQ：995998760
     * @date：2015年7月30日 下午6:25:04
     */
    public void  takeOutEmpty(Map map){
    	Set keySet = map.keySet();
    	Object[] array = keySet.toArray();
    	for (Object key : array) {
			Object object = map.get(key);
			if(object==null){
				map.remove(key);
				continue;
			}
			if(object instanceof String){
				if(object.equals("")){
					map.remove(key);
					continue;
				}
			}
			if(object instanceof List){
				if(((List) object).isEmpty()){
					map.remove(key);
					continue;
				}
			}
		}
    }
    
    /**
     * 方法说明: 替换真的id
     * @param valuesList 要被替换部分字符的字符串
     * @param seqList  替换的id
     * @param target  替换的老字符
     * @author：陈思凡
     * @QQ：995998760
     * @date：2015年8月4日 上午11:50:23
     */
    public void addPK(List<List<String>> valuesList,List<String> seqList,String target){
    	for (int i = 0; i < valuesList.size(); i++) {
    		List<String> list = valuesList.get(i);
			String str=list.get(0);
			String id=seqList.get(i);
			list.set(0, str.replace("$"+target, id));
		}
    }

}
