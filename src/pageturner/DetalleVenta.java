package pageturner;

import java.math.BigDecimal;
import java.util.Objects;

/** Representa un libro y su cantidad dentro de una venta. */
public class DetalleVenta {
    private final Libro libro;
    private final int cantidad;
    private final BigDecimal precioUnitario;

    public DetalleVenta(Libro libro, int cantidad) {
        this.libro = Objects.requireNonNull(libro, "El libro no puede ser nulo");
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        this.cantidad = cantidad;
        this.precioUnitario = libro.getPrecio();
    }

    public Libro getLibro() {
        return libro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal calcularSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
