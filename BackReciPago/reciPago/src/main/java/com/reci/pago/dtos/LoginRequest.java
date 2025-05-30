package com.reci.pago.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
	
	 @JsonProperty("telefono")
	 private String telefono;
	 
	 @JsonProperty("contrasena")
     private String contrasena;
     
     
     
     
     
	public LoginRequest(String telefono, String contrasena) {
		super();
		this.telefono = telefono;
		this.contrasena = contrasena;
	}

	
	public LoginRequest() {
	}
	
	
	
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getContrasena() {
	    return contrasena;
	}

	public void setContrasena(String contrasena) {
	    this.contrasena = contrasena;
	}
     
     

}
