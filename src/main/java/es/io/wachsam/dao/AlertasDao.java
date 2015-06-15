package es.io.wachsam.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Alert;
import es.io.wachsam.model.DB;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Peligro;

@Transactional
public class AlertasDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Alert alert) {
		/*if (alert == null || alert.getId() == null)
			return -1L;*/
		if(alert.getPeligro()!=null && alert.getPeligro().getId()!=null){
			alert.setPeligro(em.find(Peligro.class,alert.getPeligro().getId()));
		}
		if(alert.getLugarObj()!=null && alert.getLugarObj().getId()!=null){
			alert.setLugarObj(em.find(Lugar.class,alert.getLugarObj().getId()));
		}
		em.persist(alert);
		return alert.getId();
	}
	public Long save(DB db) {
		if (db == null || db.getId() == null)
			db.setId(1L);
		em.persist(db);
		return db.getId();
	}
	public Alert getAlert(Long id){
		return em.find(Alert.class,id);
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
		String[] paisArray=pais!=null?removeAcentos(pais.trim()).split("\\|"):new String[]{};
		String[] textoArray=texto!=null?removeAcentos(texto.trim()).split("\\|"):new String[]{};
		tipo=removeAcentos(tipo);
		boolean flag=false;
		int index=0;
		
		
		for(String texto_i:textoArray){
			if (texto_i != null && texto_i.length() > 0) {
				
				if(!flag){
					 sb.append(" and (");
					 flag=true;
				}
				else sb.append(" or ");
				if(!texto_i.contains("{-TEXTO}")){
				  sb.append("(TRANSLATE( UCASE(texto),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜÑ','ACEIOUAEIOUAEIOUAOEUN') like :texto"+index);
				  sb.append(" or TRANSLATE( UCASE(text),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜÑ','ACEIOUAEIOUAEIOUAOEUN') like :text"+index);
				  sb.append(" or TRANSLATE( UCASE(nombre),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜÑ','ACEIOUAEIOUAEIOUAOEUN') like :nombre"+index+")");
				}else{
				  sb.append("(TRANSLATE( UCASE(nombre),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜÑ','ACEIOUAEIOUAEIOUAOEUN') like :nombre"+index+")");
				}
			}
			index++;
		}
		index=0;
		if(flag) {
			sb.append(")");
			flag=!flag;
		}
			
		for(String pais_i:paisArray){
			if (pais_i != null && pais_i.length() > 0) {
				if(!flag){
					 sb.append(" and (");
					 flag=true;
				}
				else sb.append(" or ");
				sb.append("TRANSLATE( UCASE(lugar),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜÑ','ACEIOUAEIOUAEIOUAOEUN') like :lugar"+index);
			}
			index++;
		}
		index=0;
		if(flag) {
			sb.append(")");
			flag=!flag;
		}
		
		if (fecha != null) {
			sb.append(" and fechaPub > :fecha");
		}
		if (tipo != null && tipo.length() > 3) {
			sb.append(" and TRANSLATE( UCASE(tipo),'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜÑ','ACEIOUAEIOUAEIOUAOEUN') like :tipo");
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
		index=0;
		for(String texto_i:textoArray){
			if (texto_i != null && texto_i.length() > 0) {
				if(!texto_i.contains("{-TEXTO}")){
					q.setParameter("texto"+index, "%" + texto_i+ "%");
					q.setParameter("text"+index, "%" + texto_i + "%");
					q.setParameter("nombre"+index, "%" + texto_i + "%");
				}else{
					q.setParameter("nombre"+index, texto_i.replaceAll("\\{\\-TEXTO\\}",""));
				}
			}
			index++;
		}
		index=0;
		for(String pais_i:paisArray){
			if (pais_i != null && pais_i.length() > 0) {
				q.setParameter("lugar"+index,pais_i);
			}
			index++;
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
	public List<Alert> getAlertasMysql(String texto, String pais, Date fecha,
			String tipo,String order) {
		StringBuilder sb = new StringBuilder("SELECT p FROM Alert p where 1=1");
		String[] paisArray=pais!=null?removeAcentos(pais.trim()).split("\\|"):new String[]{};
		String[] textoArray=texto!=null?removeAcentos(texto.trim()).split("\\|"):new String[]{};
		tipo=removeAcentos(tipo);
		boolean flag=false;
		int index=0;
		
		
		for(String texto_i:textoArray){
			if (texto_i != null && texto_i.length() > 0) {
				
				if(!flag){
					 sb.append(" and (");
					 flag=true;
				}
				else sb.append(" or ");
				if(!texto_i.contains("{-TEXTO}")){
				  sb.append("(texto like :texto"+index);
				  sb.append(" or text  like :text"+index);
				  sb.append(" or nombre  like :nombre"+index+")");
				}else{
				  sb.append("(nombre)  like :nombre"+index+")");
				}
			}
			index++;
		}
		index=0;
		if(flag) {
			sb.append(")");
			flag=!flag;
		}
			
		for(String pais_i:paisArray){
			if (pais_i != null && pais_i.length() > 0) {
				if(!flag){
					 sb.append(" and (");
					 flag=true;
				}
				else sb.append(" or ");
				sb.append("lugar like :lugar"+index);
			}
			index++;
		}
		index=0;
		if(flag) {
			sb.append(")");
			flag=!flag;
		}
		
		if (fecha != null) {
			sb.append(" and fechaPub > :fecha");
		}
		if (tipo != null && tipo.length() > 3) {
			sb.append(" and tipo like :tipo");
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
		index=0;
		for(String texto_i:textoArray){
			if (texto_i != null && texto_i.length() > 0) {
				if(!texto_i.contains("{-TEXTO}")){
					q.setParameter("texto"+index, "%" + texto_i+ "%");
					q.setParameter("text"+index, "%" + texto_i + "%");
					q.setParameter("nombre"+index, "%" + texto_i + "%");
				}else{
					q.setParameter("nombre"+index, texto_i.replaceAll("\\{\\-TEXTO\\}",""));
				}
			}
			index++;
		}
		index=0;
		for(String pais_i:paisArray){
			if (pais_i != null && pais_i.length() > 0) {
				q.setParameter("lugar"+index,pais_i);
			}
			index++;
		}
		
		if (fecha != null) {
			q.setParameter("fecha", fecha);
		}
		if (tipo != null && tipo.length() > 0) {
			q.setParameter("tipo", "%" + tipo + "%");
		}
		return q.getResultList();
	}
	public void deleteById(Long id) throws Exception {
		Alert ent = em.find(Alert.class, id);
		em.remove(ent); 
	}
}
