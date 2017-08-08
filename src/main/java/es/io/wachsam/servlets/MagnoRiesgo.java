package es.io.wachsam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.io.wachsam.exception.ApiBadRequestException;
import es.io.wachsam.model.ResultMetadata;
import es.io.wachsam.model.Riesgo;
import es.io.wachsam.services.LugarService;
import es.io.wachsam.services.RiesgoService;
import es.io.wachsam.services.SecurityService;
import es.io.wachsam.util.Tools;

/**
 * Servlet implementation class Magno
 */
public class MagnoRiesgo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	WebApplicationContext context;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MagnoRiesgo() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// request.setCharacterEncoding("UTF-8");
		final long timestamp_1=new Date().getTime();
		final Gson gson = new Gson();
		final Gson prettyGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		PrintWriter out = response.getWriter();
		SecurityService securityService = (SecurityService) context.getBean("securityService");
		RiesgoService service = (RiesgoService) context.getBean("riesgoService");
		LugarService serviceLugar = (LugarService) context.getBean("lugarService");
		try {
			String id = request.getParameter("id");
			String countrycode = request.getParameter("countryCode");
			String countryId = request.getParameter("countryId");
			String peligroId = request.getParameter("peligroId");
			String lang = request.getParameter("lang");
			String max = request.getParameter("max");
			String skip = request.getParameter("skip");
			String api_token = request.getParameter("apiToken");

			String[] _countrycodes = !GenericValidator.isBlankOrNull(countrycode) ? countrycode.split(",") : null;
			String[] countryIds = !GenericValidator.isBlankOrNull(countryId) ? countryId.split(",") : null;
			String[] _peligroIds = !GenericValidator.isBlankOrNull(peligroId) ? peligroId.split(",") : null;
			Integer index = (skip != null ? Integer.parseInt(skip) : null);
			Integer maximo = (max != null ? Integer.parseInt(max) : null);
			ResultMetadata metadata = new ResultMetadata();
			List<Long> lugarIds=new ArrayList<Long>();
			
			if(api_token==null || securityService.verifyApiToken(api_token)){
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,prettyGson.toJson(new ApiBadRequestException(HttpServletResponse.SC_UNAUTHORIZED,
						"Bad authentication token.")));
				return;
			}
			// Validate countrycodes
			if(_countrycodes!=null){
				List<Long> countrycodes = serviceLugar.getLugarFromISO_3166_1_alpha2(_countrycodes);
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < _countrycodes.length; i++) {
					if (countrycodes.get(i) == null)
						sb.append(_countrycodes[i] + " ");
				}
				if (sb.length() > 0) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,
							prettyGson.toJson(new ApiBadRequestException(HttpServletResponse.SC_BAD_REQUEST,
									"parameter countryCode:" + sb + " erroneous.Use ISO_3166_1_alpha2 encoding.")));
					return;
				}
				lugarIds.addAll(countrycodes);
			}
			if(countryIds!=null){
				try{
				lugarIds.addAll(Tools.stringToLongList(countryIds));
				}catch(Exception e){
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,
							prettyGson.toJson(new ApiBadRequestException(HttpServletResponse.SC_BAD_REQUEST,
									"parameter countryIds: "+countryId+" value must be a number and correct id" )));
					return;
				}				
			}
			List<Long> peligroIds=new ArrayList<Long>();
			if(_peligroIds!=null){
				try{
				peligroIds.addAll(Tools.stringToLongList(_peligroIds));
				}catch(Exception e){
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,
							prettyGson.toJson(new ApiBadRequestException(HttpServletResponse.SC_BAD_REQUEST,
									"parameter peligroIds: "+peligroId+" value must be a number and correct id" )));
					return;
				}				
			}
			if(lugarIds.size()==0){
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						prettyGson.toJson(new ApiBadRequestException(HttpServletResponse.SC_BAD_REQUEST,
								"parameters countrycodes or countryIds are mandatory" )));
				return;				
			}
			List<Riesgo> riesgos = service.searchRiesgos(lugarIds,peligroIds, lang, maximo,
					index, metadata);
			response.setContentType("application/json");
			response.addHeader("Results-Matching", metadata.getTotalResultMatching().toString());
			response.addHeader("Results-Skipped", metadata.getSkip() + "");
			response.addHeader("Results-Presented", riesgos.size() + "");
			response.addHeader("Results-Processed-time", new Date().getTime()-timestamp_1 + "");
			response.setCharacterEncoding("ISO-8859-1");

			out.println(prettyGson.toJson(riesgos));
		} catch (ApiBadRequestException e) {
			response.sendError(e.getError(), prettyGson.toJson(e));
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
