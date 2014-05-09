package fun.setting.controller;

import com.jfinal.core.Controller;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年5月9日
 * @description 页面资源控制类
 */
public class PageController extends Controller {
	
	public void index() {
		render("/setting/pages.html");
	}
	
	
	
}
