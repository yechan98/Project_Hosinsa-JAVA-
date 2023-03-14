package com.ezen.ex02;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Category {
		
	
	private static String driver="oracle.jdbc.driver.OracleDriver";
	private static String url="jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static String user="root";
	private static String pwd="1234";
		
	
	 public static void category() {
			
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Scanner sc = new Scanner(System.in);

		try {
			Class.forName(driver);				
			conn = DriverManager.getConnection(url,user,pwd); //forname,conn 잊지말기!

			System.out.print("카테고리를 선택해주세요:\n 0.전체출력\n 1.아우터\t\t 2.상의\t\t 3.바지\n 4.신발\t\t 5.스니커즈\t 6.가방\n 7.여성가방\t 8.액세서리\t 9.스포츠/용품\t"); 
			int num2 = sc.nextInt(); //번호를 받음
			String category = ""; //초기화 필수!
			
			switch(num2)  {//스위치문으로 입력받은 카테고리를 출력한다
				
				case 0 :category = "전체출력";
					System.out.println("상품 전체출력");
					break;
					
				case 1 : category = "아우터"; //검색키워드를 string으로 지정해준다
					System.out.println("아우터를 선택하셨습니다.");
					break;
			
				case 2 : category = "상의";
					System.out.println("상의를 선택하셨습니다.");
					break;
					
				case 3 : category = "바지";
					System.out.println("바지를 선택하셨습니다."); 
					break;
					
				case 4 : category = "신발";
					System.out.println("신발를 선택하셨습니다."); 
					break;
				
				case 5 : category = "스니커즈";
					System.out.println("스니커즈를 선택하셨습니다."); 
					break;
					
				case 6 : category = "가방";
					System.out.println("가방를 선택하셨습니다."); 
					break;
					
				case 7 : category = "여성가방";
					System.out.println("여성가방를 선택하셨습니다.");
					break;
				
				case 8 : category = "액세서리";
					System.out.println( "액세서리를 선택하셨습니다.");
					break;
					
				case 9 : category = "스포츠";
					System.out.println("스포츠/용품");
					break;	
					
				}
				
				String sql = "";
			
				 if(num2 == 0)
					sql =  "select * from musinsa order by seq_num";
				 else if (num2 != 0)
					sql = "select * from musinsa where CATEGORY = '" + category + "'";
				

			 
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();

			System.out.println("   번호                    제품명            제품번호              브랜드        카테고리    가격     재고  ");
			System.out.println("================================================================================================");
	
				while(rs.next()) {
					
				String str = rs.getString("PRODUCT");
				
				
				
				System.out.print(rs.getInt("SEQ_NUM")+"\t");
				
				 if(str.length() > 25) {
					System.out.print(rs.getString("PRODUCT").substring(0,25)+"\t");
				} else if (str.length() <= 25) { //짧을때
					System.out.print(rs.getString("PRODUCT")+"\t");
				}
				
				System.out.print(rs.getString("PRODUCT_NUM")+"\t"+"\t");
				System.out.print(rs.getString("BRAND")+"\t"+"\t");
				System.out.print(rs.getString("CATEGORY")+"\t"+"\t"); 
				System.out.print(rs.getInt("PRICE")+"\t"+"\t");
				System.out.print(rs.getString("STOCK")+"\t"+"\t");
				System.out.println();
			}
				
			}catch(Exception e) {} //try - catch 사용
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

