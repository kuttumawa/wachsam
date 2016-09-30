package es.io.wachsam.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.io.wachsam.dao.DataDao;
import es.io.wachsam.dao.FactorDao;
import es.io.wachsam.dao.MitigacionDao;
import es.io.wachsam.dao.PeligroDao;
import es.io.wachsam.exception.NoAutorizadoException;
import es.io.wachsam.model.Data;
import es.io.wachsam.model.Factor;
import es.io.wachsam.model.Mitigacion;
import es.io.wachsam.model.ObjetoSistema;
import es.io.wachsam.model.Peligro;
import es.io.wachsam.model.Usuario;
import es.io.wachsam.model.ValorMitigacion;
import es.io.wachsam.services.MitigacionService;


public class ProvisionalMitigacionUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalMitigacionUpdaterForYou() {
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
		MitigacionDao mitigacionDao = (MitigacionDao) context.getBean("mitigacionDao");
		PeligroDao peligroDao = (PeligroDao) context.getBean("peligroDao");
		List<Peligro> peligros =peligroDao.getAll();
		request.setAttribute("peligros",peligros);
		
		FactorDao factorDao = (FactorDao) context.getBean("factorDao");
		List<Factor> factores=factorDao.getAll();
		request.setAttribute("factores",factores);
		
		String peligroId=request.getParameter("peligroId");
		Peligro peligro=null;
		List<Mitigacion> mitigaciones=new ArrayList<Mitigacion>();
		if(peligroId!=null && peligroId.length()>0){
			mitigaciones=mitigacionDao.getMitigacionesFromPeligro(Long.parseLong(peligroId));
			peligro=peligroDao.getPeligro(Long.parseLong(peligroId));
			if(mitigaciones==null)  mitigaciones=new ArrayList<Mitigacion>();
		}
		
		
		request.setAttribute("peligro",peligro);
		request.setAttribute("mitigaciones",mitigaciones);
		String nextJSP = "/ioUpdaterMitigacion.jsp";
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
		MitigacionService mitigacionService=(MitigacionService) context.getBean("mitigacionService");
		String mitigacionId=request.getParameter("mitigacionId");
		String peligroId=request.getParameter("peligroId");
		String factorId=request.getParameter("factorId");
		String valorMitigacion=request.getParameter("valorMitigacionId");
	
		String oper=request.getParameter("oper");
		
		Mitigacion mitigacion=new Mitigacion();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(mitigacionId!=null){
				try {
					mitigacionService.deleteById(Long.parseLong(mitigacionId),usuario);
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
			mitigacion=new Mitigacion();
			Peligro peligro=new Peligro();
			peligro.setId(Long.parseLong(peligroId));
			mitigacion.setPeligro(peligro);
			Factor factor=new Factor();
			factor.setId(Long.parseLong(factorId));
			mitigacion.setFactor(factor);
			mitigacion.setValue(ValorMitigacion.valueOf(valorMitigacion));
			
			try {
				mitigacionService.save(mitigacion,usuario);
				request.setAttribute("resultado","INSERTADO OK: " + mitigacion);
			} catch (NoAutorizadoException e) {
				request.setAttribute("resultado","No tienes permisos para la operacion");
			}
			
			
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("mitigacion",mitigacion);
		MitigacionDao mitigacionDao = (MitigacionDao) context.getBean("mitigacionDao");
		List<Mitigacion> mitigacions =mitigacionDao.getAll();
		request.setAttribute("mitigacions",mitigacions);
		
		List<Data> datas=new ArrayList<Data>();
		if(mitigacion.getId()!=null){
			DataDao dataDao = (DataDao) context.getBean("dataDao");
			Data filtro=new Data();
			filtro.setObjetoId(mitigacion.getId());
			filtro.setObjetoConnected(ObjetoSistema.Mitigacion);
			datas=dataDao.getAll(filtro);
			request.setAttribute("datas",datas);
		}
		
		FactorDao factorDao = (FactorDao) context.getBean("factorDao");
		List<Factor> factores =factorDao.getAll();
		request.setAttribute("factores",factores);
		String nextJSP = "/ioUpdaterMitigacion.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		
	}
	private String validar(HttpServletRequest request){
		StringBuilder resultado=new StringBuilder();
		String peligroId=request.getParameter("peligroId");
		String factorId=request.getParameter("factorId");
		String nivelProbabilidad=request.getParameter("nivelProbabilidadId");
		
		if(factorId==null || factorId.length()<1) resultado.append("Factor Obligatorio;");
		if(peligroId==null || peligroId.length()<1) resultado.append("Peligro Obligatorio;");
		if(nivelProbabilidad==null || nivelProbabilidad.length()<1) resultado.append("Probabilidad Obligatorio;");
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
