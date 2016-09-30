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
import es.io.wachsam.model.Factor;
import es.io.wachsam.model.NivelProbabilidad;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Mitigacion;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.model.ValorMitigacion;
import es.io.wachsam.services.MitigacionService;


/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class MitigacionServletJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final Gson gson=new Gson();
	Gson prettyGson = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MitigacionServletJSON() {
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
		MitigacionService mitigacionService=(MitigacionService) context.getBean("mitigacionService");
		final Gson gson=new Gson();
	    final Gson prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String oper=request.getParameter("oper");
		List<Mitigacion> mitigacions=new ArrayList<Mitigacion>();
		String mitigacionId=request.getParameter("mitigacionId");
		String peligroIdstr=request.getParameter("peligroId");
		
		if(oper!=null){
			if(oper.equalsIgnoreCase("getAllForPeligro")){
				Long peligroId=Long.parseLong(peligroIdstr);
				mitigacions=mitigacionService.getAllMitigacionForPeligro(peligroId);
				out.println(prettyGson.toJson(mitigacions));
			}else if(oper.equalsIgnoreCase("getMitigacion")){
				Long id=Long.parseLong(mitigacionId);
			    Mitigacion mitigacion=null;
				try {
					mitigacion = mitigacionService.getMitigacion(id,usuario);
					out.write(prettyGson.toJson(mitigacion));
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
			MitigacionService mitigacionService=(MitigacionService) context.getBean("mitigacionService");
	        Resultado resultado=null;
			
			String mitigacionId=request.getParameter("mitigacionId");
			String peligroId=request.getParameter("peligroId");
			String factorId=request.getParameter("factorId");
			String valorMitigacionId=request.getParameter("valorMitigacionId");
			String oper=request.getParameter("oper");
			
			Mitigacion mitigacion=new Mitigacion();
			if(oper!=null){
				if(oper.equalsIgnoreCase("delete")){
			
					if(mitigacionId!=null){
									try {
										mitigacionService.deleteById(Long.parseLong(mitigacionId),usuario);
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
						
						Factor factor=new Factor();
						factor.setId(Long.parseLong(factorId));
						Peligro peligro=new Peligro();
						peligro.setId(Long.parseLong(peligroId));
					    if(!GenericValidator.isBlankOrNull(mitigacionId)) mitigacion.setId(Long.parseLong(mitigacionId));
						mitigacion.setFactor(factor);
					    mitigacion.setPeligro(peligro);
					    mitigacion.setValue(ValorMitigacion.valueOf(valorMitigacionId));
					    //Validar que no existe ya el mitigacion para el peligro
						if(mitigacion.getId()==null && mitigacionService.existeYaMitigacion(mitigacion)){
							resultado=new Resultado(true,"Ya existe el Mitigacion");
						}else{
							mitigacionService.save(mitigacion,usuario);
					    	resultado=new Resultado(false,"Inserción ok");
							resultado.setObjeto(mitigacion);
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
			String mitigacionId=request.getParameter("mitigacionId");
			String peligroId=request.getParameter("peligroId");
			String factorId=request.getParameter("factorId");
			String valorMitigacionId=request.getParameter("valorMitigacionId");
			
			
			if(GenericValidator.isBlankOrNull(mitigacionId) && oper.equalsIgnoreCase("delete") ) resultado.add("Se debe seleccionar un Mitigación");			
			if(!GenericValidator.isBlankOrNull(mitigacionId) && !GenericValidator.isLong(mitigacionId)) resultado.add("Error en id del Mitigación");
			if(GenericValidator.isBlankOrNull(peligroId) || !GenericValidator.isLong(peligroId)) resultado.add("Se debe seleccionar un Peligro");			
			if(GenericValidator.isBlankOrNull(factorId) || !GenericValidator.isLong(factorId)) resultado.add("Se debe seleccionar un Factor");
			if(GenericValidator.isBlankOrNull(valorMitigacionId)) resultado.add("Se debe seleccionar una Valor de mitigación");
			
			
			
			if(resultado.size() > 0) return resultado;
			return null;
		}

}
