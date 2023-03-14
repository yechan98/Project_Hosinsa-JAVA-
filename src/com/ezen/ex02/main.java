package com.ezen.ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class main {
	private static String driver="oracle.jdbc.driver.OracleDriver";
	private static String url="jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static String user="root";
	private static String pwd="1234";
	
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs=null;
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pwd);
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}				
		System.out.println("***호신사 프로그램에 접속합니다.***");
		
	while (true) {
		System.out.println();
		System.out.println("원하는 작업번호를 입력해주세요.");
		System.out.println("=============================");
		System.out.print("1.제품추가(Create)"+"\t"+"2.제품삭제(Delete)"+"\t"+"3.제품 입출고(Update)"+"\n"+
		"4.카테고리 조회"+"\t"+"\t"+"5.검색(Read)"+"\t"+"\t"+"0.종료"+"\n");
		System.out.println("=============================");
		System.out.print("작업번호 : ");
		
		int num = sc.nextInt();
		sc.nextLine();
		if(num==1) {
			Product_Add.add();  
		}
		else if(num==2) {
			Product_Delete.delete();
		}
		else if(num==3) {		
			Update.updateStart();
		}
		else if(num==4) {
			Category.category();
		}
		else if(num==5) {			
			Search.doSearch();		
		}
		else if(num==0) {
			System.out.println("프로그램을 종료합니다.");
			break;
		}
		else {
			System.out.println("번호를 다시 입력해주세요.");
		}
	}
	System.exit(0);
	}
}
