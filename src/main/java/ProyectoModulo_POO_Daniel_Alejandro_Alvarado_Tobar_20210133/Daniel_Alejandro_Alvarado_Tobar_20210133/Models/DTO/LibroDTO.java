package ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LibroDTO {

    //Aqui encontraremos los campos de la tabla  con su respectivas validaciones, como el que no pueden ser nulos, o cuanto es lo maximo de caracteres, entre otros mas
    private Long id;

    @NotBlank(message = "El titulo no puede ser nulo")
    @Size(max = 200, message = "El titulo no puede sobrepasar los 200 caracteres")
    private String titulo;

    @NotBlank(message = "El isbn no puede ser nulo")
    @Size(max = 20, message = "El isbn no puede sobrepasar los 20 caracteres")
    private String isbn;

    private Long año_publicacion;

    @Size(max = 50, message = "La nacionalidad no puede sobrepasar los 50 caracteres")
    private String genero;

    private Long autor_id;
}
