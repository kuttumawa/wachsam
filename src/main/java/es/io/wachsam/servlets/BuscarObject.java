package es.io.wachsam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import es.io.wachsam.model.Alert;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Recurso;
import es.io.wachsam.model.ResultadoBusqueda;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.services.AlertService;
import es.io.wachsam.services.RecursoService;
import es.io.wachsam.services.SitioService;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class BuscarObject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscarObject() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		final Gson gson=new Gson();
	    final Gson prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String object=request.getParameter("object");
		String filter=request.getParameter("filter");
		ResultadoBusqueda res=new ResultadoBusqueda();
		
		
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> filterMap = gson.fromJson(filter,type);
		int pageSize=20;
		int pageNum=Integer.parseInt(filterMap.get("page"));
		int totalResults=0;
		if(object.equalsIgnoreCase(ObjetoSistema.Alert.name())){
			AlertService alertService=(AlertService) context.getBean("alertService");	
			totalResults=alertService.getNumeroTotalSitios(filterMap);
			List<Alert> alerts=alertService.getAlertasMysql(filterMap,pageNum,pageSize);
			for(Alert _alert:alerts){
				_alert.getFechaPubFormattedForDateHtmlInput();
			}
			res.setData(alerts.toArray());			
		}else if(object.equalsIgnoreCase(ObjetoSistema.Sitio.name())){
			SitioService sitioService=(SitioService) context.getBean("sitioService");	
			totalResults=sitioService.getNumeroTotalSitios(filterMap);
			List<Sitio> sitios=sitioService.getSitios(filterMap,pageNum,pageSize);
			res.setData(sitios.toArray());		
		}else if(object.equalsIgnoreCase(ObjetoSistema.Recurso.name())){
			RecursoService recursoService=(RecursoService) context.getBean("recursoService");	
			totalResults=recursoService.getNumeroTotalRecursos(filterMap);
			List<Recurso> recursos=recursoService.getRecursos(filterMap,pageNum,pageSize);
			res.setData(recursos.toArray());		
		}
		int numpages=(int) Math.ceil(totalResults/pageSize);
		res.setNumpages(numpages);
		res.setTotalResults(totalResults);
		res.setCurrentpage(pageNum);
		out.println(prettyGson.toJson(res));	
		
	}

}
