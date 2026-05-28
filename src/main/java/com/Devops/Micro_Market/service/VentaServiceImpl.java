package com.Devops.Micro_Market.service;

import com.Devops.Micro_Market.dto.VentaRequest;
import com.Devops.Micro_Market.dto.VentaResponse;
import com.Devops.Micro_Market.entity.DetalleVenta;
import com.Devops.Micro_Market.entity.Empleado;
import com.Devops.Micro_Market.entity.Producto;
import com.Devops.Micro_Market.entity.Venta;
import com.Devops.Micro_Market.exception.Empresarial;
import com.Devops.Micro_Market.exception.NotFound;
import com.Devops.Micro_Market.repository.EmpleadoRepository;
import com.Devops.Micro_Market.repository.ProductoRepository;
import com.Devops.Micro_Market.repository.VentaRepository;
import com.Devops.Micro_Market.service.VentaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

   
    private static final BigDecimal TASA_IVA = new BigDecimal("0.19");

    private final VentaRepository ventaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ProductoRepository productoRepository;

    public VentaServiceImpl(VentaRepository ventaRepository,
                            EmpleadoRepository empleadoRepository,
                            ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.empleadoRepository = empleadoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public VentaResponse crear(VentaRequest request) {
        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new NotFound(
                        "Empleado no encontrado con ID: " + request.getEmpleadoId()));

        Venta venta = new Venta();
        venta.setEmpleado(empleado);
        venta.setFecha(LocalDateTime.now());

        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal subtotalTotal = BigDecimal.ZERO;

        for (VentaRequest.DetalleVentaRequest detalleReq : request.getDetalles()) {
            Producto producto = productoRepository.findById(detalleReq.getProductoId())
                    .orElseThrow(() -> new NotFound(
                            "Producto no encontrado con ID: " + detalleReq.getProductoId()));

           
            if (!Boolean.TRUE.equals(producto.getEstado())) {
                throw new Empresarial(
                        "El producto '" + producto.getNombre() + "' no está disponible.");
            }

            
            if (producto.getStock() < detalleReq.getCantidad()) {
                throw new Empresarial(
                        "Stock insuficiente para '" + producto.getNombre() +
                        "'. Disponible: " + producto.getStock() +
                        ", solicitado: " + detalleReq.getCantidad());
            }

            
            producto.setStock(producto.getStock() - detalleReq.getCantidad());
            productoRepository.save(producto);

            BigDecimal subtotalDetalle = producto.getPrecio()
                    .multiply(BigDecimal.valueOf(detalleReq.getCantidad()))
                    .setScale(2, RoundingMode.HALF_UP);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleReq.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotalDetalle);
            detalles.add(detalle);

            subtotalTotal = subtotalTotal.add(subtotalDetalle);
        }

       
        BigDecimal iva   = subtotalTotal.multiply(TASA_IVA).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotalTotal.add(iva).setScale(2, RoundingMode.HALF_UP);

        venta.setSubtotal(subtotalTotal);
        venta.setIva(iva);
        venta.setTotal(total);
        venta.setDetalles(detalles);
        ventaRepository.save(venta);

        return mapear(venta);
    }

    @Override
    public List<VentaResponse> listarTodas() {
        return ventaRepository.findAll().stream()
                .map(this::mapear)
                .collect(Collectors.toList());
    }

    @Override
    public VentaResponse buscarPorId(Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new NotFound(
                        "Venta no encontrada con ID: " + id));
        return mapear(venta);
    }

    private VentaResponse mapear(Venta venta) {
        VentaResponse response = new VentaResponse();
        response.setId(venta.getId());
        response.setFecha(venta.getFecha());
        response.setSubtotal(venta.getSubtotal());
        response.setIva(venta.getIva());
        response.setTotal(venta.getTotal());
        response.setEmpleadoNombre(venta.getEmpleado().getNombre());

        if (venta.getDetalles() != null) {
            List<VentaResponse.DetalleVentaResponse> detallesResp = venta.getDetalles()
                    .stream()
                    .map(d -> {
                        VentaResponse.DetalleVentaResponse dr = new VentaResponse.DetalleVentaResponse();
                        dr.setProductoId(d.getProducto().getId());
                        dr.setProductoNombre(d.getProducto().getNombre());
                        dr.setCantidad(d.getCantidad());
                        dr.setPrecioUnitario(d.getPrecioUnitario());
                        dr.setSubtotal(d.getSubtotal());
                        return dr;
                    })
                    .collect(Collectors.toList());
            response.setDetalles(detallesResp);
        }
        return response;
    }
}