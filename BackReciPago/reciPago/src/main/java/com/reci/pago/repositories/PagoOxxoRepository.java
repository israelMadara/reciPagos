package com.reci.pago.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reci.pago.modelos.PagoOxxo;

@Repository
public interface PagoOxxoRepository extends JpaRepository<PagoOxxo, Long> {
    List<PagoOxxo> findByServicioId(Long servicioId);
}
