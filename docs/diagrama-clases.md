# Diagrama de clases UML — PageTurner

Este diagrama representa el modelo orientado a objetos de la librería académica
PageTurner. Las clases Java se encuentran en `src/pageturner`.

```mermaid
classDiagram
    class Cliente {
        -String nombre
        -String dni
        -String correo
        +Cliente(nombre, dni, correo)
        +getNombre() String
        +getDni() String
        +getCorreo() String
    }

    class Libro {
        -String titulo
        -String autor
        -String isbn
        -BigDecimal precio
        -int stock
        +hayStock() boolean
        +hayStock(cantidad) boolean
        +descontarStock(cantidad) void
        +aumentarStock(cantidad) void
    }

    class Venta {
        -Cliente cliente
        -LocalDateTime fecha
        -List~DetalleVenta~ detalles
        -boolean registrada
        +agregarDetalle(libro, cantidad) void
        +calcularTotal() BigDecimal
        +registrar() void
    }

    class DetalleVenta {
        -Libro libro
        -int cantidad
        -BigDecimal precioUnitario
        +calcularSubtotal() BigDecimal
    }

    class Reserva {
        -Cliente cliente
        -Libro libro
        -LocalDateTime fecha
        -String estado
        +confirmar() void
        +cancelar() void
    }

    class Reporte {
        +calcularIngresos(ventas) BigDecimal
        +ventasPorPeriodo(ventas, inicio, fin) List~Venta~
        +librosVendidos(ventas) Map~String, Integer~
    }

    Cliente "1" --> "0..*" Venta : realiza
    Cliente "1" --> "0..*" Reserva : solicita
    Venta "1" *-- "1..*" DetalleVenta : contiene
    DetalleVenta "0..*" --> "1" Libro : corresponde a
    Reserva "0..*" --> "1" Libro : reserva
    Reporte ..> Venta : analiza
```

## Reglas del modelo

- Una venta se registra solo si todos sus libros tienen stock suficiente; al
  registrarse, el stock se descuenta automáticamente.
- Una reserva se crea únicamente para un libro cuyo stock sea cero.
- Los reportes usan las ventas registradas que se mantienen en memoria.
