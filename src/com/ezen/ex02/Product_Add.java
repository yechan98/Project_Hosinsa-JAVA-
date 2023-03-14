package com.ezen.ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class Product_Add {

	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static String user = "root";
	private static String pwd = "1234";

	public static void add() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt;
		
		Scanner sc = new Scanner(System.in);
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);

			System.out.println("추가하실 제품의 코드(7자리)를 입력해주세요.");
			int pnumber = sc.nextInt();
			
			System.out.println("=============================");

			String sql = "SELECT COUNT(*) FROM MUSINSA WHERE PRODUCT_NUM =" + pnumber;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(sql);

			// 제품번호중 중복된 제품번호가 없는지 학인
			if (rs.next()) {
				int cnt = Integer.parseInt(rs.getString("COUNT(*)"));
				System.out.println(rs.getString("COUNT(*)") + "건의 데이터가 확인되었습니다!");

				if (cnt == 0) {
					System.out.println("데이터가 존재하지않습니다! 작업을 계속 진행합니다!");
					System.out.println("=============================");
				} else if (cnt == 1 || cnt > 1) {
					System.out.println("이미 같은 제품번호의 제품이 " + cnt + "개 존재합니다.");
					System.out.println("제품번호를 다시 입력해주세요.");
					System.out.println("=============================");
					add();
				}
			}
			sc.nextLine();
			
			// 중복된 값이 없을시 진행
			System.out.println("추가하실 제품명을 입력해주세요.");
			String name = sc.nextLine();

			System.out.println("추가하실 제품의 브랜드를 입력해주세요.");
			String brand = sc.nextLine();

			System.out.println("추가하실 제품의 카테고리를 입력해주세요.");
			String category = sc.nextLine();

			System.out.println("추가하실 제품의 가격을 입력해주세요.");
			int price = sc.nextInt();
			sc.nextLine();

			System.out.println("추가하실 제품의 재고를 입력해주세요.");
			int stock = sc.nextInt();
			System.out.println("=============================");
			sc.nextLine();

			// 제품이 맞는지 확인하는 절차
			System.out.println("추가하실 제품의 정보가 맞습니까? (맞으면 Y/ 틀리면 N)");
			System.out.println("제품이름 : " + name);
			System.out.println("제품번호 : " + pnumber);
			System.out.println("브랜드  : " + brand);
			System.out.println("카테고리 : " + category);
			System.out.println("가격    : " + price);
			System.out.println("재고    : " + stock);
			System.out.print("입력란 : ");
			String input = sc.nextLine();
			
			// 제품을 추가할 정보가 맞을때
			if (input.equalsIgnoreCase("y")) {

				try {
					Class.forName(driver);
					conn = DriverManager.getConnection(url, user, pwd);

					String sql2 = "INSERT INTO MUSINSA VALUES(seq_num.NEXTVAL,?,?,?,?,?,?)";
					pstmt = conn.prepareStatement(sql2);

					pstmt.setString(1, name);
					pstmt.setInt(2, pnumber);
					pstmt.setString(3, brand);
					pstmt.setString(4, category);
					pstmt.setInt(5, price);
					pstmt.setInt(6, stock);

					pstmt.executeUpdate();

					System.out.println();
					System.out.println("제품이 추가되었습니다!");
					System.out.println();
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 제품을 추가할 정보가 아닐때
			} else if (input.equalsIgnoreCase("n")) {
				System.out.println();
				System.out.println("제품 정보를 다시 입력해주세요.");
				System.out.println("=============================");
				add();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
