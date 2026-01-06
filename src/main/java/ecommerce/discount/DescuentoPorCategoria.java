package ecommerce.discount;

import java.util.Map;
import ecommerce.model.Producto;

public class DescuentoPorCategoria implements ReglaDescuento {

    private final String categoria;
    private final double porcentaje;

    public DescuentoPorCategoria(String categoria, double porcentaje) {
        this.categoria = categoria;
        this.porcentaje = porcentaje;
    }

    @Override
    public String nombre() {
        return "Descuento por categoría";
    }

    @Override
    public String condicion() {
        return String.format("Si el carrito contiene categoría '%s', descuenta %.0f%% sobre totalBase",
                categoria, porcentaje * 100);
    }

    @Override
    public boolean aplica(double totalBase, Map<Integer, Integer> carrito, Map<Integer, Producto> catalogo) {
        for (Integer id : carrito.keySet()) {
            Producto p = catalogo.get(id);
            if (p != null && p.getCategoria().equalsIgnoreCase(categoria)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double calcularMonto(double totalBase, Map<Integer, Integer> carrito, Map<Integer, Producto> catalogo) {
        return totalBase * porcentaje;
    }
}
