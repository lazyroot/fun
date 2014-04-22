package core.entity;

import java.util.Date;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月22日
 * @description 邮件属性
 */
public class MailEntity {
	
	private String subject;
	private Date date;
	private String from;
	private String desMail;
	private String content;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getDesMail() {
		return desMail;
	}
	public void setDesMail(String desMail) {
		this.desMail = desMail;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "MailEntity [subject=" + subject + ", date=" + date + ", from="
				+ from + ", desMail=" + desMail + ", content=" + content + "]";
	}

}
