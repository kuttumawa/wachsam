package es.io.wachsam.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

import com.google.gson.annotations.Expose;

public class Tendencia{
	@Expose
	int numEventosActual;
	@Expose
	int numEventosPasado;
	@Expose
	long periodoDias;
	@Expose
	double porcentaje;
	@Expose
	String message;
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}	
	private double result(){
		return round(((numEventosActual-numEventosPasado)/(numEventosPasado==0?1:numEventosPasado))*100,2);
	}
	public Tendencia(int numEventosActual, int numEventosPasado, long milliseconds) {
		super();
		this.numEventosActual = numEventosActual;
		this.numEventosPasado = numEventosPasado;
		this.periodoDias = milliseconds/1000/3600/24;
		this.porcentaje=result();
		this.message=this.toString();
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(porcentaje + "%[(");
		builder.append(numEventosActual);
		builder.append("/"+ numEventosPasado);
		builder.append("),");
		builder.append(periodoDias);
		builder.append(" días");		
		builder.append("]");
		return builder.toString();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


}
