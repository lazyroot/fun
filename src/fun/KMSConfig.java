package fun;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

import core.framework.ContextPathHandler;
import fun.security.SecurityRoutes;
import fun.security.controller.SecurityController;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月21日
 * @description Fun JFinal配置类
 */
public class KMSConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("fun.properties");
		me.setDevMode(getPropertyToBoolean("devMode", true));
		me.setError403View("/403.html");
		me.setError404View("/404.html");
		me.setError500View("/500.html");
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler());
	}

	@Override
	public void configInterceptor(Interceptors arg0) {
		
	}

	@Override
	public void configPlugin(Plugins me) {
		DruidPlugin druidPlugin = new DruidPlugin(getProperty("jdbcURL"), getProperty("username"), getProperty("password"));
		me.add(druidPlugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		me.add(arp);
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
//		me.add(new SecurityRoutes());
		me.add("/", SecurityController.class);
		me.add("/security", SecurityController.class);
	}

}
