package com.dower.demo.comm.util.excel;

import java.io.OutputStream;
import java.util.Map;

import jxl.write.NumberFormat;
import jxl.write.WritableSheet;

/**
 * 服务于ExcelExportUtil工具类
 * @author NiuXueJun
 * 2015-8-5 上午9:51:12
 */
public class VerifyUtil {
	
	/**
	 * 是否空对象
	 * @param content 二维数组
	 * @param os 内容输出流
	 * @return boolean
	 */
    public static boolean isNullObject(String[][] content, OutputStream os) {
        // TODO Auto-generated method stub
        if(content != null && content.length > 0 && os != null)
        {
            return false;
        }
        return true;
    }
    
    /**
     * 是否空二维数组
     * @param content 二维数组
     * @return boolean
     */
    public static boolean isNull2DArray(String[][] content) {
        // TODO Auto-generated method stub
        if(content != null && content.length > 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 是否格式化数字
     * @param nf 格式化对象
     * @return boolean
     */
    public static boolean isNullObject(NumberFormat nf) {
        // TODO Auto-generated method stub
        if(nf != null)
        {
            return false;
        }
        return true;
    }
    
    /**
     * 是否空字符串 
     * @param sheetName 
     * @return boolean
     */
    public static boolean isNullObject(String sheetName) {
        if(sheetName != null && !"".equals(sheetName.trim()))
        {
            return false;
        }
        return true;
    }

    /**
     * 是否空对象
     * @param content map结合  Map<String, String[][]>
     * @param os 内容输出流
     * @return boolean
     */
    public static boolean isNullObject(Map<String, String[][]> content,
            OutputStream os) {
        // TODO Auto-generated method stub
        if(content != null && content.size() > 0 && os != null)
        {
            return false;
        }
        return true;
    }

    /**
     * 是否空数组
     * @param mergeInfo 数组
     * @return boolean
     */
    public static boolean isNull1DArray(String[] mergeInfo) {
        // TODO Auto-generated method stub
        if(mergeInfo != null && mergeInfo.length > 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 是否空表格
     * @param sheet
     * @return boolean
     */
    public static boolean isNullObject(WritableSheet sheet) {
        // TODO Auto-generated method stub
        if(sheet != null)
        {
            return false;
        }
        return true;
    }

}

