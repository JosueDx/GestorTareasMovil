package com.modelo.informacion;

public class TareaRealizadaE {

private int id;
private int idTarea;
private String descripcion;
private String fechafin;
private String estado;
public TareaRealizadaE(int id, int idTarea, String descripcion, String fechafin, String estado) {
	super();
	this.id = id;
	this.idTarea = idTarea;
	this.descripcion = descripcion;
	this.fechafin = fechafin;
	this.estado = estado;
}
public TareaRealizadaE() {
	super();
	// TODO Auto-generated constructor stub
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getIdTarea() {
	return idTarea;
}
public void setIdTarea(int idTarea) {
	this.idTarea = idTarea;
}
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
public String getFechafin() {
	return fechafin;
}
public void setFechafin(String fechafin) {
	this.fechafin = fechafin;
}
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}
	
	
}
