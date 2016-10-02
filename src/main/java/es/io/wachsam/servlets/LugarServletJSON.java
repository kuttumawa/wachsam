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
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.AlertService;
import es.io.wachsam.services.DataService;
import es.io.wachsam.services.LugarService;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class LugarServletJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LugarServletJSON() {
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
		LugarService lugarService=(LugarService) context.getBean("lugarService");
		final Gson gson=new Gson();
	    final Gson prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	   //http://localhost:8080/wachsam/DataServletJSON?objetoId=1376424326&objetoTipo=0&oper=getAllForObject
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String oper=request.getParameter("oper");
		String lugarId=request.getParameter("lugarId");
		List<Lugar> lugares=new ArrayList<Lugar>();
		
		if(oper!=null){
			if(oper.equalsIgnoreCase("getAll")){
				lugares=lugarService.getAll();
				out.println(prettyGson.toJson(lugares));
			}else if(oper.equalsIgnoreCase("getJerarquia")){
				Lugar lugar=lugarService.getLugar(Long.parseLong(lugarId), usuario);
				String res=lugarService.getAllLugaresConJerarquiaDeJSON(lugar);
				out.println(res);
			}
			
		}
		 
	}

	

}
