package com.dower.demo.comm.util.word;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 *  Word 处理
 * @author AOKUNY
 */
public class PoiWord {
	protected static final String errNotHaveContent = "文件内容为空或无法读取文件内容";
	public String errorMsg = StringUtils.EMPTY;
	public PoiWord(){
		// 以后提取配置、初始化
	}
	public String readWord(String fileName) throws Exception {
		return StringUtils.EMPTY;
    }
	static String encodeJson(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
    /**
     *  将日期格式更改为yyyy-MM-dd
     * @param datestring
     * @return
     * @throws Exception 
     */
    private String formatDate(String datestring) throws Exception{
		  try {
			  // 去除特殊字符及字母等
			  String excdatestring = datestring.replaceAll("[^(0-9\\u4e00-\\u9fa5)]", "");
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			  DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			  Date date = sdf.parse(excdatestring);
			  return format.format(date);
		} catch (ParseException e) {
			  try {
				  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				  DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				  Date date = sdf.parse(datestring);
				  return format.format(date);
			} catch (ParseException e1) {
				  this.errorMsg = "时间转换出错";
				  throw new Exception(this.errorMsg);
			}
		}
    }
	public static void poiReadWordTitleDocx(String fileName,String destPath,Map<String,String>  param) throws Exception {
		OPCPackage pack = POIXMLDocument.openPackage(fileName);
		XWPFDocument doc = new XWPFDocument(pack);
		if (doc.getParagraphs().size() > 0) {
			for(XWPFParagraph paragraph : doc.getParagraphs()){
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					String text = run.getText(0);
					if(text != null){
						boolean isSetText = false;
						for (Map.Entry<String,String> entry : param.entrySet()) {
							String key = entry.getKey();
							if(text.indexOf(key) != -1){
								isSetText = true;
								Object value = entry.getValue();
								if (value instanceof String) {//文本替换
									text = text.replace(key, value.toString());
								}
							}
						}
						if(isSetText){
							run.setText(text,0);
						}
					}
				}
			}
		}
		FileOutputStream outStream = null;
		outStream = new FileOutputStream(destPath);
		doc.write(outStream);
		outStream.close();
	}
	/**
	 * 处理段落
	 * @param paragraphList
	 */
	public static void processParagraphs(List<XWPFParagraph> paragraphList,Map<String,String> param){
		if(paragraphList != null && paragraphList.size() > 0){
			for(XWPFParagraph paragraph:paragraphList){
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					String text = run.getText(0);
					if(text != null){
						boolean isSetText = false;
						for (Map.Entry<String,String> entry : param.entrySet()) {
							String key = entry.getKey();
							if(text.indexOf(key) != -1){
								isSetText = true;
								Object value = entry.getValue();
								if (value instanceof String) {//文本替换
									text = text.replace(key, value.toString());
								}
							}
						}
						if(isSetText){
							run.setText(text,0);
						}
					}
				}
			}
		}
	}
	public static void searchAndReplace(String srcPath ,String destPath,Map<String,String> map) {
		try {
			XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
			// 处理订单编号
			if (document.getParagraphs().size() > 0) {
				List<XWPFParagraph> paragraphsList = new ArrayList<XWPFParagraph>();
				paragraphsList.add(document.getParagraphArray(0));
				processParagraphs(paragraphsList, map);
			}
			Iterator it = document.getTablesIterator();
			while(it.hasNext()){
				XWPFTable table = (XWPFTable)it.next();
				int rcount = table.getNumberOfRows();
				for(int i =0 ;i < rcount;i++){
					XWPFTableRow row = table.getRow(i);
					List<XWPFTableCell> cells =  row.getTableCells();
					for (XWPFTableCell cell : cells){
						for(Map.Entry<String,String> e : map.entrySet()){
							if (cell.getText().equals(e.getKey())){
								cell.removeParagraph(0);
								cell.setText(e.getValue());
							}
						}
						processParagraphs(cell.getParagraphs(),map);
					}
				}
			}
			FileOutputStream outStream = null;
			outStream = new FileOutputStream(destPath);
			document.write(outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}