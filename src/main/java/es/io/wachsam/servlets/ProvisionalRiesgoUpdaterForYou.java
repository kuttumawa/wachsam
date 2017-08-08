package es.io.wachsam.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import es.io.wachsam.model.Mes;
import es.io.wachsam.model.NivelProbabilidad;
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
		Peligro peligro=null;
		List<Riesgo> riesgos=new ArrayList<Riesgo>();
		if(peligroId!=null && peligroId.length()>0){
			riesgos=riesgoDao.getRiesgosFromPeligro(Long.parseLong(peligroId));
			for(Riesgo r: riesgos){
				r.iniRiesgoTransientInfo();
			}
			Collections.sort(riesgos,Riesgo.getRiesgoValorComparator());
			peligro=peligroDao.getPeligro(Long.parseLong(peligroId));
			if(riesgos==null)  riesgos=new ArrayList<Riesgo>();
		}
		
		
		request.setAttribute("peligro",peligro);
		request.setAttribute("riesgos",riesgos);
		String nextJSP = "/riesgoPeligro.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario=(Usuario)request.getSession().getAttribute("user");
		if(usuario==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		RiesgoService riesgoService=(RiesgoService) context.getBean("riesgoService");
		String riesgoId=request.getParameter("riesgoId");
		String peligroId=request.getParameter("peligroId");
		String lugarId=request.getParameter("lugarId");
		String nivelProbabilidad=request.getParameter("nivelProbabilidadId");
		String fechaActivacion=request.getParameter("fechaActivacion");
		String mesActivacion=request.getParameter("mesActivacion");
		String caducidad=request.getParameter("caducidad");
		String oper=request.getParameter("oper");
		
		Riesgo riesgo=new Riesgo();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(riesgoId!=null){
				try {
					riesgoService.deleteById(Long.parseLong(riesgoId),usuario);
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
			Peligro peligro=new Peligro();
			peligro.setId(Long.parseLong(peligroId));
			riesgo.setPeligro(peligro);
			Lugar lugar=new Lugar();
			lugar.setId(Long.parseLong(lugarId));
			riesgo.setLugar(lugar);
			riesgo.setValue(NivelProbabilidad.valueOf(nivelProbabilidad));
			riesgo.setCaducidad(Integer.parseInt(caducidad));
			if(fechaActivacion!=null)
				try {
					riesgo.setFechaActivacion(new SimpleDateFormat("yyyy-MM-dd").parse(fechaActivacion.trim()));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			else{
				riesgo.setFechaActivacion(null);
			}
			if(mesActivacion!=null && mesActivacion.length()>0){
				riesgo.setMesActivacion(Mes.valueOf(mesActivacion));
			}else{
				riesgo.setMesActivacion(null);
			}
			
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
		String peligroId=request.getParameter("peligroId");
		String lugarId=request.getParameter("lugarId");
		String nivelProbabilidad=request.getParameter("nivelProbabilidadId");
		String fechaActivacion=request.getParameter("fechaActivacion");
		String mesActivacion=request.getParameter("fechaActivacion");
		String caducidad=request.getParameter("caducidad");
		
		if(lugarId==null || lugarId.length()<1) resultado.append("Lugar Obligatorio;");
		if(peligroId==null || peligroId.length()<1) resultado.append("Peligro Obligatorio;");
		if(nivelProbabilidad==null || nivelProbabilidad.length()<1) resultado.append("Probabilidad Obligatorio;");
		if(fechaActivacion!=null && fechaActivacion.length()>0){
		      try{
				   new SimpleDateFormat("yyyy-MM-dd").parse(fechaActivacion);
				}catch(Exception e){
					 resultado.append(" Fecha Formato Erróneo");
				}
		      }
		if(mesActivacion==null){
			try{
				Mes.valueOf(mesActivacion);
			}catch(IllegalArgumentException e){
				resultado.append("mesActivacion valor no válido;");
			}
        }
		if(caducidad==null || caducidad.length()<1) resultado.append("Caducidad Obligatorio;");
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}
*/
}
