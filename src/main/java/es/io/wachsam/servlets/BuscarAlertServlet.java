package es.io.wachsam.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class BuscarAlertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscarAlertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		
		if(request.getSession().getAttribute("user")==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		
		String nextJSP = "/buscarAlert.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		if(request.getSession().getAttribute("user")==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		
		String nombre=request.getParameter("nombre")!=null && request.getParameter("nombre").length()>1 ?request.getParameter("nombre"):null;
		String texto=request.getParameter("texto")!=null && request.getParameter("texto").length()>1 ?request.getParameter("texto"):null;
		String tipo=request.getParameter("tipo")!=null && request.getParameter("tipo").length()>1 ?request.getParameter("tipo"):null;
		String lugar=request.getParameter("lugar")!=null && request.getParameter("lugar").length()>1 ?request.getParameter("lugar"):null;
		String fechaPub=request.getParameter("fechaPubDesde")!=null && request.getParameter("fechaPubDesde").length()>1 ?request.getParameter("fechaPubDesde"):null;
		String lugarObj=request.getParameter("lugarObj")!=null && request.getParameter("lugarObj").length()>1 ?request.getParameter("lugarObj"):null;
		String peligro=request.getParameter("peligro")!=null && request.getParameter("peligro").length()>1 ?request.getParameter("peligro"):null;
		String caducidad=request.getParameter("caducidad")!=null && request.getParameter("caducidad").length()>1 ?request.getParameter("caducidad"):null;
		
		Date fechaDesde=null;
		String order=null;
		AlertasDao alertDao=(AlertasDao) context.getBean("alertasDao");
		List<Alert> alerts=alertDao.getAlertasMysql(texto, lugar, fechaDesde, tipo, order);
		request.setAttribute("alertas",alerts);
		
		
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		
		String nextJSP = "/buscarAlert.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String texto=request.getParameter("texto");
		String text=request.getParameter("text");
		String tipo=request.getParameter("tipo");
		String lugar=request.getParameter("lugar");
		String fechaPub=request.getParameter("fechaPub");
		//String lugarObj=request.getParameter("lugarObj");
		String peligro=request.getParameter("peligro");
		
		if(texto==null || texto.length()<1) resultado.append(" Texto Obligatorio;");
		if(tipo==null || tipo.length()<1) resultado.append(" Tipo Obligatorio;");
		if(lugar==null || lugar.length()<1) resultado.append(" Lugar Obligatorio;");
		if(peligro==null || peligro.length()<1) resultado.append(" Peligro Obligatorio;");
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
