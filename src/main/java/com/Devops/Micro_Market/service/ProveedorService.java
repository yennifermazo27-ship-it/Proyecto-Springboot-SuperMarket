package com.Devops.Micro_Market.service;

import com.Devops.Micro_Market.dto.EntradaAlmacenRequest;
import com.Devops.Micro_Market.dto.ProveedorRequest;
import com.Devops.Micro_Market.entity.Proveedor;

import java.util.List;

public interface ProveedorService {
    Proveedor crear(ProveedorRequest request);
    List<Proveedor> listarTodos();
    Proveedor buscarPorId(Integer id);
    Proveedor actualizar(Integer id, ProveedorRequest request);
    void eliminar(Integer id);
    void entradaAlmacen(EntradaAlmacenRequest request); // Módulo II - Regla 1
}