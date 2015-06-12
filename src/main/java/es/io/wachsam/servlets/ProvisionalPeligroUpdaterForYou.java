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
public class ProvisionalPeligroUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalPeligroUpdaterForYou() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		
		String peligroId=request.getParameter("peligro");
		Peligro peligro=new Peligro();
		if(peligroId!=null && peligroId.length()>0){
			peligro=peligroDao.getPeligro(Long.parseLong(peligroId));
		}
		request.setAttribute("peligro",peligro);
		String nextJSP = "/ioUpdaterPeligro.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		request.getCharacterEncoding();
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String nombreEn=request.getParameter("nombreEn");
		String categoria=request.getParameter("categoria");
		String damage=request.getParameter("damage");
		String oper=request.getParameter("oper");
		
		Peligro peligro=new Peligro();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				PeligroDao peligroDao=(PeligroDao) context.getBean("peligroDao");
				try {
					peligroDao.deleteById(Long.parseLong(id));
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
			peligro=new Peligro(id,nombre,nombreEn,categoria,damage);
			PeligroDao peligroDao=(PeligroDao) context.getBean("peligroDao");
			peligroDao.save(peligro);
			
			request.setAttribute("resultado","INSERTADO OK: " + peligro);
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("peligro",peligro);
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		String nextJSP = "/ioUpdaterPeligro.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String nombreEn=request.getParameter("nombreEn");
		String categoria=request.getParameter("categoria");
		String damage=request.getParameter("damage");
		
		if(nombre==null || nombre.length()<1) resultado.append("Nombre Obligatorio;");
		if(categoria==null || categoria.length()<1) resultado.append("Categoría Obligatorio;");
		if(damage==null || damage.length()<1) resultado.append("Daño Obligatorio;");
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
