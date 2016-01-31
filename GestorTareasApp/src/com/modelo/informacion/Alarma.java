package com.modelo.informacion;

public class Alarma {

	private int id;
	private String hora;
	
	
	public Alarma(int id, String hora) {
		super();
		this.id = id;
		this.hora = hora;
	}
	public Alarma() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	
	
	
}
