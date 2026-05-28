package com.Devops.Micro_Market.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {

    private Integer id;
    private String nombre;
    private String codigoBarras;
    private BigDecimal precio;
    private Integer stock;
    private Boolean estado;
    private String categoriaNombre;
}