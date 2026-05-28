package com.Devops.Micro_Market.service;

import com.Devops.Micro_Market.dto.EmpleadoRequest;
import com.Devops.Micro_Market.entity.Empleado;
import com.Devops.Micro_Market.entity.Cargo;
import com.Devops.Micro_Market.exception.Empresarial;
import com.Devops.Micro_Market.exception.NotFound;
import com.Devops.Micro_Market.repository.EmpleadoRepository;
import com.Devops.Micro_Market.service.EmpleadoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado crear(EmpleadoRequest request) {
        if (empleadoRepository.existsByCedula(request.getCedula())) {
            throw new Empresarial("Ya existe un empleado con la cédula: " + request.getCedula());
        }
        return empleadoRepository.save(mapear(new Empleado(), request));
    }

    @Override
    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado buscarPorId(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new NotFound(
                        "Empleado no encontrado con ID: " + id));
    }

    @Override
    public Empleado actualizar(Integer id, EmpleadoRequest request) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new NotFound(
                        "Empleado no encontrado con ID: " + id));
        return empleadoRepository.save(mapear(empleado, request));
    }

    @Override
    public void eliminar(Integer id) {
        if (!empleadoRepository.existsById(id)) {
            throw new NotFound("Empleado no encontrado con ID: " + id);
        }
        empleadoRepository.deleteById(id);
    }

    @Override
    public List<Empleado> buscarPorCargo(Cargo cargo) {
        return empleadoRepository.findByCargo(cargo);
    }

    @Override
    public List<Empleado> buscarPorRangoFecha(LocalDate inicio, LocalDate fin) {
        return empleadoRepository.findByFechaIngresoBetween(inicio, fin);
    }

    private Empleado mapear(Empleado empleado, EmpleadoRequest request) {
        empleado.setCedula(request.getCedula());
        empleado.setNombre(request.getNombre());
        empleado.setCargo(request.getCargo());
        empleado.setFechaIngreso(request.getFechaIngreso());
        empleado.setSalario(request.getSalario());
        return empleado;
    }
}