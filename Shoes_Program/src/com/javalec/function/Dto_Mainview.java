package com.javalec.function;

import java.io.FileInputStream;

public class Dto_Mainview {

	/*
	 * Description : 메인뷰 페이지 dto
	 * Date : 2023.12.31
	 * Author : 박지환
	 * Update :
	 * 		1. Constructor 추가
	 */
	// Field
	int p_seq;
	String pbrand;
	String pname;
	int pprice;
	String pcolor;
	int pqty;
	int psize;
	String fake_filename;

	// Constructor
	public Dto_Mainview() {
		// TODO Auto-generated constructor stub
	}


	//제품목록 부르기 위한 생성자
	public Dto_Mainview(String pbrand, String pname, int pprice, String fake_filename) {
		super();
		this.pbrand = pbrand;
		this.pname = pname;
		this.pprice = pprice;
		this.fake_filename = fake_filename;
	}


	// Method
	public int getP_seq() {
		return p_seq;
	}

	public void setP_seq(int p_seq) {
		this.p_seq = p_seq;
	}

	public String getPbrand() {
		return pbrand;
	}

	public void setPbrand(String pbrand) {
		this.pbrand = pbrand;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public int getPprice() {
		return pprice;
	}

	public void setPprice(int pprice) {
		this.pprice = pprice;
	}

	public String getPcolor() {
		return pcolor;
	}

	public void setPcolor(String pcolor) {
		this.pcolor = pcolor;
	}

	public int getPqty() {
		return pqty;
	}

	public void setPqty(int pqty) {
		this.pqty = pqty;
	}

	public int getPsize() {
		return psize;
	}

	public void setPsize(int psize) {
		this.psize = psize;
	}


	public String getFake_filename() {
		return fake_filename;
	}


	public void setFake_filename(String fake_filename) {
		this.fake_filename = fake_filename;
	}




}