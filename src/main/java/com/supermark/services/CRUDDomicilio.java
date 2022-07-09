package com.supermark.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.models.Domicilio;

public class CRUDDomicilio {
	private Conexion conn;
	private String sql;
	
	public CRUDDomicilio() {
		super();
		this.conn = new Conexion("Supermark");
		this.conn.Connect();
		this.sql = "";
	}
	public Domicilio register(Domicilio dom) {
		this.sql = "INSERT INTO Domicilio "+
				"(calle,numero,depNumero,piso) "+
				"VALUE ('"+
				dom.getCalle()+"',"+
				dom.getNumero()+","+
				dom.getDepNumero()+","+
				dom.getPiso()+")";
		try {
			PreparedStatement stmt = conn.getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int filas = stmt.executeUpdate();
			if(filas>0) {
				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next()) {
					dom.setId(rs.getInt(1));
				}
			}
			System.out.println("Domicilio registrado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dom;
}
	
	public Domicilio getDomicilio(Integer id) {
		this.sql = "SELECT calle,numero,depNumero,piso FROM domicilio "+
				"WHERE domicilio.id="+id;
		Domicilio dom = null;
		try {
			conn.setRs(conn.getStmt().executeQuery(sql));
			dom = new Domicilio(
						id,
						conn.getRs().getString("calle"),
						conn.getRs().getInt("numero"),
						conn.getRs().getInt("depNumero"),
						conn.getRs().getInt("piso")
						);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dom;
	}
}