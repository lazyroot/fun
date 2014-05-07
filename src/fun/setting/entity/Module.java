package fun.setting.entity;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年5月5日
 * @description 模块数据库操作类
 */
public class Module extends Model<Module> {

	private static final long serialVersionUID = 6837992963544894521L;
	
	public static final Module dao = new Module();
	
	/**
	 * @title getModuleList
	 * @description 根据参数获取模块内容
	 * @param @param param
	 * @param @return
	 * @return List<Module>
	 * @date 2014年5月5日
	 */
	public List<Module> getModuleList(String param) {
		List<Module> moduleList = new ArrayList<Module>();
		String sql = "select * from t_s_module";
		if (null != param && !"".equals(param)) {
			sql += " where s_module_name like '%" + param + "%'";
		}
		moduleList = find(sql);
		return moduleList;
	}
	
}
