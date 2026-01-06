package ecommerce.discount;

import java.util.Map;
import ecommerce.model.Producto;

public interface ReglaDescuento {
    String nombre();
    String condicion();

    boolean aplica(double totalBase, Map<Integer, Integer> carrito, Map<Integer, Producto> catalogo);
    double calcularMonto(double totalBase, Map<Integer, Integer> carrito, Map<Integer, Producto> catalogo);
}
