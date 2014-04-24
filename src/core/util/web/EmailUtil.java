package core.util.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.util.Assert;

import core.Constants;
import core.entity.MailEntity;
import core.util.file.FileUtil;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月22日
 * @description 邮件功能类，包括读取邮件配置文件，发送邮件
 */
public class EmailUtil {
	
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
			String path = EmailUtil.class.getClassLoader().getResource("").toURI().getPath();
			prop = FileUtil.parsePropertyFile(path + Constants.EMAIL_PROP_FILE);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Session session = getSession();
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject(mail.getSubject());
			message.setSentDate(mail.getDate());
			message.setFrom(new InternetAddress(prop.getProperty("mail.from")));
			message.setRecipient(RecipientType.TO, new InternetAddress(mail.getDesMail()));
			message.setContent(mail.getContent(), "text/html;charset=GBK");
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @title getMailAddr
	 * @description 根据邮箱地址获取邮箱网址
	 * @param @param email
	 * @param @return
	 * @return String
	 * @date 2014年4月24日
	 */
	@SuppressWarnings("unused")
	public static String getMailAddr(String email) {
		Assert.notNull(email, "Mail must not be null");
		String mailAddr = "https://mail.qq.com";	//缺省为QQ邮箱地址
		if (isEmailAddr(email)) {
			int index = email.indexOf("@");
			if (index == -1) {
				throw new IllegalArgumentException("Email is not correct, there is no '@'");
			} else {
				int lastIndex = email.lastIndexOf(".");
				String mailType = email.substring(index + 1, lastIndex).toLowerCase();
				Properties prop = null;
				try {
					String path = EmailUtil.class.getClassLoader().getResource("").toURI().getPath();
					prop = FileUtil.parsePropertyFile(path + Constants.EMAIL_PROP_FILE);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				String[] mailNameArr = prop.getProperty("mail.name").split(",");
				String[] mailAddrArr = prop.getProperty("mail.address").split(",");
				for (int i=0; i<mailNameArr.length; i++) {
					if (mailNameArr[i].equals(mailType)) {
						mailAddr = mailAddrArr[i];
						break;
					} else {
						throw new IllegalArgumentException("Current email is not support, please check and add, current email : " + email);
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Email is not correct, this is not an email address");
		}
		return mailAddr.toLowerCase();
	}
	
	/**
	 * @title isEmailAddr
	 * @description 验证Email地址
	 * @param @param email
	 * @param @return
	 * @return boolean
	 * @date 2014年4月24日
	 */
	public static boolean isEmailAddr(String email) {
		boolean flag = false;
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		flag = matcher.matches();
		return flag;
	}
	
	private static Session getSession() {
		Session session = null;
		try {
			String path = EmailUtil.class.getClassLoader().getResource("").toURI().getPath();
			final Properties prop = FileUtil.parsePropertyFile(path + Constants.EMAIL_PROP_FILE);
			session = Session.getInstance(prop, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(prop.getProperty("mail.from"), prop.getProperty("mail.smtp.password"));
				}
			});
//			session.setDebug(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return session;
	}
	
}
