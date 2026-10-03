# PageTurner

PageTurner es un modelo de dominio desarrollado en Java para gestionar las
operaciones básicas de una librería académica. El proyecto aplica conceptos de
programación orientada a objetos mediante clientes, libros, ventas, reservas y
reportes.

## Funcionalidades

- Registro de clientes y libros.
- Control y actualización del stock disponible.
- Creación de ventas con uno o varios libros.
- Cálculo automático de subtotales y del total de cada venta.
- Validación y descuento de stock al registrar una venta.
- Reserva de libros agotados y gestión de su estado.
- Reportes de ingresos, ventas por periodo y unidades vendidas por libro.

## Tecnologías y requisitos

- Java 21 o una versión compatible.
- Una terminal con `javac` y `java` disponibles en el `PATH`.

El proyecto no utiliza dependencias externas ni requiere Maven o Gradle.

## Estructura del proyecto

```text
PageTurner/
├── docs/
│   ├── README.md             # Índice de documentación
│   ├── diagrama-clases.md    # Modelo UML y decisiones de diseño
│   └── flujos-negocio.md     # Secuencias y estados del dominio
├── src/
│   └── pageturner/
│       ├── Cliente.java
│       ├── DetalleVenta.java
│       ├── Libro.java
│       ├── PageTurnerApp.java
│       ├── Reporte.java
│       ├── Reserva.java
│       └── Venta.java
└── README.md
```

## Instalación y ejecución

1. Clona el repositorio y entra en su directorio:

   ```bash
   git clone https://github.com/Riguin-pe/PageTurner_UPN_TPOO.git
   cd PageTurner_UPN_TPOO
   ```

2. Compila el código fuente desde la raíz del proyecto.

   En PowerShell:

   ```powershell
   New-Item -ItemType Directory -Force out | Out-Null
   javac -d out (Get-ChildItem src\pageturner\*.java)
   ```

   En Bash:

   ```bash
   mkdir -p out
   javac -d out src/pageturner/*.java
   ```

3. Ejecuta la demostración:

   ```bash
   java -cp out pageturner.PageTurnerApp
   ```

La aplicación crea datos de ejemplo, registra una venta, genera una reserva y
muestra los resultados de los reportes en la consola.

## Ejemplo de salida

```text
=== PageTurner: demostración del modelo ===
Cliente: Ana Torres (DNI: 71234567)
Total de la venta: S/ 151.80
Stock de 'Java desde cero': 0
Estado de la reserva: PENDIENTE
Ingresos: S/ 151.80
Libros vendidos: {Java desde cero (ISBN: 978-612-000-001-1)=2, Programación Orientada a Objetos (ISBN: 978-612-000-002-8)=1}
Ventas de hoy: 1
```

## Reglas de negocio principales

- Un libro no puede tener precio ni stock negativos.
- Una venta debe contener al menos un detalle antes de registrarse.
- La venta solo se registra si existe stock suficiente para todos sus libros.
- Al registrar una venta, el stock se descuenta automáticamente.
- Una venta registrada ya no puede modificarse ni registrarse nuevamente.
- Solo se puede reservar un libro cuando no tiene stock.
- Una reserva solo puede confirmarse cuando el libro vuelve a estar disponible.
- Los reportes consideran únicamente las ventas registradas.

## Documentación

- El [índice de documentación](docs/README.md) propone una ruta de lectura.
- El [diagrama de clases](docs/diagrama-clases.md) explica la estructura, las
  responsabilidades y las relaciones del modelo.
- Los [flujos de negocio](docs/flujos-negocio.md) muestran cómo se registran las
  ventas, cómo cambia una reserva y cómo se generan los reportes.

## Conceptos de POO aplicados

- **Encapsulamiento:** los atributos se mantienen privados y se exponen mediante
  operaciones controladas.
- **Composición:** una venta contiene sus detalles de venta.
- **Asociación:** clientes, libros, ventas y reservas colaboran dentro del modelo.
- **Responsabilidad única:** cada clase representa una entidad o tarea concreta
  del dominio.

## Estado actual

Los datos se mantienen en memoria y `PageTurnerApp` funciona como una
demostración por consola. El proyecto no incluye todavía persistencia en base de
datos, interfaz gráfica ni pruebas automatizadas.
