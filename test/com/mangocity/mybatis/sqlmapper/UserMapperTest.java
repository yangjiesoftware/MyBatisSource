package com.mangocity.mybatis.sqlmapper;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.mangocity.mybatis.util.New;
import com.mangocity.mybatis.util.SqlUtil;

public class UserMapperTest {

	@Before
	public void setUp() throws Exception {
		SqlUtil.getInstance().init();
	}

	@Test
	public void testQueryUserById() {
		Map<String, Object> paramMap = New.map();
		paramMap.put("userId", 1);
		Map<String, Object> resultMap = SqlUtil.getInstance().selectOne(
				"queryUserById", paramMap);
		System.out.println("resultMap: " + resultMap);
	}

	@Test
	public void testAddUser() {
		Map<String, Object> paramMap = New.map();
		paramMap.put("userName", "haojiao");
		paramMap.put("desc", "beautiful");
		int row = SqlUtil.getInstance().insertOne("addUser", paramMap);
		System.out.println("row: " + row);
	}
	
	@Test
	public void testQueryAll(){
		List<Map<String,Object>> resultList = SqlUtil.getInstance().selectList("queryAllUsers", null);
		System.out.println("resultList: " + resultList);
	}
	
	@Test
	public void testQueryAll2(){
	}

}
