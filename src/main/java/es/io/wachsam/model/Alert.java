package es.io.wachsam.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.elasticsearch.annotations.Document;

import com.google.gson.annotations.Expose;

@Document(indexName = "alertas", type = "alerta", shards = 1, replicas = 0, indexStoreType = "memory", refreshInterval = "-1")
@Entity
@Table(name = "alert")
public class Alert {
	@org.springframework.data.annotation.Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	Long id;
	@Expose
	String nombre;
	@Expose
	String tipo;
	@Column(length = 500)
	@Expose
	String link1;
	@Column(length = 500)
	@Expose
	String link2;
	@Column(length = 500)
	@Expose
	String link3;
	@Column(length = 1000)
	@Expose
	String texto;
	@Column(length = 1000)
	@Expose
	String text;
	@Expose
	String lugar;
	Date fechaPub;
	@Expose
	Integer caducidad;
	@Transient
	@Expose
	String fechaPubFormatted;
	@ManyToOne(fetch = FetchType.EAGER)
	@Expose
	Lugar lugarObj;
	@ManyToOne(fetch = FetchType.EAGER)
	@Expose
	Peligro peligro;
	@Transient
	@Expose
	boolean caducado;
	@ManyToOne(fetch = FetchType.EAGER)
	@Expose
	Fuente fuente;

	public Alert() {
		super();
	}

	public static Alert createAlert(String[] t) {
		Alert alert = null;
		Long id = null;
		Lugar lugar = null;
		Peligro peligro = null;
		Integer caducidad = null;
		Fuente fuente = null;

		for (String x : t) {
			if (x.length() > 1000) {
				return new Alert();
			}
			x.replaceAll("<SEMICOLON>", ";");
		}
		Date fechapub = new Date();
		try {
			id = Long.parseLong(t[0].trim());
			try{
				 fechapub = new SimpleDateFormat("yyyy-MM-dd").parse(t[9].trim());
				}catch(ParseException e){
					//ignorado
				}

			if (t.length > 10 && t[10] != null) {
				peligro = new Peligro();
				try{
				peligro.setId(Long.parseLong(t[11].trim()));
				}catch(Exception e){
					
				}
			}
			if (t.length > 11 && t[11] != null) {
				lugar = new Lugar();
				try{
				lugar.setId(Long.parseLong(t[10].trim()));
                }catch(Exception e){
					
				}
			}
			if (t.length > 12 && t[12] != null) {
				try{
				caducidad = Integer.parseInt(t[12]);
                }catch(Exception e){
				}
			}

			if (t.length > 13 && t[13] != null) {
				fuente = new Fuente();
				try{
				fuente.setId(Long.parseLong(t[13].trim()));
				}catch(Exception e){
				}
			}
		} catch (Exception e) {
			if (t != null && t.length > 1)
				System.out.println("Error:: " + t[0] + "," + t[1] + " ::  "
						+ e.getMessage());
			else
				System.out.println("Error:: " + t);
			return new Alert();
		}

		alert = new Alert(id, t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8],
				fechapub, lugar, peligro, caducidad, fuente);
		return alert;
	}

	public static Alert createAlertSinId(String[] t) {
		Alert alert = null;
		Long id = null;
		Lugar lugar = null;
		Peligro peligro = null;
		Integer caducidad = null;
		Fuente fuente = null;

		Date fechapub = new Date();
		try {
			if (t[0] != null && t[0].length() > 0)
				id = Long.parseLong(t[0].trim());
			try{
			 fechapub = new SimpleDateFormat("yyyy-MM-dd").parse(t[9].trim());
			}catch(ParseException e){
				//ignorado
			}
			if (t.length > 11 && t[11] != null) {
				peligro = new Peligro();
				try{
				  peligro.setId(Long.parseLong(t[11].trim()));
				}catch(Exception e){}
			}
			if (t.length > 10 && t[10] != null) {
				lugar = new Lugar();
				try{
				lugar.setId(Long.parseLong(t[10].trim()));
				}catch(Exception e){}
			}
			if (t.length > 12 && t[12] != null) {
				caducidad = Integer.parseInt(t[12]);
			}
			if (t.length > 13 && t[13] != null && t[13].length() > 0) {
				fuente = new Fuente();
				try{
				fuente.setId(Long.parseLong(t[13].trim()));
				}catch(Exception e){}
			}
		} catch (Exception e) {
			if (t != null && t.length > 1)
				System.out.println("Error:: " + t[0] + "," + t[1] + " ::  "
						+ e.getMessage());
			else
				System.out.println("Error:: " + t);
			return null;
		}

		alert = new Alert(id, t[1], t[2], t[3], t[4], t[5], t[6], t[7], t[8],
				fechapub, lugar, peligro, caducidad, fuente);
		return alert;
	}

	public String getFechaPubFormatted() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fechaPubFormatted = df.format(fechaPub);
		} catch (Exception e) {
			fechaPubFormatted = "";
		}
		return fechaPubFormatted;
	}
	public String getFechaPubFormattedForDateHtmlInput() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fechaPubFormatted = df.format(fechaPub);
		} catch (Exception e) {
			fechaPubFormatted = null;
		}
		return fechaPubFormatted;
	}

	public void setFechaPubFormatted_ddMMyyyy() {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			fechaPubFormatted = df.format(fechaPub);
		} catch (Exception e) {
			fechaPubFormatted = null;
		}

	}
	public void setFechaPubFormatted_yyyyMMdd() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fechaPubFormatted = df.format(fechaPub);
		} catch (Exception e) {
			fechaPubFormatted = null;
		}

	}

	public Alert(Long id, String nombre, String tipo, String link1,
			String link2, String link3, String texto, String text,
			String lugar, Date fechaPub, Lugar lugarObj, Peligro peligro,
			Integer caducidad, Fuente fuente) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.link1 = link1;
		this.link2 = link2;
		this.link3 = link3;
		this.texto = texto;
		this.text = text;
		this.lugar = lugar;
		this.fechaPub = fechaPub;
		setFechaPubFormatted_ddMMyyyy();
		this.lugarObj = lugarObj;
		this.peligro = peligro;
		this.caducidad = caducidad;
		this.fuente = fuente;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Alert [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append(", link1=");
		builder.append(link1);
		builder.append(", link2=");
		builder.append(link2);
		builder.append(", link3=");
		builder.append(link3);
		builder.append(", texto=");
		builder.append(texto);
		builder.append(", text=");
		builder.append(text);
		builder.append(", lugar=");
		builder.append(lugar);
		builder.append(", fechaPub=");
		builder.append(fechaPub);
		builder.append(", caducidad=");
		builder.append(caducidad);
		builder.append(", fechaPubFormatted=");
		builder.append(fechaPubFormatted);
		builder.append(", lugarObj=");
		builder.append(lugarObj);
		builder.append(", peligro=");
		builder.append(peligro);
		builder.append("]");
		return builder.toString();
	}

	public String toStringLite() {
		StringBuilder builder = new StringBuilder();
		builder.append("Alert [id=").append(id).append(", nombre=")
				.append(nombre).append("]");
		return builder.toString();
	}

	public String prettyPrint() {
		StringBuilder builder = new StringBuilder();
		try {
			builder.append(id).append(" - ").append(nombre).append(" - ")
					.append(texto).append("..");
		} catch (Exception e) {
			System.out.println(e);
		}
		return builder.toString().substring(0, Math.min(70, builder.toString().length()));
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getLink1() {
		return link1;
	}

	public void setLink1(String link1) {
		this.link1 = link1;
	}

	public String getLink2() {
		return link2;
	}

	public void setLink2(String link2) {
		this.link2 = link2;
	}

	public String getLink3() {
		return link3;
	}

	public void setLink3(String link3) {
		this.link3 = link3;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public Date getFechaPub() {
		return fechaPub;
	}

	public void setFechaPub(Date fechaPub) {
		this.fechaPub = fechaPub;
	}

	public Lugar getLugarObj() {
		return lugarObj;
	}

	public void setLugarObj(Lugar lugarObj) {
		this.lugarObj = lugarObj;
	}

	public Peligro getPeligro() {
		return peligro;
	}

	public void setPeligro(Peligro peligro) {
		this.peligro = peligro;
	}

	public Integer getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(Integer caducidad) {
		this.caducidad = caducidad;
	}

	public boolean isCaducado() {
		if (caducidad == null || caducidad < 0)
			caducado = false;
		else {
			Calendar today = Calendar.getInstance();
			Calendar fechaLimite = Calendar.getInstance();
			fechaLimite.setTime(fechaPub);
			fechaLimite.add(Calendar.DATE, caducidad);
			if (fechaLimite.after(today))
				caducado = false;
			else
				caducado = true;

		}
		return caducado;
	}

	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

	public boolean hasPermisos(Usuario usuario, Acciones accion) {
		for (Permiso permiso : usuario.getPermisos()) {
			if (permiso.getObjeto().equalsIgnoreCase(this.getClass().getSimpleName())) {
				if (permiso.getAccion().equals(Acciones.ALL)|| permiso.getAccion().equals(accion)){
					if(permiso.getFiltroFlag()==null || !permiso.getFiltroFlag()) return true;
					else{
						List<Long> filtroPeligro =permiso.listOfIdsFromJson("peligro");
						if(this.peligro!=null && filtroPeligro.contains(this.peligro.id)) return true;
					}
				}
			}
			
		}

		return false;
	}

	public Node toNode() {
		return new Node(this.id,this.getNombre(),ObjetoSistema.Alert.ordinal(),this.prettyPrint());		
	}

	public static String toCSVCabecera(String separador) {
		StringBuilder builder = new StringBuilder();
		builder.append("id");
		builder.append(separador+"nombre");
		builder.append(separador+"tipo");
		builder.append(separador+"link1");
		builder.append(separador+"link2");
		builder.append(separador+"link3");
		builder.append(separador+"texto");
		builder.append(separador+"text");
		builder.append(separador+"lugar");
		builder.append(separador+"fechaPub");
		builder.append(separador+"caducidad");
		builder.append(separador+"fechaPubFormatted");
		builder.append(separador+"lugarObj.nombre");
		builder.append(separador+"lugarObj.latitud");
		builder.append(separador+"lugarObj.longitud");
		builder.append(separador+"peligro.nombre");
		return builder.toString();		
	}
	public String toCSV(String separador) {
		StringBuilder builder = new StringBuilder();
		builder.append(id);
		builder.append(separador);
		builder.append(nombre);
		builder.append(separador);
		builder.append(tipo);
		builder.append(separador);
		builder.append(link1);
		builder.append(separador);
		builder.append(link2);
		builder.append(separador);
		builder.append(link3);
		builder.append(separador);
		builder.append(texto);
		builder.append(separador);
		builder.append(text);
		builder.append(separador);
		builder.append(lugar);
		builder.append(separador);
		builder.append(fechaPub);
		builder.append(separador);
		builder.append(caducidad);
		builder.append(separador);
		builder.append(fechaPubFormatted);
		builder.append(separador);
		builder.append(lugarObj!=null?lugarObj.getNombre():"");
		builder.append(separador);
		builder.append(lugarObj!=null?lugarObj.getLatitud():"");
		builder.append(separador);
		builder.append(lugarObj!=null?lugarObj.getLongitud():"");
		builder.append(separador);
		builder.append(peligro!=null?peligro.getNombre():"");
		return builder.toString();
	}
	
	

}
