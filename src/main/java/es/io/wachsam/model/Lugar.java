package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="lugar")
public class Lugar {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	Long id;
	@Expose
	String nombre;
	@Expose
	String nombreEn;
	@ManyToOne(fetch = FetchType.EAGER)
	Lugar padre1;
	@ManyToOne(fetch = FetchType.EAGER)
	Lugar padre2;
	@ManyToOne(fetch = FetchType.EAGER)
	Lugar padre3;
	@Expose
	String latitud;
	@Expose
	String longitud;
	@Expose
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
	
	public Lugar(String id, String nombre, String nombreEn, String padre1,
			String padre2, String padre3, String latitud, String longitud,
			String nivel) {
		super();
		try{
			this.id= Long.parseLong(id.trim());
		}catch(Exception e){
			
		}
		this.nombre = nombre;
		this.nombreEn = nombreEn;
		if(padre1!=null && padre1.length()>0){
			try{
				Lugar lugar=new Lugar();
				lugar.setId( Long.parseLong(padre1.trim()));
				this.padre1=lugar;
			}catch(Exception e){
				//void
			}
		}
		if(padre2!=null && padre2.length()>0){
			try{
				Lugar lugar=new Lugar();
				lugar.setId( Long.parseLong(padre2.trim()));
				this.padre2=lugar;
			}catch(Exception e){
				//void
			}
		}
		if(padre3!=null && padre3.length()>0){
			try{
				Lugar lugar=new Lugar();
				lugar.setId( Long.parseLong(padre3.trim()));
				this.padre3=lugar;
			}catch(Exception e){
				//void
			}
		}
		this.latitud = latitud;
		this.longitud = longitud;
		try{
			this.nivel = Integer.parseInt(nivel.trim());
		}catch(Exception e){
			//void
		}
		
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
		builder.append("Lugar [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (nombre != null)
			builder.append("nombre=").append(nombre).append(", ");
		if (nombreEn != null)
			builder.append("nombreEn=").append(nombreEn).append(", ");
		if (padre1 != null)
			builder.append("padre1=").append(padre1).append(", ");
		if (padre2 != null)
			builder.append("padre2=").append(padre2).append(", ");
		if (padre3 != null)
			builder.append("padre3=").append(padre3).append(", ");
		if (latitud != null)
			builder.append("latitud=").append(latitud).append(", ");
		if (longitud != null)
			builder.append("longitud=").append(longitud).append(", ");
		if (nivel != null)
			builder.append("nivel=").append(nivel);
		builder.append("]");
		return builder.toString();
	}
	

}
