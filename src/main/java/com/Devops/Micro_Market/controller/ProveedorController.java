package com.Devops.Micro_Market.controller;

import com.Devops.Micro_Market.dto.EntradaAlmacenRequest;
import com.Devops.Micro_Market.dto.ProveedorRequest;
import com.Devops.Micro_Market.entity.Proveedor;
import com.Devops.Micro_Market.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

 
    @PostMapping
    public ResponseEntity<Proveedor> crear(@Valid @RequestBody ProveedorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.crear(request));
    }

   
    @GetMapping
    public ResponseEntity<List<Proveedor>> listarTodos() {
        return ResponseEntity.ok(proveedorService.listarTodos());
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedorService.buscarPorId(id));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Integer id,
                                                @Valid @RequestBody ProveedorRequest request) {
        return ResponseEntity.ok(proveedorService.actualizar(id, request));
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

   
    @PostMapping("/entrada-almacen")
    public ResponseEntity<Map<String, String>> entradaAlmacen(@Valid @RequestBody EntradaAlmacenRequest request) {
        proveedorService.entradaAlmacen(request);
        return ResponseEntity.ok(Map.of("mensaje", "Entrada de almacén registrada correctamente."));
    }
}