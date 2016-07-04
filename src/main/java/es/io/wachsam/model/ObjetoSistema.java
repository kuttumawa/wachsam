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
	@SerializedName("9") TipoSitio;
}
