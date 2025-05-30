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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.reci.pago.dtos.LoginRequest;
import com.reci.pago.dtos.OtpRequest;
import com.reci.pago.dtos.OxxoWebhookRequest;
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
    
    @Autowired
    private PasswordEncoder passwordEncoder;

	
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
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        String telefono = loginRequest.getTelefono();

   
        String token = jwtUtil.generateToken(telefono); // usa tu método actual de generación
        return Map.of("token", token);
    }
   /* 
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        String telefono = loginRequest.getTelefono();
        String contrasena = loginRequest.getPassword();

        Usuario user = usuarioRepository.findByTelefono(telefono)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!user.isCuentaActivada()) {
            throw new RuntimeException("Cuenta no activada. Verifica tu OTP.");
        }

        if (!passwordEncoder.matches(contrasena, user.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(telefono);
        return ResponseEntity.ok(Map.of("token", token));
    }
*/
    
    @PostMapping("/debug-body")
    public ResponseEntity<String> debugBody(@RequestBody Map<String, Object> body) {
        System.out.println("Recibido: " + body);
        return ResponseEntity.ok("OK");
    }
    
    
    
    @GetMapping("/usuario/actual")
    public ResponseEntity<Usuario> obtenerUsuarioActual(Authentication authentication) {
        String telefono = authentication.getName();
        Usuario usuario = usuarioRepository.findByTelefono(telefono)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con teléfono: " + telefono));
        return ResponseEntity.ok(usuario);
    }
    
    
    @PostMapping("/validar-otp")
    public ResponseEntity<Boolean> validarOtp(@RequestParam String telefono, @RequestParam String otp) {
        return ResponseEntity.ok(usuarioService.validarOtp(telefono, otp));
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
	
    
    
    
    
    //-------ppago con oxxo
    @PostMapping("/webhook/oxxo")
    public ResponseEntity<String> recibirWebhookOxxo(@RequestBody OxxoWebhookRequest payload,
                                                    @RequestHeader(value = "X-Auth-Token", required = false) String authToken) {

        // Validar token secreto (lo configuras y compartes con Oxxo)
        if (!"mi_token_secreto".equals(authToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        System.out.println("Webhook recibido: " + payload);

        try {
        	usuarioService.actualizarPagoOxxo(
                payload.getCodigoBarras(),
                payload.getEstado(),
                payload.getMonto(),
                payload.getFechaPago()
            );
        } catch (Exception e) {
            // Log y devuelve error para que Oxxo pueda reintentar
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar webhook");
        }

        return ResponseEntity.ok("Webhook procesado correctamente");
    }

    
    
    
    
    
    
    
    
    
    

}
