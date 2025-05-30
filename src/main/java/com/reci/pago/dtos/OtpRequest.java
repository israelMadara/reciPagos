package com.reci.pago.dtos;

public class OtpRequest {

	
	private String telefono;
    private String otp;

    
    
    
    
    
    
    
    public OtpRequest(String telefono, String otp) {
		super();
		this.telefono = telefono;
		this.otp = otp;
	}

    
    
    public OtpRequest() {
			}
    
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }

	
}
