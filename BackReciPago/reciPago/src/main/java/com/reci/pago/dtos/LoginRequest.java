package com.reci.pago.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
	
	 @JsonProperty("telefono")
	 private String telefono;
	 @JsonProperty("telefono")
     private String password;
     
     
     
     
     
	public LoginRequest(String telefono, String password) {
		super();
		this.telefono = telefono;
		this.password = password;
	}

	
	public LoginRequest() {
	}
	
	
	
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
     
     
     

}
