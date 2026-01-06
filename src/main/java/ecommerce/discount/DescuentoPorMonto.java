package ecommerce.discount;

import java.util.Map;
import ecommerce.model.Producto;

public class DescuentoPorMonto implements ReglaDescuento {

    private final double minimo;
    private final double porcentaje; // ej 0.10 = 10%

    public DescuentoPorMonto(double minimo, double porcentaje) {
        this.minimo = minimo;
        this.porcentaje = porcentaje;
    }

    @Override
    public String nombre() {
        return "Descuento por monto";
    }

    @Override
    public String condicion() {
        return String.format("Si totalBase >= %.0f, descuenta %.0f%%", minimo, porcentaje * 100);
    }

    @Override
    public boolean aplica(double totalBase, Map<Integer, Integer> carrito, Map<Integer, Producto> catalogo) {
        return totalBase >= minimo;
    }

    @Override
    public double calcularMonto(double totalBase, Map<Integer, Integer> carrito, Map<Integer, Producto> catalogo) {
        return totalBase * porcentaje;
    }
}
