package com.Devops.Micro_Market.service;

import com.Devops.Micro_Market.dto.ProductoRequest;
import com.Devops.Micro_Market.dto.ProductoResponse;

import java.util.List;

public interface ProductoService {
    ProductoResponse crear(ProductoRequest request);
    List<ProductoResponse> listarTodos();
    ProductoResponse buscarPorId(Integer id);
    ProductoResponse actualizar(Integer id, ProductoRequest request);
    void eliminar(Integer id); // Soft Delete
}