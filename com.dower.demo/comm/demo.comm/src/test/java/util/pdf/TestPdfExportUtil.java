package util.pdf;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.pdf.PdfExportUtil;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;

import util.xml.TestXmlConverUtil;

public class TestPdfExportUtil {

	private static String EXPORT_PDF_FILE ="";
	
	private static Properties properties;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream in = TestXmlConverUtil.class.getClassLoader().getResourceAsStream("test.properties");
		properties = new Properties();
		properties.load(in);
		EXPORT_PDF_FILE = properties.getProperty("export.pdf.file");
		File file = new File(EXPORT_PDF_FILE);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGeneratePDF() {
		File file = new File(EXPORT_PDF_FILE+"bb.pdf");
		PdfExportUtil pdfExportUtil = new PdfExportUtil(file);
		PdfPTable table = pdfExportUtil.createTable(4);  
        table.addCell(pdfExportUtil.createCell("学生信息列表：", pdfExportUtil.keyfont,Element.ALIGN_LEFT,4,false));  
              
        table.addCell(pdfExportUtil.createCell("姓名", pdfExportUtil.keyfont, Element.ALIGN_CENTER));  
        table.addCell(pdfExportUtil.createCell("年龄", pdfExportUtil.keyfont, Element.ALIGN_CENTER));  
        table.addCell(pdfExportUtil.createCell("性别", pdfExportUtil.keyfont, Element.ALIGN_CENTER));  
        table.addCell(pdfExportUtil.createCell("住址", pdfExportUtil.keyfont, Element.ALIGN_CENTER));  
          
        for(int i=0;i<5;i++){  
            table.addCell(pdfExportUtil.createCell("姓名"+i, pdfExportUtil.textfont));  
            table.addCell(pdfExportUtil.createCell(i+15+"", pdfExportUtil.textfont));  
            table.addCell(pdfExportUtil.createCell((i%2==0)?"男":"女", pdfExportUtil.textfont));  
            table.addCell(pdfExportUtil.createCell("地址"+i, pdfExportUtil.textfont));  
        }  
        try {
			pdfExportUtil.document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
          
        pdfExportUtil.document.close();  
     }  

}
