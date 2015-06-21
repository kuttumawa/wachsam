package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="db")
public class DB {
  @Id
  Long id;
  Long size;

public DB(){
	super();
	}
public DB(Long i, Long size) {
	super();
	this.id = i;
	this.size = size;
}
public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getSize() {
	return size;
}

public void setSize(Long size) {
	this.size = size;
}
}
