package es.io.wachsam.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * @author 
 * 
 *
 */
@Entity
@Table(name="operationlog")
public class OperationLog {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	String objeto;
	Long objetoId;
	String operation;
	Long usuarioId;
	Date timestamp;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public OperationLog() {
		super();
	}
	public Long getObjetoId() {
		return objetoId;
	}
	public void setObjetoId(Long objetoId) {
		this.objetoId = objetoId;
	}
	public OperationLog(String objeto,Long objetoId, String operation, Long usuarioId,
			Date timestamp) {
		super();
		this.objeto = objeto;
		this.objetoId = objetoId;
		this.operation = operation;
		this.usuarioId = usuarioId;
		this.timestamp = timestamp;
	}
	
	
	
	
	
	
}
