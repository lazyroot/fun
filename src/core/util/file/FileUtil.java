package core.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月22日
 * @description 文件操作工具类
 */
public class FileUtil {
	
	/**
	 * @title parsePropertyFile
	 * @description 操作properties文件
	 * @param @param filePath
	 * @param @return
	 * @param @throws IOException
	 * @return Properties
	 * @date 2014年4月22日
	 */
	public static Properties parsePropertyFile(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(inputStream);
		return prop;
	}

}
