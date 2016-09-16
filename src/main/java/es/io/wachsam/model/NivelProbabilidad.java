package es.io.wachsam.model;

/**
 * https://www.mitre.org/publications/systems-engineering-guide/acquisition-systems-engineering/risk-management/risk-impact-assessment-and-prioritization
 *
 */
public enum NivelProbabilidad {
	Impossibility(0,""),
	Almost_certainly_not(5,""),
	Probably_not(30,""),
	Fifty_fifty(50,""),
	Probable(75,""),
	Almost_certain(80,""),
	Certain(0.99,"")	
	;
	
	private final double value;
	private final String descripcion;
	
	private NivelProbabilidad(double value,String des){
		this.value=value;
		this.descripcion=des;
	}
	public double getNivel(){
		return this.value;
	}
}
