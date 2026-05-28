package com.Devops.Micro_Market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponse {

    private Integer id;
    private LocalDateTime fecha;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private String empleadoNombre;
    private List<DetalleVentaResponse> detalles;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetalleVentaResponse {

        private Integer productoId;
        private String productoNombre;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;
    }
}