package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;



/**
 * @author 
 * 
 * Data represents a unique data value , defined by tags.
 *
 */
@Entity
@Table(name="data")
public class Data {
	@Expose
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	@Expose
	String value;
	@Expose
    String descripcion;
	@Expose
	DataValueTipo tipoValor;
	@Expose
	@ManyToOne(fetch = FetchType.EAGER)
	Tag tag;
	@Expose
	Long objetoId;
	@Expose
	Long connectToId;
	@Expose
	ObjetoSistema objetoTipo;
	@Expose
	ObjetoSistema objetoConnectedTipo;
	@Expose
	@Transient
	String objetoConnectedTipoString;
	@Expose
	@Transient
	Object connectedObject;
	
	
	
	
	
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
		return objetoConnectedTipo;
	}



	public void setObjetoConnected(ObjetoSistema objetoConnected) {
		this.objetoConnectedTipo = objetoConnected;
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
		if (objetoConnectedTipo != null) {
			builder.append("objetoConnected=");
			builder.append(objetoConnectedTipo);
		}
		builder.append("]");
		return builder.toString();
	}



	public String getObjetoConnectedTipoString() {
		return objetoConnectedTipoString;
	}



	public void setObjetoConnectedTipoString(String objetoConnectedTipoString) {
		this.objetoConnectedTipoString = objetoConnectedTipoString;
	}



	public Object getConnectedObject() {
		return connectedObject;
	}



	public void setConnectedObject(Object connectedObject) {
		this.connectedObject = connectedObject;
	}







	



	
	
	
}
