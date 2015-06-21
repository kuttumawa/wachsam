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
import es.io.wachsam.dao.FactorDao;
import es.io.wachsam.model.Alert;
import es.io.wachsam.model.Lugar;
import es.io.wachsam.model.Factor;

/**
 * Servlet implementation class ProvisionalAlertUpdaterForYou
 */
public class ProvisionalFactorUpdaterForYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvisionalFactorUpdaterForYou() {
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
		FactorDao factorDao = (FactorDao) context.getBean("factorDao");
		List<Factor> factors =factorDao.getAll();
		request.setAttribute("factors",factors);
		
		String factorId=request.getParameter("factor");
		Factor factor=new Factor();
		if(factorId!=null && factorId.length()>0){
			factor=factorDao.getFactor(Long.parseLong(factorId));
		}
		request.setAttribute("factor",factor);
		String nextJSP = "/ioUpdaterFactor.jsp";
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
		String oper=request.getParameter("oper");
		
		Factor factor=new Factor();
		if(oper!=null && oper.equalsIgnoreCase("delete")){
			if(id!=null){
				FactorDao factorDao=(FactorDao) context.getBean("factorDao");
				try {
					factorDao.deleteById(Long.parseLong(id));
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
			factor=new Factor(id,nombre,nombreEn,texto,textoEn);
			FactorDao factorDao=(FactorDao) context.getBean("factorDao");
			factorDao.save(factor);
			
			request.setAttribute("resultado","INSERTADO OK: " + factor);
		}else{
			
			request.setAttribute("resultado",validar(request));
		}
		request.setAttribute("factor",factor);
		FactorDao factorDao = (FactorDao) context.getBean("factorDao");
		List<Factor> factors =factorDao.getAll();
		request.setAttribute("factors",factors);
		
		
		String nextJSP = "/ioUpdaterFactor.jsp";
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
		
		if(nombre==null || nombre.length()<1) resultado.append("Nombre Obligatorio;");
		if(texto==null || texto.length()<1) resultado.append("Texto Obligatorio;");
		
		
		if(resultado.length() > 0) return resultado.toString();
		return null;
	}

}
