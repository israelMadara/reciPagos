package com.reci.pago.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reci.pago.modelos.ServicioCFE;

@Repository
public interface ServicioCFERepository extends JpaRepository<ServicioCFE, Long> {
    List<ServicioCFE> findByUsuarioId(Long usuarioId);
    List<ServicioCFE> findByFechaVencimiento(LocalDate fecha);
}
