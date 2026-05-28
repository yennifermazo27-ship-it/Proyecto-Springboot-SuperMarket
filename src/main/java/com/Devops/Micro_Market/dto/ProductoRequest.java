package com.Devops.Micro_Market.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductoRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "El código de barras no puede estar vacío")
    @Size(max = 100)
    private String codigoBarras;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "La categoría es obligatoria")
    private Integer categoriaId;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }
}