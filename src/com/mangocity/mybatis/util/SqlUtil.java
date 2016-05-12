package com.mangocity.mybatis.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.log4j.Logger;

/**
 * SQLLITE和Mysql操作数据库方法,sqlId以"_"开头的,则操作SQLLITE
 */
public class SqlUtil {
	private static final Logger LOGGER = Logger.getLogger(SqlUtil.class
			.getName());
	private static SqlUtil uniqueInstance = null;
	private static SqlSessionFactory mysqlSessionFactory;
	private static SqlSessionFactory sqlliteSessionFactory;
	private static SqlSessionFactory oracleSessionFactory;
	private static String resource = "sqlconfig.xml";

	private SqlUtil() {
		LOGGER.debug("init SqlManager .....");
	}

	public static SqlUtil getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new SqlUtil();
		}
		return uniqueInstance;
	}

	public void init() {
		try {
			//根据资源文件获得字符流对象
			Reader xmlReader = Resources.getResourceAsReader(resource);
			
			//根据字符流对象和对应的environment id生成SqlSessionFactory
			sqlliteSessionFactory = new SqlSessionFactoryBuilder().build(
					xmlReader, "sqllite");
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/*
	 * 查询一条
	 * 
	 * @param sqlid
	 * 
	 * @param inmap
	 * 
	 * @return作者:刘春元2013-5-13
	 */
	public Map<String, Object> selectOne(String sqlid, Map<String, Object> inmap) {
		Map<String, Object> outmap = new HashMap<String, Object>();
		SqlSession sqlsession = null;
		if (sqlid.contains("o_")) {
			sqlsession = oracleSessionFactory.openSession();
		} else {
			sqlsession = sqlliteSessionFactory.openSession();
		}
		try {
			outmap = sqlsession.selectOne(sqlid, inmap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			;
		} finally {
			sqlsession.close();
		}
		return outmap;
	}

	public Object selectOneString(String sqlid, Map<String, Object> inmap) {
		Object str = "";
		SqlSession sqlsession = null;
		if (sqlid.contains("o_")) {
			sqlsession = oracleSessionFactory.openSession();
		} else {
			sqlsession = sqlliteSessionFactory.openSession();
		}
		try {
			str = sqlsession.selectOne(sqlid, inmap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			;
		} finally {
			sqlsession.close();
		}
		return str;
	}

	/*
	 * 查询所有
	 * 
	 * @param sqlid
	 * 
	 * @return作者:刘春元2013-5-13
	 */
	public List<Map<String, Object>> selectList(String sqlid,
			Map<String, Object> inmap) {
		List<Map<String, Object>> outListMap = new ArrayList<Map<String, Object>>();
		SqlSession sqlsession = null;
		if (sqlid.contains("o_")) {
			sqlsession = oracleSessionFactory.openSession();
		} else {
			sqlsession = sqlliteSessionFactory.openSession();
		}
		try {
			outListMap = sqlsession.selectList(sqlid, inmap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			;
			// e.printStackTrace();
		} finally {
			sqlsession.close();
		}
		return outListMap;
	}

	/*
	 * 插入单条数据(此方式没有事务处理)
	 * 
	 * @param sqlid
	 * 
	 * @param inMap
	 * 
	 * @return
	 * 
	 * @throws Exception作者:刘春元2013-5-17
	 */
	public int insertOne(String sqlid, Map<String, Object> inMap) {
		int flag = 0;
		Transaction newTransaction = null;
		SqlSession sqlsession = null;
		try {
			TransactionFactory transactionFactory = new JdbcTransactionFactory();
			if (sqlid.contains("o_")) {
				sqlsession = oracleSessionFactory.openSession();
			} else {
				sqlsession = sqlliteSessionFactory.openSession();
			}
			newTransaction = transactionFactory.newTransaction(sqlsession
					.getConnection());
			flag = sqlsession.insert(sqlid, inMap);
		} catch (Exception e) {
			try {
				newTransaction.rollback();
				LOGGER.error(e.getMessage(), e);
				;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				newTransaction.close();
				sqlsession.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public int updateOne(String sqlid, Map<String, Object> inMap) {
		int flag = 0;
		Transaction newTransaction = null;
		SqlSession sqlsession = null;
		try {
			TransactionFactory transactionFactory = new JdbcTransactionFactory();
			if (sqlid.contains("o_")) {
				sqlsession = oracleSessionFactory.openSession();
			} else {
				sqlsession = sqlliteSessionFactory.openSession();
			}
			newTransaction = transactionFactory.newTransaction(sqlsession
					.getConnection());
			flag = sqlsession.update(sqlid, inMap);
		} catch (Exception e) {
			try {
				newTransaction.rollback();
				LOGGER.error(e.getMessage(), e);
				;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				newTransaction.close();
				sqlsession.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public int deleteOne(String sqlid, Map<String, Object> inMap) {
		int flag = -1;
		Transaction newTransaction = null;
		SqlSession sqlsession = null;
		try {
			TransactionFactory transactionFactory = new JdbcTransactionFactory();
			if (sqlid.contains("o_")) {
				sqlsession = oracleSessionFactory.openSession();
			} else {
				sqlsession = sqlliteSessionFactory.openSession();
			}
			newTransaction = transactionFactory.newTransaction(sqlsession
					.getConnection());
			flag = sqlsession.delete(sqlid, inMap);
		} catch (Exception e) {
			try {
				newTransaction.rollback();
				LOGGER.error(e.getMessage(), e);
				;
			} catch (SQLException e1) {
				LOGGER.error(e.getMessage(), e);
				;
			}
		} finally {
			try {
				newTransaction.close();
				sqlsession.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage(), e);
				;
			}
		}
		return flag;
	}

	public String selectSequences(String sequences) {
		String str = "";
		SqlSession sqlsession = mysqlSessionFactory.openSession();
		try {
			str = sqlsession.selectOne("Sql_sequences", sequences);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			;
		} finally {
			sqlsession.close();
		}
		return str;
	}
}
