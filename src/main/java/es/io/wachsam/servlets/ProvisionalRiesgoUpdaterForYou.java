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
import es.io.wachsam.dao.RiesgoDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Riesgo;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.LugarService;
import es.io.wachsam.services.RiesgoService;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalRiesgoUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalRiesgoUpdaterForYou() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario=(Usuario)request.getSession().getAttribute("user");
		if(usuario==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		RiesgoDao riesgoDao = (RiesgoDao) context.getBean("riesgoDao");
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAllPorPaísOrdenadosPorNivelyNombre();
		request.setAttribute("lugares",lugares);
		
		String peligroId=request.getParameter("peligroId");
		
		List<Riesgo> riesgos=new ArrayList<Riesgo>();
		if(peligroId!=null && peligroId.length()>0){
			riesgos=riesgoDao.getRiesgosFromPeligro(Long.parseLong(peligroId));			
		}
		request.setAttribute("riesgos",riesgos);
		String nextJSP = "/ioUpdaterRiesgo.jsp";
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
		RiesgoService riesgoService=(RiesgoService) context.getBean("riesgoService");
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String nombreEn=request.getParameter("nombreEn");
		String categoria=request.getParameter("categoria");
		String damage=request.getParameter("damage");
		String oper=request.getParameter("oper");
		
		Riesgo riesgo=new Riesgo();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				try {
					riesgoService.deleteById(Long.parseLong(id),usuario);
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
			riesgo=new Riesgo();
			try {
				riesgoService.save(riesgo,usuario);
				request.setAttribute("resultado","INSERTADO OK: " + riesgo);
			} catch (NoAutorizadoException e) {
				request.setAttribute("resultado","No tienes permisos para la operacion");
			}
			
			
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("riesgo",riesgo);
		RiesgoDao riesgoDao = (RiesgoDao) context.getBean("riesgoDao");
		List<Riesgo> riesgos =riesgoDao.getAll();
		request.setAttribute("riesgos",riesgos);
		
		List<Data> datas=new ArrayList<Data>();
		if(riesgo.getId()!=null){
			DataDao dataDao = (DataDao) context.getBean("dataDao");
			Data filtro=new Data();
			filtro.setObjetoId(riesgo.getId());
			filtro.setObjetoConnected(ObjetoSistema.Riesgo);
			datas=dataDao.getAll(filtro);
			request.setAttribute("datas",datas);
		}
		
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		String nextJSP = "/ioUpdaterRiesgo.jsp";
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
