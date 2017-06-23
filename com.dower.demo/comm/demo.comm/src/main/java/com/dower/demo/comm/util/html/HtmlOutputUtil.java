package com.dower.demo.comm.util.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理有html标签的内容
 * @author NiuXueJun
 * 2015-7-31 下午3:34:03
 */
public class HtmlOutputUtil {
	
	private String content;

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		// 配置html标记。
		Pattern p = Pattern.compile("<(\\S*?)[^>]*>.*?| <.*? />");
		Matcher m = p.matcher(content);

		String rs = new String(content);
		// 找出所有html标记。
		while (m.find()) {
			// System.out.println(m.group());
			// 删除html标记。
			rs = rs.replace(m.group(), "");
		}
		return rs;
	}

	public String getSubContent() {
		if (getContent() != null && getContent().length() > 100) {
			return getContent().substring(0, 100) + "...";
		} else {
			return getContent();
		}
	}
}	
