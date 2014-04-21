package fun.security;

import com.jfinal.config.Routes;

import fun.security.controller.LoginController;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014��4��18��
 * @description ��ȫ��֤ģ��·��
 */
public class SecurityRoutes extends Routes {

	@Override
	public void config() {
		add("/security/randomCode", LoginController.class);			//������֤��
		add("/security/auth", LoginController.class);				//��֤��½
	}

}
