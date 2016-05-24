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

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.DataValueTipo;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Tag;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.DataService;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class DataServletJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final Gson gson=new Gson();
	Gson prettyGson = null;
   
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataServletJSON() {
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
		DataService dataService=(DataService) context.getBean("dataService");
		//http://localhost:8080/wachsam/DataServletJSON?objetoId=1376424326&objetoTipo=0&oper=getAllForObject
		response.setContentType("text/html");
    	response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String oper=request.getParameter("oper");
		String dataId=request.getParameter("dataId");

		if(oper!=null){
			if(oper.equalsIgnoreCase("getAllForObject")){
				boolean error=false;
				List<Data> datas=new ArrayList<Data>();
				String _objetoId =request.getParameter("objetoId");
				String _objetoTipo =request.getParameter("objetoTipo");
				Long objetoId =null;
				ObjetoSistema objetoTipo=null;
				try{
				  objetoId = Long.parseLong(_objetoId);
				  objetoTipo = ObjetoSistema.values()[Integer.parseInt(_objetoTipo)];				 	
				}catch(Exception e){
					error=true;
				}
				prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();				
				if(!error) datas=dataService.getAllForObject(objetoId, objetoTipo);
				out.write(prettyGson.toJson(datas));
			}else if(oper.equalsIgnoreCase("getAllTags")){
				List<Tag> tags=new ArrayList<Tag>();
				prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
				tags=dataService.getAllTags();
				out.write(prettyGson.toJson(tags));
			}else if(oper.equalsIgnoreCase("getData")){
				Long id=Long.parseLong(dataId);
			    Data data=null;
				try {
					data = dataService.getDataById(id,usuario);
					prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
					out.write(prettyGson.toJson(data));
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
		DataService dataService=(DataService) context.getBean("dataService");
        Resultado resultado=null;
		request.getCharacterEncoding();
		String id=request.getParameter("dataId");
		String value=request.getParameter("value");
		String descripcion=request.getParameter("descripcion");
		String tipoValor=request.getParameter("tipoValor");
		String objetoId=request.getParameter("objetoId");
		String objetoTipo=request.getParameter("objetoTipo");
		String objetoConnectedId=request.getParameter("objetoConnectedId");	
		String objetoConnectedTipo=request.getParameter("objetoConnectedTipo");		
		String dataId=request.getParameter("dataId");
		
		Tag tag = null;
		try{
		tag=request.getParameter("tag")!=null?Tag.createTag(Long.parseLong(request.getParameter("tag"))):null;
		}catch(NumberFormatException e){
			
		}
		
		String oper=request.getParameter("oper");
		
		Data data=new Data();
		if(oper!=null){
			if(oper.equalsIgnoreCase("delete")){
		
				if(id!=null){
								try {
									dataService.deleteById(Long.parseLong(id),usuario);
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
				data=new Data();
				if(objetoId!=null) data.setObjetoId(Long.parseLong(objetoId));
				if(objetoConnectedId!=null) data.setConnectToId(Long.parseLong(objetoConnectedId));
				if(objetoTipo!=null) data.setObjetoTipo(ObjetoSistema.values()[Integer.parseInt(objetoTipo)]);
				if(objetoConnectedTipo!=null) data.setObjetoConnected(ObjetoSistema.values()[Integer.parseInt(objetoConnectedTipo)]);
				data.setValue(value);
				data.setDescripcion(descripcion);
				data.setTipoValor(DataValueTipo.valueOf(tipoValor));
				data.setTag(tag);
				try{
					  data.setId(Long.parseLong(id));
			    }catch(Exception e){
						
				}
				try {
					dataService.save(data,usuario);
			    	resultado=new Resultado(false,"Inserción ok");
					resultado.getMessages().add(data.toString());
				} catch (NoAutorizadoException e) {
					resultado=new Resultado(true,"No tienes permisos para la operacion");
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
		String id=request.getParameter("id");
		String tipoValor=request.getParameter("tipoValor");
		String objetoId=request.getParameter("objetoId");
		String objetoTipo=request.getParameter("objetoTipo");
		
		
		if(oper==null || oper.length()<1) resultado.add("No se ha seleccionado una operación");
		if(tipoValor==null || tipoValor.length()<1) resultado.add("TipoValor Obligatorio;");
		if(objetoId==null || objetoId.length()<1) resultado.add("objetoId Obligatorio;");
		if(objetoTipo==null || objetoTipo.length()<1) resultado.add("objetoTipo Obligatorio;");
		String tag = request.getParameter("tag");
		if(tag==null || tag.length()<1) resultado.add("Tag Obligatorio;");
		if(resultado.size() > 0) return resultado;
		return null;
	}


}
