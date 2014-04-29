package fun.setting.entity;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月23日
 * @description 用户实体模型
 */
public class User extends Model<User> {

	private static final long serialVersionUID = 1178916490307035793L;
	
	public static final User dao = new User();

	/**
	 * @title findUser
	 * @description 根据用户名和密码查找用户
	 * @param @param userName
	 * @param @param password
	 * @param @return
	 * @return User
	 * @date 2014年4月23日
	 */
	public User findUser(String userName, String password) {
		User user = findFirst("select * from t_s_user where s_user_name = ? and s_user_pass = ? and s_user_state = '0'", userName, password); 
		return user;
	}

	public User findEmailUser(String email) {
		User user = findFirst("select * from t_s_user where s_user_email = ?", email);
		return user;
	}
	
	

}
