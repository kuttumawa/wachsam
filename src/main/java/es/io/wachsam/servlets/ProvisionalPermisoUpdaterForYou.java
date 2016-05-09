package es.io.wachsam.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.io.wachsam.dao.AlertasDao;
import es.io.wachsam.dao.LugarDao;
import es.io.wachsam.dao.UsuarioDao;
import es.io.wachsam.model.AccionesSobreObjetosTipos;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Permiso;
import es.io.wachsam.model.TipoSitio;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalPermisoUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalPermisoUpdaterForYou() {
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
		UsuarioDao usuarioDao = (UsuarioDao) context.getBean("usuarioDao");
		List<Permiso> permisos =usuarioDao.getAllPermiso();
		request.setAttribute("permisos",permisos);
		
		String permisoId=request.getParameter("permiso");
		Permiso permiso=new Permiso();
		if(permisoId!=null && permisoId.length()>0){
			permiso=usuarioDao.getPermiso(Long.parseLong(permisoId));
		}
		request.setAttribute("permiso",permiso);
		String nextJSP = "/ioUpdaterPermiso.jsp";
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
		String accion=request.getParameter("accion");
		String objeto=request.getParameter("objeto");
		String filtroFlag=request.getParameter("filtroFlag");
		String filtro=request.getParameter("filtro");
		String oper=request.getParameter("oper");
		
		
		Permiso permiso=new Permiso();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				UsuarioDao usuarioDao=(UsuarioDao) context.getBean("usuarioDao");
				try {
					usuarioDao.deleteByIdPermiso(Long.parseLong(id));
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
			permiso=new Permiso();
			try{
			  if(id!=null)permiso.setId(Long.parseLong(id));
			}catch(Exception e){
				
			}
			permiso.setObjeto(objeto);
			permiso.setAccion(AccionesSobreObjetosTipos.values()[Integer.parseInt(accion)]);
			permiso.setNombre("VOID");
			permiso.setFiltro(filtro);
			permiso.setFiltroFlag(filtroFlag!=null && filtroFlag.length()>0?true:false);
			UsuarioDao usuarioDao=(UsuarioDao) context.getBean("usuarioDao");
			usuarioDao.savePermiso(permiso);
			
			request.setAttribute("resultado","INSERTADO OK: " + permiso);
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("permiso",permiso);
		UsuarioDao usuarioDao = (UsuarioDao) context.getBean("usuarioDao");
		List<Permiso> permisos =usuarioDao.getAllPermiso();
		request.setAttribute("permisos",permisos);
		
		
		String nextJSP = "/ioUpdaterPermiso.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String accion=request.getParameter("accion");
		String objeto=request.getParameter("objeto");
		String oper=request.getParameter("oper");
		String filtroFlag=request.getParameter("filtroFlag");
		String filtro=request.getParameter("filtro");
		if(objeto==null || objeto.length()<1) resultado.append("Objeto Obligatorio;");
		if(accion==null || accion.length()<1) resultado.append("Accion Obligatorio;");
		if(filtro!=null && filtro.length()>0){
			try{
				JSONObject obj = new JSONObject(filtro);
			}catch(Exception e){
				resultado.append("En filtro: bad JSON format");
			}
		}
		
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
