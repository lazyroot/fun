package core.util;

import java.util.UUID;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月23日
 * @description 通用工具类
 */
public class CommonUtil {
	
	/**
	 * @title generateUUID
	 * @description 生成UUID
	 * @param @return
	 * @return String
	 * @date 2014年4月23日
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

}
