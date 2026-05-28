package com.Devops.Micro_Market.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class VentaRequest {

    @NotNull(message = "El empleado es obligatorio")
    private Integer empleadoId;

    @NotEmpty(message = "La venta debe tener al menos un producto")
    @Valid
    private List<DetalleVentaRequest> detalles;

    public Integer getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Integer empleadoId) { this.empleadoId = empleadoId; }

    public List<DetalleVentaRequest> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVentaRequest> detalles) { this.detalles = detalles; }

    public static class DetalleVentaRequest {

        @NotNull(message = "El producto es obligatorio")
        private Integer productoId;

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser mayor a 0")
        private Integer cantidad;

        public Integer getProductoId() { return productoId; }
        public void setProductoId(Integer productoId) { this.productoId = productoId; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}