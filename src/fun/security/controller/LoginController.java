package fun.security.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.render.CaptchaRender;

import core.Constants;

public class LoginController extends Controller {
	
	public void randomCode() {
		CaptchaRender img = new CaptchaRender(Constants.RANDOM_CODE_KEY);
		render(img);
	}

}
