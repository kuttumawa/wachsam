package es.io.wachsam.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.validator.GenericValidator;

import com.google.gson.annotations.Expose;



/**
 * @author 
 * 
 *
 */
@Entity
@Table(name="recurso")
public class Recurso {
	@Expose
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	@Expose
	String nombre;
	@Expose
	@Transient
	String s3Key;
	@Expose
	String s3Bucket;
	@Expose
	Boolean s3Publico;
	@Expose
	String descripcion;
	
	/**
	 * Media type
	 */
	@Expose
	String formato;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "creador_id")
	Usuario creador;
	@Expose
	String  uri;
	@Expose
	Date fecha;
	@Expose
	long size;
	
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

	public String getS3Key() {
		return nombre +"_###_"+ id;
	}

	public void setS3Key(String s3Key) {
		this.s3Key = s3Key;
	}

	public String getS3Bucket() {
		return s3Bucket;
	}

	public void setS3Bucket(String s3Bucket) {
		this.s3Bucket = s3Bucket;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Date getFechaCreacion() {
		return fecha;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fecha = fechaCreacion;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Boolean getS3Publico() {
		return s3Publico;
	}

	public void setS3Publico(Boolean s3Publico) {
		this.s3Publico = s3Publico;
	}
	
	public  List<String> validate(){
		List<String> errores=new ArrayList<String>();
		if(GenericValidator.isBlankOrNull(nombre)) errores.add("Nombre Obligatorio;");
		if(nombre!=null && nombre.length()>100) errores.add("Nombre  debe se menor de 100");
		if(GenericValidator.isBlankOrNull(formato)) errores.add("Formato Obligatorio;");
		return errores;
	}

	/**
	 *https://en.wikipedia.org/wiki/Dublin_Core
	 */
	public Map<String, String> toMetadata() {
		Map<String,String> userMetadata=new HashMap<String,String>();
		userMetadata.put("DC.Language","");
		userMetadata.put("DC.Date",fecha.toString());
		userMetadata.put("DC.Format",formato);
		userMetadata.put("DC.Title",formato);
		userMetadata.put("DC.Subject",descripcion);
		userMetadata.put("DC.Creator",creador.getLogin());
		return userMetadata;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Recurso [");
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
		if (s3Key != null) {
			builder.append("s3Key=");
			builder.append(s3Key);
			builder.append(", ");
		}
		if (s3Bucket != null) {
			builder.append("s3Bucket=");
			builder.append(s3Bucket);
			builder.append(", ");
		}
		if (s3Publico != null) {
			builder.append("s3Publico=");
			builder.append(s3Publico);
			builder.append(", ");
		}
		if (descripcion != null) {
			builder.append("descripcion=");
			builder.append(descripcion);
			builder.append(", ");
		}
		if (formato != null) {
			builder.append("formato=");
			builder.append(formato);
			builder.append(", ");
		}
		if (creador != null) {
			builder.append("creador=");
			builder.append(creador);
			builder.append(", ");
		}
		if (uri != null) {
			builder.append("uri=");
			builder.append(uri);
			builder.append(", ");
		}
		if (fecha != null) {
			builder.append("fecha=");
			builder.append(fecha);
			builder.append(", ");
		}
		builder.append("size=");
		builder.append(size);
		builder.append("]");
		return builder.toString();
	}

	
	
}
