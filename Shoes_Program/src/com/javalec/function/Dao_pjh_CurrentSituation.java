package com.javalec.function;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Dao_pjh_CurrentSituation {
	/*
	 * Description : 매출 현황 Dao
	 * Date : 2023.12.31
	 * Author : 박지환
	 * Update :
	 * 		1. 월별, 상품별, 브랜드별 매출현황 추가
	 *
	 */
	// Field
	private final String url_mysql = ShareVar.dbName;
	private final String id_mysql = ShareVar.dbUser;
	private final String pw_mysql = ShareVar.dbPass;

	//product
	int p_seq;
	String pbrand;
	String pname;
	int pprice;
	String pcolor;
	int pqty;
	int psize;
	FileInputStream file;
	//customer
	String customer_id;
	String cname;
	String cpw;
	String telno;
	String email;
	//delivery
	int dseq;
	int dp_seq1;
	int p_seq2;
	int ddate;	//date도 int 인트로?
	int dqty;
	int dprice;
	int product_p_seq;
	//sale
	int sseq;
	int sp_seq1;
	int sp_seq2;
	int sdate;
	int sprice;
	//save
	int saseq;
	int sap_seq;
	String sacustomer_id;






	public Dao_pjh_CurrentSituation() {
		// TODO Auto-generated constructor stub
	}








	//----------------------Method
	// 일자별 매출을 위한 qurry(뭐리문 다 작성한 뒤에 for문으로 처리하기)
	public ArrayList<Dto_pjh_CurrentSituation> selectList() {
		ArrayList<Dto_pjh_CurrentSituation> dtolist = new ArrayList<Dto_pjh_CurrentSituation>();
		String whereDefault = "select date,sum(price*qty) from sale group by date order by date";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);

			Statement stmt_mysql = conn_mysql.createStatement();
//			Statement stmt_mysql= conn_mysql.createStatement();

			ResultSet rs = stmt_mysql.executeQuery(whereDefault);

			while (rs.next()) {
				String wkDate = rs.getString(1);
				int wkprice = rs.getInt(2);


				Dto_pjh_CurrentSituation dto = new Dto_pjh_CurrentSituation(wkDate, wkprice);
				dtolist.add(dto);

			}
			conn_mysql.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dtolist;

	}
	// 일주일 매출 Qurry
	public ArrayList<Dto_pjh_CurrentSituation> selectWeekList() {
		ArrayList<Dto_pjh_CurrentSituation> dtolist = new ArrayList<Dto_pjh_CurrentSituation>();
		String whereDefault = "SELECT CONCAT(\n"
				+ "        DATE_FORMAT(MIN(date), '%Y-%m-%d'),\n"
				+ "        ' ~ ',\n"
				+ "        DATE_FORMAT(MAX(DATE_ADD(date, INTERVAL 6-DAYOFWEEK(date) DAY)), '%Y-%m-%d')\n"
				+ "       ) AS week_range,\n"
				+ "       SUM(price * qty) AS weekly_sales\n"
				+ "FROM sale\n"
				+ "GROUP BY YEAR(date), WEEK(date)";
				
//				"SELECT CONCAT(\r\n"
//				//날짜를 년,월,일 포멧으로 변환
//				+ " DATE_FORMAT(MIN(date), '%Y-%m-%d'), \r\n"
//				+ " ' ~ ', \r\n"
//				//데이트 포멧에서 max로최대날짜( DAYOFWEEK로 요일을 숫자로 반환한다. 그 숫자로 각 주의 일요일 날짜를 가져옴)
//				+ " DATE_FORMAT MAX(DATE_ADD(date, INTERVAL 6-DAYOFWEEK(date) DAY)), '%Y-%m-%d')\r\n"
//				+ " ) AS week_range,\r\n"
//				+ " SUM(price * qty) AS weekly_sales\r\n"
//				+ "FROM sale \r\n"
//				//연도랑 주차로 그룹핑하기
//				+ "GROUP BY YEAR(date), WEEK(date)";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);

			Statement stmt_mysql = conn_mysql.createStatement();
//				Statement stmt_mysql= conn_mysql.createStatement();

			ResultSet rs = stmt_mysql.executeQuery(whereDefault);

			while (rs.next()) {
				String wkDate = rs.getString(1);
				int wkprice = rs.getInt(2);


				Dto_pjh_CurrentSituation dto = new Dto_pjh_CurrentSituation(wkDate, wkprice);
				dtolist.add(dto);

			}
			conn_mysql.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dtolist;

	}
	// 월매출 Qurry
	public ArrayList<Dto_pjh_CurrentSituation> selecMonthList() {
		ArrayList<Dto_pjh_CurrentSituation> dtolist = new ArrayList<Dto_pjh_CurrentSituation>();
		String whereDefault = "SELECT DATE_FORMAT(date, '%Y-%m'), sum(price * qty) FROM sale group by DATE_FORMAT(date, '%Y-%m')";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);

			Statement stmt_mysql = conn_mysql.createStatement();
//			Statement stmt_mysql= conn_mysql.createStatement();

			ResultSet rs = stmt_mysql.executeQuery(whereDefault);

			while (rs.next()) {
				String wkDate = rs.getString(1);
				int wkprice = rs.getInt(2);


				Dto_pjh_CurrentSituation dto = new Dto_pjh_CurrentSituation(wkDate, wkprice);
				dtolist.add(dto);

			}
			conn_mysql.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dtolist;

	}

	// 상품별 매출 Query
	public ArrayList<Dto_pjh_CurrentSituation> selectProductList() {
		ArrayList<Dto_pjh_CurrentSituation> dtolist = new ArrayList<Dto_pjh_CurrentSituation>();
		String whereDefault = "SELECT \r\n"
				+ "    p.brand,\r\n"
				+ "    p.name,\r\n"
				//cast:부호 없는 정수형으로 변환->이거로 소수점 버리기
				+ "    CAST(AVG(s.price) AS UNSIGNED) ,\r\n"
				+ "    SUM(s.qty),\r\n"
				+ "    SUM(p.price * s.qty) \r\n"
				+ "FROM \r\n"
				+ "    product p\r\n"
				//product 테이블과 sale 테이블을 조인
				+ "JOIN \r\n"
				+ "    sale s ON p.p_seq = s.product_p_seq\r\n"
				+ "GROUP BY \r\n"
				+ "    p.brand, p.name;";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);

			Statement stmt_mysql = conn_mysql.createStatement();
//				Statement stmt_mysql= conn_mysql.createStatement();

			ResultSet rs = stmt_mysql.executeQuery(whereDefault);

			while (rs.next()) {
				String wkbrand = rs.getString(1);
				String wkname = rs.getString(2);
				int wkprice = rs.getInt(3);
				int wkqty = rs.getInt(4);
				int wktotalprice = rs.getInt(5);


				Dto_pjh_CurrentSituation dto = new Dto_pjh_CurrentSituation(wkbrand, wkname, wkprice, wkqty, wktotalprice);
				dtolist.add(dto);

			}
			conn_mysql.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dtolist;

	}

	// 브랜드별 매출 Qurry
	public ArrayList<Dto_pjh_CurrentSituation> selectBrandList() {
		ArrayList<Dto_pjh_CurrentSituation> dtolist = new ArrayList<Dto_pjh_CurrentSituation>();
		String whereDefault = " SELECT p.brand,  SUM(s.price * s.qty) \r\n"
				+ "FROM product p\r\n"
				+ "JOIN sale s ON p.p_seq = s.product_p_seq\r\n"
				+ "GROUP BY p.brand;";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);

			Statement stmt_mysql = conn_mysql.createStatement();
//				Statement stmt_mysql= conn_mysql.createStatement();

			ResultSet rs = stmt_mysql.executeQuery(whereDefault);

			while (rs.next()) {
				String wkDate = rs.getString(1);
				int wkprice = rs.getInt(2);


				Dto_pjh_CurrentSituation dto = new Dto_pjh_CurrentSituation(wkDate, wkprice);
				dtolist.add(dto);

			}
			conn_mysql.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dtolist;

	}








}
