package es.io.wachsam.model;

public enum NivelProbabilidad {
	Impossibility(0),
	Almost_certainly_not(5),
	Probably_not(30),
	Fifty_fifty(50),
	Probable(75),
	Almost_certain(80),
	Certain(99)	
	;
	
	private final int value;
	
	private NivelProbabilidad(int value){
		this.value=value;
	}
	public int getNivel(){
		return this.value;
	}
}
