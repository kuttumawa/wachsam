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

import es.io.wachsam.dao.UsuarioDao;
import es.io.wachsam.model.Permiso;
import es.io.wachsam.model.Usuario;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalUsuarioUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalUsuarioUpdaterForYou() {
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
		List<Usuario> usuarios =usuarioDao.getAll();
		request.setAttribute("usuarios",usuarios);
		
		String usuarioId=request.getParameter("usuario");
		Usuario usuario=new Usuario();
		if(usuarioId!=null && usuarioId.length()>0){
			usuario=usuarioDao.getUsuario(Long.parseLong(usuarioId));
		}
		request.setAttribute("usuario",usuario);
		
		List<Permiso> permisos=usuarioDao.getAllPermiso();
		request.setAttribute("permisos",permisos);
		String nextJSP = "/ioUpdaterUsuario.jsp";
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
		String login=request.getParameter("login");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		String permisoId=request.getParameter("permisoId");
		String oper=request.getParameter("oper");
		UsuarioDao usuarioDao=(UsuarioDao) context.getBean("usuarioDao");
		
		Usuario usuario=new Usuario();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				try {
					usuarioDao.deleteById(Long.parseLong(id));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("resultado","Borrado Correcto");
			}else{
				request.setAttribute("resultado","Error al borrar");
			}
			
		}if(oper!=null && oper.equalsIgnoreCase("addPermiso")){
			if(id!=null && permisoId!=null){
				try {
					usuario=usuarioDao.getUsuario(Long.parseLong(id));
					Permiso permiso=usuarioDao.getPermiso(Long.parseLong(permisoId));
					usuario.addPermiso(permiso);
					usuarioDao.save(usuario);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("resultado","Permiso añadido");
			}else{
				request.setAttribute("resultado","Error al añadir Permiso");
			}
			
		}if(oper!=null && oper.equalsIgnoreCase("deletePermiso")){
			if(id!=null && permisoId!=null){
				try {
					usuario=usuarioDao.getUsuario(Long.parseLong(id));
					Permiso permiso=usuarioDao.getPermiso(Long.parseLong(permisoId));
					usuario.removePermiso(permiso);
					usuarioDao.save(usuario);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("resultado","Permiso eliminado");
			}else{
				request.setAttribute("resultado","Error al eliminar Permiso");
			}
			
		}else if(validar(request)==null){
			usuario=new Usuario();
			try{
			  if(id!=null)usuario.setId(Long.parseLong(id));
			}catch(Exception e){
				
			}
			usuario.setLogin(login);
			usuario.setPassword(password);
			usuario.setEmail(email);
			usuarioDao.save(usuario);
			
			request.setAttribute("resultado","INSERTADO OK: " + usuario);
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("usuario",usuario);
		List<Usuario> usuarios =usuarioDao.getAll();
		request.setAttribute("usuarios",usuarios);
		
		List<Permiso> permisos=usuarioDao.getAllPermiso();
		request.setAttribute("permisos",permisos);
		
		String nextJSP = "/ioUpdaterUsuario.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String login=request.getParameter("login");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		String oper=request.getParameter("oper");
		if(login==null || login.length()<1) resultado.append("Login Obligatorio;");
		if(password==null || password.length()<1) resultado.append("Password Obligatorio;");
		if(email==null || email.length()<1) resultado.append("Email in English Obligatorio;");
		
		
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
