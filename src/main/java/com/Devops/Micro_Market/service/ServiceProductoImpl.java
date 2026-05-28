package com.Devops.Micro_Market.service;

import com.Devops.Micro_Market.dto.ProductoRequest;
import com.Devops.Micro_Market.dto.ProductoResponse;
import com.Devops.Micro_Market.entity.Categoria;
import com.Devops.Micro_Market.entity.Producto;
import com.Devops.Micro_Market.exception.Empresarial;
import com.Devops.Micro_Market.exception.NotFound;
import com.Devops.Micro_Market.repository.CategoriaRepository;
import com.Devops.Micro_Market.repository.ProductoRepository;
import com.Devops.Micro_Market.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceProductoImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ServiceProductoImpl(ProductoRepository productoRepository,
                               CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
       
        if (productoRepository.existsByCodigoBarras(request.getCodigoBarras())) {
            throw new Empresarial("Ya existe un producto con el código de barras: "
                    + request.getCodigoBarras());
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new NotFound(
                        "Categoría no encontrada con ID: " + request.getCategoriaId()));

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setCodigoBarras(request.getCodigoBarras());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setEstado(true);
        producto.setCategoria(categoria);
        productoRepository.save(producto);
        return mapear(producto);
    }

    @Override
    public List<ProductoResponse> listarTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapear)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponse buscarPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotFound(
                        "Producto no encontrado con ID: " + id));
        return mapear(producto);
    }

    @Override
    public ProductoResponse actualizar(Integer id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotFound(
                        "Producto no encontrado con ID: " + id));

        if (productoRepository.existsByCodigoBarrasAndIdNot(request.getCodigoBarras(), id)) {
            throw new Empresarial("Ya existe un producto con el código de barras: "
                    + request.getCodigoBarras());
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new NotFound(
                        "Categoría no encontrada con ID: " + request.getCategoriaId()));

        producto.setNombre(request.getNombre());
        producto.setCodigoBarras(request.getCodigoBarras());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);
        productoRepository.save(producto);
        return mapear(producto);
    }

    @Override
    public void eliminar(Integer id) {
       
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotFound(
                        "Producto no encontrado con ID: " + id));
        producto.setEstado(false);
        productoRepository.save(producto);
    }

    private ProductoResponse mapear(Producto p) {
        String categoriaNombre = p.getCategoria() != null
                ? p.getCategoria().getNombre()
                : null;
        return new ProductoResponse(
                p.getId(), p.getNombre(), p.getCodigoBarras(),
                p.getPrecio(), p.getStock(), p.getEstado(), categoriaNombre);
    }
}