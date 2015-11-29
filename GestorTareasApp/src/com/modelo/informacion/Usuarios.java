package com.modelo.informacion;

public class Usuarios {

	private int id_usuario;
	private int id_tipousuario;
	private String estado;
	private Tipo_Usuario tipo_usuario;
	
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public int getId_tipousuario() {
		return id_tipousuario;
	}
	public void setId_tipousuario(int id_tipousuario) {
		this.id_tipousuario = id_tipousuario;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	public Tipo_Usuario getTipo_usuario() {
		return tipo_usuario;
	}
	public void setTipo_usuario(Tipo_Usuario tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}
	
	
	
	public Usuarios(int id_usuario, int id_tipousuario, String estado, Tipo_Usuario tipo_usuario) {
		super();
		this.id_usuario = id_usuario;
		this.id_tipousuario = id_tipousuario;
		this.estado = estado;
		this.tipo_usuario = tipo_usuario;
	}
	public Usuarios() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
