package com.supermark.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.models.Comprobante;
import com.models.Detalle;
import com.models.Usuario;


public class CRUDComprobante {
	private Conexion conn;
	private String sql;
	
	public CRUDComprobante() {
		super();
		this.conn = new Conexion("Supermark");
		this.conn.Connect();
		this.sql = "";
	}
	
	public boolean registrarCompra(Comprobante comprobante) {
		boolean resultado = false;
		CRUDCarrito ccarr = new CRUDCarrito();
		ArrayList<Detalle> detalles = ccarr.getListado(comprobante.getDestinatario());
		comprobante.setDetalles(detalles);
		float total = 0;
		for(Detalle detalle:detalles) {
			total += (float)detalle.getProducto().getPrecio()*(float)detalle.getCantidad()*detalle.getProducto().getDescuento();
			
		}
		System.out.println(total);
		this.sql = "INSERT INTO Comprobante "+
				"(tipo,fecha,id_usuario,id_tc,total) "+
				"VALUE ('"+
				comprobante.getTipo()+"','"+
				comprobante.getFecha()+"',"+
				comprobante.getDestinatario().getId()+","+
				comprobante.getTarjeta().getNumero()+","+
				total+")";
		try {
			PreparedStatement stmt = conn.getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int filas = stmt.executeUpdate();
			if(filas>0) {
				resultado = true;
				ResultSet rs = stmt.getGeneratedKeys();
				while(rs.next()) {//Asignamos al comprobante el id generado en MySQL
					comprobante.setId(rs.getInt(1));
				}
			}
			agregarDetallesAComprobante(detalles,comprobante.getId());
			ccarr.vaciar(comprobante.getDestinatario());
			System.out.println("Comprobante registrado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	private void agregarDetallesAComprobante(ArrayList<Detalle> detalles,Integer id_comprobante) {
		CRUDDetalle cd = new CRUDDetalle();
		CRUDProducto cp = new CRUDProducto();
		for(Detalle det:detalles) {
			int stockActual = cp.getStockActual(det.getProducto());
			det.getProducto().setStock(stockActual);
			int stockProducto = det.getProducto().getStock();
			int cantidadProducto = det.getCantidad();
			if(stockProducto-cantidadProducto>=0) {
				cd.registrarDetalle(det,id_comprobante);
				cp.actualizarStock(det.getProducto(), -det.getCantidad());
			}else {		
				System.out.println("No se dispone del stock necesario para realizar esta venta");
			}
		}
	}
	
	public ArrayList<Comprobante> getComprobantes(Usuario usuario) {
		ArrayList<Comprobante> comprobantes = new ArrayList<Comprobante>();
		this.sql = "SELECT * FROM comprobante WHERE id_usuario ="+
					usuario.getId();
		try {
			conn.setRs(conn.getStmt().executeQuery(sql));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			System.out.println("Comprobantes del Usuario:");
			ResultSet rs = conn.getRs();
			try {
				CRUDDetalle cd = new CRUDDetalle();
				while (rs.next()) {
					Comprobante comp = new Comprobante();
					comp.setId(rs.getInt("id"));
					comp.setTipo(rs.getString("tipo"));
					comp.setFecha(rs.getTimestamp("fecha"));
					comp.setDetalles(cd.getLineasDetalle(comp));
					
					comprobantes.add(comp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return comprobantes;
	}
}