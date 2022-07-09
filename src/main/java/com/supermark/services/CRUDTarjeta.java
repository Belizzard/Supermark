package com.supermark.services;

import java.sql.SQLException;
import com.models.TarjetaCredito;

public class CRUDTarjeta {
	private Conexion conn;
	private String sql;
	
	public CRUDTarjeta() {
		super();
		this.conn = new Conexion("Supermark");
		this.conn.Connect();
		this.sql = "";
	}
	public boolean register(TarjetaCredito tar) {
		this.sql = "INSERT INTO TarjetaCredito "+
				"(numero,banco,titular,fecha_caducidad,id_usuario) "+
				"VALUE ('"+
				tar.getNumero()+"','"+
				tar.getBanco()+"','"+
				tar.getTitular()+"','"+
				tar.getFecha_caducidad()+"',"+
				tar.getId_usuario().getId()+")";
		boolean resultado = false;
		try {
			conn.getStmt().executeUpdate(sql);
			resultado = true;
			System.out.println("Descuento registrado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
