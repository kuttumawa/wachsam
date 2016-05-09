package es.io.wachsam.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * @author 
 * 
 *
 *
 */
@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	String login;
	String password;
	String email;
	@OneToMany(fetch = FetchType.EAGER)	
	private Set<Permiso> permisos=new HashSet<Permiso>();;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	

	public void addPermiso(Permiso p) {
		if(permisos==null) permisos=new HashSet<Permiso>();
		permisos.add(p);
	}

	public void removePermiso(Permiso permiso) {
		if(permisos==null) permisos=new HashSet<Permiso>();
		permisos.remove(permiso);
		
	}

	public Set<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(Set<Permiso> permisos) {
		this.permisos = permisos;
	}

	
}
