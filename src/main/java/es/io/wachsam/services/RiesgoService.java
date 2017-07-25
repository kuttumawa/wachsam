package es.io.wachsam.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.dao.RiesgoDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Riesgo;
import es.io.wachsam.model.Usuario;

/**
 *
 *
 */
public class RiesgoService {
	private RiesgoDao dao;
	private SecurityService securityService;
	private OperationLogDao operationLogDao;
	private LugarService lugarService;
	public RiesgoDao getDao() {
		return dao;
	}
	public void setDao(RiesgoDao dao) {
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
	public Riesgo save(Riesgo riesgo,Usuario usuario) throws NoAutorizadoException{
		Acciones operation=Acciones.CREATE;
		Riesgo riegoOld=riesgo;
		if(riesgo.getId()!=null){
			operation=Acciones.UPDATE;
			riegoOld=dao.getRiesgo(riesgo.getId());
		}
		if(!securityService.hasAuth(usuario,Riesgo.class, operation, riegoOld))
		 throw new NoAutorizadoException();
		riesgo= dao.save(riesgo);
		operationLogDao.save(new OperationLog(riesgo.getClass().getSimpleName(),riesgo.getId(),operation.name(),usuario.getId(),new Date()));
		return riesgo;
		
	}
	
	public Riesgo getRiesgo(Long id,Usuario usuario) throws NoAutorizadoException{
			Acciones operation=Acciones.READ;
			Riesgo riesgo=dao.getRiesgo(id);
			if(!securityService.hasAuth(usuario,Riesgo.class, operation, riesgo))
			 throw new NoAutorizadoException();
			return riesgo;
	}
	
	public void deleteById(Long id,Usuario usuario) throws Throwable {
		Riesgo riesgo=dao.getRiesgo(id);
		if(!securityService.hasAuth(usuario,Riesgo.class, Acciones.DELETE, riesgo))
			 throw new NoAutorizadoException();
		dao.deleteById(id);
		operationLogDao.save(new OperationLog(riesgo.getClass().getSimpleName(),riesgo.getId(),Acciones.DELETE.name(),usuario.getId(),new Date()));
	}
	
	public List<Riesgo> getAll(){
		return dao.getAll();
	}
	public List<Riesgo> getAllRiesgoForPeligro(Long idPeligro){
		return dao.getRiesgosFromPeligro(idPeligro);
	}
	public boolean existeYaRiesgo(Riesgo riesgo) {
		return dao.existeYaRiesgo(riesgo);
		
	}
	public List<Riesgo> getAllRiesgoForLugar(Long lugarId) {
		return dao.getRiesgosFromLugar(lugarId);
	}
	public List<Riesgo> getAllRiesgoForLugarQuan(Long lugarId) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Riesgo> getAllRiesgoForLugarConAscendentes(Long lugarId) {
		List<Lugar> lugares=lugarService.getAscendientes(lugarId);
		List<Riesgo> listRiesgo=new ArrayList<Riesgo>();
		for(Lugar lugar:lugares){
			List<Riesgo> listRiesgoTemp=dao.getRiesgosFromLugar(lugar.getId());
			for(Riesgo r_candidato:listRiesgoTemp){
				boolean addit=true;
				for(Riesgo r_final:listRiesgo){
					if(r_candidato.getPeligro().getId().equals(r_final.getPeligro().getId())){
						addit=false;
						break;
					}		
				}
				if(addit){
					if(!r_candidato.getLugar().getId().equals(lugarId)) r_candidato.setHeredado(r_candidato.getLugar().getNombre());
					listRiesgo.add(r_candidato);
				}
			}
			
		}		
		return listRiesgo;
	}
	public LugarService getLugarService() {
		return lugarService;
	}
	public void setLugarService(LugarService lugarService) {
		this.lugarService = lugarService;
	}

}
