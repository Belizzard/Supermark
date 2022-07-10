package com.supermark.services;


import java.sql.ResultSet;
import java.sql.SQLException;
import com.models.Producto;

public class CRUDProducto {
	private Conexion conn;
	private String sql;
	
	public CRUDProducto() {
		super();
		this.conn = new Conexion("supermark");
		this.conn.Connect();
		this.sql = "";
	}
	public boolean registrar(Producto producto) {
		this.sql = "INSERT INTO Producto "+
				"(nombre,marca,fecha_venc,precio,stock,descripcion,Descuento) "+
				"VALUE ('"+
				producto.getNombre()+"','"+
				producto.getMarca()+"','"+
				producto.getF_venc()+"',"+
				producto.getPrecio()+",'"+
				producto.getStock()+"','"+
				producto.getDescripcion()+"','"+
				producto.getDescuento()+"')";
		boolean resultado = false;
		try {
			conn.getStmt().executeUpdate(sql);
			resultado = true;
			System.out.println("Producto registrado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
}
	public void actualizarStock(Producto producto,int cantidad) {
		this.sql = "UPDATE Producto SET stock="+
				(producto.getStock()+cantidad)+
				" WHERE id="+producto.getId();
		try {
			conn.getStmt().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			System.out.println("Stock Actualizado");
		}
	}
	
	public int getStockActual(Producto producto) {
		int stockActual = 0;
		this.sql = "SELECT stock FROM Producto WHERE id="+producto.getId();
		try {
			conn.setRs(conn.getStmt().executeQuery(sql));
			int count = 0;
			while(conn.getRs().next()) {
				stockActual = conn.getRs().getInt("stock");
			}
			if(count>0) {
				System.out.println("Stock Actualizado");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(stockActual);
		return stockActual;
}
	public Producto getProducto(Integer id) {
		Producto prod = null;
		this.sql = "SELECT * FROM Producto WHERE id ="+id;
		try {
			ResultSet rs = conn.getStmt().executeQuery(sql);
			while (rs.next()) {
				prod = new Producto(
						rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("marca"),
						rs.getDate("fecha_venc"), 
						rs.getFloat("precio"), 
						rs.getInt("stock"),
						rs.getString("descripcion"),
						rs.getFloat("Descuento")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prod;
	}
	public boolean actualizarProducto(Producto producto) {
		this.sql = "UPDATE Supermark.Producto SET nombre ='"+producto.getNombre()+
				"',Producto.marca='"+producto.getMarca()+
				"',Producto.fecha_venc='"+producto.getF_venc()+
				"',Producto.precio="+producto.getPrecio()+
				",Producto.stock='"+producto.getStock()+
				"',Producto.descripcion='"+producto.getDescripcion()+
				"',Producto.Descuento='"+producto.getDescuento()+
				"' WHERE Producto.id="+producto.getId();
		boolean resultado = false;
		try {
			conn.getStmt().executeUpdate(sql);
			resultado = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}