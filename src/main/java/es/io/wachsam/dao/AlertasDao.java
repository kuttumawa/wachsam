package es.io.wachsam.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.model.Alert;
import es.io.wachsam.model.DB;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Peligro;


@Transactional
public class AlertasDao {
	final String ALERT_CACHE="ioCacheAlertas";
	private CacheManager cacheManager;
	private static final Logger LOG = Logger.getLogger(AlertasDao.class);
	@PersistenceContext
	private EntityManager em;
	@CacheEvict(ALERT_CACHE)
	public Alert save(Alert alert) {
		LOG.debug("Entrando save: " + alert);
		if(alert.getPeligro()!=null && alert.getPeligro().getId()!=null){
			alert.setPeligro(em.find(Peligro.class,alert.getPeligro().getId()));
		}
		if(alert.getLugarObj()!=null && alert.getLugarObj().getId()!=null){
			alert.setLugarObj(em.find(Lugar.class,alert.getLugarObj().getId()));
			alert.setLugar(alert.getLugarObj().getNombre());
		}
		if (alert == null)
			return null;
		if(alert.getId() == null) em.persist(alert);
		else em.merge(alert);
		evictCache();
		LOG.debug("Saliendo save id: " + alert.getId());
		return alert;
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
	@Cacheable(ALERT_CACHE)
	public List<Alert> getAll() {
		return em.createQuery("SELECT p FROM Alert p order by id desc", Alert.class)
				.getResultList();
	}
	
	@Cacheable(ALERT_CACHE)
	public List<Alert> getAllMin() {
		return em.createQuery("SELECT p.id,p.nombre FROM Alert p order by id desc", Alert.class)
				.getResultList();
	}

	public List<Alert> getAllPorPaís(String pais) {
		Query q = em.createQuery(
				"SELECT p FROM Alert p where p.lugar LIKE :pais", Alert.class);
		q.setParameter("pais", "%" + pais + "%");
		return q.getResultList();
	}
	@Cacheable(ALERT_CACHE)
	public List<Alert> getAlertas(String texto, String pais, Date fecha,
			String tipo,String order) {
		LOG.debug("Entrando getAlertas: texto=" + texto +",pais="+pais+",fecha="+fecha+",tipo="+",order="+order);
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
		List<Alert> res=q.getResultList();
		LOG.debug("Saliendo getAlertas encontrados: " + res.size());
		return res;
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
	@Cacheable(ALERT_CACHE)
	public List<Alert> getAlertasMysql(String texto, String pais, Date fecha,
			String tipo,String order) {
		LOG.debug("Entrando getAlertasMysql: texto=" + texto +",pais="+pais+",fecha="+fecha+",tipo="+",order="+order);		
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
			if(!flagPrimero)sb.append(" DESC,id DESC");
			else sb.append(" ORDER BY nombre,fechaPub DESC,id DESC");
		}else{
		    sb.append(" ORDER BY nombre,fechaPub DESC,id DESC");
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
		List<Alert> res=q.getResultList();
		LOG.debug("Saliendo getAlertasMysql encontrados: " + res.size());
		return res;
	}
	@Cacheable(ALERT_CACHE)
	public List<Alert> getAlertasMysql(Long id,String texto, Long pais,Long peligro, Date fecha,
			String tipo,String order,Boolean caducidad,int offset,int maxNumberResults) {
		LOG.debug("Entrando getAlertasMysql: texto=" + texto +",pais="+pais+",fecha="+fecha+",tipo="+",order="+order+",caducidad="+caducidad+",offset="+offset+",maxNumberResults="+maxNumberResults);				
		StringBuilder sb = new StringBuilder("SELECT p FROM Alert p where 1=1");
		String[] textoArray=texto!=null?removeAcentos(texto.trim()).split("\\|"):new String[]{};
		tipo=removeAcentos(tipo);
		boolean flag=false;
		int index=0;
		
		if(id!=null){
			sb.append(" and id = :id ");
		}else{
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
				
			
			if (pais != null) {
					sb.append(" and lugarObj.id = :lugar ");
			}
			if (peligro != null) {
				sb.append(" and peligro.id = :peligro ");
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
			if (caducidad != null && caducidad.equals(true)) {
				sb.append(" and caducidad = 1");
			}
			if (caducidad != null && caducidad.equals(false)) {
				sb.append(" and caducidad = 0");
			}
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
			if(!flagPrimero)sb.append(" DESC,id DESC");
			else sb.append(" ORDER BY fechaPub DESC,id DESC");
		}else{
		    sb.append(" ORDER BY fechaPub DESC,id DESC");
		}
		
		Query q = em.createQuery(sb.toString(), Alert.class);
		if(offset>0)q.setFirstResult(offset);
		if(maxNumberResults>0)q.setMaxResults(maxNumberResults);
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
		if (id != null) {
			q.setParameter("id",id);
		}
		if (pais != null) {
				q.setParameter("lugar",pais);
			}
		if (peligro != null) {
			q.setParameter("peligro",peligro);
		}
		
		if (fecha != null) {
			q.setParameter("fecha", fecha);
		}
		if (tipo != null && tipo.length() > 0) {
			q.setParameter("tipo", "%" + tipo + "%");
		}
		List<Alert> res=q.getResultList();
		LOG.debug("Saliendo getAlertasMysql encontrados: " + res.size());
		return res;
	}
	@CacheEvict(ALERT_CACHE)
	public void deleteById(Long id) throws Exception {
		Alert ent = em.find(Alert.class, id);
		em.remove(ent); 
		evictCache();
	}
	public void evictCache(){
		cacheManager.getCache(ALERT_CACHE).clear();
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	@Cacheable(ALERT_CACHE)
	public int getNumeroAlertasMysql(Long id,String texto, Long pais,Long peligro, Date fecha,
			String tipo,String order,Boolean caducidad) {
		LOG.debug("Entrando getNumeroAlertasMysql: texto=" + texto +",pais="+pais+",fecha="+fecha+",tipo="+",order="+order+",caducidad="+caducidad);						
		StringBuilder sb = new StringBuilder("SELECT p FROM Alert p where 1=1");
		String[] textoArray=texto!=null?removeAcentos(texto.trim()).split("\\|"):new String[]{};
		tipo=removeAcentos(tipo);
		boolean flag=false;
		int index=0;

		if(id!=null){
			sb.append(" and id = :id ");
		}else{
		
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
				
			
			if (pais != null) {
					sb.append(" and lugarObj.id = :lugar ");
			}
			if (peligro != null) {
				sb.append(" and peligro.id = :peligro ");
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
			if (caducidad != null && caducidad.equals(true)) {
				sb.append(" and caducidad = 1");
			}
			if (caducidad != null && caducidad.equals(false)) {
				sb.append(" and caducidad = 0");
			}
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
			if(!flagPrimero)sb.append(" DESC,id DESC");
			else sb.append(" ORDER BY fechaPub DESC,id DESC");
		}else{
		    sb.append(" ORDER BY fechaPub DESC,id DESC");
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
		if (id != null) {
			q.setParameter("id",id);
		}
		if (pais != null) {
				q.setParameter("lugar",pais);
			}
		if (peligro != null) {
			q.setParameter("peligro",peligro);
		}
		
		if (fecha != null) {
			q.setParameter("fecha", fecha);
		}
		if (tipo != null && tipo.length() > 0) {
			q.setParameter("tipo", "%" + tipo + "%");
		}
		List<Alert> res=q.getResultList();
		LOG.debug("Saliendo getAlertasMysql encontrados: " + res.size());
		return res.size();
		
	}
	
}
