package com.Devops.Micro_Market.controller;

import com.Devops.Micro_Market.dto.EmpleadoRequest;
import com.Devops.Micro_Market.entity.Empleado;
import com.Devops.Micro_Market.entity.Cargo;
import com.Devops.Micro_Market.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

   
    @PostMapping
    public ResponseEntity<Empleado> crear(@Valid @RequestBody EmpleadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.crear(request));
    }

   
    @GetMapping
    public ResponseEntity<List<Empleado>> listarTodos() {
        return ResponseEntity.ok(empleadoService.listarTodos());
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(empleadoService.buscarPorId(id));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Integer id,
                                               @Valid @RequestBody EmpleadoRequest request) {
        return ResponseEntity.ok(empleadoService.actualizar(id, request));
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/buscar")
    public ResponseEntity<List<Empleado>> buscarPorCargo(@RequestParam(required = false) Cargo cargo,
                                                         @RequestParam(required = false)
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                                                         @RequestParam(required = false)
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        if (cargo != null) {
            return ResponseEntity.ok(empleadoService.buscarPorCargo(cargo));
        }
        if (inicio != null && fin != null) {
            return ResponseEntity.ok(empleadoService.buscarPorRangoFecha(inicio, fin));
        }
        return ResponseEntity.ok(empleadoService.listarTodos());
    }
}