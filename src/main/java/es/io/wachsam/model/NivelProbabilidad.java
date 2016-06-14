package es.io.wachsam.model;

public enum NivelProbabilidad {
	muy_raro(0),raro(1);
	
	private final int value;
	
	private NivelProbabilidad(int value){
		this.value=value;
	}
	public int getNivel(){
		return this.value;
	}
}
