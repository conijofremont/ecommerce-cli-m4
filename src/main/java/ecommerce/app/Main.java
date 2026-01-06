package ecommerce.app;

import ecommerce.repo.Catalogo;
import ecommerce.service.TiendaService;

public class Main {
    public static void main(String[] args) {
        Catalogo catalogo = new Catalogo();
        TiendaService tienda = new TiendaService(catalogo);

        ConsolaUI ui = new ConsolaUI(catalogo, tienda);
        ui.iniciar();
    }
}
