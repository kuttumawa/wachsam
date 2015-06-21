package es.io.wachsam.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.model.Lugar;


/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalLugarUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalLugarUpdaterForYou() {
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
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		
		String lugarId=request.getParameter("lugar");
		Lugar lugar=new Lugar();
		if(lugarId!=null && lugarId.length()>0){
			lugar=lugarDao.getLugar(Long.parseLong(lugarId));
		}
		request.setAttribute("lugar",lugar);
		String nextJSP = "/ioUpdaterLugar.jsp";
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
		String nombreEn=request.getParameter("nombreEn");
		String padre1=request.getParameter("padre1");
		String padre2=request.getParameter("padre2");
		String padre3=request.getParameter("padre3");		
		String latitud=request.getParameter("latitud");
		String longitud=request.getParameter("longitud");
		String nivel=request.getParameter("nivel");
		String oper=request.getParameter("oper");
		
		Lugar lugar=new Lugar();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				LugarDao lugarDao=(LugarDao) context.getBean("lugarDao");
				try {
					lugarDao.deleteById(Long.parseLong(id));
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
			lugar=new Lugar(id,nombre,nombreEn,padre1,padre2,padre3,latitud,longitud,nivel);
			LugarDao lugarDao=(LugarDao) context.getBean("lugarDao");
			lugarDao.save(lugar);
			request.setAttribute("resultado","INSERTADO OK: " + lugar);
		}else{
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("lugar",lugar);
		
		
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		String nextJSP = "/ioUpdaterLugar.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String nombreEn=request.getParameter("nombreEn");
		String padre1=request.getParameter("padre1");
		String padre2=request.getParameter("padre2");
		String padre3=request.getParameter("padre3");		
		String latitud=request.getParameter("latitud");
		String longitud=request.getParameter("longitud");
		String nivel=request.getParameter("nivel");
		
		if(nombre==null || nombre.length()<1) resultado.append("Nombre Obligatorio;");
		if(padre1==null || padre1.length()<1) resultado.append("Padre1 Obligatorio;");
		if(latitud==null || latitud.length()<1) resultado.append("Latitud Obligatorio;");
		if(longitud==null || longitud.length()<1) resultado.append("Longitud Obligatorio;");
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
