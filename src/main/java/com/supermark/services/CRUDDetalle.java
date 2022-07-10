package com.supermark.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.models.Comprobante;
import com.models.Detalle;
import com.models.Producto;


public class CRUDDetalle {
	private Conexion conn;
	private String sql;
	
	public CRUDDetalle() {
		super();
		this.conn = new Conexion("Supermark");
		this.conn.Connect();
		this.sql = "";
	}
	
	public ArrayList<Detalle> getLineasDetalle(Comprobante comp){
		ArrayList<Detalle> detalles = new ArrayList<Detalle>();
		this.sql = "SELECT * FROM Detalle WHERE id_comprobante ="+
				comp.getId();
		ResultSet rs;
		try {
			rs = conn.getStmt().executeQuery(sql);
			CRUDProducto cp = new CRUDProducto();
			while (rs.next()) {
				Producto prod = cp.getProducto(rs.getInt("id_producto"));
				Detalle linea = new Detalle(prod, rs.getInt("cantidad"));
				detalles.add(linea);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return detalles;
	}
	
	public void registrarDetalle(Detalle detalle,Integer id_comprobante) {
		this.sql = "INSERT INTO Detalle "+
				"(id_comprobante,id_producto,cantidad) "+
				"VALUE ("+
				id_comprobante+","+
				detalle.getProducto().getId()+","+
				detalle.getCantidad()+")";
		try {
			conn.getStmt().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			System.out.println("Linea de Detalle agregada");
		}
	}
}