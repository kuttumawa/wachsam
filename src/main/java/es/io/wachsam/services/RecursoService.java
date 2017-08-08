package es.io.wachsam.services;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;

import com.google.common.base.Charsets;

import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.dao.RecursoDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Recurso;
import es.io.wachsam.model.Usuario;


public class RecursoService {
	private RecursoDao dao;
	private SecurityService securityService;
	private OperationLogDao operationLogDao;
	private S3service s3service;
	
	public RecursoDao getDao() {
		return dao;
	}
	public void setDao(RecursoDao dao) {
		this.dao = dao;
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
	
	
	public Recurso getRecurso(Long id,Usuario usuario) throws NoAutorizadoException{
			Acciones operation=Acciones.READ;
			Recurso recurso=dao.getRecurso(id);
			if(!securityService.hasAuth(usuario,Recurso.class, operation, recurso))
			 throw new NoAutorizadoException();
			return recurso;
	}
	
	public void deleteById(Long id,Usuario usuario) throws Throwable {
		Recurso recurso=dao.getRecurso(id);
		if(!securityService.hasAuth(usuario,Recurso.class, Acciones.DELETE, recurso))
			 throw new NoAutorizadoException();
		s3service.deleteObject(recurso.getS3Bucket(), recurso.getS3Key());
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(recurso.getClass().getSimpleName(),recurso.getId(),Acciones.DELETE.name(),usuario.getId(),new Date()));
	}
	
	
	public boolean existeYaRecurso(Recurso recurso) {
		return dao.existeYaRecurso(recurso);		
	}
	public List<Recurso> getRecursos(Map<String, String> filter, int pageNum, int pageSize) {
		Recurso recurso=new Recurso();
		String order=filter.containsKey("order")?filter.get("order"):null;
		if(filter.containsKey("id") && GenericValidator.isLong(filter.get("id"))){
			recurso.setId(Long.parseLong(filter.get("id")));
		}
		
		if(filter.containsKey("nombre") && !GenericValidator.isBlankOrNull(filter.get("nombre"))){
			try{
				recurso.setNombre(filter.get("nombre"));
			}catch(Exception e){
			
			}
		}
		
		if(filter.containsKey("descripcion") && !GenericValidator.isBlankOrNull(filter.get("descripcion"))){
			try{
				recurso.setNombre(filter.get("descripcion"));
			}catch(Exception e){
			
			}
		}
		
		List<Recurso> recursos=dao.getAll(recurso,order,pageNum,pageSize);
		return recursos;
	}
	
	public int getNumeroTotalRecursos(Map<String, String> filter) {
		Recurso recurso=new Recurso();
		String order=filter.containsKey("order")?filter.get("order"):null;
		if(filter.containsKey("id") && GenericValidator.isLong(filter.get("id"))){
			recurso.setId(Long.parseLong(filter.get("id")));
		}
		
		if(filter.containsKey("nombre") && !GenericValidator.isBlankOrNull(filter.get("nombre"))){
			try{
				recurso.setNombre(filter.get("nombre"));
			}catch(Exception e){
			
			}
		}
		
		if(filter.containsKey("descripcion") && !GenericValidator.isBlankOrNull(filter.get("descripcion"))){
			try{
				recurso.setNombre(filter.get("descripcion"));
			}catch(Exception e){
			
			}
		}
		
		List<Recurso> recursos=dao.getAll(recurso);
		return recursos.size();
	}
	public List<Recurso>  getAllRecursos(Map<String, String> filter) {
		Recurso recurso=new Recurso();
		String order=filter.containsKey("order")?filter.get("order"):null;
		if(filter.containsKey("id") && GenericValidator.isLong(filter.get("id"))){
			recurso.setId(Long.parseLong(filter.get("id")));
		}
		
		if(filter.containsKey("nombre") && !GenericValidator.isBlankOrNull(filter.get("nombre"))){
			try{
				recurso.setNombre(filter.get("nombre"));
			}catch(Exception e){
			
			}
		}
		
		if(filter.containsKey("decripcion") && !GenericValidator.isBlankOrNull(filter.get("decripcion"))){
			try{
				recurso.setNombre(filter.get("decripcion"));
			}catch(Exception e){
			
			}
		}
		
		List<Recurso> recursos=dao.getAll(recurso);
		return recursos;
	}
	public Recurso save(Recurso recurso, Usuario usuario, InputStream inputStream) throws NoAutorizadoException {
		Acciones operation=Acciones.CREATE;
		Recurso recursoOld=recurso;
		recurso.setFechaCreacion(new Date());
		if(recurso.getId()!=null){
			operation=Acciones.UPDATE;
			recursoOld=dao.getRecurso(recurso.getId());
		}else{
			recurso= dao.save(recurso);
		}
		if(!securityService.hasAuth(usuario,Recurso.class, operation, recursoOld))
		 throw new NoAutorizadoException();
		
		//CAMBIO NOMBRE
		if(!recurso.getNombre().equals(recursoOld.getNombre())){
			//CAMBIO FICHERO
			if(inputStream!=null && recurso.getSize()>0 ){			
				s3service.uploadFile(recurso.getS3Bucket(),recurso.getS3Key(),inputStream,recurso.toMetadata(),recurso.getFormato());							
				s3service.deleteObject(recursoOld.getS3Bucket(),recursoOld.getS3Key());
			}else{
				s3service.renameObject(recurso.getS3Bucket(), recursoOld.getS3Key(), recurso.getS3Key());
			}
		}else{
			if(inputStream!=null && recurso.getSize()>0 ){			
				s3service.uploadFile(recurso.getS3Bucket(),recurso.getS3Key(),inputStream,recurso.toMetadata(),recurso.getFormato());								
			}else{
				//void
			}
		}		
		if(recurso.getS3Publico()) {
			s3service.changeObjectACLPublic(recurso.getS3Bucket(), recurso.getS3Key());			
			try {
				recurso.setUri(s3service.getS3_URL_CONTEXT()+s3service.getBUCKET_NAME()+"/"+ URLEncoder.encode(recurso.getS3Key(),Charsets.ISO_8859_1.toString()));
			} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
			}
	    }else{
	    	s3service.changeObjectACLPrivate(recurso.getS3Bucket(), recurso.getS3Key());
			recurso.setUri(null);
	    }
		recurso= dao.save(recurso);
		operationLogDao.save(new OperationLog(recurso.getClass().getSimpleName(),recurso.getId(),operation.name(),usuario.getId(),new Date()));
		return recurso;		
	}
	public S3service getS3service() {
		return s3service;
	}
	public void setS3service(S3service s3service) {
		this.s3service = s3service;
	}
	
}
