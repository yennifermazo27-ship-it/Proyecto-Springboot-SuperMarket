package com.Devops.Micro_Market.service;

import com.Devops.Micro_Market.dto.CategoriaRequest;
import com.Devops.Micro_Market.dto.CategoriaResponse;

import java.util.List;

public interface CategoriaService {
    CategoriaResponse crear(CategoriaRequest request);
    List<CategoriaResponse> listarTodas();
    CategoriaResponse buscarPorId(Integer id);
    CategoriaResponse actualizar(Integer id, CategoriaRequest request);
    void eliminar(Integer id);
}