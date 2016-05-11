package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="factor")
public class Factor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	Long id;
	@Expose
	String nombre;
	@Expose
	String nombreEn;
	@Expose
	String texto;
	@Expose
	String textoEn;

	
	public Factor() {
		super();
	}


	public Factor(String id,String nombre, String nombreEn, String texto, String textoEn) {
		super();
		try{
			this.id= Long.parseLong(id.trim());
		}catch(Exception e){
			
		}
		this.nombre = nombre;
		this.nombreEn = nombreEn;
		this.texto = texto;
		this.textoEn = textoEn;
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


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public String getTextoEn() {
		return textoEn;
	}


	public void setTextoEn(String textoEn) {
		this.textoEn = textoEn;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Factor [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", nombreEn=");
		builder.append(nombreEn);
		builder.append(", texto=");
		builder.append(texto);
		builder.append(", textoEn=");
		builder.append(textoEn);
		builder.append("]");
		return builder.toString();
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
