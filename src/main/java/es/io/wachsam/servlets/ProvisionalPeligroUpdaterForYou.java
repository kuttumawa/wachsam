package es.io.wachsam.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.dao.PeligroDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.LugarService;
import es.io.wachsam.services.PeligroService;

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
		if(request.getSession().getAttribute("user")==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		
		String peligroId=request.getParameter("peligro");
		List<Data> datas=new ArrayList<Data>();
		Peligro peligro=new Peligro();
		if(peligroId!=null && peligroId.length()>0){
			peligro=peligroDao.getPeligro(Long.parseLong(peligroId));
			DataDao dataDao = (DataDao) context.getBean("dataDao");
			Data filtro=new Data();
			filtro.setSubjectId(peligro.getId());
			datas=dataDao.getAllnoExtrict(filtro);
			request.setAttribute("datas",datas);
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
		Usuario usuario=(Usuario)request.getSession().getAttribute("user");
		if(usuario==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		PeligroService peligroService=(PeligroService) context.getBean("peligroService");
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String nombreEn=request.getParameter("nombreEn");
		String categoria=request.getParameter("categoria");
		String damage=request.getParameter("damage");
		String oper=request.getParameter("oper");
		
		Peligro peligro=new Peligro();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				try {
					peligroService.deleteById(Long.parseLong(id),usuario);
					request.setAttribute("resultado","Borrado Correcto");
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (NoAutorizadoException e) {
					request.setAttribute("resultado","No tienes permisos para la operacion");
				} catch (Throwable e) {
					e.printStackTrace();
				}
				
			}else{
				request.setAttribute("resultado","Error al borrar");
			}
			
		}else if(validar(request)==null){
			peligro=new Peligro(id,nombre,nombreEn,categoria,damage);
			try {
				peligroService.save(peligro,usuario);
				request.setAttribute("resultado","INSERTADO OK: " + peligro);
			} catch (NoAutorizadoException e) {
				request.setAttribute("resultado","No tienes permisos para la operacion");
			}
			
			
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
