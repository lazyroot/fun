package core.util.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年5月7日
 * @description 统计session数量，可用于在线用户的统计等操作
 */
public class SessionCounter implements HttpSessionListener {
	
	/*
	 * session生效事件
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		ServletContext ctx = event.getSession().getServletContext();
		Integer sessionNum = (Integer) ctx.getAttribute("sessionNum");
		if (sessionNum == null) {
			sessionNum = new Integer(1);
		} else {
			int count = sessionNum.intValue();
			sessionNum = new Integer(count + 1);
		}
		ctx.setAttribute("sessionNum", sessionNum);
	}

	/*
	 * session失效事件
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		ServletContext ctx = event.getSession().getServletContext();
		Integer sessionNum = (Integer) ctx.getAttribute("sessionNum");
		if (sessionNum == null) {
			sessionNum = new Integer(0);
		} else {
			int count = sessionNum.intValue();
			sessionNum = new Integer(count - 1);
		}
		ctx.setAttribute("sessionNum", sessionNum);
	}

}
