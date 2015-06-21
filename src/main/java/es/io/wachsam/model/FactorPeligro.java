package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="factorpeligro")
public class FactorPeligro {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	Peligro peligro;
	@ManyToOne(fetch = FetchType.EAGER)
	Factor factor;
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
	public Factor getFactor() {
		return factor;
	}
	public void setFactor(Factor factor) {
		this.factor = factor;
	}

}
