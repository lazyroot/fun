package core.util.web;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import core.entity.MailEntity;
import core.util.file.FileUtil;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月22日
 * @description 邮件功能类，包括读取邮件配置文件，发送邮件
 */
public class EmailUtil {
	
	private static final String EMAIL_PROP_FILE = "mail.properties";
	
	/**
	 * @title sendMail
	 * @description 发送邮件
	 * @param @param mail
	 * @return void
	 * @date 2014年4月22日
	 */
	public static void sendMail(MailEntity mail) {
		Properties prop = null;
		try {
			prop = FileUtil.parsePropertyFile(EMAIL_PROP_FILE);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Session session = getSession();
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject(mail.getSubject());
			message.setSentDate(mail.getDate());
			message.setFrom(new InternetAddress(prop.getProperty("mail.from")));
			message.setRecipient(RecipientType.TO, new InternetAddress(mail.getDesMail()));
			message.setContent(mail.getContent(), "text/html;charset=utf-8");
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static Session getSession() {
		Session session = null;
		try {
			final Properties prop = FileUtil.parsePropertyFile(EMAIL_PROP_FILE);
			session = Session.getInstance(prop, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(prop.getProperty(""), prop.getProperty(""));
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return session;
	}
	
}
