package es.io.wachsam.model;



public enum DataValueTipo {
	NUMERICO("Numérico"),
	TEXTO("Texto"),
	VACIO("Sin Valor"),
	DATE("Fecha"),
	OBJETO_ID("ID Objeto a conectar")
	;
	
	private final String descripcion;
	
	private DataValueTipo(String des){
		this.descripcion=des;
	}
	public String getDescripcion(){
		return this.descripcion;
	}
}
