package com.supermark.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.models.Carrito;
import com.models.Detalle;
import com.models.Producto;
import com.models.Usuario;

public class CRUDCarrito {
	private Conexion conn;
	private String sql;
	
	public CRUDCarrito() {
		super();
		this.conn = new Conexion("Supermark");
		this.conn.Connect();//Abre la conexion
		this.sql = "";
	}
	
	public boolean agregar(Carrito carrito) {//-->Registrar un usuario
		this.sql = "INSERT INTO Carrito "+
				"(id_usuario,id_producto,cantidad) "+
				"VALUE ("+
				carrito.getUsuario().getId()+","+
				carrito.getProducto().getId()+","+
				carrito.getCantidad()+")";
		boolean resultado = false;
		System.out.println(resultado);
		try {
			int filas = conn.getStmt().executeUpdate(sql);
			if(filas>0){
				resultado = true;
			}
			System.out.println("Producto agregado al Carrito");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public ArrayList<Detalle> getListado(Usuario usuario){
		ArrayList<Detalle> detalles = new ArrayList<Detalle>();
		
		this.sql = "SELECT Carrito.id_producto,Carrito.cantidad,producto.precio,producto.Descuento FROM Carrito "+
					"JOIN producto ON producto.id = carrito.id_producto "+
					"WHERE id_usuario = "+usuario.getId();
		
		try {
			ResultSet rs = conn.getStmt().executeQuery(sql);
			while(rs.next()) {
				int id_producto = rs.getInt("id_producto");
				int cantidad = rs.getInt("cantidad");
				float precio = rs.getFloat("precio");
				float descuento = rs.getFloat("Descuento");
				detalles.add(new Detalle(
							new Producto(id_producto,precio,descuento),
							cantidad
						));
				System.out.println(descuento);
				System.out.println(precio);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return detalles;
	}
	
	public boolean vaciar(Usuario usuario){
		boolean resultado = false;
		this.sql = "DELETE FROM Carrito WHERE id_usuario = "+usuario.getId();
		
		try {
			int rows = conn.getStmt().executeUpdate(sql);
			if(rows>0) {
				resultado = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
//	public boolean Contador (Carrito carrito) {
//		int count = 0;
//		boolean resultado = false;
//		this.sql = "SELECT * FROM Carrito WHERE id_usuario ="+
//				carrito.getUsuario().getId();
//		try {
//			conn.setRs(conn.getStmt().executeQuery(sql));
//			
//			while(conn.getRs().next()) {
//				count=count+1;
//			}
//		}
	}