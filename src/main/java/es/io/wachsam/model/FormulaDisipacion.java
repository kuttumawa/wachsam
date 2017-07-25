package es.io.wachsam.model;

/**
 * https://www.mitre.org/publications/systems-engineering-guide/acquisition-systems-engineering/risk-management/risk-impact-assessment-and-prioritization
 *
 */
public enum FormulaDisipacion {
	Homogenea,
	Lineal,
	NoLineal
	;
	
	public double compute(NivelProbabilidad np,Integer caducidad,Integer dia){
		if(dia<1)return np.getNivel();
		if (this.equals(Homogenea)){
			return np.getNivel();
		}else if (this.equals(Lineal)){			
			return (-(np.getNivel()/caducidad)*dia) + np.getNivel();
		}else if (this.equals(NoLineal)){
			double n=(Math.log(2)/(double)(caducidad/(double)50));
			return Math.exp(-(dia-1) * n) *np.getNivel();
		}else{
			return np.getNivel();
		}
		
	}
}
