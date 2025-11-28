package excepcion;

public class NotaInvalidaRuntimeException extends RuntimeException {
    public NotaInvalidaRuntimeException(String mensaje) {
        super(mensaje);
    }
}
