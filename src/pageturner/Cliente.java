package pageturner;

import java.util.Objects;

/** Representa a una persona que compra o reserva libros. */
public class Cliente {
    private final String nombre;
    private final String dni;
    private final String correo;

    public Cliente(String nombre, String dni, String correo) {
        this.nombre = textoObligatorio(nombre, "nombre");
        this.dni = textoObligatorio(dni, "DNI");
        this.correo = textoObligatorio(correo, "correo");
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getCorreo() {
        return correo;
    }

    private static String textoObligatorio(String valor, String campo) {
        Objects.requireNonNull(valor, campo + " no puede ser nulo");
        if (valor.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
        return valor;
    }

    @Override
    public String toString() {
        return nombre + " (DNI: " + dni + ")";
    }
}
