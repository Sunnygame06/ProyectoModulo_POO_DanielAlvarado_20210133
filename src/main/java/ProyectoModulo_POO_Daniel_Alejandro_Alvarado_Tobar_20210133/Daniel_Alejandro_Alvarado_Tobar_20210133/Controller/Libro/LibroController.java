package ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Controller.Libro;

import ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Entity.Libro.LibroEntity;
import ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Exceptions.ExceptionDatoNoEncontrado;
import ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Exceptions.ExceptionDatosDuplicados;
import ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Models.DTO.LibroDTO;
import ProyectoModulo_POO_Daniel_Alejandro_Alvarado_Tobar_20210133.Daniel_Alejandro_Alvarado_Tobar_20210133.Service.Libro.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiLibro")
public class LibroController {

    @Autowired
    private LibroService service;

    //Aqui se manda a llamar desde el service para obtener todos los datos, y utilizamos la anotacion @GetMapping para crear una Url que nos ayudara a mostrar todos los libros
    @GetMapping("/getDataLibro")
    public List<LibroDTO> getData(){
        return service.getAllLibros();
    }

    //Aqui mandamos a llamar el libro mediante su ID, caso no se encuentra lanzara un error de que no se ha encontrado, y si lo encuentra mostrara el libro en cuestion
    @GetMapping("/getDataLibro/{id}")
    public ResponseEntity<?> Buscar (@PathVariable Long id){
        try{
            LibroEntity libro = service.SearchById(id);
            return  ResponseEntity.ok(libro);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                            "X-Mensaje-Error", "Libro no encontrado")
                    .body(Map.of(
                            "error","Not found",
                            "mensaje", "El libro no ha sido encontrado",
                            "timestamp", Instant.now().toString()
                    ));
        }
    }

    //Aqui se manda a llamar desde el DTO los campos que se requieren validar, caso las respuestas sean nulas se enviara el error de las lineas 40, 41 y 42, en el caso
    //todo haya salido bien nos enviara success, caso haya un error interno, es decir en la base de datos, habra un error.
    @PostMapping("/createLibro")
    public ResponseEntity<Map<String, Object>> crear (@Valid @RequestBody LibroDTO libro){
        try{
            LibroDTO respuesta = service.create(libro);
            if(respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                   "status", "Insercion incorrecta",
                   "errorType", "VALIDATION_ERROR",
                   "message", "Datos del usuario invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED). body(Map.of(
                    "status", "success",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar",
                    "detail", e.getMessage()
            ));
        }
    }

    //Aqui mandamos a llamar al libro que deseamos actualizar por medio de su ID, caso todo este bien nos lanzara el libro actualizado, caso contrario nos dira que hubo un error
    @PutMapping("/updateLibro/{id}")
    public ResponseEntity <?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody LibroDTO libro,
            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            LibroDTO libroActualizado = service.update(id, libro);
            return  ResponseEntity.ok(libroActualizado);
        }catch (ExceptionDatoNoEncontrado e){
            return ResponseEntity.notFound().build();
        }catch (ExceptionDatosDuplicados e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos duplicados",
                    "campo", e.getCampoDuplicado()
            ));
        }
    }

    //Aqui mandamos a llamar al libro que deseamos eliminar por medio de su ID, lo primero que hara es buscar el ID, si lo encuentra se eliminara, caso contrario lanzara un error ya sea interno del sistema o que el dato no existe
    @DeleteMapping("/deleteLibro/{id}")
    public ResponseEntity<Map<String, Object>> eliminar (@PathVariable Long id){
        try{
            if(!service.delete(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                        "X-Mensaje-Error", "Libro no encontrado")
                        .body(Map.of(
                                "error","Not found",
                                "mensaje", "El libro no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Libro eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el libro",
                    "detail", e.getMessage()
            ));
        }
    }
}
