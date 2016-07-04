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

import es.io.wachsam.dao.TipoSitioDao;
import es.io.wachsam.model.TipoSitio;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalTipoSitioUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalTipoSitioUpdaterForYou() {
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
		TipoSitioDao tipoSitioDao = (TipoSitioDao) context.getBean("tipoSitioDao");
		List<TipoSitio> tipoSitios =tipoSitioDao.getAll();
		request.setAttribute("tipoSitios",tipoSitios);
		
		String tipoSitioId=request.getParameter("tipoSitio");
		TipoSitio tipoSitio=new TipoSitio();
		if(tipoSitioId!=null && tipoSitioId.length()>0){
			tipoSitio=tipoSitioDao.getTipoSitio(Long.parseLong(tipoSitioId));
		}
		request.setAttribute("tipoSitio",tipoSitio);
		String nextJSP = "/ioUpdaterTipoSitio.jsp";
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
		
		String oper=request.getParameter("oper");
		
		TipoSitio tipoSitio=new TipoSitio();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				TipoSitioDao tipoSitioDao=(TipoSitioDao) context.getBean("tipoSitioDao");
				try {
					tipoSitioDao.deleteById(Long.parseLong(id));
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
			tipoSitio=new TipoSitio(id,nombre,descripcion);
			TipoSitioDao tipoSitioDao=(TipoSitioDao) context.getBean("tipoSitioDao");
			tipoSitioDao.save(tipoSitio);
			
			request.setAttribute("resultado","INSERTADO OK: " + tipoSitio);
		}else{
	     	request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("tipoSitio",tipoSitio);
		TipoSitioDao tipoSitioDao = (TipoSitioDao) context.getBean("tipoSitioDao");
		List<TipoSitio> tipoSitios =tipoSitioDao.getAll();
		request.setAttribute("tipoSitios",tipoSitios);
		
		
		String nextJSP = "/ioUpdaterTipoSitio.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String descripcion=request.getParameter("descripcion");
		
		
		if(nombre==null || nombre.length()<1) resultado.append("Nombre Obligatorio;");
		
		
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
