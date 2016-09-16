package es.io.wachsam.model;

public enum ValorMitigacion {
	AumentaMucho(2),
	AumentaPoco(1),
	NoAfecta(0),
	DisminuyePoco(-1),
	DisminuyeMucho(-2)	
	;
	
	private final int value;
	
	private ValorMitigacion(int value){
		this.value=value;
	}
	public int getValorNumerico(){
		return this.value;
	}
}
