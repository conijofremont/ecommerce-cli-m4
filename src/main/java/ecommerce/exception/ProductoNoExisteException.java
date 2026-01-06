package ecommerce.exception;

public class ProductoNoExisteException extends RuntimeException {
    public ProductoNoExisteException(String mensaje) {
        super(mensaje);
    }
}
