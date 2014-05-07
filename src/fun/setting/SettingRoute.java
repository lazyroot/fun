package fun.setting;

import com.jfinal.config.Routes;

import fun.setting.controller.PagesController;

public class SettingRoute extends Routes {

	@Override
	public void config() {
		add("/pages", PagesController.class);
	}

}
