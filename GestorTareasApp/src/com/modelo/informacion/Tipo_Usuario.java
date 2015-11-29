package com.modelo.informacion;

public class Tipo_Usuario {

	int id_tipo_usuario;
	String descripcion;
	
	public int getId_tipo_usuario() {
		return id_tipo_usuario;
	}
	public void setId_tipo_usuario(int id_tipo_usuario) {
		this.id_tipo_usuario = id_tipo_usuario;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Tipo_Usuario(int id_tipo_usuario, String descripcion) {
		super();
		this.id_tipo_usuario = id_tipo_usuario;
		this.descripcion = descripcion;
	}
	public Tipo_Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
