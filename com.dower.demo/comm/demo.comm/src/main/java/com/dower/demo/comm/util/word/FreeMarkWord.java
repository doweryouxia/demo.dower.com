package com.dower.demo.comm.util.word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * FreeMark导出Word
 * @author NiuXueJun
 * 2015-8-6 上午11:46:12
 */
public class FreeMarkWord {
	
	private  Configuration configuration = null;  
	
	private  String EXPORT_WORD_FILE ="";
	
	private  Properties properties;
	  
    public FreeMarkWord() {  
        configuration = new Configuration();  
        configuration.setDefaultEncoding("utf-8");  
        configuration.setClassicCompatible(true);
        
        InputStream in = FreeMarkWord.class.getClassLoader().getResourceAsStream("test.properties");
		properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EXPORT_WORD_FILE = properties.getProperty("export.word.file");
		File file = new File(EXPORT_WORD_FILE);
		if(!file.exists()){
			file.mkdirs();
		}
    }  
  
    /**
     * 生成doc文档
     * @param dataMap 数据map集合
     * @param fileName 文件名称
     * @param templateFile 模板文件
     * @throws UnsupportedEncodingException
     */
    public  void createDoc(Map<String,Object> dataMap,String fileName,String templateFile) throws UnsupportedEncodingException {  
        //dataMap 要填入模本的数据文件  
        //设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，  
       //加载模板文件
//    	String filePath = FreeMarkWord.class.getClassLoader().getResource("").getPath();
//    	System.out.println(filePath);
//    	try {
//			configuration.setDirectoryForTemplateLoading(new File(filePath));
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
        configuration.setClassForTemplateLoading(FreeMarkWord.class, "/com/ihandy/ucp/comm/util/word");  
        Template t=null;  
        try {  
            //test.ftl为要装载的模板  
            t = configuration.getTemplate(templateFile);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        //输出文档路径及名称  
        File outFile = new File(fileName);  
        Writer out = null;  
        FileOutputStream fos=null;  
        try {  
            fos = new FileOutputStream(outFile);  
            OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");  
            //这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。  
            //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));  
             out = new BufferedWriter(oWriter);   
        } catch (FileNotFoundException e1) {  
            e1.printStackTrace();  
        }  
           
        try {  
            t.process(dataMap, out);  
            out.close();  
            fos.close();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        //System.out.println("---------------------------");  
    }  
    
    public static void main(String[] args) {
    	Map<String, Object> dataMap = new HashMap<String, Object>();  
        dataMap.put("xytitle", "试卷");  
        int index = 1;  
        // 选择题  
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();//题目  
        List<Map<String, Object>> list11 = new ArrayList<Map<String, Object>>();//答案  
        index = 1;  
        for (int i = 0; i < 5; i++) {  
  
            Map<String, Object> map = new HashMap<String, Object>();  
            map.put("xzn", index + ".");  
            map.put("xztest",  
                    "(   )操作系统允许在一台主机上同时连接多台终端，多个用户可以通过各自的终端同时交互地使用计算机。");  
            map.put("ans1", "A" + index);  
            map.put("ans2", "B" + index);  
            map.put("ans3", "C" + index);  
            map.put("ans4", "D" + index);  
            list1.add(map);  
  
            Map<String, Object> map1 = new HashMap<String, Object>();  
            map1.put("fuck", index + ".");  
            map1.put("abc", "A" + index);  
            list11.add(map1);  
  
            index++;  
        }  
        dataMap.put("table1", list1);  
        dataMap.put("table11", list11);  
  
        // 填空题  
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();  
        List<Map<String, Object>> list12 = new ArrayList<Map<String, Object>>();  
        index = 1;  
        for (int i = 0; i < 5; i++) {  
  
            Map<String, Object> map = new HashMap<String, Object>();  
            map.put("tkn", index + ".");  
            map.put("tktest",  
                    "操作系统是计算机系统中的一个___系统软件_______，它管理和控制计算机系统中的___资源_________.");  
            list2.add(map);  
  
            Map<String, Object> map1 = new HashMap<String, Object>();  
            map1.put("fill", index + ".");  
            map1.put("def", "中级调度" + index);  
            list12.add(map1);  
  
            index++;  
        }  
        dataMap.put("table2", list2);  
        dataMap.put("table12", list12);  
  
        // 判断题  
        List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();  
        List<Map<String, Object>> list13 = new ArrayList<Map<String, Object>>();  
        index = 1;  
        for (int i = 0; i < 5; i++) {  
  
            Map<String, Object> map = new HashMap<String, Object>();  
            map.put("pdn", index + ".");  
            map.put("pdtest",  
                    "复合型防火墙防火墙是内部网与外部网的隔离点，起着监视和隔绝应用层通信流的作用，同时也常结合过滤器的功能。");  
            list3.add(map);  
  
            Map<String, Object> map1 = new HashMap<String, Object>();  
            map1.put("judge", index + ".");  
            map1.put("hij", "对" + index);  
            list13.add(map1);  
  
            index++;  
        }  
        dataMap.put("table3", list3);  
        dataMap.put("table13", list13);  
  
        // 简答题  
        List<Map<String, Object>> list4 = new ArrayList<Map<String, Object>>();  
        List<Map<String, Object>> list14 = new ArrayList<Map<String, Object>>();  
        index = 1;  
        for (int i = 0; i < 5; i++) {  
  
            Map<String, Object> map = new HashMap<String, Object>();  
            map.put("jdn", index + ".");  
            map.put("jdtest", "说明作业调度，中级调度和进程调度的区别，并分析下述问题应由哪一级调度程序负责。");  
            list4.add(map);  
  
            Map<String, Object> map1 = new HashMap<String, Object>();  
            map1.put("answer", index + ".");  
            map1.put("xyz", "说明作业调度，中级调度和进程调度的区别，并分析下述问题应由哪一级调度程序负责。");  
            list14.add(map1);  
  
            index++;  
        }  
        dataMap.put("table4", list4);  
        dataMap.put("table14", list14);  
  
        FreeMarkWord mdoc = new FreeMarkWord();  
        try {
        	mdoc.createDoc(dataMap, mdoc.EXPORT_WORD_FILE+"outtemplatefile.doc","template.ftl");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
