package com.Devops.Micro_Market.service;

import com.Devops.Micro_Market.dto.EntradaAlmacenRequest;
import com.Devops.Micro_Market.dto.ProveedorRequest;
import com.Devops.Micro_Market.entity.Producto;
import com.Devops.Micro_Market.entity.Proveedor;
import com.Devops.Micro_Market.exception.Empresarial;
import com.Devops.Micro_Market.exception.NotFound;
import com.Devops.Micro_Market.repository.ProductoRepository;
import com.Devops.Micro_Market.repository.ProveedorRepository;
import com.Devops.Micro_Market.service.ProveedorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository,
                                ProductoRepository productoRepository) {
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public Proveedor crear(ProveedorRequest request) {
       
        if (proveedorRepository.existsByNit(request.getNit())) {
            throw new Empresarial("Ya existe un proveedor con el NIT: " + request.getNit());
        }
        return proveedorRepository.save(mapearDesdeRequest(new Proveedor(), request));
    }

    @Override
    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor buscarPorId(Integer id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new NotFound(
                        "Proveedor no encontrado con ID: " + id));
    }

    @Override
    public Proveedor actualizar(Integer id, ProveedorRequest request) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new NotFound(
                        "Proveedor no encontrado con ID: " + id));

        if (proveedorRepository.existsByNitAndIdNot(request.getNit(), id)) {
            throw new Empresarial("Ya existe un proveedor con el NIT: " + request.getNit());
        }
        return proveedorRepository.save(mapearDesdeRequest(proveedor, request));
    }

    @Override
    public void eliminar(Integer id) {
        if (!proveedorRepository.existsById(id)) {
            throw new NotFound("Proveedor no encontrado con ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    @Override
    public void entradaAlmacen(EntradaAlmacenRequest request) {
        
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new NotFound(
                        "Producto no encontrado con ID: " + request.getProductoId()));

        Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new NotFound(
                        "Proveedor no encontrado con ID: " + request.getProveedorId()));

     
        if (!producto.getProveedores().contains(proveedor)) {
            producto.getProveedores().add(proveedor);
        }

        
        producto.setStock(producto.getStock() + request.getCantidad());
        productoRepository.save(producto);
    }

    private Proveedor mapearDesdeRequest(Proveedor proveedor, ProveedorRequest request) {
        proveedor.setNombre(request.getNombre());
        proveedor.setNit(request.getNit());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setCorreo(request.getCorreo());
        proveedor.setDireccion(request.getDireccion());
        return proveedor;
    }
}
