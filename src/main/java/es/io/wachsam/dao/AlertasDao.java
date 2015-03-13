package es.io.wachsam.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Alert;
import es.io.wachsam.model.DB;

@Transactional
public class AlertasDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Alert alert) {
		if (alert == null || alert.getId() == null)
			return -1L;
		em.persist(alert);
		return alert.getId();
	}
	public Long save(DB db) {
		if (db == null || db.getId() == null)
			db.setId(1L);
		em.persist(db);
		return db.getId();
	}
	public DB getDB(Long id){
		return em.find(DB.class,id);
	}

	public List<Alert> getAll() {
		return em.createQuery("SELECT p FROM Alert p", Alert.class)
				.getResultList();
	}

	public List<Alert> getAllPorPaís(String pais) {
		Query q = em.createQuery(
				"SELECT p FROM Alert p where p.lugar LIKE :pais", Alert.class);
		q.setParameter("pais", "%" + pais + "%");
		return q.getResultList();
	}

	public List<Alert> getAlertas(String texto, String pais, Date fecha,
			String tipo,String order) {
		StringBuilder sb = new StringBuilder("SELECT p FROM Alert p where 1=1");
		texto=removeAcentos(texto);
		pais=removeAcentos(pais);
		tipo=removeAcentos(tipo);
		if (texto != null && texto.length() > 0) {
			sb.append(" and (TRANSLATE( UCASE(texto),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜ','ACEIOUAEIOUAEIOUAOEU') like :texto");
			sb.append(" or TRANSLATE( UCASE(text),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜ','ACEIOUAEIOUAEIOUAOEU') like :text");
			sb.append(" or TRANSLATE( UCASE(nombre),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜ','ACEIOUAEIOUAEIOUAOEU') like :nombre)");
		}
		if (pais != null && pais.length() > 0) {
			sb.append(" and TRANSLATE( UCASE(lugar),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜ','ACEIOUAEIOUAEIOUAOEU') like :lugar");
		}
		if (fecha != null) {
			sb.append(" and fechaPub < :fecha");
		}
		if (tipo != null && tipo.length() > 3) {
			sb.append(" and TRANSLATE( UCASE(tipo),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜ','ACEIOUAEIOUAEIOUAOEU') like :tipo");
		}
		if(order != null && order.length() > 0){
			String[] orden=order.split("\\s");
			String[] p={"nombre","fecha","tipo"};
			List<String> permitidos=Arrays.asList(p);
			boolean flagPrimero=true;
			for(String s:orden){
				if(!permitidos.contains(s)) continue;
				if(s.equalsIgnoreCase("fecha")) s="fechaPub";
				if(flagPrimero){
				   sb.append(" ORDER BY ").append(s);
				   flagPrimero=false;
				}else{
					sb.append(",").append(s);
				}
			}
			if(!flagPrimero)sb.append(" DESC");
		}else{
		    sb.append(" ORDER BY nombre,fechaPub DESC");
		}
		Query q = em.createQuery(sb.toString(), Alert.class);
		if (texto != null && texto.length() > 0) {
			q.setParameter("texto", "%" + texto+ "%");
			q.setParameter("text", "%" + texto + "%");
			q.setParameter("nombre", "%" + texto + "%");
		}
		if (pais != null && pais.length() > 0) {
			q.setParameter("lugar", "%" + pais+ "%");
		}
		if (fecha != null) {
			q.setParameter("fecha", fecha);
		}
		if (tipo != null && tipo.length() > 0) {
			q.setParameter("tipo", "%" + tipo + "%");
		}
		return q.getResultList();
	}
	
	public static String removeAcentos(String input) {
	    if(input==null) return null;
		// Cadena de caracteres original a sustituir.
	    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
	    // Cadena de caracteres ASCII que reemplazarán los originales.
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    String output = input;
	    for (int i=0; i<original.length(); i++) {
	         output = output.replace(original.charAt(i), ascii.charAt(i));
	    }
	    return output.toUpperCase();
	}
}
