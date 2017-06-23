package com.dower.demo.comm.util.mail;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendMailUtil {


	/**
	 * 发送邮件，不包含附件
	 *
	 * @param content 邮件内容
	 * @param title 邮件标题
	 * @param sendServer 邮件发送服务器
	 * @param send 发送邮箱
	 * @param pwd 发送邮箱密码
	 * @param receive 接受邮箱
	 */
	public static void sendMailMu(String content, String title,
								String sendServer, String send, String pwd, List receive) {
		Properties props = new Properties();
		props.put("mail.smtp.host", sendServer);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(send));

			if(receive.size()>0){

				InternetAddress[] address = new InternetAddress[receive.size()];
				for (int i = 0; i < receive.size(); i++) {
					address[i] = new InternetAddress(receive.get(i).toString());
				}
				message.addRecipients(Message.RecipientType.TO, address);
				message.setSubject(title);
				Multipart multipart = new MimeMultipart();
				BodyPart contentPart = new MimeBodyPart();
				contentPart.setText(content);
				multipart.addBodyPart(contentPart);
				message.setContent(multipart);
				message.saveChanges();
				Transport transport = session.getTransport("smtp");
				transport.connect(sendServer, send, pwd);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 发送邮件，不包含附件
	 * 
	 * @param content 邮件内容
	 * @param title 邮件标题
	 * @param sendServer 邮件发送服务器
	 * @param send 发送邮箱
	 * @param pwd 发送邮箱密码
	 * @param receive 接受邮箱
	 */
	public static void sendMail(String content, String title,
			String sendServer, String send, String pwd, String receive) {
		Properties props = new Properties();
		props.put("mail.smtp.host", sendServer);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(send));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					receive));
			message.setSubject(title);
			Multipart multipart = new MimeMultipart();

			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(content);
			multipart.addBodyPart(contentPart);
			message.setContent(multipart);
			message.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(sendServer, send, pwd);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送带样式邮件，没有附件
	 * @param sendServer 邮件发送服务器
	 * @param send 发送邮箱
	 * @param receive 接受邮箱
	 * @param title 邮件标题
	 * @param content 邮件内容
	 * @param pwd 发送邮箱密码
	 */
	public static void sendHtmlMail(String sendServer, String send,
			String receive, String title, String content, String pwd) {
		try {
			Properties props = new java.util.Properties();
			props.setProperty("mail.smtp.auth", "true");// 指定是否需要SMTP验证
			props.setProperty("mail.smtp.host", sendServer);// 指定SMTP服务器
			props.put("mail.transport.protocol", "smtp");
			Session mailSession = Session.getDefaultInstance(props);
			InternetAddress fromAddress = new InternetAddress(send);
			InternetAddress toAddress = new InternetAddress(receive);

			MimeMessage testMessage = new MimeMessage(mailSession);
			testMessage.setFrom(fromAddress);
			testMessage.addRecipient(javax.mail.Message.RecipientType.TO,
					toAddress);
			testMessage.setSentDate(new java.util.Date());
			testMessage
					.setSubject(MimeUtility.encodeText(title, "gb2312", "B"));

			testMessage.setContent(content, "text/html;charset=gb2312");
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(sendServer, send, pwd);
			transport.sendMessage(testMessage, testMessage.getAllRecipients());
			transport.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  发送邮件 含有附件
	 * @param sendServer 发送邮件服务器
	 * @param send 发送邮箱
	 * @param receive 接受邮箱
	 * @param title 邮件标题
	 * @param content 邮件内容
	 * @param pwd 发送邮箱密码
	 * @param path 附件路径
	 */	 
	public static void sendAdjunctMessage(String sendServer, String send,
			String receive, String title, String content, String pwd,
			String path) {
		try {
			Properties props = new java.util.Properties();
			props.setProperty("mail.smtp.auth", "true");// 指定是否需要SMTP验证
			props.setProperty("mail.smtp.host", sendServer);// 指定SMTP服务器
			props.put("mail.transport.protocol", "smtp");
			Session mailSession = Session.getDefaultInstance(props);
			InternetAddress fromAddress = new InternetAddress(send);
			InternetAddress toAddress = new InternetAddress(receive);

			MimeMessage testMessage = new MimeMessage(mailSession);
			testMessage.setFrom(fromAddress);
			testMessage.addRecipient(javax.mail.Message.RecipientType.TO,
					toAddress);
			testMessage.setSentDate(new java.util.Date());
			testMessage.setSubject(MimeUtility.encodeText(title, "utf-8", "B"));

			// 附件
			MimeBodyPart attachment02 = createAttachment(path);
			// 解决附件名称中文乱码
			String fileName = attachment02.getFileName();
			attachment02.setFileName(MimeUtility.encodeText(fileName));
			MimeBodyPart contentHTML = createContent(content);
			MimeMultipart allPart = new MimeMultipart("mixed");
			allPart.addBodyPart(attachment02);
			allPart.addBodyPart(contentHTML);

			testMessage.setContent(allPart);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(sendServer, send, pwd);
			transport.sendMessage(testMessage, testMessage.getAllRecipients());
			transport.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据传入的文件路径创建附件并返回
	 */
	private static MimeBodyPart createAttachment(String fileName)
			throws Exception {
		MimeBodyPart attachmentPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(fileName);
		attachmentPart.setDataHandler(new DataHandler(fds));
		attachmentPart.setFileName(fds.getName());
		return attachmentPart;
	}

	/**
	 * 根据传入的邮件正文body和文件路径创建图文并茂的正文部分
	 */
	private static MimeBodyPart createContent(String body) throws Exception {
		// 用于保存最终正文部分
		MimeBodyPart contentBody = new MimeBodyPart();
		// 用于组合文本和图片，"related"型的MimeMultipart对象
		MimeMultipart contentMulti = new MimeMultipart("related");

		// 正文的文本部分
		MimeBodyPart textBody = new MimeBodyPart();
		textBody.setContent(body, "text/html;charset=gbk");
		contentMulti.addBodyPart(textBody);

		// 将上面"related"型的 MimeMultipart 对象作为邮件的正文
		contentBody.setContent(contentMulti);
		return contentBody;
	}

}