package com.reci.pago.modelos;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;



@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String password;
    private String otp;
    private boolean cuentaActivada;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<ServicioCFE> servicios;
    
    
    
	public Usuario(Long id, String nombre, String email, String telefono, String password, String otp,
			boolean cuentaActivada) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.password = password;
		this.otp = otp;
		this.cuentaActivada = cuentaActivada;
	}
	
	
	public Usuario() {
		
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getContrasena() {
		return password;
	}


	public void setContrasena(String password) {
		this.password = password;
	}


	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public boolean isCuentaActivada() {
		return cuentaActivada;
	}
	public void setCuentaActivada(boolean cuentaActivada) {
		this.cuentaActivada = cuentaActivada;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
	    return this.password;
	}

	@Override
	public String getUsername() {
	    return this.telefono;
	}
    
    
    
    
    
    
    
    
    
    
    
    
}