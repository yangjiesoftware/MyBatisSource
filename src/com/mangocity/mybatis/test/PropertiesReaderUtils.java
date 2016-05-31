package com.mangocity.mybatis.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: PropertiesReaderUtils
 * @Description: (properties文件解析工具类)
 * @author Jerrik
 * @date 2016年5月31日 上午9:27:23
 */
public class PropertiesReaderUtils {
	private static PropertiesReaderUtils uniqueInstance = null;
	
	private static final Logger LOGGER = Logger
			.getLogger(PropertiesReaderUtils.class);

	/**匹配Properties文件名正则(联合IGNORE_REPEAT_KEY_FILE_LIST一起使用)*/
	private static final String PROPS_FILE_REGEX = ".+.properties";
	
	/**
	 * 忽略重复key的配置文件,多个以逗号隔开(xxx_pro,xxx_dev,xxx_test.properties)
	 */
	private static final String IGNORE_REPEAT_KEY_FILE_LIST = "db.+";//such as: security.+.properties(忽略security_xx开头的属性文件),db.+(忽略以db开头的属性文件)
	
	/**
	 * 存放Properties文件子目录(如果不指定,则从classpath下面开始加载属性文件)
	 */
	private static final String SUB_DIR = "";
	
	/**
	 * 缓存Properties文件key-value容器
	 */
	private static  Map<String,String> CACHE_MAP;
	
	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static PropertiesReaderUtils getInstance() {
		if (uniqueInstance == null) {
			LOGGER.debug("new PropertiesReaderUtils() begin.....");
			uniqueInstance = new PropertiesReaderUtils();
			LOGGER.debug("new PropertiesReaderUtils() end .....");
		}
		return uniqueInstance;
	}

	private PropertiesReaderUtils() {
		init();
	}

	private void init() {
		LOGGER.info("PropertiesReaderUtils init()...");
		LOGGER.info("seekConfigPath(): " + seekConfigPath());
		ArrayList<String> returnFileList = new ArrayList<String>();
		Map<String,String> propsMap = new HashMap<String,String>();
		returnFileList = seekAllFilePath(seekConfigPath(), PROPS_FILE_REGEX, returnFileList);
		LOGGER.info("properties file size: " + returnFileList.size() + " ,path list: " + returnFileList);
		if(returnFileList.size() == 0){
			throw new RuntimeException("properties file size: " + returnFileList.size() + " ,Stop Here.");
		}
		for(String returnFile : returnFileList){
			readerProperties(returnFile, propsMap);
		}
		CACHE_MAP = Collections.unmodifiableMap(propsMap);
		LOGGER.debug("CACHE_MAP: " + CACHE_MAP);
	}

	/**
	 * @param rootPath
	 *            根路径
	 * @param regex
	 *            文件目录下的正则匹配
	 * @return 所有文件的根路径
	 */
	public ArrayList<String> seekAllFilePath(String rootPath, String regex,
			ArrayList<String> returnFilePath) {
		LOGGER.debug("seek file path:" + rootPath + " regex:" + regex
				+ " begin...");
		File file = new File(rootPath);
		returnFilePath = null == returnFilePath ? new ArrayList<String>()
				: returnFilePath;
		if (!file.isDirectory()) {
			if (null == regex || "".equals(regex)
					|| stringFilter(file.getName(), regex)) {
				returnFilePath.add(file.getPath());
				LOGGER.debug("add path:" + file.getPath());
			}
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				file = new File(rootPath + File.separator + filelist[i]);
				if (!file.isDirectory()) {
					if (null == regex || "".equals(regex)
							|| stringFilter(file.getName(), regex)) {
						returnFilePath.add(file.getPath());
						LOGGER.debug("add path:" + file.getPath());
					}
				} else if (file.isDirectory()) {
					seekAllFilePath(rootPath + "/" + filelist[i], regex,
							returnFilePath);
				}
			}
		}
		LOGGER.debug("seek file path:" + rootPath + " regex:" + regex
				+ " end...");
		return returnFilePath;
	}

	/**
	 * 读取Properties文件
	 * 
	 * @param path
	 * @param map
	 */
	public void readerProperties(String path, Map<String, String> map) {
		LOGGER.debug("reader properties:" + path + " begin...");
		Properties p = new Properties();
		try {
			File pFile = new File(path);
			FileInputStream pInStream = new FileInputStream(pFile);
			p.load(pInStream);
		} catch (IOException e) {
			LOGGER.error(e);
			return;
		}
		Enumeration<?> enu = p.propertyNames();
		while (enu.hasMoreElements()) {
			String key = enu.nextElement().toString();
			String propsFilePath = path.substring(path.lastIndexOf("\\")+1,path.length());
			//IGNORE_REPEAT_KEY_REGEX 不为空,就进行校验,否则,不进行文件过滤
			if(isNotBlank(IGNORE_REPEAT_KEY_FILE_LIST)){//如果指定忽略文件不为空
				if(!isIgnored(propsFilePath, IGNORE_REPEAT_KEY_FILE_LIST) && map.containsKey(key)){//如果该properties文件不能够匹配到忽略文件,则需要进行重复校验
					throw new RuntimeException("properties file repeat key...{" + key + "} , path: " + propsFilePath);
				}
			}else{
				if(map.containsKey(key)){
					throw new RuntimeException("properties file repeat key...{" + key + "} , path: " + propsFilePath);
				}
			}
			map.put(key, p.getProperty(key));
			LOGGER.debug("reader properties:" + path + " key:" + key + "="
					+ p.getProperty(key));
		}
		LOGGER.debug("reader properties:" + path + " end...");
	}

	//指定的xx.properties是否被添加到忽略列表
	private boolean isIgnored(String propsFilePath, String ignoreRepeatKeyFileList) {
		for(String str : ignoreRepeatKeyFileList.split(",")){
			if(stringFilter(propsFilePath, str)){//是否匹配
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到文件的配置路径
	 * 
	 * @return
	 */
	private String seekConfigPath() {
		StringBuilder sb = new StringBuilder();
		sb.append(PropertiesReaderUtils.class.getResource("/").getPath());
		if(isNotBlank(SUB_DIR)){
			sb.append(SUB_DIR);
			sb.append(File.separator);
		}
		return sb.toString();
	}

	public static boolean isNotBlank(String subDir) {
		if(!"".equals(subDir) && null != subDir){
			return true;
		}
		return false;
	}
	
	public static boolean isBlank(String subDir){
		return !isNotBlank(subDir);
	}
	
	private static boolean stringFilter(String string, String regex) {
		Pattern p = Pattern.compile(regex);
		return p.matcher(string).matches();
	}
	
	public static String getValue(String propsKey){
		PropertiesReaderUtils.getInstance();
		LOGGER.info("PropertiesReaderUtils getValue: {" + propsKey + "} begin()...");
		if(null == CACHE_MAP){
			LOGGER.info("PropertiesReaderUtils CACHE_MAP is Null...Stop Here.");
			throw new RuntimeException("CACHE_MAP未初始化");
		}
		return String.valueOf(CACHE_MAP.get(propsKey));
		
	}

	public static void main(String[] args) {
		System.out.println(PropertiesReaderUtils.getValue("driver.sqllite"));
	}
}
