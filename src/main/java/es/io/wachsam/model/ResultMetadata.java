package es.io.wachsam.model;

public class ResultMetadata {
	Integer max;
	Integer skip;
	Integer totalResultMatching;
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getSkip() {
		return skip;
	}
	public void setSkip(Integer skip) {
		this.skip = skip;
	}
	public Integer getTotalResultMatching() {
		return totalResultMatching;
	}
	public void setTotalResultMatching(Integer totalResultMatching) {
		this.totalResultMatching = totalResultMatching;
	}
	
}
