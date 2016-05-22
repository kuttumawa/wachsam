package es.io.wachsam.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * @author 
 * 
 * Data represents a unique data value , defined by tags.
 *
 */
@Entity
@Table(name="data")
public class DataN {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	String value;
    String descripcion;
	DataValueTipo tipoValor;
	@ManyToOne(fetch = FetchType.EAGER)
	Tag tag;
	Long objetoId;
	Long connectToId;
	ObjetoSistema objetoTipo;
	ObjetoSistema objetoConnected;
	
	
	
	public boolean hasPermisos(Usuario usuario, Acciones accion) {
		for (Permiso permiso : usuario.getPermisos()) {
			if (permiso.getObjeto().equalsIgnoreCase(this.getClass().getSimpleName())) {
				if (permiso.getAccion().equals(Acciones.ALL)|| permiso.getAccion().equals(accion)){
					if(permiso.getFiltroFlag()==null || !permiso.getFiltroFlag()) return true;
					else{
						boolean resultado1 = false,resultado2 = false;
							
						return resultado1 && resultado2;
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



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public DataValueTipo getTipoValor() {
		return tipoValor;
	}



	public void setTipoValor(DataValueTipo tipoValor) {
		this.tipoValor = tipoValor;
	}



	public Tag getTag() {
		return tag;
	}



	public void setTag(Tag tag) {
		this.tag = tag;
	}



	public Long getObjetoId() {
		return objetoId;
	}



	public void setObjetoId(Long objetoId) {
		this.objetoId = objetoId;
	}



	public Long getConnectToId() {
		return connectToId;
	}



	public void setConnectToId(Long connectToId) {
		this.connectToId = connectToId;
	}



	public ObjetoSistema getObjetoTipo() {
		return objetoTipo;
	}



	public void setObjetoTipo(ObjetoSistema objetoTipo) {
		this.objetoTipo = objetoTipo;
	}



	public ObjetoSistema getObjetoConnected() {
		return objetoConnected;
	}



	public void setObjetoConnected(ObjetoSistema objetoConnected) {
		this.objetoConnected = objetoConnected;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataN [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (value != null) {
			builder.append("value=");
			builder.append(value);
			builder.append(", ");
		}
		if (descripcion != null) {
			builder.append("descripcion=");
			builder.append(descripcion);
			builder.append(", ");
		}
		if (tipoValor != null) {
			builder.append("tipoValor=");
			builder.append(tipoValor);
			builder.append(", ");
		}
		if (tag != null) {
			builder.append("tag=");
			builder.append(tag);
			builder.append(", ");
		}
		if (objetoId != null) {
			builder.append("objetoId=");
			builder.append(objetoId);
			builder.append(", ");
		}
		if (connectToId != null) {
			builder.append("connectToId=");
			builder.append(connectToId);
			builder.append(", ");
		}
		if (objetoTipo != null) {
			builder.append("objetoTipo=");
			builder.append(objetoTipo);
			builder.append(", ");
		}
		if (objetoConnected != null) {
			builder.append("objetoConnected=");
			builder.append(objetoConnected);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}
