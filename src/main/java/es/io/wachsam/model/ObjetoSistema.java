package es.io.wachsam.model;

import com.google.gson.annotations.SerializedName;


public enum ObjetoSistema {
	@SerializedName("0") Alert, 
	@SerializedName("1") Peligro,
	@SerializedName("2") Lugar,
	@SerializedName("3") Factor,
	@SerializedName("4") Sitio,
	@SerializedName("5") Fuente,
	@SerializedName("6") Airport,
	@SerializedName("7") Usuario,
	@SerializedName("8") Riesgo,
	@SerializedName("9") TipoSitio,
	@SerializedName("10") Mitigacion,
	@SerializedName("11") Recurso,
	@SerializedName("100") Data;
	
	public Object getInstanceObject(){
		if(this.equals(Alert)) return new Alert();
		else if(this.equals(Peligro)) return new Peligro();
		else if(this.equals(Lugar)) return new Lugar();
		else if(this.equals(Factor)) return new Factor();
		else if(this.equals(Sitio)) return new Sitio();
		else if(this.equals(Fuente)) return new Fuente();
		else if(this.equals(Airport)) return new Airport();
		else if(this.equals(Usuario)) return new Usuario();
		else if(this.equals(Riesgo)) return new Riesgo();
		else if(this.equals(TipoSitio)) return new TipoSitio();
		else if(this.equals(Data)) return  new Data();
		else if(this.equals(Mitigacion)) return  new Mitigacion();
		else if(this.equals(Recurso)) return  new Recurso();
		else if(this.equals(Data)) return  new Data();
		return null;
	}
}
