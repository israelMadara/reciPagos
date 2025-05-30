package com.reci.pago.utils;


import io.conekta.Conekta;
import io.conekta.Order;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UtilidadPagoOxxoConekta {

    @Value("${conekta.api-key}")
    private String claveApiConekta;

    private static final int EXPIRACION_SEGUNDOS = 172800; // 48 horas

    public String generarCodigoOxxo(BigDecimal monto) throws Exception {
        Conekta.setApiKey(claveApiConekta);
        Conekta.setLocale("es");

        Map<String, Object> params = new HashMap<>();
        params.put("currency", "MXN");
        params.put("line_items", List.of(
            new HashMap<String, Object>() {{
                put("name", "Pago CFE");
                put("unit_price", monto.multiply(BigDecimal.valueOf(100)).intValueExact());
                put("quantity", 1);
            }}
        ));
        params.put("charges", List.of(
            new HashMap<String, Object>() {{
                put("payment_method", new HashMap<String, Object>() {{
                    put("type", "oxxo_cash");
                    put("expires_at", (System.currentTimeMillis() / 1000) + EXPIRACION_SEGUNDOS);
                }});
            }}
        ));

        try {
            Order orden = Order.create(new JSONObject(params));
            Map<String, Object> charge = (Map<String, Object>) orden.charges.get(0);
            Map<String, Object> paymentMethod = (Map<String, Object>) charge.get("payment_method");
            return (String) paymentMethod.get("reference");
        } catch (Exception e) {
            // Podrías usar un logger aquí
            throw new Exception("Error generando código Oxxo con Conekta", e);
        }
    }
}
