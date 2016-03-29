package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import com.google.gson.annotations.Expose;
@Entity
@Table(name="sitio")
public class Sitio {
	
	@org.springframework.data.annotation.Id
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	Long id;
	
	String nombre;
	String nombreEn;
	String direccion;
	TipoSitio tipo;
	String texto;
	String textoEn;
	@ManyToOne(fetch = FetchType.EAGER)
	Lugar lugarObj;
	Integer valoracion;
	
	
	
	public Sitio() {
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



	public String getNombreEn() {
		return nombreEn;
	}



	public void setNombreEn(String nombreEn) {
		this.nombreEn = nombreEn;
	}



	public TipoSitio getTipo() {
		return tipo;
	}



	public void setTipo(TipoSitio tipo) {
		this.tipo = tipo;
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



	public Lugar getLugarObj() {
		return lugarObj;
	}



	public void setLugarObj(Lugar lugarObj) {
		this.lugarObj = lugarObj;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public Integer getValoracion() {
		return valoracion;
	}



	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}



	
	public String prettyPrint() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sitio [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", valoracion=");
		builder.append(valoracion);
		builder.append("]");
		return builder.toString();
	}
	
	

	

}
