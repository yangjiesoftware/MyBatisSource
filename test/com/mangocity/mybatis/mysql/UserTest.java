package com.mangocity.mybatis.mysql;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	public static SqlSessionFactory sqlSessionFactory;
	public static SqlSession sqlSession;
	
	private static final String CONFIG_FILE_NAME = "sqlconfig.xml";
	
	@Before
	public void setup() throws IOException{
		Reader xmlReader = Resources.getResourceAsReader(CONFIG_FILE_NAME);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(
				xmlReader, "mysql");
	}
	
	@Test
	public void testQueryAllUsers(){
		sqlSession = sqlSessionFactory.openSession();
		List<Map<String,Object>> resultList = sqlSession.selectList("queryAllUsers");
		System.out.println("resultList: " + resultList);
	}
}
