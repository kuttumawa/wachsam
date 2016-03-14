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
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Factor;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Tag;

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
		
		
		String dataId=request.getParameter("data");
		Data data=new Data();
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
		if(request.getSession().getAttribute("user")==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		request.getCharacterEncoding();
		String id=request.getParameter("id");
		String value=request.getParameter("value");
		String descripcion=request.getParameter("descripcion");
		String tipoValor=request.getParameter("tipoValor");
		String subject=request.getParameter("peligro");
		String evento=request.getParameter("evento");
		String lugar=request.getParameter("lugar");
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
				DataDao dataDao=(DataDao) context.getBean("dataDao");
				try {
					if(id!=null)dataDao.deleteById(Long.parseLong(id));
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
			data=new Data(value,descripcion,DataValueTipo.valueOf(tipoValor),tag1,tag2,tag3,lugarId,subjectId,eventoId);
			try{
				  data.setId(Long.parseLong(id));
		    }catch(Exception e){
					
			}
			DataDao dataDao=(DataDao) context.getBean("dataDao");
			dataDao.save(data);
			request.setAttribute("resultado","INSERTADO OK: " + data.prettyPrint());
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
		
		String nextJSP = "/ioUpdaterData.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String tipoValor=request.getParameter("tipoValor");
		
		
		if(tipoValor==null || tipoValor.length()<1) resultado.append("TipoValor Obligatorio;");
		
		
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
