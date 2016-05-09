package es.io.wachsam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * http://openflights.org/data.html
 *
 */
@Entity
@Table(name="airport")
public class Airport {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Expose
	Long id;
	/**
	 * Name of airport. May or may not contain the City name.
	 */
	@Expose
	String name;
	/**
	 * Main city served by airport. May be spelled differently from Name.
	 */
	@Expose
	String city;
	/**
	 * Country or territory where airport is located.
	 */
	@Expose
	String country;
	/**
	 *  3-letter FAA code, for airports located in Country "United States of America".
        3-letter IATA code, for all other airports.
        Blank if not assigned.
	 */
	@Expose
	String IATA_FAA;
	/**
	 * 4-letter ICAO code.
       Blank if not assigned.
	 */
	@Expose
	String ICAO;
	/**
	 * Decimal degrees, usually to six significant digits. Negative is South, positive is North.
	 */
	@Expose
	String latitud;
	/**
	 * Decimal degrees, usually to six significant digits. Negative is West, positive is East.
	 */
	@Expose
	String logitud;
	/**
	 * In feet.
	 */
	@Expose
	String altitud;
	/**
	 * Hours offset from UTC. Fractional hours are expressed as decimals, eg. India is 5.5.
	 */
	@Expose
	String timezone;
	
	/**
	 * Daylight savings time. One of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown).
	 */
	@Expose
	String DST;
	
	/**
	 *  Timezone in "tz" (Olson) format, eg. "America/Los_Angeles".
	 */
	@Expose
	String TZ;
	
	

	
	public Airport() {
		super();
	}




	public Airport(String id2, String nombre, String city2, String country2,
			String iATA_FAA2, String iCAO2, String latitud2, String logitud2,
			String altitud2, String timezone2, String dST2, String tZ2) {
		try{
		this.id=Long.parseLong(id2);
		}catch(Exception e){
			
		}
		this.name=nombre;
		this.city=city2;
		this.country=country2;
		this.IATA_FAA=iATA_FAA2;
		this.ICAO=iCAO2;
		this.latitud=latitud2;
		this.logitud=logitud2;
		this.altitud=altitud2;
		this.timezone=timezone2;
		this.TZ=tZ2;
		this.DST=dST2;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	public String getCountry() {
		return country;
	}




	public void setCountry(String country) {
		this.country = country;
	}




	public String getIATA_FAA() {
		return IATA_FAA;
	}




	public void setIATA_FAA(String iATA_FAA) {
		IATA_FAA = iATA_FAA;
	}




	public String getICAO() {
		return ICAO;
	}




	public void setICAO(String iCAO) {
		ICAO = iCAO;
	}




	public String getLatitud() {
		return latitud;
	}




	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}




	public String getLogitud() {
		return logitud;
	}




	public void setLogitud(String logitud) {
		this.logitud = logitud;
	}




	public String getAltitud() {
		return altitud;
	}




	public void setAltitud(String altitud) {
		this.altitud = altitud;
	}




	public String getTimezone() {
		return timezone;
	}




	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}




	public String getDST() {
		return DST;
	}




	public void setDST(String dST) {
		DST = dST;
	}




	public String getTZ() {
		return TZ;
	}




	public void setTZ(String tZ) {
		TZ = tZ;
	}




	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Airport [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (city != null) {
			builder.append("city=");
			builder.append(city);
			builder.append(", ");
		}
		if (country != null) {
			builder.append("country=");
			builder.append(country);
			builder.append(", ");
		}
		if (IATA_FAA != null) {
			builder.append("IATA_FAA=");
			builder.append(IATA_FAA);
			builder.append(", ");
		}
		if (ICAO != null) {
			builder.append("ICAO=");
			builder.append(ICAO);
			builder.append(", ");
		}
		if (latitud != null) {
			builder.append("latitud=");
			builder.append(latitud);
			builder.append(", ");
		}
		if (logitud != null) {
			builder.append("logitud=");
			builder.append(logitud);
			builder.append(", ");
		}
		if (altitud != null) {
			builder.append("altitud=");
			builder.append(altitud);
			builder.append(", ");
		}
		if (timezone != null) {
			builder.append("timezone=");
			builder.append(timezone);
			builder.append(", ");
		}
		if (DST != null) {
			builder.append("DST=");
			builder.append(DST);
			builder.append(", ");
		}
		if (TZ != null) {
			builder.append("TZ=");
			builder.append(TZ);
		}
		builder.append("]");
		return builder.toString();
	}

	public String prettyPrint(){
		return this.name + '-' + this.city + '-' + this.country;
	}
	
	public boolean hasPermisos(Usuario usuario, AccionesSobreObjetosTipos accion) {
		for (Permiso permiso : usuario.getPermisos()) {
			if (permiso.getObjeto().equalsIgnoreCase(this.getClass().getSimpleName())) {
				if (permiso.getAccion().equals(AccionesSobreObjetosTipos.ALL)|| permiso.getAccion().equals(accion)){
					if(permiso.getFiltroFlag()==null || !permiso.getFiltroFlag()) return true;
					else{
						 return true;
					}
				}
			}
			
		}

		return false;
	}
	
}
