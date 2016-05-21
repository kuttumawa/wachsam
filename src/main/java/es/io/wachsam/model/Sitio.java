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
	public Sitio(String[] csv) {
		this.nombre=csv[0];
		this.nombreEn=csv[1];
		this.direccion=csv[2];
		this.tipo=TipoSitio.values()[Integer.parseInt(csv[3])];
		this.texto=csv[4];
		this.textoEn=csv[5];
		if(csv[6]!=null && csv[6].length()>1){
			Lugar lugar=new Lugar();
			lugar.setId(Long.parseLong(csv[6]));
		}
		if(csv[7]!=null) this.valoracion=Integer.parseInt(csv[7]);
		else this.valoracion=-1;
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
		
		return builder.toString().substring(0,Math.min(70,builder.toString().length()))+"]";
		
	}
	
	public  List<String> validate(){
		List<String> errores=new ArrayList<String>();
		if(GenericValidator.isBlankOrNull(nombre)) errores.add("Nombre Obligatorio;");
		if(nombreEn!=null && nombreEn.length()>100) errores.add("Nombre Eng debe se menor de 100");
		if(direccion!=null && direccion.length()>100) errores.add("Dirección debe se menor de 100");
		if(texto!=null && texto.length()>500) errores.add("Texto debe se menor de 500");
		if(textoEn!=null && textoEn.length()>500) errores.add("Text debe se menor de 500");
	   
		return errores;
	}
	
	/**
	 * @param csv [nommbre;nombreEn;direccion;tipo;texto;textoEn;lugarId*;valoracion]
	 * 
	 * *lugarId: puede ser numérico , o String si string se intentará encontra un lugar con la proximidad textual menor.
	 * @return
	 */
	public static List<String> validateCSVLine(String csvLine){
		String CSV_SEPARATOR=";";
		int CSVLINE_ELEMENTS=8;
		String[] csv=csvLine.split(CSV_SEPARATOR);
		List<String> errores=new ArrayList<String>();
		if(csv.length != CSVLINE_ELEMENTS){
			errores.add("Debe contener 8 elementos separados por ';'");
			return errores;
		}
	
		//Nombre
		if(GenericValidator.isBlankOrNull(csv[0])) errores.add("Nombre Obligatorio;");
		
		//NombreEn
		if(csv[1]!=null && csv[1].length()>100) errores.add("Nombre Eng debe ser menor de 100");

		//direccion
		if(csv[2]!=null && csv[2].length()>100) errores.add("Dirección debe ser menor de 100");
		
		//tipo
		if(GenericValidator.isBlankOrNull(csv[3])) errores.add("Tipo Sitio Obligatorio;");
		else if(GenericValidator.isInt(csv[3])){
			try{
				TipoSitio tipo=TipoSitio.values()[Integer.parseInt(csv[3])];
			}catch(Exception e){
				errores.add("Tipo Sitio no es correcto valores " + TipoSitio.values());
			}
		}else{
			errores.add("Tipo Sitio no es correcto valores " + Arrays.toString(TipoSitio.values()));
		}
		
		//texto
		if(csv[4]!=null && csv[4].length()>500) errores.add("Texto debe ser menor de 500");
		//textoEn
		if(csv[5]!=null && csv[5].length()>500) errores.add("Text debe ser menor de 500");
		//lugar
		if(csv[6]!=null && csv[6].length()>100) errores.add("Lugar debe  ser menor de 100");
		
		//valoracion
	    if(csv[7]!=null && !GenericValidator.isInt(csv[7])) errores.add("Valoracion debe  ser númerico");
	    else if(csv[7]!=null && !csv[7].matches("-?[12345]")) errores.add("Valoracion debe ser 1,2,3,4,5");
	   
		return errores;
	}
	
	public boolean hasPermisos(Usuario usuario, Acciones accion) {
		for (Permiso permiso : usuario.getPermisos()) {
			if (permiso.getObjeto().equalsIgnoreCase(this.getClass().getSimpleName())) {
				if (permiso.getAccion().equals(Acciones.ALL)|| permiso.getAccion().equals(accion)){
					if(permiso.getFiltroFlag()==null || !permiso.getFiltroFlag()) return true;
					else{
						List<Long> filtroTipo =permiso.listOfIdsFromJson("tipo");
						if(this.tipo!=null && filtroTipo.contains(new Long(this.tipo.ordinal()))) return true;
					}
				}
			}
			
		}

		return false;
	}
	
	

}
