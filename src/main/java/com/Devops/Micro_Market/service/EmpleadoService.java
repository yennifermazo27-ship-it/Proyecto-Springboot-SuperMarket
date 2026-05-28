package com.Devops.Micro_Market.service;

import com.Devops.Micro_Market.dto.EmpleadoRequest;
import com.Devops.Micro_Market.entity.Empleado;
import com.Devops.Micro_Market.entity.Cargo;

import java.time.LocalDate;
import java.util.List;

public interface EmpleadoService {
    Empleado crear(EmpleadoRequest request);
    List<Empleado> listarTodos();
    Empleado buscarPorId(Integer id);
    Empleado actualizar(Integer id, EmpleadoRequest request);
    void eliminar(Integer id);
    List<Empleado> buscarPorCargo(Cargo cargo);                          
    List<Empleado> buscarPorRangoFecha(LocalDate inicio, LocalDate fin); }