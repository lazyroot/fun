package fun.security.controller;

import java.util.Date;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.ext.render.CaptchaRender;

import core.Constants;
import core.entity.MailEntity;
import core.util.CommonUtil;
import core.util.security.DES3Util;
import core.util.web.EmailUtil;
import core.util.web.WebUtil;
import fun.setting.entity.IP;
import fun.setting.entity.LoginLog;
import fun.setting.entity.User;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月22日
 * @description 主要包括一些安全性操作，如登陆，密码重置等
 */
public class SecurityController extends Controller {
	
	private Logger log = Logger.getLogger(SecurityController.class);
	
	public void index() {
		User user = getSessionAttr("loginUser");
		if (user == null) {
			log.warn("Auto login failed, reason : user session is not exist, render to login page!");
			render("/login.html");
		} else {
			log.info("用户session未过期，直接登录，转至首页！");
			redirect("/main");
		}
	}
	
	/**
	 * @title randomCode
	 * @description 使用JFinal内置的CaptchaRender生成验证码
	 * @param 
	 * @return void
	 * @date 2014年4月23日
	 */
	public void randomCode() {
		CaptchaRender img = new CaptchaRender(Constants.RANDOM_CODE_KEY);
		render(img);
	}
	
	/**
	 * @title auth
	 * @description 登录验证
	 * @param 
	 * @return void
	 * @date 2014年4月23日
	 */
	public void auth() {
		String userName = getPara("username");
		String password = getPara("password");
//		String randomCode = getPara("randomCode");
		//验证验证码是否正确
//		boolean isCorrCode = CaptchaRender.validate(this, randomCode, Constants.RANDOM_CODE_KEY);
//		if (isCorrCode) {
			//采用两次DES3加密方法进行加密
			password = DES3Util.getTwiceEncStr(password);
			//判断是否登录用户是否存在
			User user = User.dao.findUser(userName, password);
			if (user == null) {
				log.warn("Illegal login, there is not this user, username =" + userName + ", password = " + DES3Util.getTwiceDecStr(password));
				redirect("/");
			} else {
				//用户存在后，添加登录日志信息
				String loginId = CommonUtil.generateUUID();
				String userId = user.getStr("s_user_id");
				
				//判断是否为非法IP地址
				String ipAddress = WebUtil.getReaIpAddr(getRequest());
				IP ip = IP.dao.findById(ipAddress);
				if (ip == null) {
					//重定向到首页，进行菜单及主页的初始化
					setSessionAttr("loginUser", user);
					new LoginLog().set("s_log_id", loginId).set("s_user_id", userId).set("s_log_flag", "0").set("s_log_ip", ipAddress).save();
					redirect("/main");
				} else {
					log.warn("Current ip [" + ipAddress + "] is denied to login, reason [" + ip.getStr("s_ip_resion") + "]");
					new LoginLog().set("s_log_id", loginId).set("s_user_id", userId).set("s_log_flag", "11").set("s_log_ip", ipAddress).save();
					render("/login.html");
				}
			}
//		} else {
//			log.warn("Auth failed, reason : random code is not correct!");
//			redirect("/");
//		}
	}
	
	/**
	 * @title forgetPwd
	 * @description 忘记密码，发送邮件
	 * @param 
	 * @return void
	 * @date 2014年4月23日
	 */
	public void forgetPwd() {
		String email = getPara("email").trim();
		User user = User.dao.findEmailUser(email);
		if (user == null) {
			log.warn("No one has email like [" + email + "]");
			redirect("/");
		} else {
			MailEntity mail = new MailEntity();
			mail.setDesMail(email);
			mail.setDate(new Date());
			mail.setSubject("密码找回");
			mail.setContent("密码找回邮件，请点击下面链接进行找回");
			EmailUtil.sendMail(mail);
			renderHtml("邮件已经发送，前往邮箱查看邮件，<a href='http://" + EmailUtil.getMailAddr(email) + "'>立即查看</a>。"
					+ "返回<a href='/fun'>登录</a>页面");
		}
	}
	
	/**
	 * @title logout
	 * @description 用户退出操作，登记数据库并移除session
	 * @param 
	 * @return void
	 * @date 2014年5月6日
	 */
	public void logout() {
		User user = getSessionAttr("loginUser");
		if (user != null) {
			String loginId = CommonUtil.generateUUID();
			log.info("User session attributes : " + user.getAttrNames());
			String userId = user.getStr("s_user_id");
			String ipAddress = WebUtil.getReaIpAddr(getRequest());
			new LoginLog().set("s_log_id", loginId).set("s_user_id", userId).set("s_log_flag", "9").set("s_log_ip", ipAddress).save();
			removeSessionAttr("loginUser");
			log.info("User [" + user.getStr("s_user_name") + "] logout.");
		}
		redirect("/");
	}
	
}
