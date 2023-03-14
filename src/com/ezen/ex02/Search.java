package com.ezen.ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Search {
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static Scanner sc = new Scanner(System.in);
	
	private static String driver="oracle.jdbc.driver.OracleDriver";
	private static String url="jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static String user="root";
	private static String pwd="1234";
	
	static {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pwd);
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	static void doSearch() {
		System.out.print("검색할 상품명을 입력해 주세요. >>");
		String keyword=sc.nextLine();
		String sql="SELECT * FROM MUSINSA WHERE PRODUCT LIKE '%"+keyword+"%'";
		try {
			rs = stmt.executeQuery(sql);
			System.out.println("번호        상품명                            품번         브랜드         카테고리      가격     재고");
			System.out.println("===========================================================================================");
			while(rs.next()) {
				System.out.printf("%1s %30s %8s %10s %10s %12s %5s",rs.getInt("SEQ_NUM"),rs.getString("PRODUCT"),rs.getString("PRODUCT_NUM"),
		                  rs.getString("BRAND"),rs.getString("CATEGORY"),rs.getInt("PRICE"),rs.getInt("STOCK"));
				System.out.println();					
			}
			sql="SELECT COUNT(*) FROM MUSINSA WHERE PRODUCT LIKE '%"+keyword+"%'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				System.out.println("- "+rs.getString("COUNT(*)")+"건의 데이터가 검색되었습니다.");
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {e.printStackTrace();}		
	}
}
