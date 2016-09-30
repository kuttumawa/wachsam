package es.io.wachsam.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.validator.GenericValidator;
import org.elasticsearch.common.netty.util.internal.StringUtil;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import com.google.gson.annotations.Expose;
@Entity
@Table(name="sitio")
public class Sitio implements ObjetoSistemaIF {
	
	@org.springframework.data.annotation.Id
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	Long id;
	@Expose
	String nombre;
	@Expose
	String nombreEn;
	@Expose
	String direccion;
	@Expose
	@ManyToOne(fetch = FetchType.EAGER)
	TipoSitio tipo;
	@Expose
	String texto;
	@Expose
	String textoEn;
	@Expose
	@ManyToOne(fetch = FetchType.EAGER)
	Lugar lugarObj;
	@Expose
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
		
		return builder.toString().substring(0,Math.min(60,builder.toString().length()))+"]";
		
	}
	
	public  List<String> validate(){
		List<String> errores=new ArrayList<String>();
		if(GenericValidator.isBlankOrNull(nombre)) errores.add("Nombre Obligatorio;");
		if(nombreEn!=null && nombreEn.length()>100) errores.add("Nombre Eng debe se menor de 100");
		if(direccion!=null && direccion.length()>100) errores.add("Dirección debe se menor de 100");
		if(texto!=null && texto.length()>500) errores.add("Texto debe se menor de 500");
		if(textoEn!=null && textoEn.length()>500) errores.add("Text debe se menor de 500");
		if(tipo==null) errores.add("Tipo es Obligatorio");
		return errores;
	}
	
	
	
	public boolean hasPermisos(Usuario usuario, Acciones accion) {
		for (Permiso permiso : usuario.getPermisos()) {
			if (permiso.getObjeto().equalsIgnoreCase(this.getClass().getSimpleName())) {
				if (permiso.getAccion().equals(Acciones.ALL)|| permiso.getAccion().equals(accion)){
					if(permiso.getFiltroFlag()==null || !permiso.getFiltroFlag()) return true;
					else{
						List<Long> filtroTipo =permiso.listOfIdsFromJson("tipo");
						if(this.tipo!=null && filtroTipo.contains(new Long(tipo.getId()))) return true;
					}
				}
			}
			
		}

		return false;
	}
	
	public Node toNode() {
		return new Node(this.id,this.getNombre(),ObjetoSistema.Sitio.ordinal(),ObjetoSistema.Sitio +":"+this.id+"-"+this.getNombre());		
	}

	public static String toCSVCabecera(String separador) {
		StringBuilder builder = new StringBuilder();
		builder.append("id");
		builder.append(separador+"nombre");
		builder.append(separador+"nombreEn");
		builder.append(separador+"direccion");
		builder.append(separador+"tipo");
		builder.append(separador+"texto");
		builder.append(separador+"textoEn");
		builder.append(separador+"lugarObj.nombre");
		builder.append(separador+"lugarObj.latitud");
		builder.append(separador+"lugarObj.longitud");
		builder.append(separador+"valoracion");		
		return builder.toString();		
	}
	public String toCSV(String separador) {
		StringBuilder builder = new StringBuilder();
		builder.append(id);
		builder.append(separador);
		builder.append(nombre);
		builder.append(separador);
		builder.append(nombreEn);
		builder.append(separador);
		builder.append(direccion);
		builder.append(separador);
		builder.append(tipo);
		builder.append(separador);
		builder.append(texto);
		builder.append(separador);
		builder.append(textoEn);
		builder.append(separador);	
		builder.append(lugarObj!=null?lugarObj.getNombre():"");
		builder.append(separador);
		builder.append(lugarObj!=null?lugarObj.getLatitud():"");
		builder.append(separador);
		builder.append(lugarObj!=null?lugarObj.getLongitud():"");
		builder.append(separador);	
		return builder.toString();
	}
	
	

}
