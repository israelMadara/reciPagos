package com.reci.pago.modelos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicios")
public class ServicioCFE {
	
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "numero_contrato", nullable = false)
	    private String numeroContrato;

	    @Column(name = "monto", nullable = false)
	    private BigDecimal monto;

	    @Column(name = "fecha_vencimiento", nullable = false)
	    private LocalDate fechaVencimiento;

	    @Column(name = "pdf_data", columnDefinition = "TEXT")
	    private String pdfData; // Datos extraídos del PDF (ej: JSON con fecha/monto)

	    @ManyToOne
	    @JoinColumn(name = "usuario_id", nullable = false)
	    private Usuario usuario;

	    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL)
	    private PagoOxxo pago;

	    
	    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL)
	    private List<PagoOxxo> pagos;
	    
	    
	    
	    
	    
	    
	    
	

		public ServicioCFE(Long id, String numeroContrato, BigDecimal monto, LocalDate fechaVencimiento, String pdfData,
				Usuario usuario, PagoOxxo pago, List<PagoOxxo> pagos) {
			super();
			this.id = id;
			this.numeroContrato = numeroContrato;
			this.monto = monto;
			this.fechaVencimiento = fechaVencimiento;
			this.pdfData = pdfData;
			this.usuario = usuario;
			this.pago = pago;
			this.pagos = pagos;
		}



		public ServicioCFE() {
	
		}
		
		
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNumeroContrato() {
			return numeroContrato;
		}

		public void setNumeroContrato(String numeroContrato) {
			this.numeroContrato = numeroContrato;
		}

		public BigDecimal getMonto() {
			return monto;
		}

		public void setMonto(BigDecimal monto) {
			this.monto = monto;
		}

		public LocalDate getFechaVencimiento() {
			return fechaVencimiento;
		}

		public void setFechaVencimiento(LocalDate fechaVencimiento) {
			this.fechaVencimiento = fechaVencimiento;
		}

		public String getPdfData() {
			return pdfData;
		}

		public void setPdfData(String pdfData) {
			this.pdfData = pdfData;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

		public PagoOxxo getPago() {
			return pago;
		}

		public void setPago(PagoOxxo pago) {
			this.pago = pago;
		}

		public List<PagoOxxo> getPagos() {
			return pagos;
		}

		public void setPagos(List<PagoOxxo> pagos) {
			this.pagos = pagos;
		}
	    
	    
	    
	    
	    
	    
	    
	    
	    


}
