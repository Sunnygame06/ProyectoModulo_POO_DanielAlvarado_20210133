package ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Exceptions;

import lombok.Getter;

public class ExceptionDatosDuplicados extends RuntimeException {

    @Getter
    private String campoDuplicado;

    public ExceptionDatosDuplicados(String message, String campoDuplicado){
        super(message);
        this.campoDuplicado = campoDuplicado;
    }

    public ExceptionDatosDuplicados(String message) {
        super(message);
    }
}
