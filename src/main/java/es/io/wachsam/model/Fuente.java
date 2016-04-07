package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="fuente")
public class Fuente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	Long id;
	@Expose
	String nombre;
	@Expose
	String descripcion;
	@Expose
	Integer fiabilidad;
	public Fuente(String id2, String nombre2, String descripcion2,
			String fiabilidad2) {
		try{
		 this.id=Long.parseLong(id2);
		}catch(Exception e){}
		this.nombre=nombre2;
		this.descripcion=descripcion2;
		try{
			this.fiabilidad=Integer.parseInt(fiabilidad2);
	    }catch(Exception e){}
		
	}
	public Fuente() {
		super();
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Fuente [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (nombre != null) {
			builder.append("nombre=");
			builder.append(nombre);
			builder.append(", ");
		}
		if (descripcion != null) {
			builder.append("descripcion=");
			builder.append(descripcion);
			builder.append(", ");
		}
		if (fiabilidad != null) {
			builder.append("fiabilidad=");
			builder.append(fiabilidad);
		}
		builder.append("]");
		return builder.toString();
	}
	public Integer getFiabilidad() {
		return fiabilidad;
	}
	public void setFiabilidad(Integer fiabilidad) {
		this.fiabilidad = fiabilidad;
	}
	
	
	
}
