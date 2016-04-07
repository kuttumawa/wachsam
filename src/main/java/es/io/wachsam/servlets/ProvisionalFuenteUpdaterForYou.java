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
import es.io.wachsam.dao.FuenteDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Fuente;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalFuenteUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalFuenteUpdaterForYou() {
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
		FuenteDao fuenteDao = (FuenteDao) context.getBean("fuenteDao");
		List<Fuente> fuentes =fuenteDao.getAll();
		request.setAttribute("fuentes",fuentes);
		
		String fuenteId=request.getParameter("fuente");
		Fuente fuente=new Fuente();
		if(fuenteId!=null && fuenteId.length()>0){
			fuente=fuenteDao.getFuente(Long.parseLong(fuenteId));
		}
		request.setAttribute("fuente",fuente);
		String nextJSP = "/ioUpdaterFuente.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
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
		String descripcion=request.getParameter("descripcion");
		String fiabilidad=request.getParameter("fiabilidad");
		
		String oper=request.getParameter("oper");
		
		Fuente fuente=new Fuente();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				FuenteDao fuenteDao=(FuenteDao) context.getBean("fuenteDao");
				try {
					fuenteDao.deleteById(Long.parseLong(id));
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
			fuente=new Fuente(id,nombre,descripcion,fiabilidad);
			FuenteDao fuenteDao=(FuenteDao) context.getBean("fuenteDao");
			fuenteDao.save(fuente);
			
			request.setAttribute("resultado","INSERTADO OK: " + fuente);
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("fuente",fuente);
		FuenteDao fuenteDao = (FuenteDao) context.getBean("fuenteDao");
		List<Fuente> fuentes =fuenteDao.getAll();
		request.setAttribute("fuentes",fuentes);
		
		
		String nextJSP = "/ioUpdaterFuente.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		
		
		if(nombre==null || nombre.length()<1) resultado.append("Nombre Obligatorio;");
		
		
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
