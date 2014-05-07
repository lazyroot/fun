package fun;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.druid.DruidPlugin;

import core.framework.ContextPathHandler;
import fun.security.controller.MainController;
import fun.security.controller.SecurityController;
import fun.setting.SettingRoute;
import fun.setting.entity.IP;
import fun.setting.entity.LoginLog;
import fun.setting.entity.Module;
import fun.setting.entity.Operate;
import fun.setting.entity.Pages;
import fun.setting.entity.Resource;
import fun.setting.entity.Role;
import fun.setting.entity.User;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月21日
 * @description Fun JFinal配置类
 */
public class KMSConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("fun.properties");
		me.setEncoding("UTF-8");
		me.setUrlParaSeparator("::");
		me.setDevMode(getPropertyToBoolean("devMode", false));
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
		//不区分Model大小写
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		me.add(arp);
		//添加setting模块的数据库与Model映射
		arp.addMapping("T_S_USER", "S_USER_ID", User.class);
		arp.addMapping("T_S_ROLE", "S_ROLE_ID", Role.class);
		arp.addMapping("T_S_Module", "S_MODULE_ID", Module.class);
		arp.addMapping("T_S_PAGES", "S_PAGE_ID", Pages.class);
		arp.addMapping("T_S_OPERATE", "S_ACTION_ID", Operate.class);
		arp.addMapping("T_S_LOGIN_LOG", "S_LOG_ID", LoginLog.class);
		arp.addMapping("T_S_IP", "S_IP_ADDR", IP.class);
		arp.addMapping("T_S_RESOURCE", "S_RES_ID", Resource.class);
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/", SecurityController.class);
		me.add("/security", SecurityController.class);
		me.add("/main", MainController.class);
		me.add(new SettingRoute());
	}

}
