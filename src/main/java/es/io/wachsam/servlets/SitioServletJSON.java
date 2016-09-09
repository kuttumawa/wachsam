package es.io.wachsam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.TipoSitio;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.SitioService;
import es.io.wachsam.services.TipoSitioService;


/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class SitioServletJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SitioServletJSON() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		Usuario usuario=(Usuario)request.getSession().getAttribute("user");
		if(usuario==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		SitioService sitioService=(SitioService) context.getBean("sitioService");
		final Gson gson=new Gson();
	    final Gson prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String oper=request.getParameter("oper");
		String _id=request.getParameter("id");
		List<Sitio> sitios=new ArrayList<Sitio>();
		
		if(oper!=null){
			if(oper.equalsIgnoreCase("getAll")){
				sitios=sitioService.getAll();
				out.println(prettyGson.toJson(sitios));
			}else if(oper.equalsIgnoreCase("getAllTipoSitio")){
				List<TipoSitio> tipoSitios=new ArrayList<TipoSitio>();
				TipoSitioService tipoSitioService=(TipoSitioService) context.getBean("tipoSitioService");
				tipoSitios=tipoSitioService.getAll();
				out.println(prettyGson.toJson(tipoSitios));
			}else if(oper.equalsIgnoreCase("getSitio") && GenericValidator.isLong(_id)){
			    Long id=Long.parseLong(_id);
				Sitio sitio=sitioService.getSitio(id, usuario);
				out.println(prettyGson.toJson(sitio));
			}
			
		}
		
	}

	

}
