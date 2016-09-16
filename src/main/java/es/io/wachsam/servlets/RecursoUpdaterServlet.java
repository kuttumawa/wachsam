package es.io.wachsam.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Recurso;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.services.RecursoService;
import es.io.wachsam.services.S3service;

@WebServlet(name = "RecursoUpdaterServlet", urlPatterns = {"/recurso"})
@MultipartConfig
public class RecursoUpdaterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecursoUpdaterServlet() {
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
		RecursoService recursoService = (RecursoService) context.getBean("recursoService");
		Recurso recurso=new Recurso();	
		String recursoId=request.getParameter("recursoId");
		if(recursoId!=null && recursoId.length()>0){
			try {
			recurso=recursoService.getRecurso(Long.parseLong(recursoId),usuario);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (NoAutorizadoException e) {
				request.setAttribute("resultado","No tienes permisos para la operacion");
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	    request.setAttribute("recurso",recurso);
		String nextJSP = "/ioUpdaterRecurso.jsp";
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
		RecursoService recursoService = (RecursoService) context.getBean("recursoService");
		S3service s3service = (S3service) context.getBean("s3service");
		
		final String BUCKET_NAME="wachsam-articulos-repository";
		final String S3_URL_CONTEXT="https://s3.amazonaws.com/";
		List<String> resultado=new ArrayList<String>();
		Recurso recurso=new Recurso();;	
		String _recursoId=request.getParameter("recursoId");
		Long recursoId=null;
		if(_recursoId!=null){
			recursoId=Long.parseLong(_recursoId);
		}		
		String descripcion=request.getParameter("descripcion");
		String formato=request.getParameter("formato");
		String nombre=request.getParameter("nombre");
		String _s3Publico=request.getParameter("s3Publico");
		
		final Part filePart = request.getPart("file");	
		InputStream filecontent = filePart!=null?filePart.getInputStream():null;
		String oper=request.getParameter("oper");
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(recursoId!=null){
				try {
					recurso=recursoService.getRecurso(recursoId, usuario);
					recursoService.deleteById(recursoId,usuario);
					s3service.deleteObject(recurso.getS3Bucket(), recurso.getS3Key());
					resultado.add("Borrado Correcto");
				
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (NoAutorizadoException e) {
					resultado.add("No tienes permisos para la operacion");
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}else{
				resultado.add("Error al borrar");
			}
			
		}else if(oper!=null && oper.equalsIgnoreCase("download")){
			if(recursoId!=null){
				try {
					recurso=recursoService.getRecurso(recursoId, usuario);
					if(recurso.getS3Publico()){
						response.sendRedirect(recurso.getUri());
					}else{
						URL url=s3service.generateObjectUrl(recurso.getS3Bucket(), recurso.getS3Key());
						response.sendRedirect(url.toString());
					}
					return;
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (NoAutorizadoException e) {
					resultado.add("No tienes permisos para la operacion");
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}else{
				resultado.add("Error en al descarga del documento");
			}
			
		}else if(recurso.validate().size()>0){
			if(filePart==null && recursoId!=null){
				resultado.add("No hay fichero adjunto");
				}else
				recurso=new Recurso();
				try {
					recurso.setCreador(usuario);
					recurso.setDescripcion(descripcion);
					recurso.setFormato(formato);
					recurso.setId(recursoId);
					recurso.setNombre(nombre);
					recurso.setCreador(usuario);
					recurso.setFechaCreacion(new Date());
					recursoService.save(recurso,usuario);				
					if(filePart!=null && filePart.getSize()>0 ){						
						recurso.setSize(filePart.getSize());					
						recurso.setFechaCreacion(new Date());
						recurso.setS3Bucket(BUCKET_NAME);
						s3service.uploadFile(recurso.getS3Bucket(),recurso.getS3Key(),filecontent,recurso.toMetadata(),recurso.getFormato());
						if(_s3Publico!=null && _s3Publico.equalsIgnoreCase("true")) {
							recurso.setS3Publico(true);
							recurso.setUri(S3_URL_CONTEXT+BUCKET_NAME+"/"+ URLEncoder.encode(recurso.getS3Key(),StandardCharsets.ISO_8859_1.toString()));
							s3service.changeObjectACLPublic(recurso.getS3Bucket(), recurso.getS3Key());
						}else{
							recurso.setS3Publico(false);
							recurso.setUri(null);
							s3service.changeObjectACLPrivate(recurso.getS3Bucket(), recurso.getS3Key());
						}
						recursoService.save(recurso,usuario);						
					}
					resultado.add("INSERTADO OK: " + recurso);
				} catch (NoAutorizadoException e) {
					resultado.add("No tienes permisos para la operacion");					
				}
		 
		    	
		}else{
			resultado=recurso.validate();
		}
		request.setAttribute("resultado",resultado);		
		request.setAttribute("recurso",recurso);
		String nextJSP = "/ioUpdaterRecurso.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	

}
