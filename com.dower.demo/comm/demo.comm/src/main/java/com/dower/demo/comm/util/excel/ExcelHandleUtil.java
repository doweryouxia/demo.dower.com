/**
 * 
 */
package com.dower.demo.comm.util.excel;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import oracle.sql.TIMESTAMP;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 操作Excel工具类
 */
public class ExcelHandleUtil {

	/**
	 * 导出excel文件.
	 * 
	 * @param outputStream
	 *            输出流
	 * @param titles
	 *            excel表格的头部各列的名称
	 * @param fields
	 *            map中对应的key的名称
	 * @param list
	 *            从数据库中查询的结果集
	 * @param fileName
	 *            导出的excel的文件名
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void exportExcel(OutputStream outputStream, String titles[], String fields[], List<?> list, String fileName) throws Exception {
		// 创建文件
		WritableWorkbook book = Workbook.createWorkbook(outputStream);
		// 设置字体样式
		jxl.write.WritableFont titleFont = new jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.DARK_BLUE);
		// 格式化字体
		jxl.write.WritableCellFormat titleCellFormat = new jxl.write.WritableCellFormat(titleFont);
		// 定义各列的宽度
		// int columnLength[] = { 20, 20, 20, 20, 20, 20, 20, 20 };
		int columnLength = 20;
		// 动态填充数据
		Map map = new HashMap();
		// map中的key
		String key = "";
		// 根据key得到的value
		Object value = null;
		// 将要填充的单元格
		Label label = null;
		// 用于判断list中对象类型的变量
		Object objectClass = null;
		// 工作表个数
		int sheetSize = 1;
		// 每行的最大记录数
		int sheetRowCount = 65000;
		if (list.size() % sheetRowCount > 0) {
			sheetSize = list.size() / sheetRowCount + 1;
		} else {
			sheetSize = list.size() / sheetRowCount;
		}
		if (sheetSize < 1) {
			sheetSize = 1;
		}
		for (int t = 0; t < sheetSize; t++) {
			// 创建工作薄
			WritableSheet sheet = book.createSheet("sheet" + (t + 1) + "(" + (t + 1) * sheetRowCount + ")", t);
			for (int j = 0; j < titles.length; j++) {
				sheet.setColumnView(t, columnLength); // 设置列的宽度
				jxl.write.Label label1 = new jxl.write.Label(j, 0, titles[j], titleCellFormat);
				sheet.addCell(label1);
			}

			for (int i = sheetRowCount * t; i < sheetRowCount * (t + 1) && i < list.size(); i++) {
				// 先判断list中是map对象还是Object对象
				objectClass = list.get(i);
				if (objectClass.getClass() == java.util.HashMap.class) {
					// 直接给map赋值
					map = (Map) objectClass;
				} else {
					// 先从对象转为map，再给map赋值
					map = genMapFromObj(objectClass, fields);
				}
				if (map != null && map.size() > 0) {
					for (int j = 0; j < fields.length; j++) {
						key = fields[j];
						if (key != null) {
							// 把key转为大写，因为数据库中的表字段名称默认都是大写
							key = key.toUpperCase().toString();
							value = map.get(key);
							// 获取key对应的value,获取不到采用原始key获取。
							if (value == null || value.equals("")) {
								value = map.get(key);
							}
							if (value instanceof TIMESTAMP) {
								DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								value = sdf.format(((TIMESTAMP) value).dateValue());
							}
							if (value != null && !value.equals("")) {
								// 三个参数分别对应，列，行，单元格内的值
								label = new Label(j, (i + 1) - sheetRowCount * t, value.toString().trim());
								sheet.addCell(label);
							}
						}
					}
				}
			}
		}
		book.write();
		book.close();
	}

	/**
	 * 导出excel文件.
	 * 
	 * @param outputStream
	 *            输出流
	 * @param titles
	 *            excel表格的头部各列的名称
	 * @param fields
	 *            map中对应的key的名称
	 * @param list
	 *            从数据库中查询的结果集
	 * @param fileName
	 *            导出的excel的文件名
	 * @param wb 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void exportExcel(String titles[], String fields[], List<?> list,String newp,String sheetName, HSSFWorkbook wb) throws Exception {
		// 设置字体样式
		HSSFFont font = wb.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL); 
//		font.setFontHeightInPoints((short) 10);// 字体大小    
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
		font.setColor(HSSFColor.DARK_BLUE.index);
		// 定义各列的宽度
		// int columnLength[] = { 20, 20, 20, 20, 20, 20, 20, 20 };
		int columnLength = 20;
		// 动态填充数据
		Map map = new HashMap();
		// map中的key
		String key = "";
		// 根据key得到的value
		Object value = null;
//		// 将要填充的单元格
//		Label label = null;
		// 用于判断list中对象类型的变量
		Object objectClass = null;
		// 工作表个数
		int sheetSize = 1;
		// 每行的最大记录数
		int sheetRowCount = 65000;
		if (list.size() % sheetRowCount > 0) {
			sheetSize = list.size() / sheetRowCount + 1;
		} else {
			sheetSize = list.size() / sheetRowCount;
		}
		if (sheetSize < 1) {
			sheetSize = 1;
		}
		for (int t = 0; t < sheetSize; t++) {
			HSSFSheet sheet = wb.createSheet(sheetName);
			HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
			HSSFCellStyle style = wb.createCellStyle();
			style.setFont(font);
			rowFirst.setRowStyle(style);
			sheet.setColumnWidth(0, columnLength*256); // 设置列的宽度
			for (int j = 0; j < titles.length; j++) {
//				sheet.setColumnWidth(j, columnLength*256); // 设置列的宽度
				//获取第一行的每一个单元格 
			    HSSFCell cell = rowFirst.createCell(j); 
			    //往单元格里面写入值 
			    cell.setCellValue(titles[j]);
			    cell.setCellStyle(style);
			}

			for (int i = sheetRowCount * t; i < sheetRowCount * (t + 1) && i < list.size(); i++) {
				// 先判断list中是map对象还是Object对象
				objectClass = list.get(i);
				if (objectClass.getClass() == java.util.HashMap.class) {
					// 直接给map赋值
					map = (Map) objectClass;
				} else {
					// 先从对象转为map，再给map赋值
					map = genMapFromObj(objectClass, fields);
				}
				if (map != null && map.size() > 0) {
					HSSFRow row = sheet.createRow(i+1);
					for (int col = 0; col < fields.length; col++) {
						key = fields[col];
						if(key!=null){
							key = key.toUpperCase().toString();
							value = map.get(key);
							// 获取key对应的value,获取不到采用原始key获取。
							if (value == null || value.equals("")) {
								value = map.get(key);
							}
							if (value instanceof TIMESTAMP) {
								DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								value = sdf.format(((TIMESTAMP) value).dateValue());
							}
							if (value != null && !value.equals("")) {
								row.createCell(col).setCellValue(value.toString().trim());
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 把对象和对象的值转为map. generate map from obj
	 * 
	 * @param object
	 * @param fields
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map genMapFromObj(Object object, String[] fields) throws Exception {
		Object obj = object;
		Map map = new HashMap<Object, Object>();
		// Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i];
			Method method = obj.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1), new Class[] {});
			Object result = method.invoke(obj, new Object[] {});
			map.put(name.toUpperCase(), result);
		}
		return map;
	}
}
