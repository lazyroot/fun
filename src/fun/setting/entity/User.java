package fun.setting.entity;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User> {

	private static final long serialVersionUID = 1178916490307035793L;
	
	public static final User dao = new User();

}
