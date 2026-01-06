package ecommerce.app;

import java.util.List;
import java.util.Scanner;

import ecommerce.model.Producto;
import ecommerce.repo.Catalogo;
import ecommerce.service.TiendaService;

public class ConsolaUI {

    private final Scanner sc = new Scanner(System.in);
    private final Catalogo catalogo;
    private final TiendaService tienda;

    public ConsolaUI(Catalogo catalogo, TiendaService tienda) {
        this.catalogo = catalogo;
        this.tienda = tienda;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione opción: ");

            try {
                switch (opcion) {
                    case 1 -> listarProductos();
                    case 2 -> buscarProductos();
                    case 3 -> agregarAlCarrito();
                    case 4 -> quitarDelCarrito();
                    case 5 -> verCarrito();
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.out.println("⚠ " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("""
                \n=== MENÚ USUARIO ===
                1) Listar productos
                2) Buscar productos
                3) Agregar al carrito
                4) Quitar del carrito
                5) Ver carrito
                0) Volver / Salir
                """);
    }

    private void listarProductos() {
        List<Producto> productos = catalogo.listar();
        productos.forEach(System.out::println);
    }

    private void buscarProductos() {
        System.out.print("Buscar (nombre o categoría): ");
        String texto = sc.nextLine();
        List<Producto> res = catalogo.buscarPorNombreOCategoria(texto);

        if (res.isEmpty()) {
            System.out.println("No se encontraron productos.");
        } else {
            res.forEach(System.out::println);
        }
    }

    private void agregarAlCarrito() {
        int id = leerEntero("ID producto: ");
        int cantidad = leerEntero("Cantidad: ");
        tienda.agregarAlCarrito(id, cantidad);
        System.out.println("Producto agregado al carrito.");
    }

    private void quitarDelCarrito() {
        int id = leerEntero("ID producto a quitar: ");
        tienda.quitarDelCarrito(id);
        System.out.println("Producto quitado del carrito.");
    }

    private void verCarrito() {
        System.out.println(tienda.verCarritoDetalle());
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }
}
