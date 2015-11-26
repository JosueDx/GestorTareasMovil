package com.modelo.informacion;

public class TipoUsuario {

	private String User;
	private String Password;
	private Integer idPersona;
	private Persona persona;
	private String estado;
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public Integer getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public TipoUsuario(String user, String password, Integer idPersona, Persona persona, String estado) {
		super();
		User = user;
		Password = password;
		this.idPersona = idPersona;
		this.persona = persona;
		this.estado = estado;
	}
	public TipoUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
