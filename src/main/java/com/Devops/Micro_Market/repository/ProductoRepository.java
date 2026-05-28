package com.Devops.Micro_Market.repository;

import com.Devops.Micro_Market.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    boolean existsByCodigoBarras(String codigoBarras);
    boolean existsByCodigoBarrasAndIdNot(String codigoBarras, Integer id);
    Optional<Producto> findByIdAndEstadoTrue(Integer id);
}