package com.modelo.informacion;

public class Tarea {

private	int id_tarea;
private	String descripcion;
private	String comentario;
private	String fecha_inicio;
private	String fecha_fin;
private int id_empleado;
private String nombre_empleado;
private int id_nivel_tarea;
private String nivel_tarea;
private String archivo_adjunto;
private int id_jefe;
private String estado;
public Tarea() {
	super();
	// TODO Auto-generated constructor stub
}
public Tarea(int id_tarea, String descripcion, String comentario, String fecha_inicio, String fecha_fin,
		int id_empleado, String nombre_empleado, int id_nivel_tarea, String nivel_tarea, String archivo_adjunto,
		int id_jefe, String estado) {
	super();
	this.id_tarea = id_tarea;
	this.descripcion = descripcion;
	this.comentario = comentario;
	this.fecha_inicio = fecha_inicio;
	this.fecha_fin = fecha_fin;
	this.id_empleado = id_empleado;
	this.nombre_empleado = nombre_empleado;
	this.id_nivel_tarea = id_nivel_tarea;
	this.nivel_tarea = nivel_tarea;
	this.archivo_adjunto = archivo_adjunto;
	this.id_jefe = id_jefe;
	this.estado = estado;
}
public int getId_tarea() {
	return id_tarea;
}
public void setId_tarea(int id_tarea) {
	this.id_tarea = id_tarea;
}
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
public String getComentario() {
	return comentario;
}
public void setComentario(String comentario) {
	this.comentario = comentario;
}
public String getFecha_inicio() {
	return fecha_inicio;
}
public void setFecha_inicio(String fecha_inicio) {
	this.fecha_inicio = fecha_inicio;
}
public String getFecha_fin() {
	return fecha_fin;
}
public void setFecha_fin(String fecha_fin) {
	this.fecha_fin = fecha_fin;
}
public int getId_empleado() {
	return id_empleado;
}
public void setId_empleado(int id_empleado) {
	this.id_empleado = id_empleado;
}
public String getNombre_empleado() {
	return nombre_empleado;
}
public void setNombre_empleado(String nombre_empleado) {
	this.nombre_empleado = nombre_empleado;
}
public int getId_nivel_tarea() {
	return id_nivel_tarea;
}
public void setId_nivel_tarea(int id_nivel_tarea) {
	this.id_nivel_tarea = id_nivel_tarea;
}
public String getNivel_tarea() {
	return nivel_tarea;
}
public void setNivel_tarea(String nivel_tarea) {
	this.nivel_tarea = nivel_tarea;
}
public String getArchivo_adjunto() {
	return archivo_adjunto;
}
public void setArchivo_adjunto(String archivo_adjunto) {
	this.archivo_adjunto = archivo_adjunto;
}
public int getId_jefe() {
	return id_jefe;
}
public void setId_jefe(int id_jefe) {
	this.id_jefe = id_jefe;
}
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}


	
}
