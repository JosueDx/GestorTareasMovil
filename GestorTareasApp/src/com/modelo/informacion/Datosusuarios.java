package com.modelo.informacion;

public class Datosusuarios {

	private int id_datosusuarios;
	private int id_usuario;
	private String usuario;
	private String clave;
	private String estado;
	private Usuarios usuarios;
	
	
	public int getId_datosusuarios() {
		return id_datosusuarios;
	}
	public void setId_datosusuarios(int id_datosusuarios) {
		this.id_datosusuarios = id_datosusuarios;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	public Usuarios getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}
	
	public Datosusuarios(int id_datosusuarios, int id_usuario, String usuario, String clave, String estado,
			Usuarios usuarios) {
		super();
		this.id_datosusuarios = id_datosusuarios;
		this.id_usuario = id_usuario;
		this.usuario = usuario;
		this.clave = clave;
		this.estado = estado;
		this.usuarios = usuarios;
	}
	public Datosusuarios() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
