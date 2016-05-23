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

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.io.wachsam.dao.AirportDao;
import es.io.wachsam.model.Airport;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Tag;
import es.io.wachsam.services.AlertService;
import es.io.wachsam.services.DataService;
import es.io.wachsam.services.PeligroService;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class PeligroServletJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PeligroServletJSON() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		PeligroService peligroService=(PeligroService) context.getBean("peligroService");
		final Gson gson=new Gson();
	    final Gson prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	   //http://localhost:8080/wachsam/DataServletJSON?objetoId=1376424326&objetoTipo=0&oper=getAllForObject
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String oper=request.getParameter("oper");
		List<Peligro> peligros=new ArrayList<Peligro>();
		
		if(oper!=null){
			if(oper.equalsIgnoreCase("getAll")){
				peligros=peligroService.getAll();
			}
			
		}
		 out.println(prettyGson.toJson(peligros));
	}

	

}
