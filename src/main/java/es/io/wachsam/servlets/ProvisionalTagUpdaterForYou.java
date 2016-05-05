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
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Tag;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalTagUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalTagUpdaterForYou() {
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
		TagDao tagDao = (TagDao) context.getBean("tagDao");
		List<Tag> tags =tagDao.getAll();
		request.setAttribute("tags",tags);
		
		String tagId=request.getParameter("tag");
		Tag tag=new Tag();
		if(tagId!=null && tagId.length()>0){
			tag=tagDao.getTag(Long.parseLong(tagId));
		}
		request.setAttribute("tag",tag);
		String nextJSP = "/ioUpdaterTag.jsp";
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
		String alias=request.getParameter("alias");
		String nombre=request.getParameter("nombre");
		String nombreEn=request.getParameter("nombreEn");
		String descripcion=request.getParameter("descripcion");
		String oper=request.getParameter("oper");
		
		
		Tag tag=new Tag();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				TagDao tagDao=(TagDao) context.getBean("tagDao");
				try {
					tagDao.deleteById(Long.parseLong(id));
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
			tag=new Tag();
			tag.setDescripcion(descripcion);
			try{
			  if(id!=null)tag.setId(Long.parseLong(id));
			}catch(Exception e){
				
			}
			tag.setAlias(alias.toLowerCase());
			tag.setNombre(nombre);
			tag.setNombreEn(nombreEn);
			TagDao tagDao=(TagDao) context.getBean("tagDao");
			tagDao.save(tag);
			
			request.setAttribute("resultado","INSERTADO OK: " + tag);
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("tag",tag);
		TagDao tagDao = (TagDao) context.getBean("tagDao");
		List<Tag> tags =tagDao.getAll();
		request.setAttribute("tags",tags);
		
		
		String nextJSP = "/ioUpdaterTag.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String alias=request.getParameter("alias");
		String nombre=request.getParameter("nombre");
		String nombreEn=request.getParameter("nombreEn");
		String descripcion=request.getParameter("descripcion");
		String oper=request.getParameter("oper");
		if(alias==null || alias.length()<1) resultado.append("Alias Obligatorio;");
		if(nombre==null || nombre.length()<1) resultado.append("Nombre Obligatorio;");
		if(nombreEn==null || nombreEn.length()<1) resultado.append("Nombre in English Obligatorio;");
		
		
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
