package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Lugar {
	@Id
	Long id;
	
	String nombre;
	String nombreEn;
	@ManyToOne(fetch = FetchType.EAGER)
	Lugar padre1;
	@ManyToOne(fetch = FetchType.EAGER)
	Lugar padre2;
	@ManyToOne(fetch = FetchType.EAGER)
	Lugar padre3;
	String latitud;
	String longitud;
	Integer nivel;
	
	public Lugar(){
		super();
	}
	
	public Lugar(Long id, String nombre, String nombreEn, Lugar padre1,
			Lugar padre2, Lugar padre3, String latitud, String longitud,
			Integer nivel) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreEn = nombreEn;
		this.padre1 = padre1;
		this.padre2 = padre2;
		this.padre3 = padre3;
		this.latitud = latitud;
		this.longitud = longitud;
		this.nivel = nivel;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreEn() {
		return nombreEn;
	}
	public void setNombreEn(String nombreEn) {
		this.nombreEn = nombreEn;
	}
	public Lugar getPadre1() {
		return padre1;
	}
	public void setPadre1(Lugar padre1) {
		this.padre1 = padre1;
	}
	public Lugar getPadre2() {
		return padre2;
	}
	public void setPadre2(Lugar padre2) {
		this.padre2 = padre2;
	}
	public Lugar getPadre3() {
		return padre3;
	}
	public void setPadre3(Lugar padre3) {
		this.padre3 = padre3;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public Integer getNivel() {
		return nivel;
	}
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Lugar [id=").append(id).append(", nombre=")
				.append(nombre).append(", nombreEn=").append(nombreEn)
				.append(", padre1=").append(padre1).append(", padre2=")
				.append(padre2).append(", padre3=").append(padre3)
				.append(", latitud=").append(latitud).append(", longitud=")
				.append(longitud).append(", nivel=").append(nivel).append("]");
		return builder.toString();
	}
	

}
