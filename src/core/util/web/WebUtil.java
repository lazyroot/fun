package core.util.web;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月23日
 * @description 系统Web工具类
 */
public class WebUtil {
	
	private static final int COOKIE_TIME_OUT = 60 * 60 * 24 * 7;	//cookie默认保存七天
	private static final String COOKIE_PATH = "/";		//全网可见
	
	/**
	 * @title getRealPath
	 * @description 从相对路径得到绝对路径
	 * @param @param servletContext
	 * @param @param path
	 * @param @return
	 * @param @throws FileNotFoundException
	 * @return String
	 * @date 2014年4月23日
	 */
	public static String getRealPath(ServletContext servletContext, String path) throws FileNotFoundException {
		Assert.notNull(servletContext, "ServletContext must not be null");
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		String realPath = servletContext.getRealPath(path);
		if (realPath == null) {
			throw new FileNotFoundException("ServletContext resource [" + path + "] cannot be resolved to absolute file path - " +
					"web application archive not expanded?");
		}
		return realPath;
	}
	
	/**
	 * @title getSessionId
	 * @description 得到当前会话编号
	 * @param @param request
	 * @param @param name
	 * @param @return
	 * @return Object
	 * @date 2014年4月23日
	 */
	public static Object getSessionId(HttpServletRequest request, String name) {
		Assert.notNull(request, "Request must bot be null");
		HttpSession session = request.getSession(false);
		return (session != null ? session.getAttribute(name) : null);
	}
	
	/**
	 * @title getSessionAttribute
	 * @description 从当前会话中查询值，如果不存在不需要创建一个新的会话，提高性能
	 * @param @param request
	 * @param @param name
	 * @param @return
	 * @return Object
	 * @date 2014年4月23日
	 */
	public static Object getSessionAttribute(HttpServletRequest request, String name) {
		Assert.notNull(request, "Request must not be null");
		HttpSession session = request.getSession(false);
		return (session != null ? session.getAttribute(name) : null);
	}
	
	/**
	 * @title getRequiredSessionAttribute
	 * @description 从当前绘画中查询值，如果查询不出则抛出异常
	 * @param @param request
	 * @param @param name
	 * @param @return
	 * @return Object
	 * @date 2014年4月23日
	 */
	public static Object getRequiredSessionAttribute(HttpServletRequest request, String name) {
		Object attr = getSessionAttribute(request, name);
		if (attr == null) {
			throw new IllegalStateException("No session attribute [" + name + "] found");
		}
		return attr;
	}
	
	/**
	 * @title extractFileNameFromUrlPath
	 * @description 从当前路径得到文件名，工具方法，方便调用
	 * @param @param urlPath
	 * @param @return
	 * @return String
	 * @date 2014年4月23日
	 */
	public static String extractFileNameFromUrlPath(String urlPath) {
		int end = urlPath.indexOf(";");
		if (end == -1) {
			end = urlPath.indexOf("?");
			if (end == -1) {
				end = urlPath.length();
			}
		}
		int begin = urlPath.lastIndexOf("/", end) + 1;
		String fileName = urlPath.substring(begin, end);
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex != -1) {
			fileName = fileName.substring(0, dotIndex);
		}
		return fileName;
	}
	
	/**
	 * @title getParametersStartingWith
	 * @description 返回当前请求中的参数的键值映射，比传统更好用，将费数组转成原始值，而不是数组
	 * @param @param request
	 * @param @param prefix
	 * @param @return
	 * @return Map
	 * @date 2014年4月23日
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getParametersStartingWith(ServletRequest request, String prefix) {
		Assert.notNull(request, "ServletRequest must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if (".".equals(prefix) || paramName.startsWith(prefix)) {
				String unPrefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					//Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unPrefixed, values);
				} else {
					params.put(unPrefixed, values[0]);
				}
			}
		}
		return params;
	}
	
	/**
	 * @title setSessionAttribute
	 * @description 设置当前会话中的值，如果为null，则不处理并将其从当前会话中移除
	 * @param @param request
	 * @param @param name
	 * @param @param value
	 * @return void
	 * @date 2014年4月23日
	 */
	public static void setSessionAttribute(HttpServletRequest request, String name, Object value) {
		Assert.notNull(request, "Request must not be null");
		if (value != null) {
			request.getSession().setAttribute(name, value);
		} else {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute(name);
			}
		}
	}
	
	/**
	 * @title getCookies
	 * @description 查询所有的cookie，返回键值形式，方便客户端调用
	 * @param @param request
	 * @param @return
	 * @return Map<String,String>
	 * @date 2014年4月23日
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return Collections.EMPTY_MAP;	//返回空集合，比返回null要安全
		}
		Map<String, String> map = new HashMap<String, String>(cookies.length);
		for (Cookie cookie : cookies) {
			map.put(cookie.getName(), cookie.getValue());
		}
		return Collections.unmodifiableMap(map);	//不可变的map
	}
	
	/**
	 * @title getCookieValue
	 * @description 根据cookie的名称返回cookie的值
	 * @param @param request
	 * @param @param name
	 * @param @return
	 * @return String
	 * @date 2014年4月23日
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		return getCookies(request).get(name);
	}
	
	/**
	 * @title removeCookie
	 * @description 删除cookie
	 * @param @param response
	 * @param @param name
	 * @return void
	 * @date 2014年4月23日
	 */
	public static void removeCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		cookie.setPath(COOKIE_PATH);
		response.addCookie(cookie);
	}
	
	/**
	 * @title modifyCookie
	 * @description 修改cookie
	 * @param @param response
	 * @param @param name
	 * @param @param value
	 * @return void
	 * @date 2014年4月23日
	 */
	public static void modifyCookie(HttpServletResponse response, String name, String value) {
		addCookie(response, name, value);
	}
	
	/**
	 * @title addCookie
	 * @description 添加cookies
	 * @param @param response
	 * @param @param cookie
	 * @return void
	 * @date 2014年4月23日
	 */
	public static void addCookie(HttpServletResponse response, Map<String, String> cookies) {
		Assert.notNull(cookies);
		for (Map.Entry<String, String> each : cookies.entrySet()) {
			Cookie cookie = new Cookie(each.getKey(), each.getValue());
			cookie.setMaxAge(COOKIE_TIME_OUT);
			cookie.setPath(COOKIE_PATH);
			response.addCookie(cookie);
		}
	}
	
	/**
	 * @title addCookie
	 * @description 增加cookie
	 * @param @param response
	 * @param @param name
	 * @param @param value
	 * @return void
	 * @date 2014年4月23日
	 */
	public static void addCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(COOKIE_TIME_OUT);
		cookie.setPath(COOKIE_PATH);
		response.addCookie(cookie);
	}

}
