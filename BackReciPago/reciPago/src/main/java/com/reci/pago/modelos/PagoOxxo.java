package com.reci.pago.modelos;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagoOxxo")
public class PagoOxxo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoBarras;
    private LocalDateTime fechaPago;
    private BigDecimal montoPagado;
    private BigDecimal comision;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private ServicioCFE servicio;
    
    

	
    

	public PagoOxxo(Long id, String codigoBarras, LocalDateTime fechaPago, BigDecimal montoPagado, BigDecimal comision,
			String estado, ServicioCFE servicio) {
		super();
		this.id = id;
		this.codigoBarras = codigoBarras;
		this.fechaPago = fechaPago;
		this.montoPagado = montoPagado;
		this.comision = comision;
		this.estado = estado;
		this.servicio = servicio;
	}






	public PagoOxxo() {
	}






	public Long getId() {
		return id;
	}






	public void setId(Long id) {
		this.id = id;
	}






	public String getCodigoBarras() {
		return codigoBarras;
	}






	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}






	public LocalDateTime getFechaPago() {
		return fechaPago;
	}






	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}






	public BigDecimal getMontoPagado() {
		return montoPagado;
	}






	public void setMontoPagado(BigDecimal montoPagado) {
		this.montoPagado = montoPagado;
	}






	public BigDecimal getComision() {
		return comision;
	}






	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}






	public String getEstado() {
		return estado;
	}






	public void setEstado(String estado) {
		this.estado = estado;
	}






	public ServicioCFE getServicio() {
		return servicio;
	}






	public void setServicio(ServicioCFE servicio) {
		this.servicio = servicio;
	}
	
	
    
    
    
	

}
