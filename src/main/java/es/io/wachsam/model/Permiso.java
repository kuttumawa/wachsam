package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * @author 
 * 
 *
 *
 */
@Entity
@Table(name="permiso")
public class Permiso {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	String nombre;
	String objeto;
	AccionesSobreObjetosTipos accion;

	
	public Permiso() {
		super();
	}

	public Permiso(String nombre,Class objeto, AccionesSobreObjetosTipos acciones) {
		super();
		this.nombre = nombre;
		this.objeto = objeto.getName();
		this.accion = acciones;
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
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public AccionesSobreObjetosTipos getAccion() {
		return accion;
	}

	public void setAccion(AccionesSobreObjetosTipos accion) {
		this.accion = accion;
	}

	public String prettyPrint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Permiso [id=");
		builder.append(id);
		builder.append(", objeto=");
		builder.append(objeto);
		builder.append(", accion=");
		builder.append(accion);
		builder.append("]");
		return builder.toString();
	}

}
