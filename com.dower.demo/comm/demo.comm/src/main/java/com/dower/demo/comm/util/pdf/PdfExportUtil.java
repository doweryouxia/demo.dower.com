package com.dower.demo.comm.util.pdf;

import java.io.File;  

import java.io.FileOutputStream;  
import java.util.List;

  
import com.lowagie.text.Document;  

import com.lowagie.text.Element;  
import com.lowagie.text.Font;  
import com.lowagie.text.PageSize;  

import com.lowagie.text.Phrase;  
import com.lowagie.text.pdf.BaseFont;  
import com.lowagie.text.pdf.PdfPCell;  
import com.lowagie.text.pdf.PdfPTable;  
import com.lowagie.text.pdf.PdfWriter;  

/**
 * 生成pdf  
 * @author NiuXueJun
 * 2015-8-4 下午5:51:17
 */
public class PdfExportUtil{  
	
    public Document document = new Document();// 建立一个Document对象      
      
    public static Font headfont ;// 设置字体大小  
    public static Font keyfont;// 设置字体大小  
    public static Font textfont;// 设置字体大小  
      
  
      
    static{  
        BaseFont bfChinese;  
        try {  
            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);  
            bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);  
            headfont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小  
            keyfont = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小  
            textfont = new Font(bfChinese, 8, Font.NORMAL);// 设置字体大小  
        } catch (Exception e) {           
            e.printStackTrace();  
        }   
    }  
      
      
    public PdfExportUtil(File file) {          
         document.setPageSize(PageSize.A4);// 设置页面大小  
         try {  
            PdfWriter.getInstance(document,new FileOutputStream(file));  
            document.open();   
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
          
          
    }  
    
    int maxWidth = 520;  
      
      
    public PdfPCell createCell(String value,com.lowagie.text.Font font,int align){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);          
         cell.setHorizontalAlignment(align);      
         cell.setPhrase(new Phrase(value,font));  
        return cell;  
    }  
      
     public PdfPCell createCell(String value,com.lowagie.text.Font font){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(Element.ALIGN_CENTER);   
         cell.setPhrase(new Phrase(value,font));  
        return cell;  
    }  
  
     public PdfPCell createCell(String value,com.lowagie.text.Font font,int align,int colspan){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(align);      
         cell.setColspan(colspan);  
         cell.setPhrase(new Phrase(value,font));  
        return cell;  
    }  
    public PdfPCell createCell(String value,com.lowagie.text.Font font,int align,int colspan,boolean boderFlag){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(align);      
         cell.setColspan(colspan);  
         cell.setPhrase(new Phrase(value,font));  
         cell.setPadding(3.0f);  
         if(!boderFlag){  
             cell.setBorder(0);  
             cell.setPaddingTop(15.0f);  
             cell.setPaddingBottom(8.0f);  
         }  
        return cell;  
    }  
    
     public PdfPTable createTable(int colNumber){  
        PdfPTable table = new PdfPTable(colNumber);  
        try{  
            table.setTotalWidth(maxWidth);  
            table.setLockedWidth(true);  
            table.setHorizontalAlignment(Element.ALIGN_CENTER);       
            table.getDefaultCell().setBorder(1);  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return table;  
    }  
     
     public PdfPTable createTable(float[] widths){  
            PdfPTable table = new PdfPTable(widths);  
            try{  
                table.setTotalWidth(maxWidth);  
                table.setLockedWidth(true);  
                table.setHorizontalAlignment(Element.ALIGN_CENTER);       
                table.getDefaultCell().setBorder(1);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return table;  
        }  
      
     public PdfPTable createBlankTable(){  
         PdfPTable table = new PdfPTable(1);  
         table.getDefaultCell().setBorder(0);  
         table.addCell(createCell("", keyfont));           
         table.setSpacingAfter(20.0f);  
         table.setSpacingBefore(20.0f);  
         return table;  
     }  
       
     public void generatePDF(String[] titles,List<?> datas) throws Exception{  
        PdfPTable table = createTable(4);  
        table.addCell(createCell("学生信息列表：", keyfont,Element.ALIGN_LEFT,4,false));  
              
        table.addCell(createCell("姓名", keyfont, Element.ALIGN_CENTER));  
        table.addCell(createCell("年龄", keyfont, Element.ALIGN_CENTER));  
        table.addCell(createCell("性别", keyfont, Element.ALIGN_CENTER));  
        table.addCell(createCell("住址", keyfont, Element.ALIGN_CENTER));  
          
        for(int i=0;i<5;i++){  
            table.addCell(createCell("姓名"+i, textfont));  
            table.addCell(createCell(i+15+"", textfont));  
            table.addCell(createCell((i%2==0)?"男":"女", textfont));  
            table.addCell(createCell("地址"+i, textfont));  
        }  
        document.add(table);  
          
        document.close();  
     }  
      
}
