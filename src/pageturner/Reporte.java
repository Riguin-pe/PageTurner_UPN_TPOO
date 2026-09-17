package pageturner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Calcula consultas de ventas e ingresos a partir del historial disponible. */
public class Reporte {
    public BigDecimal calcularIngresos(List<Venta> ventas) {
        return ventasRegistradas(ventas).stream()
                .map(Venta::calcularTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Venta> ventasPorPeriodo(List<Venta> ventas, LocalDate inicio, LocalDate fin) {
        Objects.requireNonNull(inicio, "La fecha de inicio no puede ser nula");
        Objects.requireNonNull(fin, "La fecha de fin no puede ser nula");
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la inicial");
        }
        return ventasRegistradas(ventas).stream()
                .filter(venta -> !venta.getFecha().toLocalDate().isBefore(inicio))
                .filter(venta -> !venta.getFecha().toLocalDate().isAfter(fin))
                .toList();
    }

    public Map<String, Integer> librosVendidos(List<Venta> ventas) {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        for (Venta venta : ventasRegistradas(ventas)) {
            for (DetalleVenta detalle : venta.getDetalles()) {
                String etiqueta = detalle.getLibro().getTitulo() + " (ISBN: "
                        + detalle.getLibro().getIsbn() + ")";
                resultado.merge(etiqueta, detalle.getCantidad(), Integer::sum);
            }
        }
        return resultado;
    }

    private List<Venta> ventasRegistradas(List<Venta> ventas) {
        Objects.requireNonNull(ventas, "La lista de ventas no puede ser nula");
        return ventas.stream().filter(Venta::isRegistrada).toList();
    }
}
