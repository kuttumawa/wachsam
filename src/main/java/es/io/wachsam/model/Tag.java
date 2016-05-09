package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * @author 
 * 
 * A tag is conceptual representation.
 * i.e: deaths, max_temperature, prevalence.
 *
 */
@Entity
@Table(name="tag")
public class Tag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	String alias;
	String nombre;
	String nombreEn;
	String descripcion;

	
	public static Tag createTag(Long id){
		Tag tag=new Tag();
		tag.setId(id);
		return tag;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tag [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", nombreEn=");
		builder.append(nombreEn);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append("]");
		return builder.toString();
	}
	public Object minimalPrint() {
		StringBuilder builder = new StringBuilder();
		builder.append(id);
		builder.append("-");
		builder.append(nombre);
		return builder.toString();
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public boolean hasPermisos(Usuario usuario, AccionesSobreObjetosTipos accion) {
		for (Permiso permiso : usuario.getPermisos()) {
			if (permiso.getObjeto().equalsIgnoreCase(this.getClass().getSimpleName())) {
				if (permiso.getAccion().equals(AccionesSobreObjetosTipos.ALL)|| permiso.getAccion().equals(accion)){
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
