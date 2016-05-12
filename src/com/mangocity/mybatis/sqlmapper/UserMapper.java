package com.mangocity.mybatis.sqlmapper;

import java.util.Map;

public interface UserMapper {
	
	/**
	 * 根据Id查询user信息
	 * @param paramMap
	 * @return
	 */
	public Map<String,Object> queryUserById(Map<String,Object> paramMap);
	
	/**
	 * 添加User
	 * @param userMap
	 * @return
	 */
	public int addUser(Map<String,Object> userMap);
}