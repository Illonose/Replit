package excepcion;

public class ErrorFicheroNotasException extends Exception {
    public ErrorFicheroNotasException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public ErrorFicheroNotasException(String mensaje) {
        super(mensaje);
    }
}