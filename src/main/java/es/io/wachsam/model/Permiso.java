package es.io.wachsam.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONArray;
import org.json.JSONObject;



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
	String filtro;
	Boolean filtroFlag;
	Acciones accion;

	
	public Permiso() {
		super();
	}

	public Permiso(String nombre,Class objeto, Acciones acciones,boolean filtroFlag,String filtro) {
		super();
		this.nombre = nombre;
		this.objeto = objeto.getName();
		this.accion = acciones;
		this.filtro=filtro;
		this.setFiltroFlag(filtroFlag);
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

	public Acciones getAccion() {
		return accion;
	}

	public void setAccion(Acciones accion) {
		this.accion = accion;
	}

	

	

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Long> listOfIdsFromJson(String name){
		List<Long> res=new ArrayList<Long>();
		if(filtro!=null && filtro.length()>0){
			JSONObject obj = new JSONObject(filtro);
			JSONArray arr = obj.getJSONArray(name);
			for (int i = 0; i < arr.length(); i++)
			    res.add(arr.getLong(i));
		}
		
		return res;
 	}

	public Boolean getFiltroFlag() {
		return filtroFlag;
	}

	public void setFiltroFlag(Boolean filtroFlag) {
		this.filtroFlag = filtroFlag;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Permiso [");
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
		if (objeto != null) {
			builder.append("objeto=");
			builder.append(objeto);
			builder.append(", ");
		}
		if (filtro != null) {
			builder.append("filtro=");
			builder.append(filtro);
			builder.append(", ");
		}
		if (filtroFlag != null) {
			builder.append("filtroFlag=");
			builder.append(filtroFlag);
			builder.append(", ");
		}
		if (accion != null) {
			builder.append("accion=");
			builder.append(accion);
		}
		builder.append("]");
		return builder.toString();
	}

	public String prettyPrint() {
		StringBuilder builder = new StringBuilder();
		if (id != null) {
			builder.append(id);
			builder.append("- ");
		}		
		if (objeto != null) {
			builder.append(objeto);
			builder.append("- ");
		}
		if (accion != null) {
			builder.append(accion);
			
		}
		if (filtro != null) {
			builder.append(" ");
			builder.append(filtro);
			
		}
	
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accion == null) ? 0 : accion.hashCode());
		result = prime * result + ((filtro == null) ? 0 : filtro.hashCode());
		result = prime * result
				+ ((filtroFlag == null) ? 0 : filtroFlag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((objeto == null) ? 0 : objeto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permiso other = (Permiso) obj;
		if (accion != other.accion)
			return false;
		if (filtro == null) {
			if (other.filtro != null)
				return false;
		} else if (!filtro.equals(other.filtro))
			return false;
		if (filtroFlag == null) {
			if (other.filtroFlag != null)
				return false;
		} else if (!filtroFlag.equals(other.filtroFlag))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (objeto == null) {
			if (other.objeto != null)
				return false;
		} else if (!objeto.equals(other.objeto))
			return false;
		return true;
	}
}
