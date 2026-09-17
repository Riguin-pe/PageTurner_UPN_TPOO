package pageturner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Demostración por consola del modelo PageTurner. */
public class PageTurnerApp {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Ana Torres", "71234567", "ana@correo.com");
        Libro javaBasico = new Libro(
                "Java desde cero", "Laura Pérez", "978-612-000-001-1",
                new BigDecimal("45.90"), 2);
        Libro poo = new Libro(
                "Programación Orientada a Objetos", "Carlos Ramos", "978-612-000-002-8",
                new BigDecimal("60.00"), 1);

        Venta venta = new Venta(cliente);
        venta.agregarDetalle(javaBasico, 2);
        venta.agregarDetalle(poo, 1);
        venta.registrar();

        Reserva reserva = new Reserva(cliente, javaBasico);
        Reporte reporte = new Reporte();
        List<Venta> ventas = List.of(venta);

        System.out.println("=== PageTurner: demostración del modelo ===");
        System.out.println("Cliente: " + venta.getCliente());
        System.out.println("Total de la venta: S/ " + venta.calcularTotal());
        System.out.println("Stock de '" + javaBasico.getTitulo() + "': " + javaBasico.getStock());
        System.out.println("Estado de la reserva: " + reserva.getEstado());
        System.out.println("Ingresos: S/ " + reporte.calcularIngresos(ventas));
        System.out.println("Libros vendidos: " + reporte.librosVendidos(ventas));
        System.out.println("Ventas de hoy: "
                + reporte.ventasPorPeriodo(ventas, LocalDate.now(), LocalDate.now()).size());
    }
}
