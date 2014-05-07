package fun.security.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;

import fun.setting.entity.Module;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年5月5日
 * @description MainController，主要包含主页相关内容的操作
 */
public class MainController extends Controller {
	
	private Logger log = Logger.getLogger(MainController.class);
	
	/**
	 * @title index
	 * @description 首页默认action，并通过参数查询
	 * @param @throws UnsupportedEncodingException
	 * @return void
	 * @date 2014年5月6日
	 */
	public void index() {
		String param = getPara("searchParam", "");
		List<Module> moduleList = Module.dao.getModuleList(param);
		log.info("Get Main dashboard : " + JsonKit.toJson(moduleList));
		setAttr("moduleList", moduleList);
		render("/dashboard.html");
	}
	
}
