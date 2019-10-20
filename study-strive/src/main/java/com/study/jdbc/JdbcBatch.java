package com.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
public class JdbcBatch {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://192.168.233.133:3306/spring?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true";
	static final String USER = "root";
	static final String PASS = "root";
	public void updateDemo(){
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: 注册mysql的驱动
			Class.forName(JDBC_DRIVER);

			// STEP 3: 获得一个连接
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// STEP 4: 关闭自动提交
			conn.setAutoCommit(false);
			
			stmt=conn.createStatement();

			// STEP 5: 创建一个更新
			String sql = "update t_user  set mobile= '13125858455' where userName= 'lison' ";
			stmt.addBatch(sql);
			System.out.println(stmt.toString());//打印sql
			int[] executeBatch = stmt.executeBatch();
			System.out.println("此次修改影响数据库的行数为："+Arrays.toString(executeBatch));

			// STEP 6: 手动提交数据
			conn.commit();
			
			// STEP 7: 关闭连接
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
