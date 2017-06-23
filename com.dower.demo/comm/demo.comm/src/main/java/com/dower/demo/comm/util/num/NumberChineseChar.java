package com.dower.demo.comm.util.num;

/**
 * 数字转大写
 * @author NiuXueJun
 * 2015-7-31 下午3:44:29
 */
public class NumberChineseChar {
	
	 private static final char[] UNIT={'亿','十','百','千','万','十','百','千'};
	    //private static final char[] UNIT={'载','十','百','千','万','十','百','千','亿','兆','京','垓','秭','壤','沟','正'};
	    private static final char[]  CHAINIESFIGURE1={'零','一','二','三','四','五','六','七','八','九'};
	    private static final char[]  CHAINIESFIGURE2={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};
	   
	    /**
	     * 整数部分的转换
	     * @param intString
	     * @return String
	     * @throws NumberFormatException
	     */
	    public static String toChineseCharI(String intString)throws NumberFormatException{
	       
	        //用来存放转换后的大写数字,因为是StringBuffer类型,所以顺便把没有转换
	        //的数字倒序排列一下,省一个变量.
	        StringBuffer ChineseCharI=new StringBuffer(intString);
	        //倒序的数字,便于同单位合并
	        String rintString=ChineseCharI.reverse().toString();
	        //清空一下
	        ChineseCharI.delete(0,ChineseCharI.length());
	        //单位索引
	        int unitIndex=0;
	        //数字长度
	        int intStringLen=intString.length();
	        //一位由字符转换的数字
	        int intStringnumber=0;
	        //补0
	        boolean addZero=false;
	       
	        for(int i=0;i<intStringLen;i++){
	            //按单位长度循环索引
	            unitIndex=i%UNIT.length;
	           
	            //异常检查
	            if(!Character.isDigit(rintString.charAt(i)))    {
	                throw new NumberFormatException("数字中含有非法字符");           
	            }   
	            intStringnumber=Character.digit(rintString.charAt(i),10);
	                       
	            //如果当前位是0,开启补0继续循环直到不是0的位
	            if(intStringnumber==0){
	                addZero=true;
	            }else{
	               
	                //若当前位不是第一位且补0开启
	                if(addZero&&ChineseCharI.length()!=0){
	                    ChineseCharI.append(CHAINIESFIGURE1[0]);
	                }
	               
	                //插入单位
	                //个位数后面不需 要单位
	                if(i>0){
	                    ChineseCharI.append(UNIT[unitIndex]);
	                }
	               
	                //插入大写数字
	                ChineseCharI.append(CHAINIESFIGURE1[intStringnumber]);   
	                //补0关闭           
	                addZero=false;               
	            }
	        }
	       
	        //异常处理
	        if(ChineseCharI.length()==0){
	            return "零";
	        }
	        return     ChineseCharI.reverse().toString();
	       
	    }
	   
	    /**
	     * 小数部分的转换
	     * @param decimal
	     * @return String
	     * @throws NumberFormatException
	     */
	    public static String toChineseCharD(String decimal)throws NumberFormatException{
	   
	        //有效数字,如最后位的0是无效数字
	        boolean effective=false;
	        //一位由字符转换的数字
	        int intStringnumber=0;
	        //用来存放转换后的数字
	        StringBuffer ChineseCharD=new StringBuffer();
	       
	        for(int i=decimal.length()-1;i>=0;i--){
	            //非法字符检查
	            if(!Character.isDigit(decimal.charAt(i)))    {
	                throw new NumberFormatException("数字中含有非法字符");           
	            }
	            //转换
	            intStringnumber=Character.digit(decimal.charAt(i),10);
	            //System.out.print(intStringnumber);//测试
	           
	            //如果当前位的0是有效的
	            if(intStringnumber==0&&effective){
	                ChineseCharD.append(CHAINIESFIGURE1[0]);
	            }else{
	                ChineseCharD.append(CHAINIESFIGURE1[intStringnumber]);
	                effective=true;
	            }   
	        }
	       
	        //异常处理
	        if(ChineseCharD.length()==0){
	            return Character.toString(CHAINIESFIGURE1[0]);
	        }
	        return ChineseCharD.reverse().toString();
	       
	    }
	   
	    /**
	     * 带有小数点的转换
	     * @param StringNumber
	     * @return String
	     * @throws NumberFormatException
	     */
	    public static String toChineseChar(String StringNumber)throws NumberFormatException{
	   
	        //用小数点把数字分割成两部分
	        String[] number=StringNumber.split("\\.",2);
	        if(number.length>1){
	            return NumberChineseChar.toChineseCharI(number[0])+"点"+NumberChineseChar.toChineseCharD(number[1]);
	        }
	        return  NumberChineseChar.toChineseCharI(number[0]);
	    }

}
