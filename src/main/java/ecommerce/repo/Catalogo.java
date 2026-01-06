package ecommerce.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ecommerce.model.Producto;

public class Catalogo {
    private final Map<Integer, Producto> productos = new HashMap<>();

    public Catalogo() {
        agregar(new Producto(1, "Cepillo Dental", "Higiene", 1990));
        agregar(new Producto(2, "Pasta Dental", "Higiene", 2490));
        agregar(new Producto(3, "Enjuague Bucal", "Higiene", 3990));
        agregar(new Producto(4, "Hilo Dental", "Higiene", 1590));
        agregar(new Producto(5, "Blanqueamiento", "Tratamiento", 19990));
    }

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>(productos.values());
        lista.sort(Comparator.comparing(Producto::getId));
        return lista;
    }

    public Optional<Producto> buscarPorId(int id) {
        return Optional.ofNullable(productos.get(id));
    }

    public List<Producto> buscarPorNombreOCategoria(String texto) {
        String q = (texto == null) ? "" : texto.trim().toLowerCase();

        List<Producto> res = new ArrayList<>();
        for (Producto p : productos.values()) {
            if (p.getNombre().toLowerCase().contains(q) ||
                p.getCategoria().toLowerCase().contains(q)) {
                res.add(p);
            }
        }
        res.sort(Comparator.comparing(Producto::getNombre));
        return res;
    }

    public void agregar(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("Producto null");
        if (productos.containsKey(producto.getId()))
            throw new IllegalArgumentException("Ya existe un producto con id " + producto.getId());
        productos.put(producto.getId(), producto);
    }

    public boolean eliminar(int id) {
        return productos.remove(id) != null;
    }

    public Map<Integer, Producto> comoMapa() {
        return Collections.unmodifiableMap(productos);
    }
}
