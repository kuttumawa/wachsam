package es.io.wachsam.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.services.AlertService;

/**
 * Servlet implementation class Magno
 */
public class Magno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClassPathXmlApplicationContext context;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Magno() {
        super();
      
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * http://alexmarandon.com/articles/web_widget_jquery/
	 * http://web.ontuts.com/tutoriales/jsonp-llamadas-ajax-entre-dominios/
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context = new 
                ClassPathXmlApplicationContext("applicationContext.xml");
		  String message="Hello ";
		  response.setContentType("text/html");
		  PrintWriter out = response.getWriter();
		  AlertService service=(AlertService) context.getBean("alertService");
	      AlertasDao dao = (AlertasDao) context.getBean("alertasDao"); 
	      
	      InputStream is=getClass().getClassLoader().getResourceAsStream("/alert.csv");
	      service.startDB(is);
	      
	      
	      String callback=request.getParameter("callback");
	      String texto=request.getParameter("texto");
	      String lugar=request.getParameter("lugar");
	      String fecha=request.getParameter("fecha");
	      String tipo=request.getParameter("fecha");//ddmmyyyy
	      Date fechapub=null;
	      if(fecha!=null && fecha.length()>0){
	      try{
			   fechapub=new SimpleDateFormat("ddmmyyyy").parse(fecha);
			}catch(Exception e){
			   //void
			}
	      }
	      List<Alert> alerts = dao.getAlertas(texto,lugar,fechapub,null);
	      final Gson gson=new Gson();
	      final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
	      System.out.println("----------------------------------------------------");
	      System.out.println(prettyGson.toJson(alerts));
	      System.out.println("----------------------------------------------------");
	      context.close(); 
		 
	     out.println(callback + "("+prettyGson.toJson(alerts)+")");
	     //out.println(callback + "('XXXXXXXXXXX')");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
