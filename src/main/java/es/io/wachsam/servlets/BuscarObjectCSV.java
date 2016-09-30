package es.io.wachsam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Recurso;
import es.io.wachsam.model.ResultadoBusqueda;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.AlertService;
import es.io.wachsam.services.DataService;
import es.io.wachsam.services.RecursoService;
import es.io.wachsam.services.SitioService;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class BuscarObjectCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscarObjectCSV() {
        super(); 
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		Usuario usuario=(Usuario)request.getSession().getAttribute("user");
		if(usuario==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}	
		final Gson gson=new Gson();
	    final Gson prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		response.setContentType("text/csv");
		PrintWriter out = response.getWriter();
		String object=request.getParameter("object");
		String filter=request.getParameter("filter");
		ResultadoBusqueda res=new ResultadoBusqueda();
		response.setHeader("Content-disposition", "attachment; filename=file"+ object +".csv");
		
		StringBuffer resultado=new StringBuffer();
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> filterMap = gson.fromJson(filter,type);
		DataService dataService=(DataService) context.getBean("dataService");
		if(object.equalsIgnoreCase(ObjetoSistema.Alert.name())){
			AlertService alertService=(AlertService) context.getBean("alertService");	
			List<Alert> alerts= alertService.getAllAlerts(filterMap);
			resultado.append(Alert.toCSVCabecera(";"));
			List<Tag> tags=dataService.getAllTags();
			resultado.append(";");
			for(Tag tag:tags){
				resultado.append(tag.getNombre());
				resultado.append(";");
			}
			resultado.append("\n");			
			for(Alert _alert:alerts){
				_alert.setFechaPubFormatted_ddMMyyyy();
				resultado.append(_alert.toCSV(";"));
				//Get all data for object
				Map<Long,String> x= toMap(dataService.getAllForObject(_alert.getId(), ObjetoSistema.Alert, usuario));
				for(Tag tag:tags){
					if(x.containsKey(tag.getId())) {
						resultado.append(x.get(tag.getId()));
						resultado.append(";");
					}
					else {
						resultado.append(";");
					}
				}
				resultado.append("\n");
			}
			out.println(resultado);		
		}else if(object.equalsIgnoreCase(ObjetoSistema.Sitio.name())){
			SitioService sitioService=(SitioService) context.getBean("sitioService");
			List<Sitio> sitios= sitioService.getAllSitios(filterMap);
			resultado.append(Sitio.toCSVCabecera(";"));
			resultado.append(";");
			List<Tag> tags=dataService.getAllTags();
			for(Tag tag:tags){
				resultado.append(tag.getNombre());
				resultado.append(";");
			}
			resultado.append("\n");			
			for(Sitio _sitio:sitios){
				resultado.append(_sitio.toCSV(";"));
				//Get all data for object
				Map<Long,String> x= toMap(dataService.getAllForObject(_sitio.getId(), ObjetoSistema.Sitio, usuario));
				for(Tag tag:tags){
					if(x.containsKey(tag.getId())) {
						resultado.append(x.get(tag.getId()));
						resultado.append(";");
					}
					else {
						resultado.append(";");
					}
				}
				resultado.append("\n");
			}
			out.println(resultado);		
				
		}else if(object.equalsIgnoreCase(ObjetoSistema.Recurso.name())){
			RecursoService recursoService=(RecursoService) context.getBean("recursoService");
			List<Recurso> recursos= recursoService.getAllRecursos(filterMap);
			resultado.append(Recurso.toCSVCabecera(";"));
			resultado.append(";");
			List<Tag> tags=dataService.getAllTags();
			for(Tag tag:tags){
				resultado.append(tag.getNombre());
				resultado.append(";");
			}
			resultado.append("\n");			
			for(Recurso _recurso:recursos){
				resultado.append(_recurso.toCSV(";"));
				//Get all data for object
				Map<Long,String> x= toMap(dataService.getAllForObject(_recurso.getId(), ObjetoSistema.Recurso, usuario));
				for(Tag tag:tags){
					if(x.containsKey(tag.getId())) {
						resultado.append(x.get(tag.getId()));
						resultado.append(";");
					}
					else {
						resultado.append(";");
					}
				}
				resultado.append("\n");
			}
			out.println(resultado);		
				
		}
		
			
		
	}
    
    private Map<Long,String> toMap(List<Data> data){
    	HashMap<Long,String> map=new HashMap<Long,String>();
    	for(Data datum:data){
    		map.put(datum.getTag().getId(),!GenericValidator.isBlankOrNull(datum.getValue())?datum.getValue():"x");
    	}
    	
    	return map;
    }
    
     

}
