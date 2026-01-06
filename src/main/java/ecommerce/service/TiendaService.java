package ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ecommerce.discount.ReglaDescuento;
import ecommerce.exception.CantidadInvalidaException;
import ecommerce.exception.ProductoNoExisteException;
import ecommerce.model.Orden;
import ecommerce.model.Producto;
import ecommerce.repo.Catalogo;

public class TiendaService {

    private final Catalogo catalogo;
    private final Map<Integer, Integer> carrito = new HashMap<>();
    private final List<ReglaDescuento> reglas = new ArrayList<>();

    public TiendaService(Catalogo catalogo) {
        if (catalogo == null) {
            throw new IllegalArgumentException("Catálogo no puede ser null");
        }
        this.catalogo = catalogo;
    }

    /* =========================
       CARRITO
       ========================= */

    public Map<Integer, Integer> getCarrito() {
        return Collections.unmodifiableMap(carrito);
    }

    public void agregarAlCarrito(int idProducto, int cantidad) {
        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad debe ser mayor a 0");
        }

        Producto producto = catalogo.buscarPorId(idProducto)
                .orElseThrow(() ->
                        new ProductoNoExisteException("No existe producto con id " + idProducto));

        carrito.merge(producto.getId(), cantidad, Integer::sum);
    }

    public void quitarDelCarrito(int idProducto) {
        if (!carrito.containsKey(idProducto)) {
            throw new ProductoNoExisteException(
                    "El producto con id " + idProducto + " no está en el carrito");
        }
        carrito.remove(idProducto);
    }

    public boolean carritoVacio() {
        return carrito.isEmpty();
    }

    public void vaciarCarrito() {
        carrito.clear();
    }

    /* =========================
       TOTALES
       ========================= */

    public double calcularTotalBase() {
        double total = 0;

        for (Map.Entry<Integer, Integer> entry : carrito.entrySet()) {
            int id = entry.getKey();
            int cantidad = entry.getValue();

            Producto producto = catalogo.buscarPorId(id)
                    .orElseThrow(() ->
                            new ProductoNoExisteException(
                                    "Producto del carrito no existe en catálogo: " + id));

            total += producto.getPrecio() * cantidad;
        }
        return total;
    }

    public String verCarritoDetalle() {
        if (carrito.isEmpty()) {
            return "Carrito vacío.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== CARRITO ===\n");

        for (Map.Entry<Integer, Integer> entry : carrito.entrySet()) {
            Producto p = catalogo.buscarPorId(entry.getKey()).orElseThrow();
            int cantidad = entry.getValue();
            double subtotal = p.getPrecio() * cantidad;

            sb.append(String.format("- %s x%d = $%.0f%n",
                    p.getNombre(), cantidad, subtotal));
        }

        sb.append(String.format("TOTAL BASE: $%.0f%n", calcularTotalBase()));
        return sb.toString();
    }

    /* =========================
       DESCUENTOS
       ========================= */

    public void agregarRegla(ReglaDescuento regla) {
        if (regla != null) {
            reglas.add(regla);
        }
    }

    public List<ReglaDescuento> listarReglas() {
        return List.copyOf(reglas);
    }

    /* =========================
       CONFIRMAR COMPRA
       ========================= */

    public Orden confirmarCompra() {
        if (carrito.isEmpty()) {
            throw new IllegalStateException("No se puede confirmar la compra: carrito vacío");
        }

        double totalBase = calcularTotalBase();
        double totalDescuentos = 0;
        List<String> detalleDescuentos = new ArrayList<>();

        Map<Integer, Producto> catalogoMap = catalogo.comoMapa();

        for (ReglaDescuento regla : reglas) {
            if (regla.aplica(totalBase, carrito, catalogoMap)) {
                double monto = regla.calcularMonto(totalBase, carrito, catalogoMap);
                if (monto > 0) {
                    totalDescuentos += monto;
                    detalleDescuentos.add(
                            regla.nombre() + " (" + regla.condicion() + "): -$"
                                    + String.format("%.0f", monto)
                    );
                }
            }
        }

        double totalFinal = totalBase - totalDescuentos;

        Orden orden = new Orden(
                LocalDateTime.now(),
                Map.copyOf(carrito),
                totalBase,
                List.copyOf(detalleDescuentos),
                totalFinal
        );

        vaciarCarrito();
        return orden;
    }
}
