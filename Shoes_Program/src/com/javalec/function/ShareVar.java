package com.javalec.function;

import java.awt.Color;

public class ShareVar {
	
	
	
	
	/*
	* Descritipon"500	, 300
	* Author
	* Date :
	* update:
	* 1.position 통일
	* 2. 색상통일
	* 3. 게시판 통일
	* 4. 게시판 다시 안보기 기능 사용

	*/

	public static String dbName = "jdbc:mysql://127.0.0.1:3306/shoe_program?serverTimezone=UTC&characterEncoding=utf8&useSSL=FALSE";
	public static String dbUser = "root";
	public static String dbPass = "qwer1234";
//	public static String dbName = "jdbc:mysql://127.0.0.1:3300/shoe_program?serverTimezone=UTC&characterEncoding=utf8&useSSL=FALSE";
//	public static String dbUser = "wondh1216";
//	public static String dbPass = "qwer1234";
	
	
	
	public static String userID = ""; // 아무거나 넣었음
	/// Window setting
	public static int position_window_x = 600;
	public static int position_window_y = 250;
	public static int window_size_x = 800;
	public static int window_size_y = 600;
	
	// setBounds(ShareVar.position_window_x, ShareVar.position_window_y, ShareVar.window_size_x, ShareVar.window_size_y);
	// contentPanel.setBackground(new Color(ShareVar.RGB_red, ShareVar.RGB_green, ShareVar.RGB_blue));
	
	// Bulletin setting
	public static int bulletin_x = 700;
	public static int bulletin_y = 350;
	
	// Window color setting
	public static int RGB_red = 247;
	public static int RGB_green = 246;
	public static int RGB_blue= 248;
	
	
	//
	
	public static boolean noticeSee = false; // 한번도 보지않았을경우, 다시보지 않기에 체크하지 않았을 경우 
	

	//keyboard on/off
	
	public static boolean keyboard = false; // when true => keyboard will up!

	// image ( 만약 고객 이미지 업로드가 필요할 경우 사용!
	public static int filename = 0;
	public static int image = 0;
	
	
	
} // End

