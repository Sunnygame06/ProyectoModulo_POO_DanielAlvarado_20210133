package ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Exceptions;

public class ExceptionDatoNoEncontrado extends RuntimeException {

    public ExceptionDatoNoEncontrado(String message) {
        super(message);
    }

    public ExceptionDatoNoEncontrado(String message, Throwable cause){
        super(message, cause);
    }

    public ExceptionDatoNoEncontrado(Throwable cause){
        super(cause);
    }
}
