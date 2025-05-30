package com.reci.pago.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OxxoWebhookRequest {
    private String codigoBarras;
    private String estado;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private String referencia;

    
    
    
    
    
    
    
    
    public OxxoWebhookRequest(String codigoBarras, String estado, BigDecimal monto, LocalDateTime fechaPago,
			String referencia) {
		super();
		this.codigoBarras = codigoBarras;
		this.estado = estado;
		this.monto = monto;
		this.fechaPago = fechaPago;
		this.referencia = referencia;
	}
    
    
    public OxxoWebhookRequest() {

	}

	// Getters y setters

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
