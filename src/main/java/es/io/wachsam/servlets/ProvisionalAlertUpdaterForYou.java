package es.io.wachsam.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.dao.PeligroDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Peligro;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalAlertUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalAlertUpdaterForYou() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		
		AlertasDao alertasDao = (AlertasDao) context.getBean("alertasDao");
		List<Alert> alertas =alertasDao.getAll();
		request.setAttribute("alertas",alertas);
		
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		
		String alertId=request.getParameter("alert");
		Alert alert=new Alert();
		if(alertId!=null && alertId.length()>0){
			alert=alertasDao.getAlert(Long.parseLong(alertId));
		}
		request.setAttribute("alert",alert);
		
		String nextJSP = "/ioUpdaterAlert.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		request.getCharacterEncoding();
		String nombre=request.getParameter("nombre");
		String texto=request.getParameter("texto");
		String text=request.getParameter("text");
		String link1=request.getParameter("link1");
		String link2=request.getParameter("link2");
		String link3=request.getParameter("link3");
		String tipo=request.getParameter("tipo");
		String lugar=request.getParameter("lugar");
		String fechaPub=request.getParameter("fechaPub");
		String lugarObj=request.getParameter("lugarObj");
		String peligro=request.getParameter("peligro");
		
		if(validar(request)==null){
			String[] _alert={null,nombre,tipo,link1,link2,link3,texto,text,null,fechaPub,lugar,peligro};
			Alert alert=Alert.createAlertSinId(_alert);
			System.out.println(alert);
			AlertasDao alertDao=(AlertasDao) context.getBean("alertasDao");
			alertDao.save(alert);
			request.setAttribute("resultado","INSERTADO OK: " + alert.toStringLite());
		}else{
			request.setAttribute("resultado",validar(request));
		}
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		String nextJSP = "/ioUpdaterAlert.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String nombre=request.getParameter("nombre");
		String texto=request.getParameter("texto");
		String text=request.getParameter("text");
		String link1=request.getParameter("link1");
		String link2=request.getParameter("link2");
		String link3=request.getParameter("link3");
		String tipo=request.getParameter("tipo");
		String lugar=request.getParameter("lugar");
		String fechaPub=request.getParameter("fechaPub");
		String lugarObj=request.getParameter("lugarObj");
		String peligro=request.getParameter("peligro");
		
		if(nombre==null || nombre.length()<1) resultado.append("Nombre Obligatorio;");
		if(texto==null || texto.length()<1) resultado.append("Texto Obligatorio;");
		if(tipo==null || tipo.length()<1) resultado.append("Tipo Obligatorio;");
		if(lugarObj==null) resultado.append("Lugar Obligatorio;");
		if(peligro==null) resultado.append("Peligro Obligatorio;");
		if(fechaPub==null  || fechaPub.length()<1) resultado.append("Fecha Obligatorio;");
		if(fechaPub!=null && fechaPub.length()>0){
		      try{
				   new SimpleDateFormat("dd/MM/yyyy").parse(fechaPub);
				}catch(Exception e){
					 resultado.append("Fecha Formato Erróneo");
				}
		      }
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
