package com.supermark.services;

import java.sql.SQLException;

import com.models.Domicilio;
import com.models.Usuario;

public class CRUDusuario {
	private Conexion conn;
	private String sql;
	
	public CRUDusuario() {
		super();
		this.conn = new Conexion ("Supermark") ;
		this.conn.Connect();
		this.sql = "";
	}

	public boolean registrar(Usuario usuario) {
		CRUDDomicilio cdom = new CRUDDomicilio();
		Domicilio dom = cdom.register(usuario.getDomicilio());
		usuario.setDomicilio(dom);
		this.sql = "INSERT INTO Usuario "+
				"(nombre,apellido,email,dni,contrasenia,id_domicilio) "+
				"VALUE ('"+
				usuario.getNombre()+"','"+
				usuario.getApellido()+"','"+
				usuario.getEmail()+"',"+
				usuario.getDni()+",'"+
				usuario.getContrasenia()+"',"+
				usuario.getDomicilio().getId()+")";
		boolean resultado = false;
		try {
			conn.getStmt().executeUpdate(sql);
			resultado = true;
			System.out.println("Usuario registrado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
}
	public boolean Verificacion_Reg(Usuario usua) {
		this.sql = "SELECT * FROM Usuario WHERE Usuario.email='"+usua.getEmail()+"'";
		boolean resultado = false;
		try{
			conn.setRs(conn.getStmt().executeQuery(sql));
			while(conn.getRs().next()) {
				resultado = true;
		}
		}catch (SQLException e){
			e.printStackTrace();
	}
		return resultado;
}
	public boolean Iniciarsesion (Usuario usuario) {
		this.sql = "SELECT * FROM Usuario WHERE Usuario.email ='"+ usuario.getEmail()+
		"' AND Usuario.Contrasenia = '"+usuario.getContrasenia()+"'";
		boolean resultado = false;
		try {
			conn.setRs(conn.getStmt().executeQuery(sql));
			while(conn.getRs().next()) {
				resultado = true;
			}
		}catch(SQLException e){		
			e.printStackTrace();
		 }
	  return resultado;
	}
	public Usuario getUsuario(Integer id) {
		this.sql="SELECT * FROM Usuario" +
				"WHERE Usuario.id="+id;
		Usuario usr=null;
		try {
			conn.setRs(conn.getStmt().executeQuery(sql));
			usr =new Usuario(
					id
					);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return usr;
	} 
}
