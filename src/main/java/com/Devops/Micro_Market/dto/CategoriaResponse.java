package com.Devops.Micro_Market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponse {

    private Integer id;
    private String nombre;
    private List<ProductoResponse> productos;
}