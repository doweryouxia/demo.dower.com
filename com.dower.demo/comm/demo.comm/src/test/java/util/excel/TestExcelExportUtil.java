package util.excel;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.InputStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.excel.ExcelExportUtil;


public class TestExcelExportUtil {

	private static String EXPORT_EXCEL_FILE = "";
	
	private static Properties properties;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream in = TestExcelExportUtil.class.getClassLoader().getResourceAsStream("test.properties");
		properties  = new Properties();
		properties.load(in);
		EXPORT_EXCEL_FILE = properties.getProperty("export.excel.file");
		File file = new File(EXPORT_EXCEL_FILE);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testExport() {
		String[][] content = new String[][] { 
        		{ "", "第一列", "第二列", "第三列" },
                { "第一行", "aa", "2.00", "22" }, 
                { "第二行", "bb", "3.01", "32" },
                { "第三行", "cc", "4.00", "41" } };
        try {
            OutputStream os = new FileOutputStream(EXPORT_EXCEL_FILE+"test1.xls");
             ExcelExportUtil.export(null,null, content,null, os,null,null);
           // ee.export(null, null, content,new String[] { "0,1,0,2", "1,0,3,0" }, os, "0,1", "0");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	@Test
	public void testExportFormatExcelStringArrayArrayStringOutputStream() {
		String[][] content = new String[][] { 
        		{ "", "第一列", "第二列", "第三列" },
                { "第一行", "aa", "2.00", "22" }, 
                { "第二行", "bb", "3.01", "32" },
                { "第三行", "cc", "4.00", "41" } };
        try {
            OutputStream os = new FileOutputStream(EXPORT_EXCEL_FILE+"test2.xls");
             ExcelExportUtil.exportFormatExcel(content,"测试小sheet", os);
           // ee.export(null, null, content,new String[] { "0,1,0,2", "1,0,3,0" }, os, "0,1", "0");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	@Test
	public void testExportFormatExcelMapOfStringStringStringArrayStringOutputStream() {
		
		String[][] con1 = new String[][] { 
        		{ "", "第一列", "第二列", "第三列" },
                { "第一行", "aa", "2.00", "22" }, 
                { "第二行", "bb", "3.01", "32" },
                { "第三行", "cc", "4.00", "41" } };
		
		String[][] con2 = new String[][] { 
        		{ "", "第一列", "第二列", "第三列" },
                { "第一行", "aa", "2.00", "22" }, 
                { "第二行", "bb", "3.01", "32" },
                { "第三行", "cc", "4.00", "41" } };
		Map<String, String[][]> content = new HashMap<String, String[][]>();
		content.put("六月份", con1);
		content.put("七月份", con2);
		String[] salary_name_array = {"六月份","七月份"};
        try {
            OutputStream os = new FileOutputStream(EXPORT_EXCEL_FILE+"test3.xls");
             ExcelExportUtil.exportFormatExcel(content, salary_name_array, "sheetName", os);
           // ee.export(null, null, content,new String[] { "0,1,0,2", "1,0,3,0" }, os, "0,1", "0");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}
