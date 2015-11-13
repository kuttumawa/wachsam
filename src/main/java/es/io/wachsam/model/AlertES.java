package es.io.wachsam.model;

import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.google.gson.annotations.Expose;
@Document(indexName = "alertas",type = "alerta" , shards = 1, replicas = 0, indexStoreType = "memory", refreshInterval = "-1")
public class AlertES {
	@org.springframework.data.annotation.Id
	@Expose
	Long id;
	@Expose
	String nombre;
	Date fechaPub;
	String lugar;
	@Field(type = FieldType.Object)
	GeoPoint  location;
	@Field(index = FieldIndex.not_analyzed)
	@Expose
	String peligro;

	public AlertES() {
		super();
	}
	public AlertES(Alert a) {
		super();
		this.id = a.getId();
		try{
		this.nombre = a.getNombre();
		this.fechaPub = a.getFechaPub();
		this.lugar = a.getLugarObj().getNombre();
		
		this.location = new GeoPoint(Double.parseDouble(a.getLugarObj().getLatitud()),
				Double.parseDouble(a.getLugarObj().getLongitud()));
		
		this.peligro = a.getPeligro().getNombre();
		}catch(Exception e){}

	}
	public AlertES(Long id, String nombre, Date fechaPub, String lugar,
			String lat,String lon, String peligro) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaPub = fechaPub;
		this.lugar = lugar;
		this.location = new GeoPoint(Double.parseDouble(lat),Double.parseDouble(lon));
		this.peligro = peligro;
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
	public Date getFechaPub() {
		return fechaPub;
	}
	public void setFechaPub(Date fechaPub) {
		this.fechaPub = fechaPub;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	
	public String getPeligro() {
		return peligro;
	}
	public void setPeligro(String peligro) {
		this.peligro = peligro;
	}
	public GeoPoint getLocation() {
		return location;
	}
	public void setLocation(GeoPoint location) {
		this.location = location;
	}
	

	
	
}
