package pageturner;

import java.math.BigDecimal;
import java.util.Objects;

/** Contiene los datos bibliográficos y el inventario de un libro. */
public class Libro {
    private final String titulo;
    private final String autor;
    private final String isbn;
    private BigDecimal precio;
    private int stock;

    public Libro(String titulo, String autor, String isbn, BigDecimal precio, int stock) {
        this.titulo = textoObligatorio(titulo, "título");
        this.autor = textoObligatorio(autor, "autor");
        this.isbn = textoObligatorio(isbn, "ISBN");
        setPrecio(precio);
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.stock = stock;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setPrecio(BigDecimal precio) {
        Objects.requireNonNull(precio, "El precio no puede ser nulo");
        if (precio.signum() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }

    public boolean hayStock() {
        return stock > 0;
    }

    public boolean hayStock(int cantidad) {
        validarCantidad(cantidad);
        return stock >= cantidad;
    }

    public void descontarStock(int cantidad) {
        if (!hayStock(cantidad)) {
            throw new IllegalStateException("Stock insuficiente para: " + titulo);
        }
        stock -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        validarCantidad(cantidad);
        stock += cantidad;
    }

    private static void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
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
        return titulo + " - " + autor + " (stock: " + stock + ")";
    }
}
