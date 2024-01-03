package com.javalec.function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class Dao_lcy {
	
	// Field
	private final String url_mysql = ShareVar.dbName;
	private final String id_mysql = ShareVar.dbUser;
	private final String pw_mysql = ShareVar.dbPass;
	
	// [product]
	int p_seq;
	String brand;
	String name;
	int price;
	String color;
	int qty;
	int size;
	int saveqty;
	FileInputStream file;
	
	// [customer]
	String customer_id;
	
	
	// Constructor
	public Dao_lcy() {
		// TODO Auto-generated constructor stub
	}
	
	public Dao_lcy(int p_seq) {
		super();
		this.p_seq = p_seq;
	}
	
	public Dao_lcy(int p_seq, int qty, int saveqty) {
		super();
		this.p_seq = p_seq;
		this.qty = qty;
		this.saveqty = saveqty;
	}
	
	
	public Dao_lcy(int p_seq, String brand, String name, int price, String color, int qty, int size,
			String customer_id) {
		super();
		this.p_seq = p_seq;
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.color = color;
		this.qty = qty;
		this.size = size;
		this.customer_id = customer_id;
	}

	public Dao_lcy(int p_seq, String customer_id, int price, int qty) {
		super();
		this.p_seq = p_seq;
		this.customer_id = customer_id;
		this.price = price;
		this.qty = qty;
	}
	
	public Dao_lcy(int p_seq, String brand, String name, int price, String color, int size, int qty,
			FileInputStream file) {
		super();
		this.p_seq = p_seq;
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.color = color;
		this.size = size;
		this.qty = qty;
		this.file = file;
	}
	

	// Method 

// =============================================================[BuyList]===================================================================
	// 검색 결과를 Table로 보내기
	public ArrayList<Dto_lcy> selectList() { 
		ArrayList<Dto_lcy> Dto_lcyList = new ArrayList<Dto_lcy>();
		String whereDefault = "select p.p_seq, p.brand, p.name, p.price, p.color, p.size, s.saveQty";
		String where1 = " from product as p, save as s";
		String where2 = " where p.p_seq = s.p_seq";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			Statement stmt_mysql = conn_mysql.createStatement();
			
			ResultSet rs = stmt_mysql.executeQuery(whereDefault+where1+where2);
			
			while(rs.next()) {
				int wkSeq = rs.getInt(1);
				String wkBrand = rs.getString(2);
				String wkName = rs.getString(3);
				String wkPrice = String.format("%,3d", Integer.parseInt(rs.getString(4)));
				String wkColor = rs.getString(5);
				int wkSize = rs.getInt(6);
				int wkSaveQty = rs.getInt(7);
				int wkqty = 1;
				Dto_lcy dto = new Dto_lcy(wkSeq, wkBrand, wkName, wkPrice, wkColor, wkSize, wkSaveQty, wkqty);
				Dto_lcyList.add(dto);
			}
			
			conn_mysql.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return Dto_lcyList;
	} 
	
	// Table을 Click 했을 때
	public Dto_lcy tableClick() {
		Dto_lcy Dto_lcy = null;
		String where1 = "select p.p_seq, p.brand, p.name, p.price, p.color, p.size, p.qty, p.image, s.saveQty";
		String where2 = " from product as p, save as s";
		String where3 = " where p.p_seq = s.p_seq and p.p_seq = " + p_seq;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			Statement stmt_mysql = conn_mysql.createStatement();
			
			ResultSet rs = stmt_mysql.executeQuery(where1+where2+where3);
			
			if(rs.next()) {
				int wkSeq = rs.getInt(1);
				String wkBrand = rs.getString(2);
				String wkName = rs.getString(3);
				String wkPrice = String.format("%,3d",Integer.parseInt(rs.getString(4)));
				String wkColor = rs.getString(5);
				int wkSize = rs.getInt(6);
				int wkQty = rs.getInt(7);
				int wkSaveQty = rs.getInt(9);
				
				// file(Image)
				ShareVar.filename = ShareVar.filename + 1;
				File file = new File(Integer.toString(ShareVar.filename));
				FileOutputStream output = new FileOutputStream(file);
				InputStream input = rs.getBinaryStream(8);
				byte[] buffer = new byte[1024];
				
				while(input.read(buffer) > 0) {
					output.write(buffer);
				}
				
				Dto_lcy = new Dto_lcy(wkSeq, wkBrand, wkName, wkPrice, wkColor, wkSize, wkQty, wkSaveQty);
			}
			
			conn_mysql.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return Dto_lcy;
	} 
	
	// 구매 확정
	public boolean buyAction() {
		PreparedStatement ps = null;
		boolean result;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			Statement stmt_mysql = conn_mysql.createStatement();
			
			String A = "insert into sale (product_p_seq, customer_customer_id, price, date, qty)";
			String B = " values (?,?,?,CAST(now() as date),?)";
			
			
			String input = Integer.toString(price).replaceAll(",", ""); // 쉼표(,) 제거
			price = Integer.parseInt(input);
			
			ps = conn_mysql.prepareStatement(A+B);
			ps.setInt(1,p_seq);
			ps.setString(2, ShareVar.userID);
			ps.setInt(3, price);
			ps.setInt(4, qty);
			ps.executeUpdate();
			
			conn_mysql.close();
			
		}catch(Exception e) {
			result = false;
		}
		result = true;
		return result;
	}

	
	// save에서 목록 삭제
	public boolean deleteAction() {
		PreparedStatement ps = null;
		boolean result;
		
		String where1 = "delete from save where p_seq = " + p_seq;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			Statement stmt_mysql = conn_mysql.createStatement();
			
			ps = conn_mysql.prepareStatement(where1);
			ps.executeUpdate();
			
			conn_mysql.close();
			
		}catch(Exception e) {
			result = false;
		}
		result = true;
		return result;
	}

	// 재고 변경
	public boolean updateAction() {
		PreparedStatement ps = null;
		boolean result;
		
		String where = "update product set qty = ? where p_seq = ?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			
			ps = conn_mysql.prepareStatement(where);
			ps.setInt(1,qty-saveqty);
			ps.setInt(2, p_seq);
			
			ps.executeUpdate();
			
			conn_mysql.close();
			
		}catch(Exception e) {
			System.out.println("update error");
			result = false;
		}
		result = true;
		return result;
	}

	

	// ===========================================================[OrderList]==============================================================
	
	
	// 검색 결과를 Table로 보내기
		public ArrayList<Dto_lcy> showOrderList() { 
			ArrayList<Dto_lcy> Dto_lcyList = new ArrayList<Dto_lcy>();
			String whereDefault = "select p.p_seq, p.brand, p.name, p.price, p.color, p.size, sale.qty, sale.date";
			String where1 = " from product as p, sale";
			String where2 = " where (p.p_seq = product_p_seq) and customer_customer_id = '" + ShareVar.userID + "'";
//			===================================================================================[ShareVar.userId]================================
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
				Statement stmt_mysql = conn_mysql.createStatement();
				
				ResultSet rs = stmt_mysql.executeQuery(whereDefault+where1+where2);
				
				while(rs.next()) {
					int wkSeq = rs.getInt(1);
					String wkBrand = rs.getString(2);
					String wkName = rs.getString(3);
					String wkPrice = String.format("%,3d", Integer.parseInt(rs.getString(4)));
					String wkColor = rs.getString(5);
					int wkSize = rs.getInt(6);
					int wkSaveQty = rs.getInt(7);
					Date wkDate = rs.getDate(8);
					Dto_lcy dto = new Dto_lcy(wkSeq, wkBrand, wkName, wkPrice, wkColor, wkSize, wkSaveQty, wkDate);
					Dto_lcyList.add(dto);
				}
				
				conn_mysql.close();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return Dto_lcyList;
		} 
	
		
		
		
}
