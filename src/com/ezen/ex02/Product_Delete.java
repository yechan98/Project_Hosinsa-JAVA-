package com.ezen.ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Product_Delete {

	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static String user = "root";
	private static String pwd = "1234";

	public static void delete() {

		Scanner sc = new Scanner(System.in);

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);

			System.out.println("삭제하실 제품의 코드(7자리)를 입력해주세요.");
			int pnumber = sc.nextInt();
			
			// 제품번호가 존재하는지 확인
			String sql2 = "SELECT COUNT(*) FROM MUSINSA WHERE PRODUCT_NUM =" + pnumber;
			pstmt = conn.prepareStatement(sql2);
			rs = pstmt.executeQuery(sql2);

			if (rs.next()) {
				int cnt = Integer.parseInt(rs.getString("COUNT(*)"));
				System.out.println(rs.getString("COUNT(*)") + "건의 데이터가 확인되었습니다.");

				// 제품번호가 확인됐을때 실행
				if (cnt == 1 || cnt > 1) {
					
					System.out.println("작업을 계속 진행합니다.");
					System.out.println("=============================");

					try {
						Class.forName(driver);
						conn = DriverManager.getConnection(url, user, pwd);

						// 제품번호가 존재할경우
						String sql3 = "SELECT * FROM MUSINSA WHERE PRODUCT_NUM =" + pnumber;
						pstmt = conn.prepareStatement(sql3);
						rs = pstmt.executeQuery(sql3);

						System.out.println("번호        상품명                            품번         브랜드         카테고리      가격     재고");
						
						while (rs.next()) {
							System.out.printf("%1s %30s %8s %10s %10s %12s %5s",rs.getInt("SEQ_NUM"),rs.getString("PRODUCT"),rs.getString("PRODUCT_NUM"),
					                  rs.getString("BRAND"),rs.getString("CATEGORY"),rs.getInt("PRICE"),rs.getInt("STOCK"));
							System.out.println();	
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					sc.nextLine();
					System.out.println("=============================");
					System.out.println("입력하신 제품이 맞습니까? (맞으면 Y/ 틀리면 N)");
					System.out.print("입력란 : ");
					String input = sc.nextLine();

					// 입력한 정보가 맞을시
					if (input.equalsIgnoreCase("y")) {

						try {
							Class.forName(driver);
							conn = DriverManager.getConnection(url, user, pwd);

							String sql4 = "DELETE FROM MUSINSA WHERE PRODUCT_NUM = ?";
							pstmt = conn.prepareStatement(sql4);
							pstmt.setInt(1, pnumber);
							pstmt.executeUpdate();

							System.out.println();
							System.out.println("제품삭제를 완료하였습니다!");
							System.out.println("=============================");

						} catch (Exception e) {
							e.printStackTrace();
						}
						// 입력한 정보가 틀릴시
					} else if (input.equalsIgnoreCase("n")) {
						System.out.println("=============================");
						System.out.println("삭제하실 제품코드를 다시 입력해주세요.");
						System.out.println("=============================");
						delete();
					}

					// 제품번호가 없거나 올바르지 않을때
				} else if (cnt == 0) {
					System.out.println("=============================");
					System.out.println("제품번호를 잘못입력하셨습니다.");
					System.out.println("다시 입력해주세요.");
					System.out.println("=============================");
					delete();
				}
			}
		} catch (Exception e) {
		}
	}
}
