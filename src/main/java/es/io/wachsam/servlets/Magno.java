package es.io.wachsam.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
	 WebApplicationContext context;
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
		  //request.setCharacterEncoding("UTF-8");
		  context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		  response.setContentType("text/html");
		  PrintWriter out = response.getWriter();
		  AlertService service=(AlertService) context.getBean("alertService");
	      AlertasDao dao = (AlertasDao) context.getBean("alertasDao"); 
	      
	      service.startDB();
	      
	      
	      String callback=request.getParameter("callback");
	      String texto=(request.getParameter("texto")!=null?new String(request.getParameter("texto").getBytes(),"UTF-8"):null);
	      String lugar=(request.getParameter("lugar")!=null?new String(request.getParameter("lugar").getBytes(),"UTF-8"):null);
	      String fecha=request.getParameter("fecha");
	      String tipo=request.getParameter("tipo");
	      String order=request.getParameter("order");
	      String ultimosdias=request.getParameter("ultimosdias");
	      Date fechapub=null;
	      if(ultimosdias!=null && ultimosdias.matches("\\d+")){
	    	  Calendar calendar = Calendar.getInstance(); 
	    	  calendar.add(Calendar.DAY_OF_YEAR,new Integer("-" + ultimosdias));
	    	  fechapub=calendar.getTime();
	      }
	      if(fecha!=null && fecha.length()>0){
	      try{
			   fechapub=new SimpleDateFormat("ddMMyyyy").parse(fecha);
			}catch(Exception e){
			   //void
			}
	      }
	      List<Alert> alerts = dao.getAlertas(texto,lugar,fechapub,tipo,order);
	      for(Alert a:alerts){
	    	  a.setFechaPubFormatted();
	      }
	      final Gson gson=new Gson();
	      final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
	     // System.out.println("----------------------------------------------------");
	     // System.out.println(prettyGson.toJson(alerts));
	     // System.out.println("----------------------------------------------------");
	      
		  if(callback!=null)
	      out.println(callback + "("+prettyGson.toJson(alerts)+")");
		  else out.println(prettyGson.toJson(alerts));
			  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
