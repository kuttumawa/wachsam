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
import es.io.wachsam.model.Link;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Node;
import es.io.wachsam.model.NodeAndLinks;
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
	private AlertService alertService;
	private PeligroService peligroService;
	private LugarService lugarService;
	private SitioService sitioService;

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
	public Data getDataById(Long id,Usuario usuario) throws NoAutorizadoException{
		Acciones operation=Acciones.READ;
		Data data=dao.getData(id);
		if(!securityService.hasAuth(usuario,Data.class, operation, data))
		 throw new NoAutorizadoException();
	
		return data;
		
	}
	public Long save(Data data,Usuario usuario) throws NoAutorizadoException{
		Acciones operation=Acciones.CREATE;
		Data dataOld=data;
		if(data.getId()!=null){
			operation=Acciones.UPDATE;
			dataOld=dao.getData(data.getId());
		}
		if(!securityService.hasAuth(usuario,Data.class, operation, dataOld))
		 throw new NoAutorizadoException();
		dao.save(data);
		operationLogDao.save(new OperationLog(data.getClass().getSimpleName(),data.getId(),operation.name(),usuario.getId(),new Date()));
		return data.getId();
		
	}
	public void saveData(List<Data> datas,Object objeto,Usuario usuario,String stamp) throws NoAutorizadoException{
		Acciones operation=Acciones.CREATE;
		for(Data data:datas){
			if(data.getId()!=null) operation=Acciones.UPDATE;
			if(!securityService.hasAuth(usuario,Data.class, operation, data))
			 throw new NoAutorizadoException();
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
				Usuario user= (Usuario) objeto;
				data.setObjetoId(usuario.getId());
				data.setObjetoTipo(ObjetoSistema.Usuario);
			}
			else if(objeto instanceof Fuente){
				Fuente fuente= (Fuente) objeto;
				data.setObjetoId(fuente.getId());
				data.setObjetoTipo(ObjetoSistema.Fuente);
			}
			if(data.getTipoValor()==null)data.setTipoValor(DataValueTipo.TEXTO);
			dao.save(data);
			operationLogDao.save(new OperationLog(data.getClass().getSimpleName(),data.getId(),operation.name(),usuario.getId(),new Date(),stamp));			
		}
			
	}
	
	public String procesarTextoYExtraerData(String textoEn,List<Data> datas) {
		List<Tag> tags=tagDao.getAll();
		return procesarTextoYExtraerData(textoEn,tags,datas);
		
	}
	
	public List<Tag> getAllTags(){
		List<Tag> tags=tagDao.getAll();
		return tags;
	}
	
	public List<Data> getAllForObject(Long id,ObjetoSistema ob,Usuario usuario){
		List<Data> res=null;
		Data filtro=new Data();
		filtro.setObjetoId(id);
		filtro.setObjetoTipo(ob);
		res= dao.getAll(filtro);
		for(Data data:res){
			if(data.getConnectToId()!=null && data.getObjetoConnected()!=null){
				data.setObjetoConnectedTipoString(data.getObjetoConnected().name());			
				Object o =null;
				try{
					if(data.getObjetoConnected().equals(ObjetoSistema.Alert)) o =	alertService.getAlert(data.getConnectToId(), usuario);
					else if (data.getObjetoConnected().equals(ObjetoSistema.Peligro)) o =	peligroService.getPeligro(data.getConnectToId(), usuario);
					else if (data.getObjetoConnected().equals(ObjetoSistema.Lugar)) o =	lugarService.getLugar(data.getConnectToId(), usuario);
					else if (data.getObjetoConnected().equals(ObjetoSistema.Sitio)) o =	sitioService.getSitio(data.getConnectToId(), usuario);
					
					
					data.setConnectedObject(o);
					
				}catch(Exception e){
					//VOID
				}
			}
		}
		return res;
	}


	public NodeAndLinks getAllNodeAndLinksForObject(Long id,ObjetoSistema ob,Usuario usuario,NodeAndLinks nodeAndLinks){
		if(nodeAndLinks==null) nodeAndLinks=new NodeAndLinks();	
		List<Data> datas=null; 
		Node node_i = null;
		if(ob.equals(ObjetoSistema.Alert)) node_i=alertService.getAlert(id, usuario).toNode();
		if(ob.equals(ObjetoSistema.Peligro)) node_i=peligroService.getPeligro(id, usuario).toNode();
		if(ob.equals(ObjetoSistema.Lugar)) node_i=lugarService.getLugar(id, usuario).toNode();
		if(ob.equals(ObjetoSistema.Sitio)) node_i=sitioService.getSitio(id, usuario).toNode();
		if(!nodeAndLinks.getNodes().contains(node_i))nodeAndLinks.getNodes().add(node_i);
		datas=getAllForObject(id,ob,usuario);
		for(Data data:datas){
			Node node_j=null;
			if(data.getConnectedObject()!=null){
				node_j= getNodeFromObject(data.getConnectedObject());
				if(!nodeAndLinks.getNodes().contains(node_j)) nodeAndLinks.getNodes().add(node_j);
				nodeAndLinks=getAllNodeAndLinksForObject(node_j.getId(),getObjetoSistemaFromObject(data.getConnectedObject()),usuario,nodeAndLinks);
				Link link=new Link();
				link.setSource(nodeAndLinks.getNodes().indexOf(node_i));
				link.setTarget(nodeAndLinks.getNodes().indexOf(node_j));
				link.setValue(data.getTag().getId().intValue());
				link.setText(data.getTag().getNombre());
				nodeAndLinks.getLinks().add(link);
			}
		}
		return nodeAndLinks;
	}
	
	private ObjetoSistema getObjetoSistemaFromObject(Object o){
		if(o instanceof Alert)  return ObjetoSistema.Alert;
		else if(o instanceof Peligro)  return ObjetoSistema.Peligro;
		else if(o instanceof Lugar)  return ObjetoSistema.Lugar;
		else if(o instanceof Sitio)  return ObjetoSistema.Sitio;
		return null;
	}
	private Node getNodeFromObject(Object o){
		if(o instanceof Alert)  return ((Alert)o).toNode();
		else if(o instanceof Peligro)  return ((Peligro)o).toNode();
		else if(o instanceof Lugar)  return ((Lugar)o).toNode();
		else if(o instanceof Sitio)  return ((Sitio)o).toNode();
		return null;
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
	public AlertService getAlertService() {
		return alertService;
	}
	public void setAlertService(AlertService alertService) {
		this.alertService = alertService;
	}
	public PeligroService getPeligroService() {
		return peligroService;
	}
	public void setPeligroService(PeligroService peligroService) {
		this.peligroService = peligroService;
	}
	public LugarService getLugarService() {
		return lugarService;
	}
	public void setLugarService(LugarService lugarService) {
		this.lugarService = lugarService;
	}
	public SitioService getSitioService() {
		return sitioService;
	}
	public void setSitioService(SitioService sitioService) {
		this.sitioService = sitioService;
	}
	public List<Tag> getTagFromTextoAlias(String texto) {
		return dao.getTagFromTextoAlias(texto);
	}
	

	

}
