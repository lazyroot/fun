package fun.security;

import com.jfinal.config.Routes;

import fun.security.controller.LoginController;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月18日
 * @description 安全验证模块路由
 */
public class SecurityRoutes extends Routes {

	@Override
	public void config() {
		add("/security/randomCode", LoginController.class);			//生成验证码
		add("/security/auth", LoginController.class);				//验证登陆
	}

}
