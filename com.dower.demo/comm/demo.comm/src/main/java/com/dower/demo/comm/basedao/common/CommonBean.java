package com.dower.demo.comm.basedao.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * 文件说明: 基类bean。
 * 特说明一下，如引入此bean（如@Resource）需要被引入java类也是多例，要不然@Scope("prototype")没用
 * @author：陈思凡
 * @QQ：995998760
 * @date：修改时间：2015年7月31日 下午1:13:57
 */
@Repository
@Scope("prototype")
public class CommonBean {
	
	public static String PREFIX="TL_";
	
	//列名
	private List<String> columnList;
	
	//表名
	private List<String> tableList;
	
	//条件集合
	private List<String> whereList;
	
	//分组集合
	private List<String> groupList;
	
	//选择
	private List<String> havingList;
	
	//排序
	private List<String> orderList;
	
	//插入数据值
	private List<List<String>> valuesList;
	
	//更新数据值
	private List<String> setList;
	
	//序列号
	private Map seqMap;
	
	//是否分页，默认分页
	private Boolean isPage=false;
	
	//开始行
	private Integer startCount=1;
	
	//结束行
	private Integer endCount=10;
	
	public CommonBean(){
		columnList=new ArrayList<String>();
		tableList=new ArrayList<String>(5);
		whereList=new ArrayList<String>();
		groupList=new ArrayList<String>(5);
		havingList=new ArrayList<String>(5);
		orderList=new ArrayList<String>(5);
		valuesList=new ArrayList<List<String>>();
		seqMap=new HashMap();
		setList=new ArrayList<String>();
	}
	
	public CommonBean(CommonBean cb){
		initializationCommonBean(new CommonBean());
	}
	
	/**
	 * 方法说明: 初始化
	 * 
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年7月31日 下午2:29:09
	 */
	public void initialization(){
		initializationCommonBean(new CommonBean());
	}
	
	public Boolean checkSeqMap(){
		if(this.valuesList.size()<1 || this.seqMap.size()<1){
			return false;
		}
//		if(Integer.parseInt(this.seqMap.get("number").toString().trim())!=this.valuesList.size()){
//		}
		this.seqMap.put("number", String.valueOf(this.valuesList.size()));
		return true;
	}
	
	/**
	 * 方法说明: 初始化
	 * @param cb
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年7月31日 下午5:58:57
	 */
	private void initializationCommonBean(CommonBean cb){
		columnList=cb.getColumnList();
		tableList=cb.getTableList();
		whereList=cb.getWhereList();
		groupList=cb.getGroupList();
		havingList=cb.getHavingList();
		orderList=cb.getOrderList();
		isPage=cb.getIsPage();
		startCount=cb.getStartCount();
		endCount=cb.getEndCount();
	}
	
	/**
	 * 方法说明: object数组转换成String数组
	 * @param obj
	 * @return
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年7月31日 下午8:32:23
	 */
	private String[] objectArrayTransforStringArray(Object[] obj){
		String[] str=new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			str[i]=obj[i].toString();
		}
		return str;
	}
	
	/**
	 * 方法说明: 装配数据
	 * @param list
	 * @param obj
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年7月31日 下午5:58:05
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void addAll(List<String> list,Object obj){
		String[] str=null;
		if (obj instanceof String) {
			str=((String) obj).split(",");
		}else{
			str=(String[]) obj;
		}
		Collections.addAll(list, str);
	}
	
	/**
	 * 方法说明: 反转，“'***'”变换成“***”,“***”变换成“'***'”
	 * @param obj
	 * @return
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月4日 下午4:01:51
	 */
	public static String reverseString(Object obj){
		String s=obj.toString();
		if(s.indexOf("'")==0 && s.lastIndexOf("'")==s.length()-1){
			return s.substring(1, s.length()-1);
		}else{
			String[] split = s.split(",");
			if(split.length>1){
				StringBuffer sb=new StringBuffer();
				for (int i = 0; i < split.length; i++) {
					sb.append("'"+split[i]+"',");
				}
				return sb.substring(0, sb.length()-1);
			}
			return "'"+s+"'";
		}
	}

	
	
	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}
	
	public void setColumnList(String[] columnList) {
		addAll(this.columnList, columnList);
	}
	
	public void setColumnList(String columnList) {
		addAll(this.columnList, columnList.toUpperCase());
	}
	
	public void setColumnList(Object obj) {
		if(obj instanceof String){
			this.setColumnList((String)obj);
			return;
		}
		if(obj instanceof String[]){
			this.setColumnList((String[])obj);
			return;
		}
		StringBuffer sb=new StringBuffer();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			sb.append(field.getName());
			sb.append(",");
		}
		this.setColumnList(sb.substring(0, sb.length()-1));
		this.setTableList(obj);
	}
	
	/**
	 * 方法说明: 重置columnList
	 * @param columnList
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月1日 下午12:17:25
	 */
	public void setNewColumnList(String columnList){
		this.getColumnList().clear();
		this.setColumnList(columnList);
	}
	
	/**
	 * 方法说明: 功能同setNewColumnList(String columnList)一样
	 * @param columnList
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月1日 下午12:48:59
	 */
	public void setNewColumnList(String[] columnList){
		this.getColumnList().clear();
		this.setColumnList(columnList);
	}
	
	/**
	 * 方法说明: 功能同setNewColumnList(String columnList)一样
	 * 特别说明：此方法不会因为bean的某个属性值为空就不加载
	 * @param obj
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月1日 下午12:26:19
	 */
	public void setNewColumnList(Object obj){
		this.getColumnList().clear();
		this.setColumnList(obj);
	}

	
	
	
	
	
	
	
	public List<String> getTableList() {
		return tableList;
	}

	public void setTableList(List<String> tableList) {
		this.tableList = tableList;
	}

	public void setTableList(String[] tableList) {
		for (int i = 0; i < tableList.length; i++) {
			tableList[i]=PREFIX+tableList[i];
		}
		addAll(this.tableList, tableList);
	}
	
	public void setTableList(String tableList) {
		this.setTableList(tableList.split(","));
	}
	
	/**
	 * 方法说明: 此方法只能是单表，调用此方法会把以前的表替换掉
	 * @param obj
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月1日 上午11:06:13
	 */
	public void setTableList(Object obj){
		if(obj instanceof String[]){
			this.setTableList((String[])obj);
			return;
		}
		if(obj instanceof String){
			this.setTableList((String)obj);
			return;
		}
		if(obj instanceof List){
			this.setTableList((List)obj);
			return;
		}
		String name = obj.getClass().getName();
		name=name.substring(name.lastIndexOf(".")+1).toUpperCase();
		this.setTableList(name);
	}
	
	/**
	 * 方法说明: 重置表名
	 * @param tableList
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月1日 下午1:02:20
	 */
	public void setNewTableList(String tableList) {
		if(this.getTableList().size()>=1){
			this.getTableList().clear();
		}
		this.setTableList(tableList);
	}
	
	/**
	 * 方法说明: 同setNewTableList(String tableList)一样
	 * @param tableList
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月1日 下午1:02:41
	 */
	public void setNewTableList(String[] tableList) {
		if(this.getTableList().size()>=1){
			this.getTableList().clear();
		}
		this.setTableList(tableList);
	}
	
	public void setNewTableList(Object obj){
		if(this.getTableList().size()>=1){
			this.getTableList().clear();
		}
		this.setTableList(obj);
	}
	
	
	
	
	
	
	
	
	public List<String> getWhereList() {
		return whereList;
	}

	public void setWhereList(List<String> whereList) {
		this.whereList = whereList;
	}
	
	public void setWhereList(String[] whereList) {
		addAll(this.whereList, whereList);
	}
	
	public void setWhereList(String whereList) {
		addAll(this.whereList, whereList);
	}
	
	public void setWhereList(SqlAttribute sqlAttribute) {
		this.whereList.add(sqlAttribute.toString());
	}

	
	
	
	public List<String> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<String> groupList) {
		this.groupList = groupList;
	}
	
	public void setGroupList(String[] groupList) {
		addAll(this.groupList, groupList);
	}
	
	public void setGroupList(String groupList) {
		addAll(this.groupList, groupList);
	}

	
	
	
	
	public List<String> getHavingList() {
		return havingList;
	}

	public void setHavingList(List<String> havingList) {
		this.havingList = havingList;
	}

	public void setHavingList(String[] havingList) {
		addAll(this.havingList, havingList);
	}
	
	public void setHavingList(String havingList) {
		addAll(this.havingList, havingList);
	}
	
	
	
	
	
	
	public List<List<String>> getValuesList() {
		return valuesList;
	}

	public void setValuesList(List<List<String>> valuesList) {
		this.valuesList = valuesList;
	}
	
	/**
	 * 方法说明: 此方法支持批量插入，
	 * @param Values 为插入数据
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年7月31日 下午7:24:14
	 */
	public void setValuesList(String... Values) {//"1,2,3,4,5","2,3,4,5,6"
		for (int i = 0; i < Values.length; i++) {
			String str=Values[i];//"1,2,3,4,5"
			if(str.isEmpty())
				continue;
			List<String> list=new ArrayList<String>(1);
			list.add(str);
			this.valuesList.add(list);//装入valuesList
		}
	}
	
	
	/**
	 * 方法说明: 键值对,只支持单个插入
	 * 具体说明，和setValuesList(Object obj)一样
	 * 序列id，请用"$id"代替
	 * @param valuesMap
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年7月31日 下午3:59:39
	 */
	public void setValuesList(Map valuesMap) {
		Set keySet = valuesMap.keySet();
    	Object[] array = keySet.toArray();
    	this.setNewColumnList(objectArrayTransforStringArray(array));
    	StringBuffer sb=new StringBuffer();
    	for (Object key : array) {
    		sb.append(valuesMap.get(key));
    		sb.append(",");
    	}
    	this.setValuesList(sb.substring(0, sb.length()-1),"");
	}
	
	/**
	 * 方法说明: obj就是bean，注意属性值为空的不加载，只支持单个插入,
	 * 如果需要批量，需多次调用此方法。警告，多次调用此方法，插入字段名，以最后一次调用此方法的bean的属性名为准
	 * 序列id，请用"$id"代替
	 * @param obj
	 * @throws Exception
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年7月31日 下午3:55:36
	 */
	public void setValuesList(Object obj){
		
		if(obj instanceof String){
			this.setValuesList((String)obj,"");
			return;
		}
		if(obj instanceof Map){
			this.setValuesList((Map)obj);
			return;
		}
		if(obj instanceof List){
			this.setValuesList((List)obj);
			return;
		}
		
		StringBuffer sbColumnList=new StringBuffer();
		StringBuffer sbValuesList=new StringBuffer();
		StringBuffer sbSeqList=new StringBuffer();
		
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			String fieldName=declaredFields[i].getName();
			
			String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	        String getter = "get" + firstLetter + fieldName.substring(1); 
			Method method;
			try {
				method = obj.getClass().getMethod(getter);
				Object invoke = method.invoke(obj);
				if(invoke!=null && !"".equals(invoke)){
					sbColumnList.append(fieldName.toUpperCase()+",");
					if(invoke.toString().indexOf("$")==0)
						sbSeqList.append(invoke.toString().substring(1)+",");
					sbValuesList.append(this.reverseString(invoke)+",");//反转
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		this.setNewColumnList(sbColumnList.substring(0, sbColumnList.length()-1));
		this.setValuesList(sbValuesList.substring(0, sbValuesList.length()-1),"");
		this.setNewTableList(obj);//默认类名为表名
		if(sbSeqList.length()>0){
			this.setSeqMap(sbSeqList.substring(0, sbSeqList.length()-1));//默认序列号名
		}
	}
	
	/**
	 * 方法说明: 初始化columnList
	 * 
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月1日 下午1:06:20
	 */
	public void initializationValuesList(){
		this.valuesList= new ArrayList<List<String>>();
	}
	
	
	
	
	
	
	

	public List<String> getSetList() {
		return setList;
	}

	/**
	 * 方法说明:同 setSetList(String[] setList)一样
	 */
	public void setSetList(List<String> setList) {
		this.setList = setList;
	}
	
	/**
	 * 方法说明: set必须
	 * @param setList
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月1日 下午2:54:19
	 */
	public void setSetList(String[] setList) {
		addAll(this.setList, setList);
	}
	
	/**
	 * 方法说明:同 setSetList(String[] setList)一样
	 */
	public void setSetList(String setList) {
		addAll(this.setList, setList);
	}
	
	/**
	 * 方法说明:同 setSetList(String[] setList)一样
	 */
	public void setSetList(Map setListMap) {
//		StringBuffer sb=new StringBuffer();
//		Set keySet = setListMap.keySet();
//		Object[] array = keySet.toArray();
//		for (int i = 0; i < array.length; i++) {
//			sb.append(array[i]);
//			sb.append(" = ");
//			sb.append(setListMap.get(array[i]));
//			sb.append(",");
//		}
//		this.setSetList(sb.substring(0, sb.length()-1));
		String string = setListMap.toString();
		this.setSetList(string.substring(1, string.length()-1));
	}
	
	
	/**
	 * 方法说明: 此方法会把bean中不为空的属性都加载上去
	 * @see setSetList(String setList)
	 * @param obj
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月3日 下午3:05:54
	 */
	public void setSetList(Object obj) {
		if(obj instanceof Map){
			this.setSetList((Map)obj);
			return;
		}
		if(obj instanceof String){
			this.setSetList((String)obj);
			return;
		}
		if(obj instanceof String[]){
			this.setSetList((String[])obj);
			return;
		}
		if(obj instanceof List){
			this.setSetList((List)obj);
			return;
		}
		StringBuffer sb=new StringBuffer();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			String fieldName=declaredFields[i].getName();
			
			String firstLetter = fieldName.substring(0, 1).toUpperCase();    
            String getter = "get" + firstLetter + fieldName.substring(1); 
    		Method method;
			try {
				method = obj.getClass().getMethod(getter);
				Object invoke = method.invoke(obj);
				if(invoke!=null && !"".equals(invoke)){
					sb.append(fieldName.toUpperCase());
					sb.append(SqlAttribute.EQUAL);
					sb.append(this.reverseString(invoke));
					sb.append(",");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		this.setSetList(sb.substring(0, sb.length()-1));
		this.setNewTableList(obj);
	}
	
	
	
	
	
	public Map getSeqMap() {
		return seqMap;
	}
	
	/**
	 * 方法说明: map里面为seq和number
	 * key值
	 * @param seqMap，里面应该有seq键值代表序列号，number为可选，代表数量，
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年7月31日 下午4:59:44
	 */
	public void setSeqMap(Map seqMap) {
		this.seqMap = seqMap;
	}
	
	/**
	 * 方法说明: map里面为seq，代表序列号；number为可选，代表数量
	 * 例子："table_seq",或者"table_seq","5"
	 * @param seq 不能为空
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年7月31日 下午5:12:52
	 */
	public void setSeqMap(String seq) {
		String[] split = seq.split(",");
		List<String> list=new ArrayList<String>();
		for (int i = 0; i < split.length; i++) {
			list.add(split[i]);
		}
		this.seqMap.put("list", list);
	}
	
	/**
	 * 方法说明: @see setSeqMap(String seq)
	 * @param seq
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月4日 下午1:59:57
	 */
	public void setNewSeqMap(String seq){
		this.seqMap=new HashMap();
		this.setSeqMap(seq);
	}
	
	
	
	
	

	public List<String> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<String> orderList) {
		this.orderList = orderList;
	}
	
	public void setOrderList(String[] orderList) {
		addAll(this.orderList, orderList);
	}
	
	public void setOrderList(String orderList) {
		addAll(this.orderList, orderList);
	}

	
	
	
	
	
	public Boolean getIsPage() {
		return isPage;
	}

	public void setIsPage(Boolean isPage) {
		this.isPage = isPage;
	}

	
	
	
	public Integer getStartCount() {
		return startCount;
	}

	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	
	
	
	public Integer getEndCount() {
		return endCount;
	}

	public void setEndCount(Integer endCount) {
		this.endCount = endCount;
	}
	
	
	public void setPageInfo(PageInfo pageInfo){
		this.startCount = pageInfo.getStartCount();
		this.endCount = pageInfo.getEndCount();
	}
	
	/**
	 * 方法说明: 得到一个SqlAttribute
	 * @return
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年8月4日 下午9:20:24
	 */
	public SqlAttribute getSqlAttribute(){
		return new SqlAttribute();
	}
	
	
}
