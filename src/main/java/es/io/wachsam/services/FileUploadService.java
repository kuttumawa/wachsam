package es.io.wachsam.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;

import org.springframework.transaction.annotation.Transactional;

import es.io.wachsam.dao.FileUploadDao;
import es.io.wachsam.dao.OperationLogDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Acciones;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.ObjetoSistemaIF;
import es.io.wachsam.model.OperationLog;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.util.Tools;

/**
 *
 *
 */

@Transactional
public class FileUploadService {
	private SecurityService securityService;
	private OperationLogDao operationLogDao;
	private AlertService alertService;
	private PeligroService peligroService;
	private LugarService lugarService;
	private SitioService sitioService;
	private DataService dataService;
	private FileUploadDao fileUploadDao;
	
	
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
	
	public Map<Object,List<Data>> cargarCsv(ObjetoSistema objetoSistema,String[] csv,List<String> errores){
		return cargarCsv(objetoSistema,new ArrayList<String>(Arrays.asList(csv)),errores);
	}
	public Map<Object,List<Data>> cargarCsv(ObjetoSistema objetoSistema,List<String> csv,List<String> errores){
		Map<Object,List<Data>> dat=new LinkedHashMap<Object,List<Data>>();
		String metadata=csv.get(0);
		String errorPopulateCsv=null;
		String errorNoExisteId=null;
		String errorValidacion=null;
		//procesar
		for(int i=1;i<csv.size();i++){
				String line=csv.get(i);
				ObjetoSistemaIF o=(ObjetoSistemaIF)objetoSistema.getInstanceObject();
				List<Data> datas=new ArrayList<Data>();
				errorPopulateCsv = populateObjectFromCsv(o,line,metadata,datas);;
				if(errorPopulateCsv!=null){
					errores.add("Error en línea "+(i+1)+": "+ errorPopulateCsv );
				}else{
					if(o.getId()!=null){
						ObjetoSistemaIF oo=fileUploadDao.getObject(o);
						if(oo==null) errorNoExisteId="No existe el objeto "+ objetoSistema.name() + " con id " + o.getId();
						if (errorNoExisteId!=null){
							errores.add("Error en línea "+(i+1)+": "+ errorNoExisteId );
						}else{
							datas.clear();
							errorPopulateCsv = populateObjectFromCsv(oo,line,metadata,datas);
							o=oo;
						}
					}
					if(errorNoExisteId==null){
						errorValidacion=Tools.listToString(o.validate());
					    if (errorValidacion!=null)errores.add("Error en línea "+(i+1)+": "+ errorValidacion);
					}					
				}
				dat.put(o,datas);
				
			}
		return dat;
		
	}
	
	/**
	 * @param object
	 * @param line
	 * @param metadata
	 * @param datas  list of data added
	 */
	private String populateObjectFromCsv(Object object, String line, String metadata,List<Data> datas) {
		//populate from metadata
		List<String> mm=new ArrayList<String>(Arrays.asList(metadata.split(",")));
		String[] cols=line.split(",");
		String error=null;
		int colsIndex=0;
		try{
		for(String meta:mm){
			Pattern patA = Pattern.compile("(A:)((?:(?!\\().)*)\\((.*?)\\)");//Generic value
			Matcher m1 = patA.matcher(meta);
			Pattern formulaRegex = Pattern.compile("((F:)(.*))\\((.*)\\)");//Formula
			Matcher m2 = formulaRegex.matcher(meta);
			Pattern dataRegex = Pattern.compile("(D:)(.*)");//Simple data
			Matcher m3 = dataRegex.matcher(meta);
			if (m1.matches()) {
				Matcher m4 = formulaRegex.matcher(m1.group(3));
				if(m4.matches()){	
					String value=executeFormula(m4.group(1),m4.group(4));
				    populateClassWithData(object,m1.group(2),value);				   
				}else{			
					populateClassWithData(object,m1.group(2),m1.group(3));	
				}
			}else if (m2.matches()) {
				String value=executeFormula(m2.group(1),cols[colsIndex]);
				populateClassWithData(object,m2.group(4),value);
			    colsIndex++;
			}else if (m3.matches()) {
				Data datum=new Data();
				Tag tag=null;
				try{
				   tag=dataService.getTagDao().fetchTag(m3.group(2));
				}catch(NoResultException e){
				      return "No existe el tag con alias : "+m3.group(2); 
				}
				datum.setTag(tag);
				datum.setValue(cols[colsIndex]);
				datas.add(datum);
				colsIndex++;
			}else{
				populateClassWithData(object,meta,cols[colsIndex]);
				colsIndex++;
			}
			
		}
		}catch(Exception e){
			return e.toString();
		}
		return null;
	
		
	}
	
	private void populateClassWithData(Object object,String fieldname,String value) throws Exception{
		Field field=null;
			field = object.getClass().getDeclaredField(fieldname);
			field.setAccessible(true);
		
			if(field.getType().getName().equals("java.lang.Integer")){
				field.set(object,Integer.valueOf(value));
			}else if(field.getType().getName().equals("java.lang.Long")){
				field.set(object,Long.valueOf(value));
			}else if(field.getType().getName().equals("es.io.wachsam.model.TipoSitio")){
				TipoSitio tipoSitio=new TipoSitio();
				tipoSitio.setId(Long.valueOf(value));
				field.set(object,tipoSitio);
			}else if(field.getType().getName().equals("es.io.wachsam.model.Lugar")){
				Lugar lugar=new Lugar();
				lugar.setId(Long.valueOf(value));
				field.set(object,lugar);
			}else if(field.getType().getName().equals("es.io.wachsam.model.Tag")){
				Tag tag=new Tag();
				tag.setId(Long.valueOf(value));
				field.set(object,tag);
			}else if(field.getType().getName().equals("es.io.wachsam.model.ObjetoSistema")){				
				field.set(object,ObjetoSistema.valueOf(value));
			}else if(field.getType().getName().equals("es.io.wachsam.model.DataValueTipo")){				
				field.set(object,DataValueTipo.valueOf(value));
			}
			
			else{//java.lang.String
				field.set(object,value);
			}
		
		
	}

	private String executeFormula(String formulaName,String value) {
		String result=null;
		if(formulaName.equalsIgnoreCase("F:texto_to_lugar")){
			result=F_texto_to_lugar(value);			
		}else if(formulaName.equalsIgnoreCase("F:ISO_3166_1_alpha2_to_lugar")){
			result=F_ISO_3166_1_alpha2_to_lugar(value);
		}else if(formulaName.equalsIgnoreCase("F:ISO_3166_1_alpha3_to_lugar")){
			result=F_ISO_3166_1_alpha3_to_lugar(value);
		}else if(formulaName.equalsIgnoreCase("F:ISO_3166_1_num_to_lugar")){
			result=F_ISO_3166_1_num_to_lugar(value);
		}else if(formulaName.equalsIgnoreCase("F:texto_to_peligro")){
			result=F_texto_to_peligro(value);
		}else if(formulaName.equalsIgnoreCase("F:text_to_tag")){
			result=F_texto_alias_to_tag(value);
		}
		else{
			//error throw exception
			throw new RuntimeException("No existe la formula: " + formulaName);
		}
		
		return result;
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
	public DataService getDataService() {
		return dataService;
	}
	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
	public FileUploadDao getFileUploadDao() {
		return fileUploadDao;
	}
	public void setFileUploadDao(FileUploadDao fileUploadDao) {
		this.fileUploadDao = fileUploadDao;
	}
	public String F_texto_to_lugar(String texto){
    	List<Lugar> lugar=lugarService.getLugarFromTexto(texto);
    	if (lugar.size()>1) throw new RuntimeException("Texto: "+texto +" ambiguo para la formula: F_texto_to_lugar");
    	else if (lugar.size()<1) throw new RuntimeException("Texto: "+texto +" no encontrado formula: F_texto_to_lugar");
    	return lugar.get(0).getId()+""; 
	}
	public String F_texto_to_peligro(String texto){
	    	List<Peligro> peligro=peligroService.getPeligroFromTexto(texto);
	    	if (peligro.size()>1) throw new RuntimeException("Texto: "+texto +" ambiguo para la formula: F_texto_to_peligro");
	    	else if (peligro.size()<1) throw new RuntimeException("Texto: "+texto +" no encontrado formula: F_texto_to_peligro");
	    	return peligro.get(0).getId()+""; 
		}
	public String F_texto_alias_to_tag(String texto){
    	List<Tag> tag=dataService.getTagFromTextoAlias(texto);
    	if (tag.size()>1) throw new RuntimeException("Texto: "+texto +" ambiguo para la formula: F_texto_alias_to_tag");
    	else if (tag.size()<1) throw new RuntimeException("Texto: "+texto +" no encontrado formula: F_texto_alias_to_tag");
    	return tag.get(0).getId()+""; 
	}
	public String F_ISO_3166_1_alpha2_to_lugar(String code){
    	List<Long> lugarIds=lugarService.getLugarFromISO_3166_1_alpha2(code);
    	if (lugarIds.size()>1) throw new RuntimeException("Código: "+code +" ambiguo para la formula: F_ISO_3166_1_alpha2_to_lugar");
    	else if (lugarIds.size()<1) throw new RuntimeException("Código: "+code +" no encontrado formula: F_ISO_3166_1_alpha2_to_lugar");
    	return lugarIds.get(0)+""; 
	}
	public String F_ISO_3166_1_alpha3_to_lugar(String code){
    	List<Long> lugarIds=lugarService.getLugarFromISO_3166_1_alpha3(code);
    	if (lugarIds.size()>1) throw new RuntimeException("Código: "+code +" ambiguo para la formula: F_ISO_3166_1_alpha3_to_lugar");
    	else if (lugarIds.size()<1) throw new RuntimeException("Código: "+code +" no encontrado formula: F_ISO_3166_1_alpha3_to_lugar");
    	return lugarIds.get(0)+""; 
	}
	public String F_ISO_3166_1_num_to_lugar(String code){
    	List<Long> lugarIds=lugarService.getLugarFromISO_3166_1_num(code);
    	if (lugarIds.size()>1) throw new RuntimeException("Código: "+code +" ambiguo para la formula: F_ISO_3166_1_num_to_lugar");
    	else if (lugarIds.size()<1) throw new RuntimeException("Código: "+code +" no encontrado formula: F_ISO_3166_1_num_to_lugar");
    	return lugarIds.get(0)+""; 
	}
	public void save(Map<Object, List<Data>> dat, String objeto, Usuario usuario,boolean actualizaObjeto) throws Exception {
		String stamp=UUID.randomUUID().toString();
		for(Object o: dat.keySet()){
			System.out.println("----->"+((Sitio)o).getNombre());
			ObjetoSistemaIF oo=(ObjetoSistemaIF) o;
			OperationLog operationLog=new OperationLog(objeto,oo.getId(),Acciones.CREATE.name(),usuario.getId(),new Date(),stamp);
			
			if(oo.getId()!=null){
				operationLog.setOperation(Acciones.UPDATE.name());
				if(actualizaObjeto){
					fileUploadDao.update(o);
					operationLogDao.save(operationLog);
				}
			}else{
				fileUploadDao.insert(o);
				operationLog.setObjetoId(((ObjetoSistemaIF)(o)).getId());
				operationLogDao.save(operationLog);
			}						
			
		   //DATA
			List<Data> datumList=dat.get(o);
			dataService.saveData(datumList, o, usuario, stamp);
		}   
	}
	
	public void procesarMetadata(List<String> csv, String objeto, List<String> errores){
		if(csv.size()<1) errores.add("Empty file");
		String metadata=csv.get(0);
		String pattern = "^(.*)->";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(metadata);
	    if (m.find( )) {
	    	if(m.group(1).equalsIgnoreCase(objeto)){
	    	        csv.set(0,m.replaceAll(""));
	    	     
	    	}else{
	    		errores.add("El objeto seleccionado:"+objeto+" no corresponde con el metadata");
	    	}
	    } else {
	    	 errores.add("En metadata no se incluye el objeto , ejemplo: "+ objeto +"->aaa,bbb,bbb... "); 
	    }
	}
}
