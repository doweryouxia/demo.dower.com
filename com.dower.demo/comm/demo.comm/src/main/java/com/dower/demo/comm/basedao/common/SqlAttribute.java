package com.dower.demo.comm.basedao.common;

public class SqlAttribute {
	
	public StringBuffer sb;
	
	public SqlAttribute(){
		sb=new StringBuffer();
	}
	
	/** 等于  */
	public static String EQUAL="=";
	
	/** 不等于  */
	public static String NOTEQUAL="!=";
	
	/** in */
	public static String IN="IN";
	
	/** 大于  */
	public static String GT=">";
	
	/** 大于等于  */
	public static String GE=">=";
	
	/** 小于  */
	public static String LT="&lt;";
	
	/** 小于等于  */
	public static String LE="&lt;=";
	
	/** and */
	public static String AND="AND";
	
	/** or */
	public static String OR="OR";
	
	/** like */
	public static String LIKE="LIKE";
	
	
	
	/** 插入时间  */
	public static String SYSDATE="'SYSDATE'";
	
	/** 降序  */
	public static String DESC="DESC";
	
	/** 升序  */
	public static String ASC="ASC";
	
	
	
	/** 后缀  */
	public static String SUFFIX=")'";
	
	/** sum前缀  */
	public static String SUMPRE="'SUM(";
	
	/** sum后缀  */
	public static String SUMSUF=SUFFIX;
	
	/** max前缀  */
	public static String MAXPRE="'MAX(";
	
	/** max后缀  */
	public static String MAXSUF=SUFFIX;
	
	/** min前缀  */
	public static String MINPRE="'MIN(";
	
	/** min后缀  */
	public static String MINSUF=SUFFIX;
	
	/** count前缀  */
	public static String COUNTPRE="'COUNT(";
	
	/** count后缀  */
	public static String COUNTSUF=SUFFIX;
	
	/** code前缀  */
	public static String CODEPRE="'CODE(";
	
	/** code后缀  */
	public static String CODESUF=SUFFIX;
	
	/** decode前缀  */
	public static String dDECODEPRE="'DECODE(";
	
	/** decode后缀  */
	public static String DECODESUF=SUFFIX;
	
	/** avg前缀  */
	public static String AVGPRE="'AVG(";
	
	/** avg后缀  */
	public static String AVGSUF=SUFFIX;
	
	/** to_date前缀 */
	public static String TO_DATEPRE="'TO_DATE(";
	
	/** to_date后缀  */
	public static String TO_DATESUF=SUFFIX;
	
	/** to_char前缀  */
	public static String TO_CHARPRE="'TO_CHAR(";
	
	/** to_char后缀  */
	public static String TO_CHARSUF=SUFFIX;
	
	
	public SqlAttribute setKey(String key){
		sb.append(key.toUpperCase());
		sb.append(" ");
		return this;
	}
	
	public SqlAttribute equal(String value){
		this.setting(EQUAL, value);
		return this;
	}
	
	public SqlAttribute notEqual(String value){
		this.setting(NOTEQUAL, value);
		return this;
	}
	
	public SqlAttribute in(String value){
		sb.append(IN);
		sb.append(" ");
		sb.append("(");
		sb.append(CommonBean.reverseString(value));
		sb.append(")");
		return this;
	}
	
	public SqlAttribute gt(String value){
		this.setting(GT, value);
		return this;
	}
	
	public SqlAttribute ge(String value){
		this.setting(GE, value);
		return this;
	}
	
	public SqlAttribute lt(String value){
		this.setting(LT, value);
		return this;
	}
	
	public SqlAttribute le(String value){
		this.setting(LE, value);
		return this;
	}
	
	public SqlAttribute like(String value){
		this.setting(LIKE, value);
		return this;
	}
	
	public SqlAttribute setValue(String value){
		sb.append(CommonBean.reverseString(value));
		sb.append(" ");
		return this;
	}
	
	public SqlAttribute and(String value){
		this.setting(AND, "'"+value.toUpperCase()+"'");
		return this;
	}
	
	public SqlAttribute or(String value){
		this.setting(OR, "'"+value.toUpperCase()+"'");
		return this;
	}
	
	private void setting(String key,String value){
		sb.append(key);
		sb.append(" ");
		sb.append(CommonBean.reverseString(value));
		sb.append(" ");
	}
	
	public String toString(){
		return sb.toString();
	}
}
