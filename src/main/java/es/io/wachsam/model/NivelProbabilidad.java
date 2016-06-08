package es.io.wachsam.model;

public enum NivelProbabilidad {
	raro;
	
	private final int value;
	
	private NivelProbabilidad(int value){
		this.value=value;
	}
	public int getNivel(){
		return this.value;
	}
}
