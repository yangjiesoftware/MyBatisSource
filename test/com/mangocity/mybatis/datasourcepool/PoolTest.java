package com.mangocity.mybatis.datasourcepool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

/**
 * 使用连接池(耗时非常少)
 * 
 * @author YangJie
 *
 */
public class PoolTest {

	public static void test() throws ClassNotFoundException, SQLException {
		String sql = "select * from t_user u where u.user_id < ? and user_id >= ?";
		PreparedStatement st = null;
		ResultSet rs = null;

		Connection con = null;
		Class.forName("org.sqlite.JDBC");

		try {
			con = Pool.getConnection();
			st = con.prepareStatement(sql);
			// 设置参数
			st.setInt(1, 101);
			st.setInt(2, 0);
			// 查询，得出结果集
			rs = st.executeQuery();
			// 取数据，省略
			// 将不再使用的Connection对象放到连接池中，供以后使用
			Pool.putConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 5000; i++) {
			test();
		}
		long end = System.currentTimeMillis();
		System.out.println("Time Costs:\t\t" + (end - start) + " ms");
	}
}