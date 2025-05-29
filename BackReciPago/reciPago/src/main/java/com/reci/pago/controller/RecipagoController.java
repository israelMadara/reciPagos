package com.reci.pago.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;

import com.reci.pago.dtos.LoginRequest;
import com.reci.pago.dtos.OtpRequest;
import com.reci.pago.modelos.PagoOxxo;
import com.reci.pago.modelos.ServicioCFE;
import com.reci.pago.modelos.Usuario;
import com.reci.pago.repositories.PagoOxxoRepository;
import com.reci.pago.repositories.ServicioCFERepository;
import com.reci.pago.repositories.UsuarioRepository;
import com.reci.pago.security.JwtUtil;
import com.reci.pago.servicies.ServicioGlobal;

@RestController
@RequestMapping("/api/recipago")
@CrossOrigin("*")
public class RecipagoController {
	
	
	@Autowired
	private  ServicioGlobal usuarioService;
	
	
    @Autowired
    private ServicioCFERepository servicioRepository;

    @Autowired
    private PagoOxxoRepository pagoRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
	
	//------------metodos de usaurios
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.registrar(usuario));
    }

    
    
    @PostMapping("/debug")
    public ResponseEntity<String> debug(@RequestBody Map<String, Object> body) {
        System.out.println("Body: " + body);
        return ResponseEntity.ok("Recibido");
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
    	  System.out.println(">> Tel: " + request.getTelefono());
    	    System.out.println(">> Pass: " + request.getPassword());
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getTelefono(), request.getPassword())
        );

        String token = jwtUtil.generateToken(auth);
        Map<String, String> response = new HashMap<>();
        response.put("jwt", token);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/validar-otp")
    public ResponseEntity<Boolean> validarOtp(@RequestParam String telefono, @RequestParam String otp) {
        return ResponseEntity.ok(usuarioService.validarOtp(telefono, otp));
    }
    
    @GetMapping("/usuario/actual")
    public ResponseEntity<Usuario> obtenerUsuarioActual(Authentication authentication) {
        String telefono = authentication.getName();
        Usuario usuario = usuarioRepository.findByTelefono(telefono)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con teléfono: " + telefono));
        return ResponseEntity.ok(usuario);
    }
    
    //-------metodos de Servicios
    
    
    @PostMapping("/servicios")
    public ResponseEntity<ServicioCFE> agregarServicio(@RequestParam Long userId,
                                                       @RequestParam MultipartFile pdfFile,
                                                       @RequestParam String contractNumber) {
        return ResponseEntity.ok(usuarioService.agregarServicio(userId, pdfFile, contractNumber));
    }

    @GetMapping("/servicios/{usuarioId}")
    public ResponseEntity<List<ServicioCFE>> obtenerServiciosPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(servicioRepository.findByUsuarioId(usuarioId));
    }
    
    //------Metodos de pagos------
    
    
    @GetMapping("/pagos/{servicioId}")
    public ResponseEntity<List<PagoOxxo>> listarPagosPorServicio(@PathVariable Long servicioId) {
        return ResponseEntity.ok(pagoRepository.findByServicioId(servicioId));
    }

    @PostMapping("/pagos")
    public ResponseEntity<PagoOxxo> registrarPago(@RequestParam Long servicioId,
                                                  @RequestParam String codigoBarras,
                                                  @RequestParam BigDecimal montoPagado,
                                                  @RequestParam BigDecimal comision) {
        ServicioCFE servicio = servicioRepository.findById(servicioId).orElseThrow();
        PagoOxxo pago = new PagoOxxo();
        pago.setServicio(servicio);
        pago.setCodigoBarras(codigoBarras);
        pago.setFechaPago(LocalDateTime.now());
        pago.setMontoPagado(montoPagado);
        pago.setComision(comision);
        pago.setEstado("PAGADO");
        return ResponseEntity.ok(pagoRepository.save(pago));
    }
	

}
