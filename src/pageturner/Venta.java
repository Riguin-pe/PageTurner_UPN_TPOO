package pageturner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Agrupa los libros comprados por un cliente en una única operación. */
public class Venta {
    private final Cliente cliente;
    private final LocalDateTime fecha;
    private final List<DetalleVenta> detalles = new ArrayList<>();
    private boolean registrada;

    public Venta(Cliente cliente) {
        this(cliente, LocalDateTime.now());
    }

    public Venta(Cliente cliente, LocalDateTime fecha) {
        this.cliente = Objects.requireNonNull(cliente, "El cliente no puede ser nulo");
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public boolean isRegistrada() {
        return registrada;
    }

    public List<DetalleVenta> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }

    public void agregarDetalle(Libro libro, int cantidad) {
        if (registrada) {
            throw new IllegalStateException("No se puede modificar una venta registrada");
        }
        detalles.add(new DetalleVenta(libro, cantidad));
    }

    public BigDecimal calcularTotal() {
        return detalles.stream()
                .map(DetalleVenta::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Valida todo el inventario y, si es suficiente, descuenta el stock de cada libro.
     */
    public void registrar() {
        if (registrada) {
            throw new IllegalStateException("La venta ya fue registrada");
        }
        if (detalles.isEmpty()) {
            throw new IllegalStateException("La venta debe tener al menos un detalle");
        }

        Map<Libro, Integer> cantidadesPorLibro = new LinkedHashMap<>();
        for (DetalleVenta detalle : detalles) {
            cantidadesPorLibro.merge(detalle.getLibro(), detalle.getCantidad(), Integer::sum);
        }
        for (Map.Entry<Libro, Integer> entrada : cantidadesPorLibro.entrySet()) {
            if (!entrada.getKey().hayStock(entrada.getValue())) {
                throw new IllegalStateException("Stock insuficiente para: " + entrada.getKey().getTitulo());
            }
        }
        for (Map.Entry<Libro, Integer> entrada : cantidadesPorLibro.entrySet()) {
            entrada.getKey().descontarStock(entrada.getValue());
        }
        registrada = true;
    }
}
