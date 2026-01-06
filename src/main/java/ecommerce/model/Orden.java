package ecommerce.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Orden {
    private final LocalDateTime fecha;
    private final Map<Integer, Integer> items; // snapshot
    private final double totalBase;
    private final List<String> descuentosAplicados; // detalle
    private final double totalFinal;

    public Orden(LocalDateTime fecha, Map<Integer, Integer> items, double totalBase,
                List<String> descuentosAplicados, double totalFinal) {
        this.fecha = fecha;
        this.items = items;
        this.totalBase = totalBase;
        this.descuentosAplicados = descuentosAplicados;
        this.totalFinal = totalFinal;
    }

    public LocalDateTime getFecha() { return fecha; }
    public Map<Integer, Integer> getItems() { return items; }
    public double getTotalBase() { return totalBase; }
    public List<String> getDescuentosAplicados() { return descuentosAplicados; }
    public double getTotalFinal() { return totalFinal; }
}
