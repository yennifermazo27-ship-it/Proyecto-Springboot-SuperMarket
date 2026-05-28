package com.Devops.Micro_Market.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProveedorRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "El NIT es obligatorio")
    @Size(max = 50)
    private String nit;

    @Size(max = 20)
    private String telefono;

    @Size(max = 100)
    private String correo;

    @Size(max = 200)
    private String direccion;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}