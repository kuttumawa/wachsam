package es.io.wachsam.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.Airport;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Factor;
import es.io.wachsam.model.Fuente;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.Usuario;

/**
 * @see http://www.adictosaltrabajo.com/tutoriales/tutoriales.php?pagina=GsonJavaJSON
 *
 */
public class DataService {
	private DataDao dao;
	private TagDao tagDao;
	private SecurityService securityService;
	private OperationLogDao operationLogDao;
	
	public DataDao getDao() {
		return dao;
	}
	public void setDao(DataDao dao) {
		this.dao = dao;
	}
	
	
	public String procesarTextoYExtraerData(String texto,List<Tag> tags,List<Data> newdatas){
		String TAGREGEX="(<.*?>)(.*?)(<\\/.*?>)";
		Pattern pattern = Pattern.compile(TAGREGEX, Pattern.COMMENTS);
	    Matcher matcher = pattern.matcher(texto);
	    HashMap<String,String> result=new HashMap<String, String>();
	    while (matcher.find()) {
	       result.put(matcher.group(1), matcher.group(2));
	    }
	 	Iterator it = result.entrySet().iterator();
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		        String t=pair.getKey().toString().replaceAll("<","").replaceAll(">","");
		        for(Tag tag:tags){
		        	String tagalias=tag.getAlias().replaceAll("\\s","");
		        	if(tagalias.equalsIgnoreCase(t.replaceAll("\\s",""))){
		        		Data dataTemp=new Data();
		        		dataTemp.setTag(tag);
		        		dataTemp.setValue(pair.getValue().toString());
		        		dataTemp.setTipoValor(DataValueTipo.TEXTO);
		        		newdatas.add(dataTemp);
		        		texto=texto.toString().replace(pair.getKey().toString(), "");
		        		texto=texto.toString().replace("</"+t+">", "");
		        	}
		        }
		    }
		System.out.println(texto);
		System.out.println(newdatas);
		return texto;
	}
	public Long save(Data data,Usuario usuario) throws NoAutorizadoException{
		Acciones operation=Acciones.CREATE;
		if(data.getId()!=null) operation=Acciones.UPDATE;
		if(!securityService.hasAuth(usuario,Data.class, operation, data))
		 throw new NoAutorizadoException();
		dao.save(data);
		operationLogDao.save(new OperationLog(data.getClass().getSimpleName(),data.getId(),operation.name(),usuario.getId(),new Date()));
		return data.getId();
		
	}
	public void saveData(List<Data> datas,Object objeto){
		for(Data data:datas){
			if(objeto instanceof Sitio){
				Sitio sitio= (Sitio) objeto;
				data.setObjetoId(sitio.getId());
				data.setObjetoTipo(ObjetoSistema.Sitio);
			}else if(objeto instanceof Alert){
				Alert alert= (Alert) objeto;
				data.setObjetoId(alert.getId());
				data.setObjetoTipo(ObjetoSistema.Alert);
			}
			else if(objeto instanceof Lugar){
				Lugar lugar= (Lugar) objeto;
				data.setObjetoId(lugar.getId());
				data.setObjetoTipo(ObjetoSistema.Lugar);
			}
			else if(objeto instanceof Peligro){
				Peligro peligro= (Peligro) objeto;
				data.setObjetoId(peligro.getId());
				data.setObjetoTipo(ObjetoSistema.Peligro);
			}else if(objeto instanceof Factor){
				Factor factor= (Factor) objeto;
				data.setObjetoId(factor.getId());
				data.setObjetoTipo(ObjetoSistema.Factor);
			}
			else if(objeto instanceof Airport){
				Airport airport= (Airport) objeto;
				data.setObjetoId(airport.getId());
				data.setObjetoTipo(ObjetoSistema.Airport);
			}
			else if(objeto instanceof Usuario){
				Usuario usuario= (Usuario) objeto;
				data.setObjetoId(usuario.getId());
				data.setObjetoTipo(ObjetoSistema.Usuario);
			}
			else if(objeto instanceof Fuente){
				Fuente fuente= (Fuente) objeto;
				data.setObjetoId(fuente.getId());
				data.setObjetoTipo(ObjetoSistema.Fuente);
			}
			dao.save(data);
		}
			
	}
	public String procesarTextoYExtraerData(String textoEn,List<Data> datas) {
		List<Tag> tags=tagDao.getAll();
		return procesarTextoYExtraerData(textoEn,tags,datas);
		
	}
	
	public List<Data> getAllForObject(Long id,ObjetoSistema ob){
		List<Data> res=null;
		Data filtro=new Data();
		filtro.setObjetoId(id);
		filtro.setObjetoTipo(ob);
		res= dao.getAll(filtro);
		return res;
	}
	
	public TagDao getTagDao() {
		return tagDao;
	}
	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
	public SecurityService getSecurityService() {
		return securityService;
	}
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}
	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}
	
	public void deleteById(Long id,Usuario usuario) throws Throwable {
		Data data=dao.getData(id);
		if(!securityService.hasAuth(usuario,Data.class, Acciones.DELETE, data))
			 throw new NoAutorizadoException();
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(data.getClass().getSimpleName(),data.getId(),Acciones.DELETE.name(),usuario.getId(),new Date()));
	}
	

}
