package com.Devops.Micro_Market.service;

import com.Devops.Micro_Market.dto.CategoriaRequest;
import com.Devops.Micro_Market.dto.CategoriaResponse;
import com.Devops.Micro_Market.dto.ProductoResponse;
import com.Devops.Micro_Market.entity.Categoria;
import com.Devops.Micro_Market.exception.NotFound;
import com.Devops.Micro_Market.repository.CategoriaRepository;
import com.Devops.Micro_Market.service.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaResponse crear(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoriaRepository.save(categoria);
        return mapearRespuesta(categoria);
    }

    @Override
public List<CategoriaResponse> listarTodas() {
    return categoriaRepository.findAll()
            .stream()
            .map(this::mapearConProductosActivos)
            .collect(Collectors.toList());
}

    @Override
    public CategoriaResponse buscarPorId(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFound("Categoría no encontrada con ID: " + id));
        return mapearConProductosActivos(categoria);
    }

    @Override
    public CategoriaResponse actualizar(Integer id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFound("Categoría no encontrada con ID: " + id));
        categoria.setNombre(request.getNombre());
        categoriaRepository.save(categoria);
        return mapearRespuesta(categoria);
    }

    @Override
    public void eliminar(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new NotFound("Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

  
    private CategoriaResponse mapearRespuesta(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNombre(), null);
    }

   
    private CategoriaResponse mapearConProductosActivos(Categoria categoria) {
        List<ProductoResponse> productosActivos = null;
        if (categoria.getProductos() != null) {
            productosActivos = categoria.getProductos().stream()
                    .filter(p -> Boolean.TRUE.equals(p.getEstado()))
                    .map(p -> new ProductoResponse(
                            p.getId(), p.getNombre(), p.getCodigoBarras(),
                            p.getPrecio(), p.getStock(), p.getEstado(),
                            categoria.getNombre()))
                    .collect(Collectors.toList());
        }
        return new CategoriaResponse(categoria.getId(), categoria.getNombre(), productosActivos);
    }
}