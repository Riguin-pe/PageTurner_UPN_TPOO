# Flujos de negocio de PageTurner

Este documento complementa el [diagrama de clases](diagrama-clases.md) mostrando
cómo colaboran los objetos durante las operaciones principales.

## Registro de una venta

```mermaid
sequenceDiagram
    actor Usuario
    participant V as Venta
    participant D as DetalleVenta
    participant L as Libro

    Usuario->>V: agregarDetalle(libro, cantidad)
    V->>D: crear(libro, cantidad)
    D->>L: getPrecio()
    L-->>D: precio vigente
    D-->>V: detalle creado

    Usuario->>V: registrar()
    V->>V: agrupar cantidades por libro
    loop Por cada libro
        V->>L: hayStock(cantidad acumulada)
        L-->>V: disponible / insuficiente
    end

    alt Todos tienen stock
        loop Por cada libro
            V->>L: descontarStock(cantidad acumulada)
        end
        V->>V: registrada = true
        V-->>Usuario: venta registrada
    else Algún libro no tiene stock
        V-->>Usuario: IllegalStateException
    end
```

La validación se completa para todos los libros antes de descontar unidades. Si
alguno no dispone de la cantidad requerida, el inventario permanece intacto.

## Ciclo de una venta

```mermaid
stateDiagram-v2
    [*] --> EnPreparacion: new Venta(cliente)
    EnPreparacion --> EnPreparacion: agregarDetalle()
    EnPreparacion --> Registrada: registrar() válido
    EnPreparacion --> EnPreparacion: registrar() inválido
    Registrada --> Registrada: cambios rechazados
```

Una venta en preparación admite detalles. Después de registrarse queda cerrada:
ya no se pueden agregar productos ni volver a ejecutar `registrar()`.

## Creación y gestión de una reserva

```mermaid
sequenceDiagram
    actor Usuario
    participant R as Reserva
    participant L as Libro

    Usuario->>R: crear(cliente, libro)
    R->>L: hayStock()

    alt El libro está agotado
        R->>R: estado = PENDIENTE
        R-->>Usuario: reserva creada
    else El libro tiene stock
        R-->>Usuario: IllegalStateException
    end

    opt El inventario se repone
        Usuario->>L: aumentarStock(cantidad)
        Usuario->>R: confirmar()
        R->>L: hayStock()
        L-->>R: true
        R->>R: estado = CONFIRMADA
    end
```

## Estados de una reserva

```mermaid
stateDiagram-v2
    [*] --> PENDIENTE: libro sin stock
    PENDIENTE --> CONFIRMADA: confirmar() con stock
    PENDIENTE --> CANCELADA: cancelar()
```

El modelo actual no impide invocar `cancelar()` o `confirmar()` desde un estado
final. El diagrama representa el uso esperado; endurecer estas transiciones sería
una posible mejora futura.

## Generación de reportes

```mermaid
flowchart LR
    A[Lista recibida] --> B{¿Venta registrada?}
    B -- No --> C[Ignorar venta]
    B -- Sí --> D[Incluir venta]
    D --> E{Operación solicitada}
    E --> F[Sumar ingresos]
    E --> G[Filtrar por fechas]
    E --> H[Agrupar unidades por libro]
```

Las tres consultas de `Reporte` trabajan sobre la lista que reciben y no
modifican los objetos originales:

- `calcularIngresos()` suma el total de cada venta registrada.
- `ventasPorPeriodo()` incluye ambas fechas límite.
- `librosVendidos()` agrupa cantidades usando el título y el ISBN como etiqueta.

## Escenarios de error esperados

| Operación | Condición inválida | Resultado |
|---|---|---|
| Crear un libro | Precio o stock negativo | `IllegalArgumentException` |
| Agregar un detalle | Cantidad igual o menor que cero | `IllegalArgumentException` |
| Modificar una venta | La venta ya está registrada | `IllegalStateException` |
| Registrar una venta | No tiene detalles | `IllegalStateException` |
| Registrar una venta | Stock insuficiente | `IllegalStateException` |
| Crear una reserva | El libro todavía tiene stock | `IllegalStateException` |
| Confirmar una reserva | El libro sigue agotado | `IllegalStateException` |
| Consultar un periodo | La fecha final precede a la inicial | `IllegalArgumentException` |
