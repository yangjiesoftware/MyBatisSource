package com.mangocity.mybatis.datasourcepool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 测试
 * 不使用连接池创建连接的开销
 * @author YangJie
 */
public class UnPoolTest {

	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {
		long start = System.currentTimeMillis();
		for(int i=0;i<5000;i++){
			costCreateConnTime();
		}
		long end = System.currentTimeMillis();
		System.out.println("Total Time Costs:\t\t"
				+ (end - start) + " ms");

	}

	private static void costCreateConnTime() throws ClassNotFoundException,
			SQLException {
		String sql = "select * from t_user u where u.user_id < ? and user_id >= ?";
		PreparedStatement st = null;
		ResultSet rs = null;

		long beforeTimeOffset = -1L; // 创建Connection对象前时间
		long afterTimeOffset = -1L; // 创建Connection对象后时间
		long executeTimeOffset = -1L; // 创建Connection对象后时间

		Connection con = null;
		Class.forName("org.sqlite.JDBC");

		beforeTimeOffset = new Date().getTime();
		System.out.println("before:\t" + beforeTimeOffset);

		con = DriverManager.getConnection("jdbc:sqlite:sql.db", "", "");

		afterTimeOffset = new Date().getTime();
		System.out.println("after:\t\t" + afterTimeOffset);
		System.out.println("Create Costs:\t\t"
				+ (afterTimeOffset - beforeTimeOffset) + " ms");

		st = con.prepareStatement(sql);
		// 设置参数
		st.setInt(1, 101);
		st.setInt(2, 0);
		// 查询，得出结果集
		rs = st.executeQuery();
		executeTimeOffset = new Date().getTime();
		System.out.println("Exec Costs:\t\t"
				+ (executeTimeOffset - afterTimeOffset) + " ms");
	}

}
