package es.io.wachsam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.NivelProbabilidad;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Riesgo;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.RiesgoService;


/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class RiesgoServletJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final Gson gson=new Gson();
	Gson prettyGson = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RiesgoServletJSON() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		Usuario usuario=(Usuario)request.getSession().getAttribute("user");
		if(usuario==null){
			   String nextJSP = "/login.jsp";
			   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			   dispatcher.forward(request,response);
			   return;
		}
		RiesgoService riesgoService=(RiesgoService) context.getBean("riesgoService");
		final Gson gson=new Gson();
	    final Gson prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String oper=request.getParameter("oper");
		List<Riesgo> riesgos=new ArrayList<Riesgo>();
		String riesgoId=request.getParameter("riesgoId");
		String peligroIdstr=request.getParameter("peligroId");
		
		if(oper!=null){
			if(oper.equalsIgnoreCase("getAllForPeligro")){
				Long peligroId=Long.parseLong(peligroIdstr);
				riesgos=riesgoService.getAllRiesgoForPeligro(peligroId);
				out.println(prettyGson.toJson(riesgos));
			}else if(oper.equalsIgnoreCase("getRiesgo")){
				Long id=Long.parseLong(riesgoId);
			    Riesgo riesgo=null;
				try {
					riesgo = riesgoService.getRiesgo(id,usuario);
					out.write(prettyGson.toJson(riesgo));
				} catch (NoAutorizadoException e) {
					e.printStackTrace();
				}
				
				
			}
			
		}
		 
	}
	
	  class Resultado{
	    	
	    	public Resultado(boolean error, String resultado) {
				super();
				this.error = error;
				this.resultado = resultado;
			}
	    	
			boolean error;
	    	String resultado;
	    	Object objeto;
	    	List<String> messages=new ArrayList<String>();
			public boolean isError() {
				return error;
			}
			public void setError(boolean error) {
				this.error = error;
			}
			public String getResultado() {
				return resultado;
			}
			public void setResultado(String resultado) {
				this.resultado = resultado;
			}
			public List<String> getMessages() {
				return messages;
			}
			public void setMessages(List<String> messages) {
				this.messages = messages;
			}
			public Object getObjeto() {
				return objeto;
			}
			public void setObjeto(Object objeto) {
				this.objeto = objeto;
			}
	    }
		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			Usuario usuario=(Usuario)request.getSession().getAttribute("user");
			List<String> errores=new ArrayList<String>();
			if(usuario==null){
				   String nextJSP = "/login.jsp";
				   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
				   dispatcher.forward(request,response);
				   return;
			}
			RiesgoService riesgoService=(RiesgoService) context.getBean("riesgoService");
	        Resultado resultado=null;
			
			String riesgoId=request.getParameter("riesgoId");
			String peligroId=request.getParameter("peligroId");
			String lugarId=request.getParameter("lugarId");
			String nivelProbabilidadId=request.getParameter("nivelProbabilidadId");
			String oper=request.getParameter("oper");
			
			Riesgo riesgo=new Riesgo();
			if(oper!=null){
				if(oper.equalsIgnoreCase("delete")){
			
					if(riesgoId!=null){
									try {
										riesgoService.deleteById(Long.parseLong(riesgoId),usuario);
										resultado=new Resultado(false,"Borrado Correcto");
									} catch (NumberFormatException e) {
										e.printStackTrace();
									} catch (NoAutorizadoException e) {
										resultado=new Resultado(true,"No tienes permisos para la operacion");
									} catch (Throwable e) {
										e.printStackTrace();
									}
					}else{
						resultado=new Resultado(true,"Error al borrar");
					}
				
			    }else if(oper.equalsIgnoreCase("save") && validar(request)==null){
				   
					try{
						
						Lugar lugar=new Lugar();
						lugar.setId(Long.parseLong(lugarId));
						Peligro peligro=new Peligro();
						peligro.setId(Long.parseLong(peligroId));
					    if(!GenericValidator.isBlankOrNull(riesgoId)) riesgo.setId(Long.parseLong(riesgoId));
						riesgo.setLugar(lugar);
					    riesgo.setPeligro(peligro);
					    riesgo.setValue(NivelProbabilidad.valueOf(nivelProbabilidadId));
					    //Validar que no existe ya el riesgo para el peligro
						if(riesgo.getId()==null && riesgoService.existeYaRiesgo(riesgo)){
							resultado=new Resultado(true,"Ya existe el Riesgo");
						}else{
							riesgoService.save(riesgo,usuario);
					    	resultado=new Resultado(false,"Inserción ok");
							resultado.setObjeto(riesgo);
						}
					} catch (NoAutorizadoException e) {
						resultado=new Resultado(true,"No tienes permisos para la operacion");
					}catch(Exception e){
						resultado=new Resultado(true,e.toString());
					}
				}else{
					resultado=new Resultado(true,"Error validación");
					resultado.setMessages(validar(request));
				   
			    }
			}
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.println(gson.toJson(resultado)); 
			
		}

		private List<String> validar(HttpServletRequest request){
			List<String> resultado=new ArrayList<String>();
			String oper=request.getParameter("oper");
			String riesgoId=request.getParameter("riesgoId");
			String peligroId=request.getParameter("peligroId");
			String lugarId=request.getParameter("lugarId");
			String nivelProbabilidadId=request.getParameter("nivelProbabilidadId");
			
			
			if(GenericValidator.isBlankOrNull(riesgoId) && oper.equalsIgnoreCase("delete") ) resultado.add("Se debe seleccionar un Riesgo");			
			if(!GenericValidator.isBlankOrNull(riesgoId) && !GenericValidator.isLong(riesgoId)) resultado.add("Error en id del Riesgo");
			if(GenericValidator.isBlankOrNull(peligroId) || !GenericValidator.isLong(peligroId)) resultado.add("Se debe seleccionar un Peligro");			
			if(GenericValidator.isBlankOrNull(lugarId) || !GenericValidator.isLong(lugarId)) resultado.add("Se debe seleccionar un Lugar");
			if(GenericValidator.isBlankOrNull(nivelProbabilidadId)) resultado.add("Se debe seleccionar una Probabilidad");
			
			
			
			if(resultado.size() > 0) return resultado;
			return null;
		}

}
