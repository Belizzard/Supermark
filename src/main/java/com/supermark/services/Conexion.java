package com.supermark.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Conexion {
	private static final String JDBC_DIRVER = "com.mysql.jdbc.Driver";
	private String DB_URL = "jdbc:mysql://localhost:3306/";

	private static final String USUARIO = "root";
	private static final String CLAVE = "ivan1166";
	
	
	private Connection cn = null;
	private Statement stm = null;
	private ResultSet rs = null;
	
	public Conexion(String db){
		this.DB_URL +=db;
		
	}
	public void Connect() {
		try {
			Class.forName(JDBC_DIRVER);
			cn= DriverManager.getConnection(DB_URL,USUARIO, CLAVE);
			stm= cn.createStatement();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void close () {
		try {
			stm.close();
			cn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public Connection getConn() {
		return cn;
	}
	public void setConn(Connection conn) {
		this.cn = conn;
	}
	public Statement getStmt() {
		return stm;
	}
	public void setStmt(Statement stmt) {
		this.stm = stmt;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
}
