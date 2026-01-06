package ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ecommerce.discount.DescuentoPorCategoria;
import ecommerce.discount.DescuentoPorMonto;
import ecommerce.exception.CantidadInvalidaException;
import ecommerce.model.Orden;
import ecommerce.repo.Catalogo;

public class TiendaServiceTest {

    private TiendaService tienda;

    @BeforeEach
    void setUp() {
        Catalogo catalogo = new Catalogo();
        tienda = new TiendaService(catalogo);

        // reglas activas
        tienda.agregarRegla(new DescuentoPorMonto(15000, 0.10));
        tienda.agregarRegla(new DescuentoPorCategoria("Higiene", 0.05));
    }

    // ✅ TEST 1: cálculo total carrito
    @Test
    void calculaTotalBaseCorrectamente() {
        tienda.agregarAlCarrito(1, 2); // 2 x 1990
        tienda.agregarAlCarrito(2, 1); // 1 x 2490

        double total = tienda.calcularTotalBase();
        assertEquals(1990 * 2 + 2490, total);
    }

    // ✅ TEST 2: validación cantidad
    @Test
    void lanzaExcepcionSiCantidadInvalida() {
        assertThrows(CantidadInvalidaException.class, () -> {
            tienda.agregarAlCarrito(1, 0);
        });
    }

    // ✅ TEST 3: aplicación descuentos al confirmar
    @Test
    void aplicaDescuentosAlConfirmarCompra() {
        tienda.agregarAlCarrito(1, 4); // 4 x 1990 = 7960 
        tienda.agregarAlCarrito(3, 2); // 2 x 3990 = 7980 
        // totalBase = 15940 (>=15000) 

        Orden orden = tienda.confirmarCompra();

        assertTrue(orden.getTotalBase() >= 15000);
        assertFalse(orden.getDescuentosAplicados().isEmpty());
        assertTrue(orden.getTotalFinal() < orden.getTotalBase());
    }
}
