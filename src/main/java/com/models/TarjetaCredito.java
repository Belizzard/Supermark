package com.models;

import java.sql.Date;

public class TarjetaCredito {
	private Integer numero;
	private String banco;
	private String titular;
	private Date fecha_caducidad;
	private Usuario id_usuario;
	
	public TarjetaCredito(Integer numero) {
		super();
		this.numero = numero;
	}

	public TarjetaCredito(Integer numero, String banco, String titular, Date fecha_caducidad, Usuario id_usuario) {
		super();
		this.numero = numero;
		this.banco = banco;
		this.titular = titular;
		this.fecha_caducidad = fecha_caducidad;
		this.id_usuario = id_usuario;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public Date getFecha_caducidad() {
		return fecha_caducidad;
	}

	public void setFecha_caducidad(Date fecha_caducidad) {
		this.fecha_caducidad = fecha_caducidad;
	}

	public Usuario getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Usuario id_usuario) {
		this.id_usuario = id_usuario;
	}
}