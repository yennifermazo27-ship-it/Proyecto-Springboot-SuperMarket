package com.Devops.Micro_Market.service;

import com.Devops.Micro_Market.dto.VentaRequest;
import com.Devops.Micro_Market.dto.VentaResponse;

import java.util.List;

public interface VentaService {
    VentaResponse crear(VentaRequest request);
    List<VentaResponse> listarTodas();
    VentaResponse buscarPorId(Integer id);
}