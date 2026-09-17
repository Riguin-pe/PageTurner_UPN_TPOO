package pageturner;

import java.time.LocalDateTime;
import java.util.Objects;

/** Solicitud de un cliente para un libro que no se encuentra disponible. */
public class Reserva {
    private final Cliente cliente;
    private final Libro libro;
    private final LocalDateTime fecha;
    private String estado;

    public Reserva(Cliente cliente, Libro libro) {
        this(cliente, libro, LocalDateTime.now());
    }

    public Reserva(Cliente cliente, Libro libro, LocalDateTime fecha) {
        this.cliente = Objects.requireNonNull(cliente, "El cliente no puede ser nulo");
        this.libro = Objects.requireNonNull(libro, "El libro no puede ser nulo");
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
        if (libro.hayStock()) {
            throw new IllegalStateException("Solo se reserva un libro sin stock: " + libro.getTitulo());
        }
        this.estado = "PENDIENTE";
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Libro getLibro() {
        return libro;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void cancelar() {
        estado = "CANCELADA";
    }

    public void confirmar() {
        if (!libro.hayStock()) {
            throw new IllegalStateException("No se puede confirmar: el libro sigue sin stock");
        }
        estado = "CONFIRMADA";
    }
}
