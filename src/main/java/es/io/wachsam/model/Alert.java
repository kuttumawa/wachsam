package es.io.wachsam.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Alert {
	
	@Id
	Long id;
	String nombre;
	String tipo;
	@Column(length=500) 
	String link1;
	String link2;
	String link3;
	@Column(length=1000) 
	String texto;
	@Column(length=1000) 
	String text;
	String lugar;
	Date fechaPub;
	String fechaPubFormatted;
	
	
	public Alert() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public static Alert createAlert(String[] t){
		Alert alert=null;
		Long id=null;
		for(String x:t){
			if(x.length()>1000){
				System.out.println("XXXXXXXXXXX");
				return  new Alert();
			}
			x.replaceAll("<SEMICOLON>", ";");
		}
		Date fechapub=new Date();
		try{
			id=Long.parseLong(t[0].trim());
			fechapub=new SimpleDateFormat("dd/mm/yyyy").parse(t[9].trim());
		}catch(Exception e){
			System.out.println("Error:: " + t);
			return new Alert();
		}
		alert=new Alert(id,t[1],t[2],t[3],t[4],t[5],t[6],t[7],t[8],fechapub); 
		return alert;
	}
	public String getFechaPubFormatted() {
		SimpleDateFormat df=new SimpleDateFormat("dd-mm-yyyy");
		try{
			fechaPubFormatted=df.format(fechaPub);
		}catch(Exception e){
			fechaPubFormatted="";
		}
		return fechaPubFormatted;
	}
	public void setFechaPubFormatted() {
		SimpleDateFormat df=new SimpleDateFormat("dd-mm-yyyy");
		try{
			fechaPubFormatted=df.format(fechaPub);
		}catch(Exception e){
			fechaPubFormatted="";
		}
		
	}
	public Alert(Long id, String nombre, String tipo, String link1,
			String link2, String link3, String texto, String text,
			String lugar, Date fechaPub) {
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
		setFechaPubFormatted();
	}
	@Override
	public String toString() {
		return "Alert [id=" + id + ", tipo=" + tipo + ", link1=" + link1
				+ ", link2=" + link2 + ", link3=" + link3 + ", texto=" + texto
				+ ", text=" + text + ", lugar=" + lugar + ", fechaPub="
				+ fechaPub + "]";
	}
}
