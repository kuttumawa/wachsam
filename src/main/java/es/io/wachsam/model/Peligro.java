package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import com.google.gson.annotations.Expose;
@Document(indexName = "peligro",type = "peligro" , shards = 1, replicas = 0, indexStoreType = "memory", refreshInterval = "-1")
@Entity
@Table(name="peligro")
public class Peligro {
	
	@org.springframework.data.annotation.Id
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	Long id;
	@Field(index = FieldIndex.not_analyzed)
	@Expose
	String nombre;
	@Expose
	String nombreEn;
	@Expose
	CategoriaPeligro categoria;
	@Expose
	Integer damage;
	
	public Peligro() {
		super();
	}
	
	public Peligro(Long id, String nombre, String nombreEn,
			CategoriaPeligro categoria, Integer damage) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreEn = nombreEn;
		this.categoria = categoria;
		this.damage = damage;
	}

	public Peligro(String id, String nombre, String nombreEn,
			String categoria, String damage) {
		try{
			this.id= Long.parseLong(id.trim());
		}catch(Exception e){
			
		}
		this.nombre = nombre.trim();
		this.nombreEn = nombreEn.trim();
		if(categoria.equalsIgnoreCase(CategoriaPeligro.enfermedad.name())){
			this.categoria = CategoriaPeligro.enfermedad;
		}else if(categoria.equalsIgnoreCase(CategoriaPeligro.accidentes.name())){
			this.categoria = CategoriaPeligro.accidentes;
		}else if(categoria.equalsIgnoreCase(CategoriaPeligro.conflicto.name())){
			this.categoria = CategoriaPeligro.conflicto;
		}else if(categoria.equalsIgnoreCase(CategoriaPeligro.naturaleza.name())){
			this.categoria = CategoriaPeligro.naturaleza;
		}else if(categoria.equalsIgnoreCase(CategoriaPeligro.violencia.name())){
			this.categoria = CategoriaPeligro.violencia;
		}else{
			this.categoria = CategoriaPeligro.otros;
		}
		
		try{
			this.damage= Integer.parseInt(damage.trim());
		}catch(Exception e){
			
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
	public CategoriaPeligro getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaPeligro categoria) {
		this.categoria = categoria;
	}
	public Integer getDamage() {
		return damage;
	}
	public void setDamage(Integer damage) {
		this.damage = damage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Peligro [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (nombre != null)
			builder.append("nombre=").append(nombre).append(", ");
		if (nombreEn != null)
			builder.append("nombreEn=").append(nombreEn).append(", ");
		if (categoria != null)
			builder.append("categoria=").append(categoria).append(", ");
		if (damage != null)
			builder.append("damage=").append(damage);
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
	
	public Node toNode() {
		return new Node(this.id,this.getNombre(),ObjetoSistema.Peligro.ordinal());		
	}

}
