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
import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.PeligroDao;
import es.io.wachsam.dao.SitioDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Factor;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.DataService;
import es.io.wachsam.services.LugarService;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalDataUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalDataUpdaterForYou() {
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
		DataDao dataDao = (DataDao) context.getBean("dataDao");
		List<Data> datas =dataDao.getAll();
		request.setAttribute("datas",datas);
		
		AlertasDao alertasDao = (AlertasDao) context.getBean("alertasDao");
		List<Alert> alertas =alertasDao.getAll();
		request.setAttribute("alertas",alertas);
		
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		
		TagDao tagDao = (TagDao) context.getBean("tagDao");
		List<Tag> tags =tagDao.getAll();
		request.setAttribute("tags",tags);
		
		SitioDao sitioDao = (SitioDao) context.getBean("sitioDao");
		List<Sitio> sitios =sitioDao.getAll();
		request.setAttribute("sitios",sitios);
		
		String subject=request.getParameter("subjectId");
		String evento=request.getParameter("eventoId");
		String lugar=request.getParameter("lugarId");
		String sitio=request.getParameter("sitioId");		
		String dataId=request.getParameter("dataId");
		Data data=new Data();
		if(sitio!=null) data.setSitioId(Long.parseLong(sitio));
		if(subject!=null) data.setSubjectId(Long.parseLong(subject));
		if(evento!=null) data.setEventoId(Long.parseLong(evento));
		if(lugar!=null) data.setLugarId(Long.parseLong(lugar));
		if(dataId!=null && dataId.length()>0){
			data=dataDao.getData(Long.parseLong(dataId));
		}
		request.setAttribute("data",data);
		String nextJSP = "/ioUpdaterData.jsp";
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
		DataService dataService=(DataService) context.getBean("dataService");
		request.getCharacterEncoding();
		String id=request.getParameter("id");
		String value=request.getParameter("value");
		String descripcion=request.getParameter("descripcion");
		String tipoValor=request.getParameter("tipoValor");
		String subject=request.getParameter("subjectId");
		String evento=request.getParameter("eventoId");
		String lugar=request.getParameter("lugarId");
		String sitio=request.getParameter("sitioId");
		Tag tag1 = null;
		try{
		tag1=request.getParameter("tag1")!=null?Tag.createTag(Long.parseLong(request.getParameter("tag1"))):null;
		}catch(NumberFormatException e){
			
		}
		Tag tag2 =null;
		try{
		tag2 = request.getParameter("tag2")!=null?Tag.createTag(Long.parseLong(request.getParameter("tag2"))):null;
	    }catch(NumberFormatException e){
		
	    }
		Tag tag3 = null;
		try{
		tag3 =request.getParameter("tag3")!=null?Tag.createTag(Long.parseLong(request.getParameter("tag3"))):null;
		}catch(NumberFormatException e){
			
	    }
		String oper=request.getParameter("oper");
		
		Data data=new Data();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				try {
					if(id!=null)dataService.deleteById(Long.parseLong(id),usuario);
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
			 Long lugarId =null;
		     try{
			   lugarId =lugar!=null?Long.parseLong(lugar):null;
             }catch(NumberFormatException e){
			
	         }
		     Long subjectId =null;
		     try{
			   subjectId = subject!=null?Long.parseLong(subject):null;
		     }catch(NumberFormatException e){
			
             }
			Long eventoId = null;
			try{
			 eventoId = evento!=null?Long.parseLong(evento):null;
			}catch(NumberFormatException e){
				
            }
			Long sitioId = null;
			try{
			 sitioId = sitio!=null?Long.parseLong(sitio):null;
			}catch(NumberFormatException e){
				
            }
			data=new Data(value,descripcion,DataValueTipo.valueOf(tipoValor),tag1,tag2,tag3,lugarId,subjectId,eventoId,sitioId);
			try{
				  data.setId(Long.parseLong(id));
		    }catch(Exception e){
					
			}
			try {
				dataService.save(data,usuario);
				request.setAttribute("resultado","INSERTADO OK: " + data.prettyPrint());
			} catch (NoAutorizadoException e) {
				request.setAttribute("resultado","No tienes permisos para la operacion");
			}
			
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("data",data);
		DataDao dataDao = (DataDao) context.getBean("dataDao");
		List<Data> datas =dataDao.getAll();
		request.setAttribute("datas",datas);
		
		AlertasDao alertasDao = (AlertasDao) context.getBean("alertasDao");
		List<Alert> alertas =alertasDao.getAll();
		request.setAttribute("alertas",alertas);
		
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		
		TagDao tagDao = (TagDao) context.getBean("tagDao");
		List<Tag> tags =tagDao.getAll();
		request.setAttribute("tags",tags);
		
		SitioDao sitioDao = (SitioDao) context.getBean("sitioDao");
		List<Sitio> sitios =sitioDao.getAll();
		request.setAttribute("sitios",sitios);
		
		request.setAttribute("data",data);
		String nextJSP = "/ioUpdaterData.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String tipoValor=request.getParameter("tipoValor");
		
		
		if(tipoValor==null || tipoValor.length()<1) resultado.append("TipoValor Obligatorio;");
		String tag1 = request.getParameter("tag1");
		if(tag1==null || tag1.length()<1) resultado.append("Tag1 Obligatorio;");
		
		
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
