package es.io.wachsam.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.validator.GenericValidator;

import com.google.gson.annotations.Expose;

/**
 * @author
 * 
 *
 */
@Entity
@Table(name = "riesgo")
public class Riesgo implements ObjetoSistemaIF {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@Expose
	Lugar lugar;
	@ManyToOne(fetch = FetchType.EAGER)
	@Expose
	Peligro peligro;
	@Expose
	NivelProbabilidad probabilidad;
	@Expose
	Date fechaActivacion;
	@Expose
	@Enumerated(EnumType.ORDINAL)
	Mes mesActivacion;
	@Expose
	Integer diaActivacion;
	@Expose
	Integer caducidad;
	@Expose
	Date fechapub;
	@Expose
	@Enumerated(EnumType.ORDINAL)
	FormulaDisipacion formulaDisipacion;
	@Expose
	String texto;
	@Expose
	String text;
	@Expose
	Boolean desactivado;
	

	// Transient fields
	@Transient
	@Expose
	String fechaActivacionFormatted;
	@Transient
	@Expose
	String heredado;
	@Transient
	@Expose
	boolean activo;
	@Transient
	@Expose
	double valor;
	@Transient
	@Expose
	int diasParaCaducar;
	@Transient
	@Expose
	Tendencia tendencia;
	

	



	public Riesgo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public Peligro getPeligro() {
		return peligro;
	}

	public void setPeligro(Peligro peligro) {
		this.peligro = peligro;
	}

	public NivelProbabilidad getProbabilidad() {
		return probabilidad;
	}

	public void setValue(NivelProbabilidad probabilidad) {
		this.probabilidad = probabilidad;
	}

	public boolean hasPermisos(Usuario usuario, Acciones accion) {
		for (Permiso permiso : usuario.getPermisos()) {
			if (permiso.getObjeto().equalsIgnoreCase(this.getClass().getSimpleName())) {
				if (permiso.getAccion().equals(Acciones.ALL) || permiso.getAccion().equals(accion)) {
					if (permiso.getFiltroFlag() == null || !permiso.getFiltroFlag())
						return true;
					else {
						return true;
					}
				}
			}

		}

		return false;
	}

	public Integer getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(Integer caducidad) {
		this.caducidad = caducidad;
	}

	public Date getFechapub() {
		return fechapub;
	}

	public void setFechapub(Date fechapub) {
		this.fechapub = fechapub;
	}

	public String getFechaActivacionFormattedForDateHtmlInput(Date fecha) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fechaActivacionFormatted = df.format(fecha);
		} catch (Exception e) {
			fechaActivacionFormatted = null;
		}
		return fechaActivacionFormatted;
	}

	

	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Mes getMesActivacion() {
		return mesActivacion;
	}

	public void setMesActivacion(Mes mesActivacion) {
		this.mesActivacion = mesActivacion;
	}


	public FormulaDisipacion getFormulaDisipacion() {
		return formulaDisipacion;
	}

	public void setFormulaDisipacion(FormulaDisipacion formulaDisipacion) {
		this.formulaDisipacion = formulaDisipacion;
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

	public void setProbabilidad(NivelProbabilidad probabilidad) {
		this.probabilidad = probabilidad;
	}
	private Integer daysHastaHoy(Date d){
        return (int)( (new Date().getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
    }
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	public double getValor() {
		if(!isActivo()) return 0;
		return round(peligro.getDamage()*formulaDisipacion.compute(probabilidad,calcularCaducidad(), daysHastaHoy(calcularFecha())),2);		
	}
	public void setValor() {
		valor=getValor();
	}
	public boolean isActivo(){
		if(desactivado!=null) return false;
		if(daysHastaHoy(calcularFecha())<0)return false;
		if(daysHastaHoy(calcularFecha())<calcularCaducidad()) return true;		
		return false;
	}
    public Integer calcularCaducidad(){
    	return (caducidad<0?999999:caducidad);
    }
	public Riesgo iniRiesgoTransientInfo(){
			if (isActivo()) setActivo(true);
			valor=getValor();
			if(caducidad>0){
				diasParaCaducar=Math.max(caducidad-daysHastaHoy(calcularFecha()),0);
			}else{
				diasParaCaducar=-1;
			}
		
			fechaActivacionFormatted=getFechaActivacionFormattedForDateHtmlInput(calcularFecha());	
			return this;
	}

	private Date calcularFecha() {
		if((fechaActivacion==null && mesActivacion!=null && diaActivacion!=null)){
			Calendar c=new GregorianCalendar();
			c.set(Calendar.DAY_OF_MONTH,diaActivacion!=null?diaActivacion:1);
			c.set(Calendar.MONTH,mesActivacion.ordinal());
			return c.getTime();
		}
		return fechaActivacion;
	}

	private void setActivo(boolean b) {
		activo=b;		
	}

	public static Comparator getRiesgoValorComparator(){
		return new Comparator<Riesgo>() {
		    public int compare(Riesgo a, Riesgo b) {
		    	if(a.isActivo() && !b.isActivo()) return -1;
		    	if(!a.isActivo() && b.isActivo()) return 1;
		        return a.getValor() < b.getValor() ? 1 : a.getValor()>b.getValor() ? -1 : 0;
		    }
	    };
	}

	public String getHeredado() {
		return heredado;
	}

	public void setHeredado(String heredado) {
		this.heredado = heredado;
	}

	public int getDiasParaCaducar() {
		return diasParaCaducar;
	}

	public void setDiasParaCaducar(int diasParaCaducar) {
		this.diasParaCaducar = diasParaCaducar;
	}

	public Boolean getDesactivado() {
		return desactivado;
	}

	public void setDesactivado(Boolean desactivado) {
		this.desactivado = desactivado;
	}

	public Integer getDiaActivacion() {
		return diaActivacion;
	}

	public void setDiaActivacion(Integer diaActivacion) {
		this.diaActivacion = diaActivacion;
	}
	public String getFechaActivacionFormatted() {
		return fechaActivacionFormatted;
	}

	public void setFechaActivacionFormatted(String fechaActivacionFormatted) {
		this.fechaActivacionFormatted = fechaActivacionFormatted;
	}

	public Tendencia getTendencia() {
		return tendencia;
	}

	public void setTendencia(Tendencia tendencia) {
		this.tendencia = tendencia;
	}

	public List<String> validate() {
		List<String> errores=new ArrayList<String>();
		if(lugar==null) errores.add("Lugar Obligatorio;");
		if(peligro==null) errores.add("Lugar Obligatorio;");
		if(probabilidad==null) errores.add("Probabilidad es Obligatorio");
		if(formulaDisipacion==null) errores.add("FormulaDisipacion es Obligatorio");
		if(fechaActivacion==null && (mesActivacion==null && diaActivacion==null)) errores.add("FechaActivacion o (Mes y días)  es Obligatorio");
		if(caducidad==null) errores.add("Caducidad Obligatorio;");
		if(fechapub==null) fechapub=new Date();
		return errores;
	}



	
	

}
