# Ecommerce CLI - Módulo 4

Aplicación de consola en Java para la gestión de un e-commerce simple, con flujos de **Usuario**, carrito de compras y **descuentos automáticos**, desarrollada con **POO, colecciones, excepciones y tests unitarios**.

---

## Tecnologías
- Java 21
- Maven
- JUnit 5
- Eclipse IDE

---

## Ejecución
Desde Eclipse:
- Ejecutar la clase `Main` como **Java Application**

Desde consola:
```bash
mvn clean test

🧭 Menú Usuario
## Menú Usuario

1. Listar productos  
2. Buscar productos  
3. Agregar al carrito  
4. Quitar del carrito  
5. Ver carrito  
6. Ver descuentos activos  
7. Confirmar compra  
0. Salir

💸 Descuentos automáticos
## Descuentos automáticos

- **Descuento por monto**: 10% si el total base es mayor o igual a $15.000  
- **Descuento por categoría**: 5% si el carrito contiene productos de la categoría *Higiene*

Los descuentos se aplican automáticamente al confirmar la compra, sin intervención del usuario.

🛒 Ejemplo de compra
## Ejemplo de compra

1. El usuario lista los productos disponibles  
2. Agrega productos al carrito  
3. Visualiza los descuentos activos  
4. Confirma la compra  
5. El sistema muestra:
   - Total base
   - Descuentos aplicados
   - Total final
6. El carrito se vacía automáticamente

🧪 Tests unitarios
## Tests unitarios

El proyecto incluye 3 tests unitarios con JUnit 5:

- Cálculo del total del carrito  
- Validación de cantidad inválida  
- Aplicación de descuentos al confirmar la compra  

Todos los tests se ejecutan correctamente con:

```bash
mvn test

## Repositorio GitHub

Repositorio público del proyecto:  
https://github.com/conijofremont/ecommerce-cli-m4

