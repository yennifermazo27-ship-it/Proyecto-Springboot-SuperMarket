package com.Devops.Micro_Market.repository;

import com.Devops.Micro_Market.entity.Empleado;
import com.Devops.Micro_Market.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    boolean existsByCedula(String cedula);
    List<Empleado> findByCargo(Cargo cargo);
    List<Empleado> findByFechaIngresoBetween(LocalDate inicio, LocalDate fin);
}