package es.io.wachsam.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * @author 
 * 
 * Data represents a unique data value , defined by tags.
 *
 */
@Entity
@Table(name="data")
public class Data {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	String value;
    String descripcion;
	DataValueTipo tipoValor;
	@ManyToOne(fetch = FetchType.EAGER)
	Tag tag1;
	@ManyToOne(fetch = FetchType.EAGER)
	Tag tag2;
	@ManyToOne(fetch = FetchType.EAGER)
	Tag tag3;
	Long lugarId;
	Long subjectId;
	Long eventoId;
	public Data(String value, String descripcion, DataValueTipo tipoValor, Tag tag1, Tag tag2, Tag tag3, Long lugarId,
			Long subjectId, Long eventoId) {
		super();
		this.value = value;
		this.descripcion = descripcion;
		this.tipoValor = tipoValor;
		this.tag1 = tag1;
		this.tag2 = tag2;
		this.tag3 = tag3;
		this.lugarId = lugarId;
		this.subjectId = subjectId;
		this.eventoId = eventoId;
	}
	
	public Data() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public DataValueTipo getTipoValor() {
		return tipoValor;
	}
	public void setTipoValor(DataValueTipo tipoValor) {
		this.tipoValor = tipoValor;
	}
	public Tag getTag1() {
		return tag1;
	}
	public void setTag1(Tag tag1) {
		this.tag1 = tag1;
	}
	public Tag getTag2() {
		return tag2;
	}
	public void setTag2(Tag tag2) {
		this.tag2 = tag2;
	}
	public Tag getTag3() {
		return tag3;
	}
	public void setTag3(Tag tag3) {
		this.tag3 = tag3;
	}
	public Long getLugarId() {
		return lugarId;
	}
	public void setLugarId(Long lugarId) {
		this.lugarId = lugarId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Long getEventoId() {
		return eventoId;
	}
	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}
	
	public List<Long> tagsIdsToList(){
		List<Long> list=new ArrayList<Long>();
		
		if(this.getTag1()!=null){
			list.add(this.getTag1().getId());
		}
		if(this.getTag2()!=null){
			list.add(this.getTag2().getId());
		}
		if(this.getTag3()!=null){
			list.add(this.getTag2().getId());
		}
		
		if(list.size()>0) return list;
		return null;
	}
	
	public String tagsIdsToString(){
		StringBuilder sb =new StringBuilder();
		boolean flag=false;
		if(this.getTag1()!=null){
			sb.append(this.getTag1().getId());
			flag=true;
		}
		
		if(this.getTag2()!=null){
			if(flag) sb.append(",");
			sb.append(this.getTag2().getId());
			flag=true;
		}
		
		if(this.getTag2()!=null){
			if(flag) sb.append(",");
			sb.append(this.getTag2().getId());
			flag=true;
		}
		return sb.toString();
	}
	
	

	public String prettyPrint() {
		StringBuilder builder = new StringBuilder();
		builder.append("Data [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (value != null) {
			builder.append("value=");
			builder.append(value);
			builder.append(", ");
		}
		
		if (descripcion != null) {
			builder.append("descripcion=");
			builder.append(descripcion);
			builder.append(", ");
		}
		if (tipoValor != null) {
			builder.append("tipoValor=");
			builder.append(tipoValor);
			builder.append(", ");
		}
		if (tag1 != null) {
			builder.append("tag1=");
			builder.append(tag1.minimalPrint());
			builder.append(", ");
		}
		if (tag2 != null) {
			builder.append("tag2=");
			builder.append(tag2);
			builder.append(", ");
		}
		if (tag3 != null) {
			builder.append("tag3=");
			builder.append(tag3);
			builder.append(", ");
		}
		if (lugarId != null) {
			builder.append("lugarId=");
			builder.append(lugarId);
			builder.append(", ");
		}
		if (subjectId != null) {
			builder.append("subjectId=");
			builder.append(subjectId);
			builder.append(", ");
		}
		if (eventoId != null) {
			builder.append("eventoId=");
			builder.append(eventoId);
		}
		builder.append("]");
		return builder.toString();
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
