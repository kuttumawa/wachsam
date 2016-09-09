package es.io.wachsam.model;

import com.google.gson.annotations.Expose;


public class ResultadoBusqueda {
	@Expose
	int numpages;
	@Expose
	int currentpage;
	@Expose
	int totalResults;
	@Expose
	int pageSize;
	@Expose
    Object[] data=null;
	public int getNumpages() {
		return numpages;
	}
	public void setNumpages(int numpages) {
		this.numpages = numpages;
	}
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	public Object[] getData() {
		return data;
	}
	public void setData(Object[] data) {
		this.data = data;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	

}
