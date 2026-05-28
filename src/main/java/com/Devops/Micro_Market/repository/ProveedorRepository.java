package com.Devops.Micro_Market.repository;

import com.Devops.Micro_Market.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    boolean existsByNit(String nit);
    boolean existsByNitAndIdNot(String nit, Integer id);
}