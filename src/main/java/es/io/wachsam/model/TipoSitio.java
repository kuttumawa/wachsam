package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="tipoSitio")
public class TipoSitio {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	Long id;
	@Expose
	String nombre;
	@Expose
	String descripcion;

	
	public TipoSitio() {
		super();
	}


	public TipoSitio(String id,String nombre, String descripcion) {
		super();
		try{
			this.id= Long.parseLong(id.trim());
		}catch(Exception e){
			
		}
		this.nombre = nombre;
		this.descripcion=descripcion;
	}
	public TipoSitio(Long id,String nombre, String descripcion) {
		this.setId(id);
		this.nombre = nombre;
		this.descripcion=descripcion;
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
	
	
	public boolean hasPermisos(Usuario usuario, Acciones accion) {
		for (Permiso permiso : usuario.getPermisos()) {
			if (permiso.getObjeto().equalsIgnoreCase(this.getClass().getSimpleName())) {
				if (permiso.getAccion().equals(Acciones.ALL)|| permiso.getAccion().equals(accion)){
					if(permiso.getFiltroFlag()==null || !permiso.getFiltroFlag()) return true;
					else{
						 return true;
					}
				}
			}
			
		}

		return false;
	}


	
}
