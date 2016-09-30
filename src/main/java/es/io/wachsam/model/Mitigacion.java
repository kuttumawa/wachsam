package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;



/**
 * @author 
 * 
 *
 */
@Entity
@Table(name="mitigacion")
public class Mitigacion {
	@Expose
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch = FetchType.EAGER)	
	@Expose
	Peligro peligro;
	@ManyToOne(fetch = FetchType.EAGER)	
	@Expose
	Factor factor;
	@Expose
	ValorMitigacion value;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public Peligro getPeligro() {
		return peligro;
	}
	public void setPeligro(Peligro peligro) {
		this.peligro = peligro;
	}
	public ValorMitigacion getValue() {
		return value;
	}
	public void setValue(ValorMitigacion value) {
		this.value = value;
	}
	public Factor getFactor() {
		return factor;
	}
	public void setFactor(Factor factor) {
		this.factor = factor;
	}
	
	public boolean hasPermisos(Usuario usuario, Acciones accion) {
		for (Permiso permiso : usuario.getPermisos()) {
			if (permiso.getObjeto().equalsIgnoreCase(this.getClass().getSimpleName())) {
				if (permiso.getAccion().equals(Acciones.ALL)|| permiso.getAccion().equals(accion)){
					if(permiso.getFiltroFlag()==null || !permiso.getFiltroFlag()) return true;
					else{
						 return true;
					}
				}
			}
			
		}

		return false;
	}
	
	
}
