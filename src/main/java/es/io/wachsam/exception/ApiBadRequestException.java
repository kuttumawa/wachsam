package es.io.wachsam.exception;

import com.google.gson.annotations.Expose;

public class ApiBadRequestException extends Exception {

	private static final long serialVersionUID = 1L;
    @Expose
	private int error;
    @Expose
    private String errorMessage;
	public ApiBadRequestException() {
		super();
	
	}

	

	public ApiBadRequestException(int httpErrorCode, String message) {		
		super(message);
		this.error=httpErrorCode;
		this.errorMessage=message;
	}



	public int getError() {
		return error;
	}



	public void setError(int error) {
		this.error = error;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	


}
