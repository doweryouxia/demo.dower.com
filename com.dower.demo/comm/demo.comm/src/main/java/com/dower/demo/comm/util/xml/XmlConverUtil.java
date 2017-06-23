package com.dower.demo.comm.util.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.xml.XMLSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author NiuXueJun 2015-7-31 上午11:26:18
 */
public class XmlConverUtil {

	public static final Log log = LogFactory.getLog(XmlConverUtil.class);

	/**
	 * java 转换成xml
	 * 
	 * @param obj
	 *            对象实例
	 * @return String xml字符串
	 */
	public static String toXml(Object obj) {
		XStream xstream = new XStream();
		// XStream xstream=new XStream(new DomDriver()); //直接用jaxp dom来解释
		// XStream xstream=new XStream(new DomDriver("utf-8"));
		// //指定编码解析器,直接用jaxp dom来解释

		// //如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
		xstream.processAnnotations(obj.getClass()); // 通过注解方式的，一定要有这句话
		// System.out.println(obj.getClass().getSimpleName());
		// xstream.alias(obj.getClass().getSimpleName(), obj.getClass());
		return xstream.toXML(obj);
	}

	/**
	 * 将传入xml文本转换成Java对象
	 * 
	 * @param xmlStr
	 * @param cls
	 *            xml对应的class类
	 * @return T xml对应的class类的实例对象
	 * 
	 *         调用的方法实例：PersonBean person=XmlUtil.toBean(xmlStr,
	 *         PersonBean.class);
	 */
	public static <T> T toBean(String xmlStr, Class<T> cls) {
		// 注意：不是new Xstream(); 否则报错：java.lang.NoClassDefFoundError:
		// org/xmlpull/v1/XmlPullParserFactory
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(cls);
		T obj = (T) xstream.fromXML(xmlStr);
		return obj;
	}

	/**
	 * 写到xml文件中去
	 * 
	 * @param obj
	 *            对象
	 * @param absPath
	 *            绝对路径
	 * @param fileName
	 *            文件名
	 * @return boolean
	 */

	public static boolean toXMLFile(Object obj, String absPath, String fileName) {
		String strXml = toXml(obj);
		String filePath = absPath + fileName;
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error("创建{" + filePath + "}文件失败!!!", e);
				return false;
			}
		}// end if
		OutputStream ous = null;
		try {
			ous = new FileOutputStream(file);
			ous.write(strXml.getBytes());
			ous.flush();
		} catch (Exception e1) {
			log.error("写{" + filePath + "}文件失败!!!", e1);
			return false;
		} finally {
			if (ous != null)
				try {
					ous.close();

				} catch (IOException e) {
					log.error("写{" + filePath + "}文件关闭输出流异常!!!", e);
				}
		}
		return true;
	}

	/**
	 * Map集合转xml
	 * 
	 * @param map
	 * @return String <node><key label="key1">value1</key><key
	 *         label="key2">value2</key>......</node>
	 */
	public static String maptoXml(Map map) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement("node");
		for (Object obj : map.keySet()) {
			Element keyElement = nodeElement.addElement("key");
			keyElement.addAttribute("label", String.valueOf(obj));
			keyElement.setText(String.valueOf(map.get(obj)));
		}
		return doc2String(document);
	}

	/**
	 * List集合转xml
	 * 
	 * @param list
	 * @see #doc2String(Document)
	 * @return String <node><key label="key1">value1</key><key
	 *         label="key2">value2</key>......</node>
	 */
	public static String listtoXml(List list) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element nodesElement = document.addElement("nodes");
		int i = 0;
		for (Object o : list) {
			Element nodeElement = nodesElement.addElement("node");
			if (o instanceof Map) {
				for (Object obj : ((Map) o).keySet()) {
					Element keyElement = nodeElement.addElement("key");
					keyElement.addAttribute("label", String.valueOf(obj));
					keyElement.setText(String.valueOf(((Map) o).get(obj)));
				}
			} else {
				Element keyElement = nodeElement.addElement("key");
				keyElement.addAttribute("label", String.valueOf(i));
				keyElement.setText(String.valueOf(o));
			}
			i++;
		}
		return doc2String(document);
	}

	/**
	 * Xml转Map
	 * 
	 * @param xml
	 *            <node><key label="key1">value1</key><key
	 *            label="key2">value2</key>......</node>
	 * @return map
	 */
	public static Map xmltoMap(String xml) {
		try {
			Map map = new HashMap();
			Document document = DocumentHelper.parseText(xml);
			Element nodeElement = document.getRootElement();
			List node = nodeElement.elements();
			for (Iterator it = node.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				map.put(elm.attributeValue("label"), elm.getText());
				elm = null;
			}
			node = null;
			nodeElement = null;
			document = null;
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Xml转List集合
	 * 
	 * @param xml
	 *            <nodes><node><key label="key1">value1</key><key
	 *            label="key2">value2</key>......</node><node><key
	 *            label="key1">value1</key><key
	 *            label="key2">value2</key>......</node></nodes>
	 * @return List
	 */
	public static List<Map> xmltoList(String xml) {
		try {
			List<Map> list = new ArrayList<Map>();
			Document document = DocumentHelper.parseText(xml);
			Element nodesElement = document.getRootElement();
			List nodes = nodesElement.elements();
			for (Iterator its = nodes.iterator(); its.hasNext();) {
				Element nodeElement = (Element) its.next();
				Map map = xmltoMap(nodeElement.asXML());
				list.add(map);
				map = null;
			}
			nodes = null;
			nodesElement = null;
			document = null;
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Xml转Json串
	 * 
	 * @param xml
	 *            <node><key label="key1">value1</key></node>
	 * @return String {"key":{"@label":"key1","#text":"value1"}}
	 */
	public static String xmltoJson(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		String jsonStr = null;
		try {
			jsonStr = xmlSerializer.read(xml).toString(3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 文档转字符串
	 * 
	 * @param document
	 * @return String
	 */
	public static String doc2String(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat("   ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}
}
