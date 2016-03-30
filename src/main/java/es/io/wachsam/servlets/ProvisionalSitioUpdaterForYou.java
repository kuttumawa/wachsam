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
import es.io.wachsam.dao.SitioDao;
import es.io.wachsam.dao.TagDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Sitio;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.TipoSitio;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalSitioUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalSitioUpdaterForYou() {
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
		SitioDao sitioDao = (SitioDao) context.getBean("sitioDao");
		List<Sitio> sitios =sitioDao.getAll();
		request.setAttribute("sitios",sitios);
		
		List<Data> datas=new ArrayList<Data>();
		String sitioId=request.getParameter("sitioId");
		Sitio sitio=new Sitio();
		if(sitioId!=null && sitioId.length()>0){
			sitio=sitioDao.getSitio(Long.parseLong(sitioId));
			DataDao dataDao = (DataDao) context.getBean("dataDao");
			Data filtro=new Data();
			filtro.setSitioId(sitio.getId());
			datas=dataDao.getAllnoExtrict(filtro);
			request.setAttribute("datas",datas);
		}
		request.setAttribute("sitio",sitio);
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		String nextJSP = "/ioUpdaterSitio.jsp";
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
		String texto=request.getParameter("texto");
		String textoEn=request.getParameter("textoEn");
		String valoracion=request.getParameter("valoracion");
		String tipo=request.getParameter("tipo");
		String direccion=request.getParameter("direccion");
		String lugarId=request.getParameter("lugarId");
		String oper=request.getParameter("oper");
		
		
		Sitio sitio=new Sitio();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				SitioDao sitioDao=(SitioDao) context.getBean("sitioDao");
				try {
					sitioDao.deleteById(Long.parseLong(id));
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
			sitio=new Sitio();
			sitio.setDireccion(direccion);
			sitio.setNombre(nombre);
			sitio.setNombreEn(nombreEn);
			sitio.setTexto(texto);
			sitio.setTextoEn(textoEn);
			sitio.setValoracion(Integer.parseInt(valoracion));
			sitio.setTipo(TipoSitio.values()[Integer.parseInt(tipo)]);			
			Lugar lugar=new Lugar();
			lugar.setId(Long.parseLong(lugarId));
			sitio.setLugarObj(lugar);
			try{
			  if(id!=null)sitio.setId(Long.parseLong(id));
			}catch(Exception e){
				
			}
			
			SitioDao sitioDao=(SitioDao) context.getBean("sitioDao");
			sitioDao.save(sitio);
			
			request.setAttribute("resultado","INSERTADO OK: " + sitio);
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("sitio",sitio);
		SitioDao sitioDao = (SitioDao) context.getBean("sitioDao");
		List<Sitio> sitios =sitioDao.getAll();
		request.setAttribute("sitios",sitios);
		
		LugarDao lugarDao = (LugarDao) context.getBean("lugarDao");
		List<Lugar> lugares =lugarDao.getAll();
		request.setAttribute("lugares",lugares);
		
		
		
		String nextJSP = "/ioUpdaterSitio.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String id=request.getParameter("id");
		String nombre=request.getParameter("nombre");
		String nombreEn=request.getParameter("nombreEn");
		String texto=request.getParameter("texto");
		String textoEn=request.getParameter("textoEn");
		String valoracion=request.getParameter("valoracion");
		String tipoSitio=request.getParameter("tipoSitio");
		String direccion=request.getParameter("direccion");
		String lugarId=request.getParameter("lugarId");
		String oper=request.getParameter("oper");
		
		if(nombre==null || nombre.length()<1) resultado.append("Nombre Obligatorio;");
		if(nombreEn!=null && nombreEn.length()>100) resultado.append("Nombre Eng debe se menor de 100");
		if(direccion!=null && direccion.length()>100) resultado.append("Dirección debe se menor de 100");
		if(texto!=null && texto.length()>500) resultado.append("Texto debe se menor de 500");
		if(textoEn!=null && textoEn.length()>500) resultado.append("Text debe se menor de 500");
		
		
		
		if(resultado.length() > 0){
			request.setAttribute("nombre",nombre);
			request.setAttribute("nombreEn",nombreEn);
			request.setAttribute("texto",texto);
			request.setAttribute("textoEn",textoEn);
			request.setAttribute("valoracion",valoracion);
			request.setAttribute("tipoSitio",tipoSitio);
			request.setAttribute("direccion",direccion);
			request.setAttribute("lugarId",lugarId);
			return resultado.toString();
		}
		return null;
	}

}
