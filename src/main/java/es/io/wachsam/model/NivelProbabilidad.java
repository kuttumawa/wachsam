package es.io.wachsam.model;

/**
 * https://www.mitre.org/publications/systems-engineering-guide/acquisition-systems-engineering/risk-management/risk-impact-assessment-and-prioritization
 *
 */
public enum NivelProbabilidad {
	Impossibility(0,"Imposible(0%)"),
	Almost_certainly_not(0.05,"Muy Baja(<5%)"),
	Probably_not(0.25,"Baja(<25%)"),
	Fifty_fifty(0.5,"Media(<50%)"),
	Probable(0.75,"Alta(<75%)"),
	Almost_certain(0.95,"Muy Alta(<95%)"),
	Certain(1,"Cierto(100%)")	
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
