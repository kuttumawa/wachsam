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
import es.io.wachsam.model.Data;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.services.DataService;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class DataServletJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataServletJSON() {
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
		DataService dataService=(DataService) context.getBean("dataService");
		final Gson gson=new Gson();
	    final Gson prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	   //http://localhost:8080/wachsam/DataServletJSON?objetoId=1376424326&objetoTipo=0&oper=getAllForObject
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String oper=request.getParameter("oper");
		String _objetoId =request.getParameter("objetoId");
		String _objetoTipo =request.getParameter("objetoTipo");
		Long objetoId =null;
		ObjetoSistema objetoTipo=null;
		try{
		  objetoId = Long.parseLong(_objetoId);
		  objetoTipo = ObjetoSistema.values()[Integer.parseInt(_objetoTipo)];
		 	
		}catch(Exception e){
			out.println(prettyGson.toJson(e));
		}
		List<Data> datas=new ArrayList<Data>();
		if(oper!=null){
			if(oper.equalsIgnoreCase("getAllForObject")){
				datas=dataService.getAllForObject(objetoId, objetoTipo);
			}
			
		}
		 out.println(prettyGson.toJson(datas));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		request.getCharacterEncoding();
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String city=request.getParameter("city");
		String country=request.getParameter("country");
		String IATA_FAA=request.getParameter("IATA_FAA");
		String ICAO=request.getParameter("ICAO");
		String latitud=request.getParameter("latitud");
		String logitud=request.getParameter("logitud");
		String altitud=request.getParameter("altitud");
		String timezone=request.getParameter("timezone");
		String DST=request.getParameter("DST");
		String TZ=request.getParameter("TZ");
		String oper=request.getParameter("oper");
	
		
		Airport airport=new Airport();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				AirportDao airportDao=(AirportDao) context.getBean("airportDao");
				try {
					airportDao.deleteById(Long.parseLong(id));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("resultado","Borrado Correcto");
			}else{
				request.setAttribute("resultado","Error al borrar");
			}
			
		}else if(validar(request)==null){
			airport=new Airport(id,nombre,city,country,IATA_FAA,ICAO,latitud,logitud,altitud,timezone,DST,TZ);
			AirportDao airportDao=(AirportDao) context.getBean("airportDao");
			airportDao.save(airport);
			
			request.setAttribute("resultado","INSERTADO OK: " + airport);
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("airport",airport);
		AirportDao airportDao = (AirportDao) context.getBean("airportDao");
		List<Airport> airports =airportDao.getAll();
		request.setAttribute("airports",airports);
		
		
		String nextJSP = "/ioUpdaterAirport.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String city=request.getParameter("city");
		String country=request.getParameter("country");
		String IATA_FAA=request.getParameter("IATA_FAA");
		String ICAO=request.getParameter("ICAO");
		String latitud=request.getParameter("latitud");
		String logitud=request.getParameter("logitud");
		String altitud=request.getParameter("altitud");
		String timezone=request.getParameter("timezone");
		String DST=request.getParameter("DST");
		
		if(nombre==null || nombre.length()<1) resultado.append("Nombre Obligatorio;");
		
		
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
