package com.reci.pago.servicies;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.management.ServiceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reci.pago.modelos.PagoOxxo;
import com.reci.pago.modelos.ServicioCFE;
import com.reci.pago.modelos.Usuario;
import com.reci.pago.repositories.PagoOxxoRepository;
import com.reci.pago.repositories.ServicioCFERepository;
import com.reci.pago.repositories.UsuarioRepository;
import com.reci.pago.utils.PdfExtractorService;
import com.reci.pago.utils.TwilioOTPUtil;
import com.reci.pago.utils.UtilidadPagoOxxoConekta;

@Service
public class ServicioGlobal implements UserDetailsService {
	

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private ServicioCFERepository servicioCFERepository;

    @Autowired
    private PagoOxxoRepository pagoOxxoRepository;

    @Autowired
    private UtilidadPagoOxxoConekta conektaOxxoUtil;

    @Autowired
    private PdfExtractorService pdfExtractorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TwilioOTPUtil twilioOTPUtil;
    
    
    
    
    //---------usuario-------------

    public Usuario registrar(Usuario user) {
        if (userRepository.existsByTelefono(user.getTelefono())) {
            throw new RuntimeException("El teléfono ya está registrado");
        }
        
        // Encriptar contraseña
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        
        // Generar y enviar OTP (retorna el OTP generado)
        String otp = twilioOTPUtil.sendOTP(user.getTelefono());
        
        // Guardar OTP y activar cuenta como false
        user.setOtp(otp);
        user.setCuentaActivada(false);
        
        return userRepository.save(user);
    }  
    
    
    public boolean validarOtp(String telefono, String otp) {
        Optional<Usuario> userOpt = userRepository.findByTelefono(telefono);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Usuario user = userOpt.get();
        if (user.getOtp() != null && user.getOtp().equals(otp)) {
            user.setCuentaActivada(true);
            user.setOtp(null); // limpiar OTP
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String telefono) throws UsernameNotFoundException {
        // Busca usuario por teléfono
        Usuario user = userRepository.findByTelefono(telefono)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con teléfono: " + telefono));

        // Crea el UserDetails usando usuario y contraseña. No roles, por eso se usa emptyList()
        return new User(
            user.getTelefono(),
            user.getContrasena(),
            user.isCuentaActivada(),  // enabled
            true,  // accountNonExpired
            true,  // credentialsNonExpired
            true,  // accountNonLocked
            java.util.Collections.emptyList()  // no hay roles
        );
    }
    
    
    
    
    //---------ServicioCFE-------------

    
    
    public ServicioCFE agregarServicio(Long userId, MultipartFile pdfFile, String contractNumber) {
        Usuario user = userRepository.findById(userId)
        		.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        ServicioCFE servicio = new ServicioCFE();
        servicio.setUsuario(user);
        servicio.setNumeroContrato(contractNumber);
        
        if (pdfFile != null) {
            String extractedData = pdfExtractorService.extractDataFromPdf(pdfFile);
            servicio.setPdfData(extractedData);// Guarda datos extraídos (ej: fecha, monto)
            
            
            /*
              // Aquí deberías parsear y asignar fechaVencimiento y monto si están en el String
        // por ejemplo si `extractedData = "2025-07-15|345.60"`:
        String[] partes = extractedData.split("\\|");
        if (partes.length == 2) {
            servicio.setFechaVencimiento(LocalDate.parse(partes[0]));
            servicio.setMonto(Double.parseDouble(partes[1]));
        }
             
             * */
            
        }
        
        return servicioCFERepository.save(servicio);
    }
	
	
    
    
    
    
    
    
    //---------PAGO OXXO---------------
    
    public PagoOxxo generarPagoOxxo(Long servicioId) throws ServiceNotFoundException {
    	ServicioCFE servicio=new ServicioCFE ();
		servicio = servicioCFERepository.findById(servicioId)
				.orElseThrow(()   -> new RuntimeException("Servicio no encontrado"));
        
	    String codigoDeBarras = null;
		try {
			codigoDeBarras = conektaOxxoUtil.generarCodigoOxxo(servicio.getMonto());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (codigoDeBarras == null) {
            throw new RuntimeException("Error generando código Oxxo con Conekta");
        }
        
      PagoOxxo pago= new PagoOxxo();
      pago.setServicio(servicio);
      pago.setMontoPagado(servicio.getMonto());
      pago.setCodigoBarras(codigoDeBarras);
      pago.setEstado("PENDING");
        
        return pagoOxxoRepository.save(pago);
    }
    
    
    
    
 // Servicio o controlador
    public void actualizarPagoOxxo(String codigoBarras, String estado, BigDecimal monto, LocalDateTime fechaPago) {
        PagoOxxo pago = pagoOxxoRepository.findFirstByCodigoBarras(codigoBarras)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado con código de barras: " + codigoBarras));

        pago.setEstado(estado);
        pago.setMontoPagado(monto);
        pago.setFechaPago(fechaPago);

        pagoOxxoRepository.save(pago);
    }


    
    
    
    
    
    
	

}
